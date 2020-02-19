package com.practica;

import java.util.Optional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.practica.model.Team;
import com.practica.model.Tournament;
import com.practica.security.User;
import com.practica.security.UserComponent;
import com.practica.security.UserRepository;
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
	private UserRepository userRepository;

	@Autowired
	private UserComponent userComponent;
	
	@PostMapping("/prueba")
	public String submitMatch(Model model, @RequestParam int quantityHome, @RequestParam int quantityAway) {
		System.out.println(quantityHome + " " + quantityAway);
		return "good";
	} 
	
	
	@GetMapping("/TenniShip/RegisterMatch/Tournament/{tournament}")
	public String selectMatch(Model model, @PathVariable String tournament) {

		String team = userComponent.getTeam();

		Optional<Tournament> t = tournamentRepository.findById(tournament);//check if that team play this tournament
		Optional<Team> tm = teamRepository.findById(team);

		if (t.isPresent() && tm.isPresent()) {
			for (int i = 0; i < Math.min(matchRepository.findMatches(tm.get(), t.get()).size(), 3); i++) {
				model.addAttribute(String.format("teamNameHome%d", i), matchRepository.findMatches(tm.get(), t.get()).get(i).getTeam1().getName());
				model.addAttribute(String.format("teamHomePoints%d", i), Integer.toString(matchRepository.findMatches(tm.get(), t.get()).get(i).getHomePoints()));
				model.addAttribute(String.format("teamAwayPoints%d", i), Integer.toString(matchRepository.findMatches(tm.get(), t.get()).get(i).getAwayPoints()));
				model.addAttribute(String.format("teamNameAway%d", i), matchRepository.findMatches(tm.get(), t.get()).get(i).getTeam2().getName());
			}
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
			for (int i = 0; i < Math.min(teamRepository.getTournaments(t.get()).size(), 6); i++) {
				model.addAttribute(String.format("tournamentName%d", i), teamRepository.getTournaments(t.get()).get(i).getName());
			}


			model.addAttribute("listTournaments", teamRepository.getTournaments(t.get()));
		}
		return "selectTournament";
	}

	
	/*protected void raffleGP(Model model) {
		List<String> teams = teamRepository.findAllTeams();
		Collections.shuffle(teams);
		for (int i = 0; i < 18; i++) {
			model.addAttribute(String.format("team%d", i), teams.get(i));
		}
	}*/




	@GetMapping("/{team}/Creator")
	public String create (Model model, @PathVariable String team) {

		Optional <Team> t = teamRepository.findById(team);

		if(t.isPresent()) {
			String tournamentName;
			tournamentName = "CopaDavis"; //cambiar futuro
			List<Team> teamsProvisional = new ArrayList<>();
			teamsProvisional.addAll(teamRepository.findAll()); //futura query


			model.addAttribute("tournament", tournamentName);

			for(int i=0; i< Math.min(18, teamsProvisional.size())/*teamList.size() Poner esto*/;i++) {
	    		model.addAttribute(String.format("team%d", i),teamsProvisional.get(i).getName());
	    	}
		}

		return "tournamentCreator";
	}

}
