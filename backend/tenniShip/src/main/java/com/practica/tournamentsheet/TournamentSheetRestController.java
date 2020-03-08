package com.practica.tournamentsheet;

import com.fasterxml.jackson.annotation.JsonView;
import com.practica.match.MatchService;
import com.practica.model.Match;
import com.practica.model.Team;
import com.practica.model.Tournament;
import com.practica.security.UserComponent;
import com.practica.team.TeamService;
import com.practica.tournament.TournamentRestController;
import com.practica.tournament.TournamentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/api/TenniShip")
public class TournamentSheetRestController {

	@Autowired
	private TournamentService tournamentService;

	@Autowired
	private TeamService teamService;

	@Autowired
	private MatchService matchService;

	@Autowired
	private UserComponent userComponent;

	static int j;

	@JsonView(TournamentRestController.PutMatch.class)
	@PutMapping("/ADMIN/Tournament/{tournament}/EditMatches/{group}/Submission")
	public ResponseEntity<Match> submitMatchEdited(@PathVariable String tournament, @RequestBody Match newMatch,
			@PathVariable String group, HttpServletRequest request) {

		if (userComponent.isLoggedUser() && request.isUserInRole("ADMIN")) {
			return tournamentService.putTheMatch(tournament, newMatch,true);
		} else
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}

	public static class AuxEdit {
		interface Basic {
		}

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

	interface EditMatch extends AuxEdit.Basic, Match.Basic, Team.Basic, Tournament.Basic {
	}

