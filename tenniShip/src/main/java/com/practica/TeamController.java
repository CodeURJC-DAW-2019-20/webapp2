package com.practica;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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
import com.practica.model.Tournament;
import com.practica.security.UserController;


@Controller
public class TeamController {
	
	@Autowired
	private TeamRepository teamRepository;
	
	@Autowired
	private UserController userController;
	
	private static int i = 0; //Matches Played Iterator
		
	
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
	public String team(Model model, @PathVariable String team) {
		
		Optional<Team> t = teamRepository.findById(team);
		double totalMatchesLost = 0.0;
		double totalMatchesWon = 0.0;
		double totalMatches = 0.0;

        if (t.isPresent()) {
        	model.addAttribute("teamName", t.get().getName());
        	
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
        	i = 0; //Needed because if f5, i does not restart and forEach loop starts with i != 0

			List<Match> recentMatches = teamRepository.getRecentMatches(t.get());
			if (!recentMatches.isEmpty()) {
				model.addAttribute("matchHistory", true);
				model.addAttribute("matchList", recentMatches);
			}
        }
		return "teamfile";
	}	
}
