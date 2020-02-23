package com.practica;

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

import com.practica.model.Match;
import com.practica.model.Player;
import com.practica.model.Team;
import com.practica.security.UserComponent;


@Controller
public class TeamController {
	
	@Autowired
	private TeamRepository teamRepository;
	
	@Autowired
	private UserComponent userComponent;
	
	@PostMapping("/TenniShip/SearchTeam")
	public String searchTeam(Model model, @RequestParam String teamName) {
		
		Optional<Team> t = teamRepository.findById(teamName);
		
		if (t.isPresent()) {
			return "redirect:/TenniShip/Team/"+teamName;
		} else {
			return "redirect:/TenniShip";
		}	
	} 
	
	@GetMapping("/TenniShip/Team/{team}")
	public String team(Model model, @PathVariable String team, HttpServletRequest request) {
		
		Optional<Team> t = teamRepository.findById(team);
		
		if(userComponent.isLoggedUser()  && !request.isUserInRole("ADMIN")) {
			String teamUser = userComponent.getTeam();
			model.addAttribute("team", teamUser);
		}
		model.addAttribute("registered",userComponent.isLoggedUser() && !request.isUserInRole("ADMIN") );
		model.addAttribute("admin", userComponent.isLoggedUser() && request.isUserInRole("ADMIN"));

        if (t.isPresent()) {	/* If there's a team with that name, we show them the team page */
        	
        	double totalMatchesLost = 0.0;
    		double totalMatchesWon = 0.0;
    		double totalMatches = 0.0;
    		
        	model.addAttribute("teamName", t.get().getName());

            List <String> tournamentsList = teamRepository.getTournamentsName(t.get());
            model.addAttribute("imageListTournaments",tournamentsList);

        	totalMatchesLost=teamRepository.getMatchesLost(t.get().getName());
        	totalMatchesWon=teamRepository.getMatchesWon(t.get().getName());
        	
        	totalMatches=totalMatchesLost+totalMatchesWon;
        	
        	totalMatchesLost = (totalMatchesLost/totalMatches)*100;
        	totalMatchesWon = (totalMatchesWon/totalMatches)*100;
        	
        	model.addAttribute("lostMatches",totalMatchesLost);        	
        	model.addAttribute("wonMatches",totalMatchesWon);

        	/* If either of these values is higher than 50, we need to indicate it to the html through a clause */
        	if (totalMatchesLost >= 50) {
        		model.addAttribute("over50lost", true);
			}
			if (totalMatchesWon >= 50) {
				model.addAttribute("over50won", true);
			}
        	
        	List<Player> players = t.get().getPlayers();
        	for (int i = 0; i < 5; i++) {
        		model.addAttribute(String.format("player%d", i), players.get(i).getName());
        	}

			List<Match> recentMatches = teamRepository.getRecentMatches(t.get());
			if (!recentMatches.isEmpty()) {
				model.addAttribute("matchHistory", true);
				model.addAttribute("matchList", recentMatches);
			}

			return "teamfile";
        }
        else {
        	model.addAttribute("teamName", team);
        	List<Team> results = teamRepository.findSimilarTeams(team);
        	if (!results.isEmpty()) {
        		List<String> names = new ArrayList<>();
				model.addAttribute("results", true);
				for (Team i : results) {
					names.add(i.getName());
				}
				model.addAttribute("resultsList", names);
			}
        	return "teamResults";
		}

	}	
}
