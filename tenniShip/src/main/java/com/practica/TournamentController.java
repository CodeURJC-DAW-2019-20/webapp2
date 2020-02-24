package com.practica;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;

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
public class TournamentController {

	@Autowired
	private TournamentRepository tournamentRepository;

	@Autowired
	private MatchRepository matchRepository;

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private UserComponent userComponent;

	@PostMapping("/TenniShip/RegisterMatch/Tournament/{tournament}/Submission")
	public String submitMatch(Model model, @PathVariable String tournament, @RequestParam String teamHome,
			@RequestParam String teamAway, @RequestParam int quantityHome, @RequestParam int quantityAway,
			HttpServletRequest request) throws InterruptedException {

		if (quantityHome == 3 ^ quantityAway == 3) { // XOR
			Optional<Team> home = teamRepository.findById(teamHome);
			Optional<Team> away = teamRepository.findById(teamAway);
			Optional<Tournament> t = tournamentRepository.findById(tournament);

			Match match = matchRepository.findMatch(home.get(), away.get(), t.get()).get(0);

			matchRepository.getOne(match.getId()).setHomePoints(quantityHome);
			matchRepository.getOne(match.getId()).setAwayPoints(quantityAway);

			matchRepository.save(match);
		} else {
			model.addAttribute("errormsg", "Someone has to win");
			model.addAttribute("goback", true);
			model.addAttribute("tournament", tournament);
			return "error";
		}
		return "redirect:/TenniShip/RegisterMatch/Tournament/" + tournament;
	}

	@GetMapping("/TenniShip/RegisterMatch/Tournament/{tournament}")
	public String selectMatch(Model model, @PathVariable String tournament, HttpServletRequest request) {

		Optional<Tournament> t = tournamentRepository.findById(tournament);// check if that team play this tournament

		if (t.isPresent() && tournamentRepository.getTeams(t.get())
				.contains(teamRepository.findById(userComponent.getTeam()).get())) {
			model.addAttribute("admin", userComponent.isLoggedUser() && request.isUserInRole("ADMIN"));
			String team = userComponent.getTeam();
			model.addAttribute("team", team);
			Optional<Team> tm = teamRepository.findById(team);
			HashMap<String, String> rounds = new HashMap<>();
			rounds.put("A", "Group Stage");
			rounds.put("B", "Group Stage");
			rounds.put("C", "Group Stage");
			rounds.put("D", "Group Stage");
			rounds.put("E", "Group Stage");
			rounds.put("F", "Group Stage");
			rounds.put("X", "Round of 8");
			rounds.put("Y", "Round of 4");
			rounds.put("Z", "Final");

			if (tournamentRepository.getNextMatches(t.get(), tm.get()).isEmpty()) {
				model.addAttribute("round", "All Played");
				model.addAttribute("allPlayed", true);
				model.addAttribute("tournamentName", t.get().getName());
			} else {
				model.addAttribute("round",
						rounds.get(tournamentRepository.getNextMatches(t.get(), tm.get()).get(0).getType()));
			}
			model.addAttribute("listMatches", tournamentRepository.getNextMatches(t.get(), tm.get()));
			return "registerMatch";
		} else {
			model.addAttribute("errormsg",
					"You need to be a participant of the tournament you want to register a match at");
			return "error";
		}
	}

	@GetMapping("/TenniShip/RegisterMatch/Tournament")
	public String selectTournament(Model model, HttpServletRequest request) {
		if (userComponent.isLoggedUser()) {
			String team = userComponent.getTeam();
			Optional<Team> t = teamRepository.findById(team);

			model.addAttribute("team", team);
			model.addAttribute("listTournaments", teamRepository.getTournaments(t.get()));

			return "selectTournament";
		} else {
			return "redirect:/TenniShip/SignIn";
		}
	}

}
