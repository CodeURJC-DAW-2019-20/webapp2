package com.practica;

import java.util.Optional;
import java.util.List;

import javax.annotation.PostConstruct;

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

	@Autowired
	private MatchRepository matchRepository;
	
	@PostConstruct
	public void init() {
	Tournament tournament1 = new Tournament("CopaDavis");
	teamRepository.findAll().forEach(team->{
		tournament1.getTournamentTeams().add(team);
	});
	
	Match m1 = new Match( tournament1.getTournamentTeams().get(0).getName(), tournament1.getTournamentTeams().get(1).getName(), 3, 0);
	matchRepository.save(m1);
	tournament1.getTournamentMatchs().add(m1);
	
	Match m2 = new Match( tournament1.getTournamentTeams().get(2).getName(), tournament1.getTournamentTeams().get(3).getName(), 3, 2);
	matchRepository.save(m2);
	
	Match m3 = new Match( tournament1.getTournamentTeams().get(4).getName(), tournament1.getTournamentTeams().get(5).getName(), 3, 2);
	matchRepository.save(m3);
	tournament1.getTournamentMatchs().add(m3);


	tournamentRepository.save(tournament1);
	}
	
	@GetMapping("/Tournament/{tournament}")
    public String tournament(Model model, @PathVariable String tournament) {
		
		Optional <Tournament> t = tournamentRepository.findById(tournament);
		
        if(t.isPresent()) {
        	model.addAttribute("tournamentName", t.get().getName());
        	List<Team> teamList = t.get().getTournamentTeams();
        	for(int i=0; i< teamList.size();i++) {
        		model.addAttribute(String.format("team%d", i+1),teamList.get(i).getName());
        	}
        }

        return "tournamentSheet";
    }
	
	
	
	@GetMapping("/Matches")
	public String tester (Model model) {
		
		List<Match> matches = matchRepository.findAllMatches();
		
    	
    	for(int i=0; i< matches.size();i++) {
    		model.addAttribute(String.format("teamNameHome%d", i),matches.get(i).getHome());
    		model.addAttribute(String.format("teamNameAway%d", i),matches.get(i).getAway());
    	}
		
		
		return "registerMatch";
	}
	
}





