package com.practica;

import com.fasterxml.jackson.annotation.JsonView;
import com.practica.model.Match;
import com.practica.model.Team;
import com.practica.model.Tournament;
import com.practica.security.UserComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/api")
public class TournamentSheetRestController {

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private UserComponent userComponent;

    static int j;

    @PutMapping("/TenniShip/ADMIN/Tournament/{tournament}/EditMatches/{group}/Submission")
    public ResponseEntity<Tournament> submitMatchEdited(@PathVariable String tournament, @RequestParam String teamHome,
                                    @PathVariable String group, @RequestParam String teamAway, @RequestParam int quantityHome,
                                    @RequestParam int quantityAway, HttpServletRequest request) {

        if (quantityHome == 3 ^ quantityAway == 3) { // XOR
            Optional<Team> home = teamRepository.findById(teamHome);
            Optional<Team> away = teamRepository.findById(teamAway);
            Optional<Tournament> t = tournamentRepository.findById(tournament);

            if (t.isPresent()) {
                Match match = matchRepository.findMatch(home.get(), away.get(), t.get()).get(0);

                matchRepository.getOne(match.getId()).setHomePoints(quantityHome);
                matchRepository.getOne(match.getId()).setAwayPoints(quantityAway);

                matchRepository.save(match);

                return new ResponseEntity<>(t.get(), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public static class AuxEdit {
        interface Basic{}

        @JsonView(Basic.class)
        Boolean admin;
        @JsonView(Basic.class)
        String round;
        @JsonView(Basic.class)
        List<Match> matches;

        public AuxEdit(Boolean admin, String round, List<Match> matches) {
            this.admin = admin;
            this.round = round;
            this.matches = matches;
        }
    }

    interface editMatch extends AuxEdit.Basic, Match.Basic {}

    @JsonView(editMatch.class)
    @GetMapping("/TenniShip/ADMIN/Tournament/{tournament}/EditMatches/{group}")
    public ResponseEntity<AuxEdit> editMatches(@PathVariable String tournament, @PathVariable String group, HttpServletRequest request) {
        if (userComponent.isLoggedUser() && request.isUserInRole("ADMIN")) {
            Optional<Tournament> t = tournamentRepository.findById(tournament);

            if (t.isPresent()) {
                AuxEdit aux = new AuxEdit(true, "Group Stage", tournamentRepository.getPhaseMatches(t.get(), group));
                return new ResponseEntity<>(aux, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/TenniShip/ADMIN/Tournament/{tournament}/Deleted")
    public ResponseEntity<Tournament> deleteTournament(@PathVariable String tournament, HttpServletRequest request) {

        if (userComponent.isLoggedUser() && request.isUserInRole("ADMIN")) {
            Optional<Tournament> t = tournamentRepository.findById(tournament);

            if (t.isPresent()) {
                for (Match m : tournamentRepository.getMatches(t.get())) {
                    matchRepository.delete(m);
                }
                tournamentRepository.delete(t.get());
                return new ResponseEntity<>(t.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    private static class AuxiliarClass implements Comparable<AuxiliarClass> {
        interface Basic{}

        @JsonView(TournamentSheetToReturn.Basic.class)
        private Team t;
        @JsonView(TournamentSheetToReturn.Basic.class)
        private Integer matchesWon; // Spain 3 - 2 France
        @JsonView(TournamentSheetToReturn.Basic.class)
        private Integer pointsWon; // Spain matchesWon += 1, Spain pointsWon += 3

        public AuxiliarClass(Team team, int matchesWon, int pointsWon) {
            this.t = team;
            this.matchesWon = matchesWon;
            this.pointsWon = pointsWon;
        }

        @Override
        public int compareTo(AuxiliarClass p2) {
            int dif = Integer.compare(p2.matchesWon, this.matchesWon);
            if (dif != 0)
                return dif;
            else
                return Integer.compare(p2.pointsWon, this.pointsWon);
        }
    }

    private static class TournamentSheetToReturn {
        interface Basic{}

        @JsonView(Basic.class)
        private Tournament tournament;
        @JsonView(Basic.class)
        private ArrayList<AuxiliarClass>[] groups;
        @JsonView(Basic.class)
        private List<Match> last4Matches = new ArrayList<>();
        @JsonView(Basic.class)
        private List<Match> last2Matches = new ArrayList<>();
        @JsonView(Basic.class)
        private List<Match> lastMatch = new ArrayList<>();
        @JsonView(Basic.class)
        private double completion;

        public TournamentSheetToReturn() {

        }

        public TournamentSheetToReturn(Tournament t, ArrayList<AuxiliarClass>[] groups, List<Match> last4Matches, List<Match> last2Matches, List<Match> lastMatch, double completion) {
            this.tournament = t;
            this.groups = groups;
            /* If the class is static, calls to tournamentRepository have to be made outside */
            this.last4Matches = last4Matches;
            this.last2Matches = last2Matches;
            this.lastMatch = lastMatch;
            this.completion = completion;
        }
    }

    interface tournamentSheet extends  Match.Basic, TournamentSheetToReturn.Basic, AuxiliarClass.Basic, Tournament.Basic {}

    @JsonView(tournamentSheet.class)
    @GetMapping("/TenniShip/Tournament/{tournament}")
    public ResponseEntity<TournamentSheetToReturn> tournament(@PathVariable String tournament, HttpServletRequest request) {

        Optional<Tournament> t = tournamentRepository.findById(tournament);


        if (t.isPresent()) {

			/*if (Objects.nonNull(t.get().hasImage()) && t.get().hasImage()) {
				model.addAttribute("hasImage", true);
			}*/

            // GROUPS-------
            String[] groups = { "A", "B", "C", "D", "E", "F" };
            @SuppressWarnings("unchecked")
            ArrayList<AuxiliarClass>[] sortedGroups = new ArrayList[6];
            for (int i = 0; i < 6; i++) {
                sortedGroups[i] = new ArrayList<>();
            }

            for (int i = 0; i < 6; i++) {
                tournamentRepository.getPhaseTeams(t.get(), groups[i]).forEach(tm -> {
                    sortedGroups[j].add(new AuxiliarClass(tm, teamRepository.getWonGroupMatches(t.get(), tm, groups[j]),
                            teamRepository.getWonGroupPointsPlayingHome(t.get(), tm, groups[j])
                                    + teamRepository.getWonGroupPointsPlayingAway(t.get(), tm, groups[j])));
                });
                Collections.sort(sortedGroups[j]);

                j++;
            }
            j = 0;
            // -------GROUPS

            // FINAL PHASE--

            List<Team> last8 = new ArrayList<>();
            List<Team> last4 = new ArrayList<>();
            List<Team> last2 = new ArrayList<>();

            if (tournamentRepository.getPlayedMatchesJQL(t.get()) >= 18) { // Groups Played
                if (tournamentRepository.getPhaseTeams(t.get(), "X").isEmpty()) { // RoundOf8 has to be created
                    List<AuxiliarClass> secondPlaceTeams = new ArrayList<>();
                    secondPlaceTeams.add(sortedGroups[0].get(1));
                    secondPlaceTeams.add(sortedGroups[1].get(1));
                    secondPlaceTeams.add(sortedGroups[2].get(1));
                    secondPlaceTeams.add(sortedGroups[3].get(1));
                    secondPlaceTeams.add(sortedGroups[4].get(1));
                    secondPlaceTeams.add(sortedGroups[5].get(1));
                    Collections.sort(secondPlaceTeams);

                    last8.add(sortedGroups[0].get(0).t);
                    last8.add(sortedGroups[1].get(0).t);
                    last8.add(sortedGroups[2].get(0).t);
                    last8.add(sortedGroups[3].get(0).t);
                    last8.add(sortedGroups[4].get(0).t);
                    last8.add(sortedGroups[5].get(0).t);
                    last8.add(secondPlaceTeams.get(0).t);
                    last8.add(secondPlaceTeams.get(1).t);
                    Collections.shuffle(last8);

                    createMatches(last8, "X", t.get());
                }
            }

            if (tournamentRepository.getPlayedMatchesJQL(t.get()) >= 22) { // RoundOf8 Played
                if (tournamentRepository.getPhaseTeams(t.get(), "Y").isEmpty()) { // RoundOf4 has to be created
                    tournamentRepository.getPhaseMatches(t.get(), "X").forEach(Match -> {
                        if (Match.getHomePoints() > Match.getAwayPoints())
                            last4.add(Match.getTeam1());
                        else
                            last4.add(Match.getTeam2());
                    });

                    createMatches(last4, "Y", t.get());
                }
            }
            if (tournamentRepository.getPlayedMatchesJQL(t.get()) >= 24) { // RoundOf4 Played
                if (tournamentRepository.getPhaseTeams(t.get(), "Z").isEmpty()) { // RoundOf2 has to be created
                    tournamentRepository.getPhaseMatches(t.get(), "Y").forEach(Match -> {
                        if (Match.getHomePoints() > Match.getAwayPoints())
                            last2.add(Match.getTeam1());
                        else
                            last2.add(Match.getTeam2());
                    });

                    createMatches(last2, "Z", t.get());
                }
            }

            // --FINAL PHASE

            double progressPercentage;
            final int TOTAL_MATCHES = 25;
            progressPercentage = tournamentRepository.getPlayedMatches(t.get().getName());
            progressPercentage = (progressPercentage / TOTAL_MATCHES) * 100;

            /* Calls to tournamentRepository have to be made outside of the static auxiliar class */
            TournamentSheetToReturn obtectToReturn = new TournamentSheetToReturn(t.get(), sortedGroups, tournamentRepository.getPhaseMatches(t.get(), "X"),
                    tournamentRepository.getPhaseMatches(t.get(), "Y"), tournamentRepository.getPhaseMatches(t.get(), "Z"), progressPercentage);

            return new ResponseEntity<>(obtectToReturn, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    private void createMatches (List<Team> lastn, String phase, Tournament t) {
        for (int i = 0; i < lastn.size(); i+=2) {
            Match a = new Match(0, 0, phase);
            a.setTeam1(lastn.get(i));
            a.setTeam2(lastn.get(i+1));
            a.setTournament(t);
            matchRepository.save(a);
        }
    }

}
