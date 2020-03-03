package com.practica;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.practica.model.Team;
import com.practica.model.Tournament;
import com.practica.security.UserComponent;
import com.practica.model.Match;

@Controller
public class TournamentSheetController {

	@Autowired
	private TournamentRepository tournamentRepository;

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private MatchRepository matchRepository;

	@Autowired
	private UserComponent userComponent;

	static int matchesWon = 0;
	static int pointsWon = 0;
	static int j;

	@PostMapping("/TenniShip/ADMIN/Tournament/{tournament}/EditMatches/{group}/Submission")
	public String submitMatchEdited(Model model, @PathVariable String tournament, @RequestParam String teamHome,
			@PathVariable String group, @RequestParam String teamAway, @RequestParam int quantityHome,
			@RequestParam int quantityAway, HttpServletRequest request) {

		if (quantityHome == 3 ^ quantityAway == 3) { // XOR
			Optional<Team> home = teamRepository.findById(teamHome);
			Optional<Team> away = teamRepository.findById(teamAway);
			Optional<Tournament> t = tournamentRepository.findById(tournament);

			Match match = matchRepository.findMatch(home.get(), away.get(), t.get()).get(0);

			matchRepository.getOne(match.getId()).setHomePoints(quantityHome);
			matchRepository.getOne(match.getId()).setAwayPoints(quantityAway);

			matchRepository.save(match);

			return "redirect:/TenniShip/Tournament/" + tournament;

		} else {
			model.addAttribute("errormsg", "Someone has to win");
			model.addAttribute("goback", true);
			model.addAttribute("admin", true);
			model.addAttribute("tournament", tournament);
			model.addAttribute("group", group);
			return "error";
			// return "redirect:/TenniShip/ADMIN/Tournament/" + tournament + "/EditMatches/"
			// + group;
		}
	}

	@GetMapping("/TenniShip/ADMIN/Tournament/{tournament}/EditMatches/{group}")
	public String editMatches(Model model, @PathVariable String tournament, @PathVariable String group) {

		Optional<Tournament> t = tournamentRepository.findById(tournament);

		if (t.isPresent()) {
			model.addAttribute("admin", true);
			model.addAttribute("round", "Group Stage");
			model.addAttribute("listMatches", tournamentRepository.getPhaseMatches(t.get(), group));
		}
		return "registerMatch";
	}

	@PostMapping("/TenniShip/ADMIN/Tournament/{tournament}/Deleted")
	public String deleteTournament(Model model, @PathVariable String tournament) {

		Optional<Tournament> t = tournamentRepository.findById(tournament);

		for (Match m : tournamentRepository.getMatches(t.get())) {
			matchRepository.delete(m);
		}
		tournamentRepository.delete(t.get());

		return "redirect:/TenniShip";
	}

