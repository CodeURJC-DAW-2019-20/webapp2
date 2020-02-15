package com.practica;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.practica.model.Match;
import com.practica.model.Player;
import com.practica.model.Team;
import com.practica.model.Tournament;

@Controller
public class TeamController {

	@Autowired
	private TournamentRepository tournamentRepository;
	
	@Autowired
	private TeamRepository teamRepository;
	
	private static int i = 0; //Matches Played Iterator
		
	
	@GetMapping("/Team/{team}")
	public String team(Model model, @PathVariable String team) {
		
		Optional<Team> t = teamRepository.findById(team);

        if (t.isPresent()) {
        	model.addAttribute("teamName", t.get().getName());
        	
        	List<Player> players = t.get().getPlayers();
        	for (int i = 0; i < 5; i++) {
        		model.addAttribute(String.format("player%d", i), players.get(i).getName());
        	}
        	
        	List<Tournament> tournaments = tournamentRepository.findAll();
        	List<Tournament> tournamentsWithMyTeam = new ArrayList<>();
        	
        	tournaments.forEach(Tournament -> {
				if (Tournament.getTournamentTeams().contains(t.get()))
					tournamentsWithMyTeam.add(Tournament);
			});
        	
        	tournamentsWithMyTeam.forEach(Tournament -> {
				Tournament.getTournamentMatchs().forEach(Match -> {
					if ((i < 10) && (Match.getHome().equals(team) || Match.getAway().equals(team))) {
						model.addAttribute(String.format("teamNameHome%d", i), Match.getHome());
						model.addAttribute(String.format("teamNameAway%d", i), Match.getAway());
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
        	i = 0;
        }
		return "teamfile";
	}
	
}