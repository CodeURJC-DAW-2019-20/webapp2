package com.practica;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
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
			@RequestParam String teamAway, @RequestParam int quantityHome, @RequestParam int quantityAway) throws InterruptedException {
		
		if (quantityHome == 3 ^ quantityAway == 3) { //XOR
			Optional<Team> home = teamRepository.findById(teamHome);
			Optional<Team> away = teamRepository.findById(teamAway);
			Optional<Tournament> t = tournamentRepository.findById(tournament);
			
			Match match = matchRepository.findMatch(home.get(), away.get(), t.get()).get(0);
			
			matchRepository.getOne(match.getId()).setHomePoints(quantityHome);
			matchRepository.getOne(match.getId()).setAwayPoints(quantityAway);
			
			matchRepository.save(match); 
			
			return "redirect:/TenniShip/RegisterMatch/Tournament/"+ tournament;
			
		} else {
			model.addAttribute("error", true);
			TimeUnit.SECONDS.sleep(4);
			return selectMatch(model, tournament);
		}		
	} 
	
	@GetMapping("/TenniShip/RegisterMatch/Tournament/{tournament}")
	public String selectMatch(Model model, @PathVariable String tournament) {

		String team = userComponent.getTeam();

		Optional<Tournament> t = tournamentRepository.findById(tournament);//check if that team play this tournament
		Optional<Team> tm = teamRepository.findById(team);
		
		HashMap<String, String> rounds = new HashMap<>(); rounds.put("A", "Group Stage"); rounds.put("B", "Group Stage");
		rounds.put("C", "Group Stage"); rounds.put("D", "Group Stage"); rounds.put("E", "Group Stage"); rounds.put("F", "Group Stage");
		rounds.put("X", "Round of 8"); rounds.put("Y", "Round of 4"); rounds.put("Z", "Final");

		if (t.isPresent()) {
			if (tournamentRepository.getNextMatches(t.get(), tm.get()).isEmpty()) {
				model.addAttribute("round", "All Played");
			} else {
				model.addAttribute("round", rounds.get(tournamentRepository.getNextMatches(t.get(), tm.get()).get(0).getType()));
			}
			model.addAttribute("listMatches", tournamentRepository.getNextMatches(t.get(), tm.get()));	
		}

		return "registerMatch";
	}

	@GetMapping("/TenniShip/RegisterMatch/Tournament")
	public String selectTournament(Model model) {

		String team = userComponent.getTeam();

		Optional<Team> t = teamRepository.findById(team);

        model.addAttribute("registered", userComponent.getLoggedUser());
        model.addAttribute("team", team);

		if (t.isPresent()) {
			model.addAttribute("listTournaments", teamRepository.getTournaments(t.get()));
		}
		return "selectTournament";
	}

	

}