	@JsonView(EditMatch.class)
	@GetMapping("/ADMIN/Tournament/{tournament}/EditMatches/{group}")
	public ResponseEntity<AuxEdit> editMatches(@PathVariable String tournament, @PathVariable String group,
			HttpServletRequest request) {
		if (userComponent.isLoggedUser() && request.isUserInRole("ADMIN")) {
			Optional<Tournament> t = tournamentService.findById(tournament);

			if (t.isPresent()) {
				AuxEdit aux = new AuxEdit(true, "Group Stage", tournamentService.getPhaseMatches(t.get(), group));
				return new ResponseEntity<>(aux, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}

	@DeleteMapping("/ADMIN/Tournament/{tournament}/Deleted")
	public ResponseEntity<Tournament> deleteTournament(@PathVariable String tournament, HttpServletRequest request) {

		if (userComponent.isLoggedUser() && request.isUserInRole("ADMIN")) {
			Optional<Tournament> t = tournamentService.findById(tournament);

			if (t.isPresent()) {
				for (Match m : tournamentService.getMatches(t.get())) {
					matchService.delete(m);
				}
				tournamentService.delete(t.get());
				return new ResponseEntity<>(t.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}

	private static class AuxiliarClass implements Comparable<AuxiliarClass> {
		interface Basic {
		}

		@JsonView(Basic.class)
		private Team team;
		@JsonView(Basic.class)
		private Integer matchesWon; // Spain 3 - 2 France
		@JsonView(Basic.class)
		private Integer pointsWon; // Spain matchesWon += 1, Spain pointsWon += 3
		@JsonView(Basic.class)
		private String group;

		public AuxiliarClass(Team team, int matchesWon, int pointsWon,String group) {
			this.team = team;
			this.matchesWon = matchesWon;
			this.pointsWon = pointsWon;
			this.group = group;
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
		interface Basic {
		}

		@JsonView(Basic.class)
		private Tournament tournament;
		@JsonView(Basic.class)
		private ArrayList<AuxiliarClass>[] groups;
		@JsonView(Basic.class)
		private List<Match> quarters = new ArrayList<>();
		@JsonView(Basic.class)
		private List<Match> theSemiFinals = new ArrayList<>();
		@JsonView(Basic.class)
		private List<Match> theFinal = new ArrayList<>();
		@JsonView(Basic.class)
		private double completion;

		@SuppressWarnings("unused")
		public TournamentSheetToReturn() {

		}

		public TournamentSheetToReturn(Tournament t, ArrayList<AuxiliarClass>[] groups, List<Match> quarters,
				List<Match> theSemiFinals, List<Match> theFinal, double completion) {
			this.tournament = t;
			this.groups = groups;
			/*
			 * If the class is static, calls to tournamentRepository have to be made outside
			 */
			this.quarters = quarters;
			this.theSemiFinals = theSemiFinals;
			this.theFinal = theFinal;
			this.completion = completion;
		}
	}

	interface tournamentSheet
			extends Match.Basic, TournamentSheetToReturn.Basic, AuxiliarClass.Basic, Tournament.Basic, Team.Basic {
	}

	@JsonView(tournamentSheet.class)
	@GetMapping("/Tournament/{tournament}")
	public ResponseEntity<TournamentSheetToReturn> tournament(@PathVariable String tournament,
			HttpServletRequest request) {

		Optional<Tournament> t = tournamentService.findById(tournament);

		if (t.isPresent()) {

			/*
			 * if (Objects.nonNull(t.get().hasImage()) && t.get().hasImage()) {
			 * model.addAttribute("hasImage", true); }
			 */

			// GROUPS-------
			String[] groups = { "A", "B", "C", "D", "E", "F" };
			@SuppressWarnings("unchecked")
			ArrayList<AuxiliarClass>[] sortedGroups = new ArrayList[6];
			for (int i = 0; i < 6; i++) {
				sortedGroups[i] = new ArrayList<>();
			}

			for (int i = 0; i < 6; i++) {
				tournamentService.getPhaseTeams(t.get(), groups[i]).forEach(tm -> {
					sortedGroups[j].add(new AuxiliarClass(tm, teamService.getWonGroupMatches(t.get(), tm, groups[j]),
							teamService.getWonGroupPointsPlayingHome(t.get(), tm, groups[j])
									+ teamService.getWonGroupPointsPlayingAway(t.get(), tm, groups[j]),groups[j]));
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

			if (tournamentService.getPlayedMatchesJQL(t.get()) >= 18) { // Groups Played
				if (tournamentService.getPhaseTeams(t.get(), "X").isEmpty()) { // RoundOf8 has to be created
					List<AuxiliarClass> secondPlaceTeams = new ArrayList<>();
					secondPlaceTeams.add(sortedGroups[0].get(1));
					secondPlaceTeams.add(sortedGroups[1].get(1));
					secondPlaceTeams.add(sortedGroups[2].get(1));
					secondPlaceTeams.add(sortedGroups[3].get(1));
					secondPlaceTeams.add(sortedGroups[4].get(1));
					secondPlaceTeams.add(sortedGroups[5].get(1));
					Collections.sort(secondPlaceTeams);

					last8.add(sortedGroups[0].get(0).team);
					last8.add(sortedGroups[1].get(0).team);
					last8.add(sortedGroups[2].get(0).team);
					last8.add(sortedGroups[3].get(0).team);
					last8.add(sortedGroups[4].get(0).team);
					last8.add(sortedGroups[5].get(0).team);
					last8.add(secondPlaceTeams.get(0).team);
					last8.add(secondPlaceTeams.get(1).team);
					Collections.shuffle(last8);

					createMatches(last8, "X", t.get());
				}
			}

			if (tournamentService.getPlayedMatchesJQL(t.get()) >= 22) { // RoundOf8 Played
				if (tournamentService.getPhaseTeams(t.get(), "Y").isEmpty()) { // RoundOf4 has to be created
					tournamentService.getPhaseMatches(t.get(), "X").forEach(Match -> {
						if (Match.getHomePoints() > Match.getAwayPoints())
							last4.add(Match.getTeam1());
						else
							last4.add(Match.getTeam2());
					});

					createMatches(last4, "Y", t.get());
				}
			}
			if (tournamentService.getPlayedMatchesJQL(t.get()) >= 24) { // RoundOf4 Played
				if (tournamentService.getPhaseTeams(t.get(), "Z").isEmpty()) { // RoundOf2 has to be created
					tournamentService.getPhaseMatches(t.get(), "Y").forEach(Match -> {
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
			progressPercentage = tournamentService.getPlayedMatchesJQL(t.get());
			progressPercentage = (progressPercentage / TOTAL_MATCHES) * 100;

			/*
			 * Calls to tournamentRepository have to be made outside of the static auxiliar
			 * class
			 */
			TournamentSheetToReturn obtectToReturn = new TournamentSheetToReturn(t.get(), sortedGroups,
					tournamentService.getPhaseMatches(t.get(), "X"),
					tournamentService.getPhaseMatches(t.get(), "Y"),
					tournamentService.getPhaseMatches(t.get(), "Z"), progressPercentage);

			return new ResponseEntity<>(obtectToReturn, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	private void createMatches(List<Team> lastn, String phase, Tournament t) {
		for (int i = 0; i < lastn.size(); i += 2) {
			Match a = new Match(0, 0, phase);
			a.setTeam1(lastn.get(i));
			a.setTeam2(lastn.get(i + 1));
			a.setTournament(t);
			matchService.save(a);
		}
	}

}
