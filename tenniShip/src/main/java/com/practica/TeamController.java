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

        if (t.isPresent()) {
        	model.addAttribute("teamName", t.get().getName());
        	
        	List<Player> players = t.get().getPlayers();
        	for (int i = 0; i < 5; i++) {
        		model.addAttribute(String.format("player%d", i), players.get(i).getName());
        	}
        	
        	teamRepository.getTournaments(t.get()).forEach(Tournament -> {
				matchRepository.findMatches(t.get(), Tournament).forEach(Match -> {
					if (i < 10) {
						model.addAttribute(String.format("teamNameHome%d", i), Match.getTeam1().getName());
						model.addAttribute(String.format("teamNameAway%d", i), Match.getTeam2().getName());
						model.addAttribute(String.format("teamHomePoints%d", i), Integer.toString(Match.getHomePoints()));
						model.addAttribute(String.format("teamAwayPoints%d", i), Integer.toString(Match.getAwayPoints()));
						model.addAttribute(String.format("matchTournament%d", i), Tournament.getName());
						i++;
					}					
				});
			});
        	for (int j=i; j<10; j++) {
        		model.addAttribute(String.format("teamNameHome%d", j), "Empty");
				model.addAttribute(String.format("teamNameAway%d", j), "Empty");
				model.addAttribute(String.format("teamHomePoints%d", j), "0");
				model.addAttribute(String.format("teamAwayPoints%d", j), "0");
				model.addAttribute(String.format("matchTournament%d", j), "Empty");
        	}
        	i = 0; //Needed because if f5, i does not restart and forEach loop starts with i != 0
        }
		return "teamfile";
	}
	
	@PostMapping("/RegisterAccount/Saved")
	public String newTeam(Model model, Team team, @RequestParam List<MultipartFile> imageFile) throws IOException {
		team.setImage(true);
		teamRepository.save(team);
		imgService.saveImage("teams", team.getName(), imageFile.get(0));
		for(int i=1;i<=6;i++) {
			imgService.saveImage("players", team.getName()+String.format("player%d",i), imageFile.get(i));
		}
		return "good";
	}
	
}
