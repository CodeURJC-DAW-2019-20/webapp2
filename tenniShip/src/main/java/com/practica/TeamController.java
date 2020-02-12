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
	

	@PostConstruct
	public void init() {
		Team team1 = new Team("Spain");
		team1.getPlayers().add(new Player("a"));
		team1.getPlayers().add(new Player("b"));
		team1.getPlayers().add(new Player("c"));
		team1.getPlayers().add(new Player("d"));
		team1.getPlayers().add(new Player("e"));

		Team team2 = new Team("France");
		team2.getPlayers().add(new Player("a"));
		team2.getPlayers().add(new Player("b"));
		team2.getPlayers().add(new Player("c"));
		team2.getPlayers().add(new Player("d"));
		team2.getPlayers().add(new Player("e"));

		Team team3 = new Team("Serbia");
		team3.getPlayers().add(new Player("a"));
		team3.getPlayers().add(new Player("b"));
		team3.getPlayers().add(new Player("c"));
		team3.getPlayers().add(new Player("d"));
		team3.getPlayers().add(new Player("e"));

		Team team4 = new Team("Italy");
		team4.getPlayers().add(new Player("a"));
		team4.getPlayers().add(new Player("b"));
		team4.getPlayers().add(new Player("c"));
		team4.getPlayers().add(new Player("d"));
		team4.getPlayers().add(new Player("e"));

		Team team5 = new Team("Norway");
		team5.getPlayers().add(new Player("a"));
		team5.getPlayers().add(new Player("b"));
		team5.getPlayers().add(new Player("c"));
		team5.getPlayers().add(new Player("d"));
		team5.getPlayers().add(new Player("e"));

		Team team6 = new Team("Australia");
		team6.getPlayers().add(new Player("a"));
		team6.getPlayers().add(new Player("b"));
		team6.getPlayers().add(new Player("c"));
		team6.getPlayers().add(new Player("d"));
		team6.getPlayers().add(new Player("e"));

		Team team7 = new Team("Brazil");
		team7.getPlayers().add(new Player("a"));
		team7.getPlayers().add(new Player("b"));
		team7.getPlayers().add(new Player("c"));
		team7.getPlayers().add(new Player("d"));
		team7.getPlayers().add(new Player("e"));

		Team team8 = new Team("Argentina");
		team8.getPlayers().add(new Player("a"));
		team8.getPlayers().add(new Player("b"));
		team8.getPlayers().add(new Player("c"));
		team8.getPlayers().add(new Player("d"));
		team8.getPlayers().add(new Player("e"));

		Team team9 = new Team("Canada");
		team9.getPlayers().add(new Player("a"));
		team9.getPlayers().add(new Player("b"));
		team9.getPlayers().add(new Player("c"));
		team9.getPlayers().add(new Player("d"));
		team9.getPlayers().add(new Player("e"));

		Team team10 = new Team("Mexico");
		team10.getPlayers().add(new Player("a"));
		team10.getPlayers().add(new Player("b"));
		team10.getPlayers().add(new Player("c"));
		team10.getPlayers().add(new Player("d"));
		team10.getPlayers().add(new Player("e"));

		Team team11 = new Team("UnitedStates");
		team11.getPlayers().add(new Player("a"));
		team11.getPlayers().add(new Player("b"));
		team11.getPlayers().add(new Player("c"));
		team11.getPlayers().add(new Player("d"));
		team11.getPlayers().add(new Player("e"));
		
		Team team12 = new Team("China");
		team12.getPlayers().add(new Player("a"));
		team12.getPlayers().add(new Player("b"));
		team12.getPlayers().add(new Player("c"));
		team12.getPlayers().add(new Player("d"));
		team12.getPlayers().add(new Player("e"));

		Team team13 = new Team("Japan");
		team13.getPlayers().add(new Player("a"));
		team13.getPlayers().add(new Player("b"));
		team13.getPlayers().add(new Player("c"));
		team13.getPlayers().add(new Player("d"));
		team13.getPlayers().add(new Player("e"));

		Team team14 = new Team("Russia");
		team14.getPlayers().add(new Player("a"));
		team14.getPlayers().add(new Player("b"));
		team14.getPlayers().add(new Player("c"));
		team14.getPlayers().add(new Player("d"));
		team14.getPlayers().add(new Player("e"));

		Team team15 = new Team("Germany");
		team15.getPlayers().add(new Player("a"));
		team15.getPlayers().add(new Player("b"));
		team15.getPlayers().add(new Player("c"));
		team15.getPlayers().add(new Player("d"));
		team15.getPlayers().add(new Player("e"));

		Team team16 = new Team("Denmark");
		team16.getPlayers().add(new Player("a"));
		team16.getPlayers().add(new Player("b"));
		team16.getPlayers().add(new Player("c"));
		team16.getPlayers().add(new Player("d"));
		team16.getPlayers().add(new Player("e"));

		Team team17 = new Team("Finland");
		team17.getPlayers().add(new Player("a"));
		team17.getPlayers().add(new Player("b"));
		team17.getPlayers().add(new Player("c"));
		team17.getPlayers().add(new Player("hola Ivan"));
		team17.getPlayers().add(new Player("e"));

		Team team18 = new Team("SouthAfrica");
		team18.getPlayers().add(new Player("a"));
		team18.getPlayers().add(new Player("b"));
		team18.getPlayers().add(new Player("c"));
		team18.getPlayers().add(new Player("d"));
		team18.getPlayers().add(new Player("e"));

		teamRepository.save(team1); teamRepository.save(team10);
		teamRepository.save(team2); teamRepository.save(team11);
		teamRepository.save(team3); teamRepository.save(team12);
		teamRepository.save(team4); teamRepository.save(team13);
		teamRepository.save(team5); teamRepository.save(team14);
		teamRepository.save(team6); teamRepository.save(team15);
		teamRepository.save(team7); teamRepository.save(team16);
		teamRepository.save(team8); teamRepository.save(team17);
		teamRepository.save(team9); teamRepository.save(team18);
	}
	
	@GetMapping("/Team/{team}")
	public String team(Model model, @PathVariable String team) {
		
		Optional<Team> t = teamRepository.findById(team);

        if (t.isPresent()) {
        	model.addAttribute("teamName", t.get().getName());
        	List<Player> players = t.get().getPlayers();
        	for (int i = 0; i < 5; i++) {
        		model.addAttribute(String.format("player%d", i), players.get(i).getName());
        	}
        }
		return "teamfile";
	}
	
}
