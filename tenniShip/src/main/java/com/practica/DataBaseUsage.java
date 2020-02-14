package com.practica;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import com.practica.model.Match;
import com.practica.model.Player;
import com.practica.model.Team;
import com.practica.model.Tournament;

@Controller
public class DataBaseUsage implements CommandLineRunner{

	@Autowired
	private TournamentRepository tournamentRepository;
	
	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private MatchRepository matchRepository;
	
	
	private static LinkedList<Player> playerGenerator () {
        LinkedList<Player> list = new LinkedList<>();
        list.add(new Player("a"));
        list.add(new Player("b"));
        list.add(new Player("c"));
        list.add(new Player("d"));
        list.add(new Player("e"));
        return list;
    }
	@Override
	public void run(String... args) throws Exception {
		
		Team team1 = new Team("Spain");
        team1.getPlayers().addAll(playerGenerator());
        Team team2 = new Team("France");
        team2.getPlayers().addAll(playerGenerator());
        Team team3 = new Team("Serbia");
        team3.getPlayers().addAll(playerGenerator());
        Team team4 = new Team("Italy");
        team4.getPlayers().addAll(playerGenerator());
        Team team5 = new Team("Norway");
        team5.getPlayers().addAll(playerGenerator());
        Team team6 = new Team("Australia");
        team6.getPlayers().addAll(playerGenerator());
        Team team7 = new Team("Brazil");
        team7.getPlayers().addAll(playerGenerator());
        Team team8 = new Team("Argentina");
        team8.getPlayers().addAll(playerGenerator());
        Team team9 = new Team("Canada");
        team9.getPlayers().addAll(playerGenerator());
        Team team10 = new Team("Mexico");
        team10.getPlayers().addAll(playerGenerator());
        Team team11 = new Team("UnitedStates");
        team11.getPlayers().addAll(playerGenerator());
        Team team12 = new Team("China");
        team12.getPlayers().addAll(playerGenerator());
        Team team13 = new Team("Japan");
        team13.getPlayers().addAll(playerGenerator());
        Team team14 = new Team("Russia");
        team14.getPlayers().addAll(playerGenerator());
        Team team15 = new Team("Germany");
        team15.getPlayers().addAll(playerGenerator());
        Team team16 = new Team("Denmark");
        team16.getPlayers().addAll(playerGenerator());
        Team team17 = new Team("Finland");
        team17.getPlayers().addAll(playerGenerator());
        Team team18 = new Team("SouthAfrica");
        team18.getPlayers().addAll(playerGenerator());

		teamRepository.save(team1); teamRepository.save(team10);
		teamRepository.save(team2); teamRepository.save(team11);
		teamRepository.save(team3); teamRepository.save(team12);
		teamRepository.save(team4); teamRepository.save(team13);
		teamRepository.save(team5); teamRepository.save(team14);
		teamRepository.save(team6); teamRepository.save(team15);
		teamRepository.save(team7); teamRepository.save(team16);
		teamRepository.save(team8); teamRepository.save(team17);
		teamRepository.save(team9); teamRepository.save(team18);	
		
		//Tournament 2
		Team team1B = new Team("SpainB");
        team1.getPlayers().addAll(playerGenerator());
        Team team2B = new Team("FranceB");
        team2.getPlayers().addAll(playerGenerator());
        Team team3B = new Team("SerbiaB");
        team3.getPlayers().addAll(playerGenerator());
        Team team4B = new Team("ItalyB");
        team4.getPlayers().addAll(playerGenerator());
        Team team5B = new Team("NorwayB");
        team5.getPlayers().addAll(playerGenerator());
        Team team6B = new Team("AustraliaB");
        team6.getPlayers().addAll(playerGenerator());
        Team team7B = new Team("BrazilB");
        team7.getPlayers().addAll(playerGenerator());
        Team team8B = new Team("ArgentinaB");
        team8.getPlayers().addAll(playerGenerator());
        Team team9B = new Team("CanadaB");
        team9.getPlayers().addAll(playerGenerator());
        Team team10B = new Team("MexicoB");
        team10.getPlayers().addAll(playerGenerator());
        Team team11B = new Team("UnitedStatesB");
        team11.getPlayers().addAll(playerGenerator());
        Team team12B = new Team("ChinaB");
        team12.getPlayers().addAll(playerGenerator());
        Team team13B = new Team("JapanB");
        team13.getPlayers().addAll(playerGenerator());
        Team team14B = new Team("RussiaB");
        team14.getPlayers().addAll(playerGenerator());
        Team team15B = new Team("GermanyB");
        team15.getPlayers().addAll(playerGenerator());
        Team team16B = new Team("DenmarkB");
        team16.getPlayers().addAll(playerGenerator());

		teamRepository.save(team1B); teamRepository.save(team10B);
		teamRepository.save(team2B); teamRepository.save(team11B);
		teamRepository.save(team3B); teamRepository.save(team12B);
		teamRepository.save(team4B); teamRepository.save(team13B);
		teamRepository.save(team5B); teamRepository.save(team14B);
		teamRepository.save(team6B); teamRepository.save(team15B);
		teamRepository.save(team7B); teamRepository.save(team16B);
		teamRepository.save(team8B); 
		teamRepository.save(team9B); 
				
		Tournament tournament1 = new Tournament("CopaDavis");
		Tournament tournament2 = new Tournament("CopaPrueba");
		
		teamRepository.findAll().forEach(team->{
			String letra = team.getName().substring(team.getName().length()-1);
			switch (letra) {
			case "B":
				tournament2.getTournamentTeams().add(team);
				break;

			default:
				tournament1.getTournamentTeams().add(team);
				break;
			}
		});
		
		tournament2.getTournamentTeams().add(team1);
		tournament2.getTournamentTeams().add(team2);
		
		Match m1 = new Match( team1.getName(), team2.getName(), 3, 0);
		matchRepository.save(m1);
		tournament1.getTournamentMatchs().add(m1);
		
		Match m2 = new Match( team3.getName(), team1.getName(), 3, 2);
		matchRepository.save(m2);
		tournament1.getTournamentMatchs().add(m2);	
		
		Match m3 = new Match( team1.getName(), team9.getName(), 3, 2);
		matchRepository.save(m3);
		tournament1.getTournamentMatchs().add(m3);


		tournamentRepository.save(tournament1);
		tournamentRepository.save(tournament2);	
	}	
}
