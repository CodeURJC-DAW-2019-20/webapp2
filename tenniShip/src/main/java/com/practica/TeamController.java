package com.practica;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.practica.model.Match;
import com.practica.model.Player;
import com.practica.model.Team;
import com.practica.model.Tournament;


@Controller
public class TeamController {
	
	@Autowired
	private TeamRepository teamRepository;
	
	@Autowired
	private MatchRepository matchRepository;	
	
	@Autowired
	private ImageService imgService;
	
	private static int i = 0; //Matches Played Iterator
		
	
	@GetMapping("/TenniShip/Team/{team}")
	public String team(Model model, @PathVariable String team) {
		
		Optional<Team> t = teamRepository.findById(team);
		double totalMatches=0;
		double totalMatchesLost=0;
	    double totalMatchesWon=0;

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
	
	@PostMapping("/RegisterAccount/Saved")
	public String newTeam(Model model, Team team, @RequestParam MultipartFile imageFile) throws IOException {
		team.setImage(true);
		teamRepository.save(team);
		imgService.saveImage("teams", team.getName(), imageFile);
		return "good";
	}
	
}
