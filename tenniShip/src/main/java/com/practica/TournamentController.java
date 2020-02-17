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
	private TeamRepository teamRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserComponent userComponent;


	@GetMapping("/Tournament/{tournament}")
	public String tournament(Model model, @PathVariable String tournament) {

		Optional<Tournament> t = tournamentRepository.findById(tournament);

		if (t.isPresent()) {
			model.addAttribute("tournamentName", t.get().getName());
			List<Team> teamList = t.get().getTournamentTeams();
			for (int i = 0; i < teamList.size(); i++) {
				model.addAttribute(String.format("team%d", i), teamList.get(i).getName());
			}
		}

		return "tournamentSheet";
	}

	@GetMapping("/TenniShip/RegisterMatch/Tournament/{tournament}")
	public String selectMatch(Model model, @PathVariable String tournament) {
		
		String team = userComponent.getTeam();
		
		Optional<Tournament> t = tournamentRepository.findById(tournament);//check if that team play this tournament
		List<Match> matchesWithMyTeam = new ArrayList<>();

		if (t.isPresent()) {
			t.get().getTournamentMatchs().forEach(Match -> {
				if (Match.getHome().equals(team) || Match.getAway().equals(team))
					matchesWithMyTeam.add(Match);
			});
			for (int i = 0; i < Math.min(matchesWithMyTeam.size(), 3); i++) {
				model.addAttribute(String.format("teamNameHome%d", i), matchesWithMyTeam.get(i).getHome());
				model.addAttribute(String.format("teamHomePoints%d", i), Integer.toString(matchesWithMyTeam.get(i).getHomePoints()));
				model.addAttribute(String.format("teamAwayPoints%d", i), Integer.toString(matchesWithMyTeam.get(i).getAwayPoints()));
				model.addAttribute(String.format("teamNameAway%d", i), matchesWithMyTeam.get(i).getAway());
			}
		}

		return "registerMatch";
	}

	@GetMapping("/TenniShip/RegisterMatch/Tournament")
	public String selectTournament(Model model) {

		
		String team = userComponent.getTeam();
		
		List<Tournament> tournaments = tournamentRepository.findAll();
		Optional<Team> t = teamRepository.findById(team);

		List<String> tournamentsWithMyTeam = new ArrayList<>();
		
		model.addAttribute("registered", userComponent.getLoggedUser());
		model.addAttribute("team", team);

		if (t.isPresent()) {
			tournaments.forEach(Tournament -> {
				if (Tournament.getTournamentTeams().contains(t.get()))
					tournamentsWithMyTeam.add(Tournament.getName());
			});
			
			model.addAttribute("listTournaments", tournamentsWithMyTeam);
			
			for (int i = 0; i < Math.min(tournamentsWithMyTeam.size(), 6); i++) {
				model.addAttribute(String.format("tournamentName%d", i), tournamentsWithMyTeam.get(i));
			}
//			if (tournamentsWithMyTeam.size() < 6) {
//				for (int i = tournamentsWithMyTeam.size(); i < 6; i++) {
//					model.addAttribute(String.format("tournamentName%d", i), "Empty");
//				}
//			}
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
	public String create (Model model,@PathVariable String team) {

		Optional <Team> t = teamRepository.findById(team);

		if(t.isPresent()) {
			String tournamentName;
			tournamentName = "CopaDavis";//cambiar futuro
			List<Team> teamsProvisional = new ArrayList<>();
			teamsProvisional.addAll(teamRepository.findAll());//futura query


			model.addAttribute("tournament", tournamentName);

			for(int i=0; i< Math.min(18, teamsProvisional.size())/*teamList.size() Poner esto*/;i++) {
	    		model.addAttribute(String.format("team%d", i),teamsProvisional.get(i).getName());
	    	}
		}

		return "tournamentCreator";
	}

	@GetMapping("/TenniShip")
	public String index (Model model) {
		
		if(userComponent.isLoggedUser()) {
			String teamUser = userComponent.getTeam();
			model.addAttribute("team", teamUser);
		}
		model.addAttribute("registered",userComponent.isLoggedUser());

		return "index";
	}

	@GetMapping("/TenniShip/SignIn")
	public String sign_in (Model model) {


		return "login";
	}
 	
	@GetMapping("/TenniShip/SignUp")
	public String sign_up (Model model) {


		return "registerAccount";
	}
}
