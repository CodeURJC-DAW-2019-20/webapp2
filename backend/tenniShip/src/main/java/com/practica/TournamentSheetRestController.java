package com.practica;

import com.practica.model.Match;
import com.practica.model.Team;
import com.practica.model.Tournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    static int j;

    public static class AuxEdit {
        Boolean admin;
        String round;
        List<Match> matches;

        public AuxEdit(Boolean admin, String round, List<Match> matches) {
            this.admin = admin;
            this.round = round;
            this.matches = matches;
        }
    }

    @GetMapping("/TenniShip/ADMIN/Tournament/{tournament}/EditMatches/{group}")
    public ResponseEntity<AuxEdit> editMatches(@PathVariable String tournament, @PathVariable String group) {

        Optional<Tournament> t = tournamentRepository.findById(tournament);

        if (t.isPresent()) {
            AuxEdit aux = new AuxEdit(true,"Group Stage", tournamentRepository.getPhaseMatches(t.get(), group));
            return  new ResponseEntity<>(aux, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);  // Not sure if this is the right one
    }

    private static class AuxiliarClass implements Comparable<AuxiliarClass> {
        private Team t;
        private Integer matchesWon; // Spain 3 - 2 France
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


    public static class TeamInGroup {
        private String name;
        private int matchesWon;
        private int pointsWon;

        public TeamInGroup(String name, int matchesWon, int pointsWon) {
            this.name = name;
            this.matchesWon = matchesWon;
            this.pointsWon = pointsWon;
        }
    }

    private static class TeamClassif {
        private String name;
        private int pointsWon;

        public TeamClassif(String name, int pointsWon) {
            this.name = name;
            this.pointsWon = pointsWon;
        }
    }

    public static class Group {
        private List<TeamInGroup> teams;

        public List<TeamInGroup> getTeams() {
            return teams;
        }
    }

    public static class Classif {
        private TeamClassif local;
        private TeamClassif away;

        public Classif(TeamClassif local, TeamClassif away) {
            this.local = local;
            this.away = away;
        }

        public Classif() {
            this.local = new TeamClassif("Keep Playing", 0);
            this.away = new TeamClassif("Keep Playing", 0);
        }
    }

    public static class Final {
        private List<TeamClassif> teams;

        public List<TeamClassif> getTeams() {
            return teams;
        }
    }

    public static class TournamentSheet {
        private Boolean found;
        private Boolean hasImage;
        private List<Group> groups;
        private List<Classif> quarters;
        private List<Classif> semis;
        private Optional<Classif> finals;
        private String tournamentName;
        private double completion;
        private List<String> results;

        public TournamentSheet(Boolean hasImage, List<Group> groups, List<Classif> quarters,
                               List<Classif> semis, Optional<Classif> finals, String tournamentName, double completion) {
            this.found = true;
            this.hasImage = hasImage;
            this.groups = groups;
            this.quarters = quarters;
            this.semis = semis;
            this.finals = finals;
            this.tournamentName = tournamentName;
            this.completion = completion;
        }

        public TournamentSheet(List<String> results) {
            this.found = false;
            this.results = results;
        }
    }

    @GetMapping("/TenniShip/Tournament/{tournament}")
    public ResponseEntity<TournamentSheet> tournament(Model model, @PathVariable String tournament, HttpServletRequest request) {

        Optional<Tournament> t = tournamentRepository.findById(tournament);

       /* if (userComponent.isLoggedUser() && !request.isUserInRole("ADMIN")) {
            String teamUser = userComponent.getTeam();
            model.addAttribute("team", teamUser);
        }
        model.addAttribute("registered", userComponent.isLoggedUser() && !request.isUserInRole("ADMIN"));
        model.addAttribute("admin", userComponent.isLoggedUser() && request.isUserInRole("ADMIN"));*/

        if (t.isPresent()) {
            //model.addAttribute("adminDeleting", userComponent.isLoggedUser() && request.isUserInRole("ADMIN"));

            Boolean hasImage = (Objects.nonNull(t.get().hasImage()) && t.get().hasImage());

            // GROUPS-------
            String[] groups = { "A", "B", "C", "D", "E", "F" };
            @SuppressWarnings("unchecked")
            ArrayList<AuxiliarClass>[] sortedGroups = new ArrayList[6];
            for (int i = 0; i < 6; i++) {
                sortedGroups[i] = new ArrayList<>();
            }

            List<Group> groupsl = new ArrayList<>();

            for (int i = 0; i < 6; i++) {

                Group group = new Group();

                tournamentRepository.getPhaseTeams(t.get(), groups[i]).forEach(tm -> {
                    sortedGroups[j].add(new AuxiliarClass(tm, teamRepository.getWonGroupMatches(t.get(), tm, groups[j]),
                            teamRepository.getWonGroupPointsPlayingHome(t.get(), tm, groups[j])
                                    + teamRepository.getWonGroupPointsPlayingAway(t.get(), tm, groups[j])));
                });
                Collections.sort(sortedGroups[j]);

                for (int k = 0; k < 3; k++) {
                    TeamInGroup tig = new TeamInGroup(sortedGroups[j].get(k).t.getName(),
                            sortedGroups[j].get(k).matchesWon, sortedGroups[j].get(k).pointsWon);
                    group.getTeams().add(tig);
                }
                groupsl.add(group);
                j++;
            }
            j = 0;
            // -------GROUPS

            // FINAL PHASE--

            List<Team> last8 = new ArrayList<>();
            List<Team> last4 = new ArrayList<>();
            List<Team> last2 = new ArrayList<>();
            int localGana = 0;
            int visitanteGana = 0;

            List<Classif> quartersList = new ArrayList<>();
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

                for (int k = 0; k < 8; k += 2) { // RoundOf8
                    if (tournamentRepository.getPhaseMatches(t.get(), "X").get(k / 2)
                            .getHomePoints() > tournamentRepository.getPhaseMatches(t.get(), "X").get(k / 2)
                            .getAwayPoints()) {

                        localGana = 0;
                        visitanteGana = 1;
                    }
                    else {
                        localGana = 1;
                        visitanteGana = 0;
                    }
                    TeamClassif teamLocal = new TeamClassif(tournamentRepository
                            .getPhaseMatches(t.get(), "X").get(k / 2).getTeam1().getName(),
                            tournamentRepository.getPhaseMatches(t.get(), "X").get(k / 2).getHomePoints());
                    TeamClassif teamAway = new TeamClassif(tournamentRepository
                            .getPhaseMatches(t.get(), "X").get(k / 2).getTeam2().getName(),
                            tournamentRepository.getPhaseMatches(t.get(), "X").get(k / 2).getAwayPoints());
                    Classif quarters = new Classif(teamLocal,teamAway);
                    quartersList.add(quarters);
                }
            }

            /* If groups have not been played, the list will simply be empty */


            List<Classif> semis = new ArrayList<>();
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
                for (int k = 0; k < 4; k += 2) { // RoundOf4
                    if (tournamentRepository.getPhaseMatches(t.get(), "Y").get(k / 2)
                            .getHomePoints() > tournamentRepository.getPhaseMatches(t.get(), "Y").get(k / 2)
                            .getAwayPoints()) {
                        localGana = 0;
                        visitanteGana = 1;
                    }
                    else {
                        localGana = 1;
                        visitanteGana = 0;
                    }
                    TeamClassif teamLocal = new TeamClassif(tournamentRepository
                            .getPhaseMatches(t.get(), "Y").get(k / 2).getTeam1().getName(),
                            tournamentRepository.getPhaseMatches(t.get(), "Y").get(k / 2).getHomePoints());
                    TeamClassif teamAway = new TeamClassif(tournamentRepository
                            .getPhaseMatches(t.get(), "Y").get(k / 2).getTeam2().getName(),
                            tournamentRepository.getPhaseMatches(t.get(), "Y").get(k / 2).getAwayPoints());
                    Classif semi = new Classif(teamLocal,teamAway);
                    semis.add(semi);
                }
            }
            /* If the quarterfinals have not been played, the semis list will be empty */

            Classif finals = new Classif();
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
                for (int k = 0; k < 2; k += 2) { // RoundOf2
                    if (tournamentRepository.getPhaseMatches(t.get(), "Z").get(k / 2)
                            .getHomePoints() > tournamentRepository.getPhaseMatches(t.get(), "Z").get(k / 2)
                            .getAwayPoints()) {
                        localGana = 0;
                        visitanteGana = 1;
                    }
                    else {
                        localGana = 1;
                        visitanteGana = 0;
                    }
                    TeamClassif local = new TeamClassif(tournamentRepository
                            .getPhaseMatches(t.get(), "Z").get(k / 2).getTeam1().getName(),
                            tournamentRepository.getPhaseMatches(t.get(), "Z").get(k / 2).getHomePoints());
                    TeamClassif away = new TeamClassif(tournamentRepository
                            .getPhaseMatches(t.get(), "Z").get(k / 2).getTeam2().getName(),
                            tournamentRepository.getPhaseMatches(t.get(), "Z").get(k / 2).getAwayPoints());

                    Classif aux = new Classif(local, away);
                    finals = aux;
                }
            }
            Optional<Classif> oFinals = new Optional.of(finals);

            double progressPercentage;
            final int TOTAL_MATCHES = 25;
            progressPercentage = tournamentRepository.getPlayedMatches(t.get().getName());
            progressPercentage = (progressPercentage / TOTAL_MATCHES) * 100;

            /*model.addAttribute("adminGroups", userComponent.isLoggedUser() && request.isUserInRole("ADMIN")
                    && tournamentRepository.getPhaseMatches(t.get(), "X").isEmpty());*/
            TournamentSheet tournamentSheet = new TournamentSheet(
                    hasImage,groupsl,quartersList,semis,oFinals,t.get().getName(),progressPercentage);

            return new ResponseEntity<>(tournamentSheet, HttpStatus.OK);

        }

        else {
            model.addAttribute("tournamentName", tournament);
            List<Team> results = tournamentRepository.findSimilarTournaments(tournament);
            List<String> names = new ArrayList<>();
            if (!results.isEmpty()) {
                model.addAttribute("results", true);
                for (Team i : results) {
                    names.add(i.getName());
                }
            }
            TournamentSheet tournamentSheet = new TournamentSheet(names);
            return new ResponseEntity<>(tournamentSheet, HttpStatus.NOT_FOUND);
        }
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
