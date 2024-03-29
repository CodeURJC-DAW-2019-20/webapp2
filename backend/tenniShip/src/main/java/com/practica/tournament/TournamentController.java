package com.practica.tournament;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.practica.team.TeamService;
import com.practica.match.MatchService;
import com.practica.model.Match;

@Controller
public class TournamentController {

	@Autowired
	private TournamentService tournamentService;

	@Autowired
	private MatchService matchService;

	@Autowired
	private TeamService teamService;

	@Autowired
	private UserComponent userComponent;

	@PostMapping("/TenniShip/RegisterMatch/Tournaments/{tournament}/Submission")
	public String submitMatch(Model model, @PathVariable String tournament, @RequestParam String teamHome,
			@RequestParam String teamAway, @RequestParam int quantityHome, @RequestParam int quantityAway,
			HttpServletRequest request) throws InterruptedException {

		if (quantityHome == 3 ^ quantityAway == 3) { // XOR
			Optional<Team> home = teamService.findById(teamHome);
			Optional<Team> away = teamService.findById(teamAway);
			Optional<Tournament> t = tournamentService.findById(tournament);

			Match match = matchService.findMatch(home.get(), away.get(), t.get()).get(0);

			matchService.getOne(match.getId()).setHomePoints(quantityHome);
			matchService.getOne(match.getId()).setAwayPoints(quantityAway);

			matchService.save(match);
		} else {
			model.addAttribute("errormsg", "Someone has to win");
			model.addAttribute("goback", true);
			model.addAttribute("tournament", tournament);
			return "error";
		}
		return "redirect:/TenniShip/RegisterMatch/Tournaments/" + tournament;
	}

	@GetMapping("/TenniShip/RegisterMatch/Tournaments/{tournament}")
	public String selectMatch(Model model, @PathVariable String tournament, HttpServletRequest request) {

		Optional<Tournament> t = tournamentService.findById(tournament);// check if that team play this tournament

		if (t.isPresent()
				&& tournamentService.getTeams(t.get()).contains(teamService.findById(userComponent.getTeam()).get())) {
			model.addAttribute("admin", userComponent.isLoggedUser() && request.isUserInRole("ADMIN"));
			String team = userComponent.getTeam();
			model.addAttribute("team", team);
			Optional<Team> tm = teamService.findById(team);

			if (tournamentService.getNextMatches(t.get(), tm.get()).isEmpty()) {
				model.addAttribute("round",
						"See the tournament progress to check if your team has to play more games!");
				model.addAttribute("allPlayed", true);
				model.addAttribute("tournamentName", t.get().getName());
			} else {
				model.addAttribute("round", tournamentService.getNextMatches(t.get(), tm.get()).get(0).getStringType());
			}
			model.addAttribute("listMatches", tournamentService.getNextMatches(t.get(), tm.get()));
			return "registerMatch";
		} else {
			model.addAttribute("errormsg",
					"You need to be a participant of the tournament you want to register a match at");
			return "error";
		}
	}

	@GetMapping("/TenniShip/RegisterMatch/Tournaments")
	public String selectTournament(Model model, HttpServletRequest request) {
		if (userComponent.isLoggedUser()) {
			String team = userComponent.getTeam();
			Optional<Team> t = teamService.findById(team);

			model.addAttribute("team", team);
			model.addAttribute("listTournaments", teamService.getTournaments(t.get()));

			return "selectTournament";
		} else {
			return "redirect:/TenniShip/SignIn";
		}
	}

	@GetMapping("/TenniShip/RegisterMatch/Tournaments/ListTournament/{position}/{end}")
	public String listTournament(Model model, HttpServletRequest request, @PathVariable int position,
			@PathVariable int end) {
		if (userComponent.isLoggedUser()) {
			String team = userComponent.getTeam();
			Optional<Team> t = teamService.findById(team);

			List<Tournament> tournaments = teamService.getTournaments(t.get());
			end = Integer.min(tournaments.size(), end);
			if (end == (tournaments.size())) {
				model.addAttribute("finished", true);
			}
			tournaments = tournaments.subList(position, end);
			List<String> tourNames = new ArrayList<>();
			for (Tournament tour : tournaments) {
				tourNames.add(tour.getName());
			}
			model.addAttribute("listTournaments", tourNames);

			return "listTournaments";
		} else {
			return "error";
		}
	}
}
