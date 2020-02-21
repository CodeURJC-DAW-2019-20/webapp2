package com.practica;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.practica.model.Team;
import com.practica.model.Tournament;
import com.practica.security.UserComponent;
import com.practica.security.UserController;
import com.practica.model.Match;

@Controller
public class TournamentController {

	@Autowired
	private TournamentRepository tournamentRepository;

	@Autowired
	private MatchRepository matchRepository;

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private UserComponent userComponent;
	

	@PostMapping("/TenniShip/RegisterMatch/Tournament/{tournament}/Submission")
	public String submitMatch(Model model, @PathVariable String tournament, @RequestParam String teamHome,
			@RequestParam String teamAway, @RequestParam int quantityHome, @RequestParam int quantityAway) throws InterruptedException {
		
		if (quantityHome == 3 ^ quantityAway == 3) { //XOR
			Optional<Team> home = teamRepository.findById(teamHome);
			Optional<Team> away = teamRepository.findById(teamAway);
			Optional<Tournament> t = tournamentRepository.findById(tournament);
			
			Match match = matchRepository.findMatch(home.get(), away.get(), t.get()).get(0);
			
			matchRepository.getOne(match.getId()).setHomePoints(quantityHome);
			matchRepository.getOne(match.getId()).setAwayPoints(quantityAway);
			
			matchRepository.save(match); 
		} else {
			model.addAttribute("error", true);
			TimeUnit.SECONDS.sleep(4);
		}

		return "redirect:/TenniShip/RegisterMatch/Tournament/"+ tournament;
	} 
	
	public void raffleTeamsCreateMatches(Tournament tournament, List<Team> teams) {
		//requiere haber comprobado el string del torneo esta libre y si los string de los equipos existen
		//una vez hecho esto, cada dato tuvo que ser guardado en sus respectivos repositorios
		
		Collections.shuffle(teams);
		
		Match m1 = new Match(0, 0, "A"); m1.setTeam1(teams.get(0)); m1.setTeam2(teams.get(2)); m1.setTournament(tournament); matchRepository.save(m1);
		Match m2 = new Match(0, 0, "A"); m2.setTeam1(teams.get(2)); m2.setTeam2(teams.get(1)); m2.setTournament(tournament); matchRepository.save(m2);
		Match m3 = new Match(0, 0, "A"); m3.setTeam1(teams.get(1)); m3.setTeam2(teams.get(0)); m3.setTournament(tournament); matchRepository.save(m3);
		Match m4 = new Match(0, 0, "B"); m4.setTeam1(teams.get(3)); m4.setTeam2(teams.get(4)); m4.setTournament(tournament); matchRepository.save(m4);
		Match m5 = new Match(0, 0, "B"); m5.setTeam1(teams.get(5)); m5.setTeam2(teams.get(3)); m5.setTournament(tournament); matchRepository.save(m5);
		Match m6 = new Match(0, 0, "B"); m6.setTeam1(teams.get(4)); m6.setTeam2(teams.get(5)); m6.setTournament(tournament); matchRepository.save(m6);
		Match m7 = new Match(0, 0, "C"); m7.setTeam1(teams.get(6)); m7.setTeam2(teams.get(7)); m7.setTournament(tournament); matchRepository.save(m7);
		Match m8 = new Match(0, 0, "C"); m8.setTeam1(teams.get(8)); m8.setTeam2(teams.get(6)); m8.setTournament(tournament); matchRepository.save(m8);
		Match m9 = new Match(0, 0, "C"); m9.setTeam1(teams.get(7)); m9.setTeam2(teams.get(8)); m9.setTournament(tournament); matchRepository.save(m9);
		Match m10 = new Match(0, 0, "D"); m10.setTeam1(teams.get(9)); m10.setTeam2(teams.get(10)); m10.setTournament(tournament); matchRepository.save(m10);
		Match m11 = new Match(0, 0, "D"); m11.setTeam1(teams.get(11)); m11.setTeam2(teams.get(9)); m11.setTournament(tournament); matchRepository.save(m11);
		Match m12 = new Match(0, 0, "D"); m12.setTeam1(teams.get(10)); m12.setTeam2(teams.get(11)); m12.setTournament(tournament); matchRepository.save(m12);
		Match m13 = new Match(0, 0, "E"); m13.setTeam1(teams.get(12)); m13.setTeam2(teams.get(13)); m13.setTournament(tournament); matchRepository.save(m13);
		Match m14 = new Match(0, 0, "E"); m14.setTeam1(teams.get(14)); m14.setTeam2(teams.get(12)); m14.setTournament(tournament); matchRepository.save(m14);
		Match m15 = new Match(0, 0, "E"); m15.setTeam1(teams.get(13)); m15.setTeam2(teams.get(14)); m15.setTournament(tournament); matchRepository.save(m15);
		Match m16 = new Match(0, 0, "F"); m16.setTeam1(teams.get(15)); m16.setTeam2(teams.get(16)); m16.setTournament(tournament); matchRepository.save(m16);
		Match m17 = new Match(0, 0, "F"); m17.setTeam1(teams.get(17)); m17.setTeam2(teams.get(15)); m17.setTournament(tournament); matchRepository.save(m17);
		Match m18 = new Match(0, 0, "F"); m18.setTeam1(teams.get(16)); m18.setTeam2(teams.get(17)); m18.setTournament(tournament); matchRepository.save(m18);
	}

