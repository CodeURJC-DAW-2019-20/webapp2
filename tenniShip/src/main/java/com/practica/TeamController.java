package com.practica;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.model.Player;
import com.model.Team;

@Controller
public class TeamController {

	
	@Autowired
	private TeamRepository teamRepository;
	
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@PostConstruct
	public void init() {
		Team team1 = new Team("Real Madrid");
		team1.getPlayers().add(new Player("pepe"));
		team1.getPlayers().add(new Player("Rosa"));
		
		teamRepository.save(team1);
	
	}
	
	
//	@GetMapping("/Team")
//	public String team(Model model) {
//		
//		Team team1 = teamRepository.findAll().get(0);
//		
//		model.addAttribute("teamName", team1.name);
//		return "teamfile";
//	}
	
	
	@GetMapping("/Team")
	public List<Team> team() {
		
		return teamRepository.findAll();
	}
	
}
