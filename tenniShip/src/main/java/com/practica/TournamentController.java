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
import com.practica.model.Match;

@Controller
public class TournamentController {
	
	@Autowired
	private TournamentRepository tournamentRepository;
	
	@Autowired
	private TeamRepository teamRepository;
	

	@GetMapping("/Tournament/{tournament}")
    public String tournament(Model model, @PathVariable String tournament) {
		
		Optional <Tournament> t = tournamentRepository.findById(tournament);
		
        if(t.isPresent()) {
        	model.addAttribute("tournamentName", t.get().getName());
        	List<Team> teamList = t.get().getTournamentTeams();
        	for(int i=0; i< teamList.size();i++) {
        		model.addAttribute(String.format("team%d", i),teamList.get(i).getName());
        	}
        }

        return "tournamentSheet";
    }
	
	
	@GetMapping("/{tournament}/SelectMatch/{team}")
	public String selectMatch (Model model, @PathVariable String tournament, @PathVariable String team) {
		Optional <Tournament> t = tournamentRepository.findById(tournament);
		List<Match> matchesWithMyTeam = new ArrayList<>();

		if(t.isPresent()) {
			t.get().getTournamentMatchs().forEach(Match->{
				if (Match.getHome().equals(team) || Match.getAway().equals(team)) 
					matchesWithMyTeam.add(Match);
			});
			for(int i =0;i<Math.min(matchesWithMyTeam.size(), 3);i++) {
				model.addAttribute(String.format("teamNameHome%d", i), matchesWithMyTeam.get(i).getHome());
				model.addAttribute(String.format("teamHomePoints%d", i), Integer.toString(matchesWithMyTeam.get(i).getHomePoints()));
				model.addAttribute(String.format("teamAwayPoints%d", i), Integer.toString(matchesWithMyTeam.get(i).getAwayPoints()));
				model.addAttribute(String.format("teamNameAway%d", i), matchesWithMyTeam.get(i).getAway());
			}
		}
			
		return "registerMatch";
	}
	
	
	@GetMapping("/SelectTournament/{team}")
	public String selectTournament (Model model, @PathVariable String team) {
				
		List<Tournament> tournaments = tournamentRepository.findAll(); 
		Optional <Team> t = teamRepository.findById(team); 
		
		List<Tournament> tournamentsWithMyTeam = new ArrayList<>();
		
		if(t.isPresent()) {
			tournaments.forEach(Tournament->{
				if(Tournament.getTournamentTeams().contains(t.get()))
					tournamentsWithMyTeam.add(Tournament);
			});
			model.addAttribute("currentTeam", team);
			for(int i =0;i<Math.min(tournamentsWithMyTeam.size(), 6);i++) {
				model.addAttribute(String.format("tournamentName%d", i), tournamentsWithMyTeam.get(i).getName());
			}
		}
		return "selectTournament";
	}
	
	protected void raffleGP (Model model) {
		List <String> teams = teamRepository.findAllTeams();
		Collections.shuffle(teams);
		for(int i=0;i<18;i++) {
			model.addAttribute(String.format("team%d",i),teams.get(i));
		}
	}
	
}