	@GetMapping("/TenniShip/RegisterMatch/Tournament/{tournament}")
	public String selectMatch(Model model, @PathVariable String tournament) {

		String team = userComponent.getTeam();

		Optional<Tournament> t = tournamentRepository.findById(tournament);//check if that team play this tournament
		Optional<Team> tm = teamRepository.findById(team);
		
		HashMap<String, String> rounds = new HashMap<>(); rounds.put("A", "Group Stage"); rounds.put("B", "Group Stage");
		rounds.put("C", "Group Stage"); rounds.put("D", "Group Stage"); rounds.put("E", "Group Stage"); rounds.put("F", "Group Stage");
		rounds.put("X", "Round of 8"); rounds.put("Y", "Round of 4"); rounds.put("Z", "Final");

		if (t.isPresent()) {
			if (tournamentRepository.getNextMatches(t.get(), tm.get()).isEmpty()) {
				model.addAttribute("round", "All Played");
			} else {
				model.addAttribute("round", rounds.get(tournamentRepository.getNextMatches(t.get(), tm.get()).get(0).getType()));
			}
			model.addAttribute("listMatches", tournamentRepository.getNextMatches(t.get(), tm.get()));	
		}

		return "registerMatch";
	}

	@GetMapping("/TenniShip/RegisterMatch/Tournament")
	public String selectTournament(Model model) {

		String team = userComponent.getTeam();

		Optional<Team> t = teamRepository.findById(team);

        model.addAttribute("registered", userComponent.getLoggedUser());
        model.addAttribute("team", team);

		if (t.isPresent()) {
			model.addAttribute("listTournaments", teamRepository.getTournaments(t.get()));
		}
		return "selectTournament";
	}

	@GetMapping("/{team}/Creator")
	public String create (Model model, @PathVariable String team) {

		Optional <Team> t = teamRepository.findById(team);

		if(t.isPresent()) {
			String tournamentName;
			tournamentName = "CopaDavis"; //cambiar futuro
			List<Team> teamsProvisional = new ArrayList<>();
			teamsProvisional.addAll(teamRepository.findAll()); //futura query


			model.addAttribute("tournament", tournamentName);

			for(int i=0; i< Math.min(18, teamsProvisional.size())/*teamList.size() Poner esto*/;i++) {
	    		model.addAttribute(String.format("team%d", i),teamsProvisional.get(i).getName());
	    	}
		}

		return "tournamentCreator";
	}

}