	@PostMapping("/TenniShip/SearchTournament")
	public String searchTournament(Model model, @RequestParam String tournamentName) {

		Optional<Tournament> t = tournamentRepository.findById(tournamentName);

		if (t.isPresent()) {
			return "redirect:/TenniShip/Tournament/" + tournamentName;
		} else {
			return "redirect:/TenniShip";
		}
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

	@GetMapping("/TenniShip/Tournament/{tournament}")
	public String tournament(Model model, @PathVariable String tournament, HttpServletRequest request) {

		Optional<Tournament> t = tournamentRepository.findById(tournament);

		if (userComponent.isLoggedUser() && !request.isUserInRole("ADMIN")) {
			String teamUser = userComponent.getTeam();
			model.addAttribute("team", teamUser);
		}
		model.addAttribute("registered", userComponent.isLoggedUser() && !request.isUserInRole("ADMIN"));
		model.addAttribute("admin", userComponent.isLoggedUser() && request.isUserInRole("ADMIN"));

		if (t.isPresent()) {
			model.addAttribute("adminDeleting", userComponent.isLoggedUser() && request.isUserInRole("ADMIN"));

			if (Objects.nonNull(t.get().hasImage()) && t.get().hasImage()) {
				model.addAttribute("hasImage", true);
			}

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

				for (int k = 0; k < 3; k++) {
					model.addAttribute(String.format("team%dGroup%s", k, groups[j]),
							sortedGroups[j].get(k).t.getName());
					model.addAttribute(String.format("team%dGroup%sMatchesWon", k, groups[j]),
							sortedGroups[j].get(k).matchesWon);
					model.addAttribute(String.format("team%dGroup%sPointsWon", k, groups[j]),
							sortedGroups[j].get(k).pointsWon);
				}
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
						model.addAttribute(String.format("team%dQuarters", k + localGana),
								tournamentRepository.getPhaseMatches(t.get(), "X").get(k / 2).getTeam1().getName());
						model.addAttribute(String.format("team%dQuartersPoints", k + localGana),
								tournamentRepository.getPhaseMatches(t.get(), "X").get(k / 2).getHomePoints());
						model.addAttribute(String.format("team%dQuarters", k + visitanteGana),
								tournamentRepository.getPhaseMatches(t.get(), "X").get(k / 2).getTeam2().getName());
						model.addAttribute(String.format("team%dQuartersPoints", k + visitanteGana),
								tournamentRepository.getPhaseMatches(t.get(), "X").get(k / 2).getAwayPoints());
				}
			} else {
				for (int k = 0; k < 8; k += 2) { // RoundOf8
					model.addAttribute(String.format("team%dQuarters", k), "-Keep Playing");
					model.addAttribute(String.format("team%dQuartersPoints", k), "");
					model.addAttribute(String.format("team%dQuarters", k + 1), "-Keep Playing");
					model.addAttribute(String.format("team%dQuartersPoints", k + 1), "");
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
						model.addAttribute(String.format("team%dSemi", k + localGana),
								tournamentRepository.getPhaseMatches(t.get(), "Y").get(k / 2).getTeam1().getName());
						model.addAttribute(String.format("team%dSemiPoints", k + localGana),
								tournamentRepository.getPhaseMatches(t.get(), "Y").get(k / 2).getHomePoints());
						model.addAttribute(String.format("team%dSemi", k + visitanteGana),
								tournamentRepository.getPhaseMatches(t.get(), "Y").get(k / 2).getTeam2().getName());
						model.addAttribute(String.format("team%dSemiPoints", k + visitanteGana),
								tournamentRepository.getPhaseMatches(t.get(), "Y").get(k / 2).getAwayPoints());
				}
			} else {
				for (int k = 0; k < 4; k += 2) { // RoundOf4
					model.addAttribute(String.format("team%dSemi", k), "-Keep Playing");
					model.addAttribute(String.format("team%dSemiPoints", k), "");
					model.addAttribute(String.format("team%dSemi", k + 1), "-Keep Playing");
					model.addAttribute(String.format("team%dSemiPoints", k + 1), "");
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
						model.addAttribute(String.format("team%dFinal", k + localGana),
								tournamentRepository.getPhaseMatches(t.get(), "Z").get(k / 2).getTeam1().getName());
						model.addAttribute(String.format("team%dFinalPoints", k + localGana),
								tournamentRepository.getPhaseMatches(t.get(), "Z").get(k / 2).getHomePoints());
						model.addAttribute(String.format("team%dFinal", k + visitanteGana),
								tournamentRepository.getPhaseMatches(t.get(), "Z").get(k / 2).getTeam2().getName());
						model.addAttribute(String.format("team%dFinalPoints", k + visitanteGana),
								tournamentRepository.getPhaseMatches(t.get(), "Z").get(k / 2).getAwayPoints());
				}
			} else {
				for (int k = 0; k < 2; k += 2) { // RoundOf2
					model.addAttribute(String.format("team%dFinal", k), "-Keep Playing");
					model.addAttribute(String.format("team%dFinalPoints", k), "");
					model.addAttribute(String.format("team%dFinal", k + 1), "-Keep Playing");
					model.addAttribute(String.format("team%dFinalPoints", k + 1), "");
				}

				// --FINAL PHASE

			}

			double progressPercentage;
			final int TOTAL_MATCHES = 25;
			progressPercentage = tournamentRepository.getPlayedMatches(t.get().getName());
			progressPercentage = (progressPercentage / TOTAL_MATCHES) * 100;

			model.addAttribute("tournamentName", t.get().getName());
			model.addAttribute("completion", progressPercentage);

			model.addAttribute("adminGroups", userComponent.isLoggedUser() && request.isUserInRole("ADMIN")
					&& tournamentRepository.getPhaseMatches(t.get(), "X").isEmpty());

			return "tournamentSheet";

		}

		else {
			model.addAttribute("tournamentName", tournament);
			List<Team> results = tournamentRepository.findSimilarTournaments(tournament);
			if (!results.isEmpty()) {
				List<String> names = new ArrayList<>();
				model.addAttribute("results", true);
				for (Team i : results) {
					names.add(i.getName());
				}
				model.addAttribute("resultsList", names);
			}
			return "tournamentResults";
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