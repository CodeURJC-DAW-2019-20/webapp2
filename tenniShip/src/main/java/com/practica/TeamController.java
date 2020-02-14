package com.practica;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.practica.model.Player;
import com.practica.model.Team;

@Controller
public class TeamController {

	
	@Autowired
	private TeamRepository teamRepository;
		
	
	@GetMapping("/Team/{team}")
	public String team(Model model, @PathVariable String team) {
		
		Optional<Team> t = teamRepository.findById(team);

        if (t.isPresent()) {
        	model.addAttribute("teamName", t.get().getName());
        	
        	List<Player> players = t.get().getPlayers();
        	for (int i = 0; i < 5; i++) {
        		model.addAttribute(String.format("player%d", i), players.get(i).getName());
        	}
        	
        	//añadir al html de Team Sheet los partidos disputados por ese equipo en vez de la lista de partidos estática
        }
		return "teamfile";
	}
	
}
