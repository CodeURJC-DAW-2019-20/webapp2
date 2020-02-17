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
		
		//
		//
		//---------------------TEAMS ZONE
		//
		//

		Team team1 = new Team("Spain"); team1.getPlayers().addAll(playerGenerator()); teamRepository.save(team1);
        Team team2 = new Team("France"); team2.getPlayers().addAll(playerGenerator()); teamRepository.save(team2);
        Team team3 = new Team("Serbia"); team3.getPlayers().addAll(playerGenerator()); teamRepository.save(team3);
        Team team4 = new Team("Italy"); team4.getPlayers().addAll(playerGenerator()); teamRepository.save(team4);
        Team team5 = new Team("Norway"); team5.getPlayers().addAll(playerGenerator()); teamRepository.save(team5);
        Team team6 = new Team("Australia"); team6.getPlayers().addAll(playerGenerator()); teamRepository.save(team6);
        Team team7 = new Team("Brazil"); team7.getPlayers().addAll(playerGenerator()); teamRepository.save(team7);
        Team team8 = new Team("Argentina"); team8.getPlayers().addAll(playerGenerator()); teamRepository.save(team8);
        Team team9 = new Team("Canada"); team9.getPlayers().addAll(playerGenerator()); teamRepository.save(team9);
        Team team10 = new Team("Mexico"); team10.getPlayers().addAll(playerGenerator()); teamRepository.save(team10);
        Team team11 = new Team("UnitedStates"); team11.getPlayers().addAll(playerGenerator()); teamRepository.save(team11);
        Team team12 = new Team("China"); team12.getPlayers().addAll(playerGenerator()); teamRepository.save(team12);
        Team team13 = new Team("Japan"); team13.getPlayers().addAll(playerGenerator()); teamRepository.save(team13);
        Team team14 = new Team("Russia"); team14.getPlayers().addAll(playerGenerator()); teamRepository.save(team14);
        Team team15 = new Team("Germany"); team15.getPlayers().addAll(playerGenerator()); teamRepository.save(team15);
        Team team16 = new Team("Denmark"); team16.getPlayers().addAll(playerGenerator()); teamRepository.save(team16);
        Team team17 = new Team("Finland"); team17.getPlayers().addAll(playerGenerator()); teamRepository.save(team17);
        Team team18 = new Team("SouthAfrica"); team18.getPlayers().addAll(playerGenerator()); teamRepository.save(team18);
        
        //Team1B not created to have Team1 in two tournaments 
        //Team2B not created to have Team2 in two tournaments
        Team team3B = new Team("SerbiaB"); team3B.getPlayers().addAll(playerGenerator()); teamRepository.save(team3B);
        Team team4B = new Team("ItalyB"); team4B.getPlayers().addAll(playerGenerator()); teamRepository.save(team4B);
        Team team5B = new Team("NorwayB"); team5B.getPlayers().addAll(playerGenerator()); teamRepository.save(team5B);
        Team team6B = new Team("AustraliaB"); team6B.getPlayers().addAll(playerGenerator()); teamRepository.save(team6B);
        Team team7B = new Team("BrazilB"); team7B.getPlayers().addAll(playerGenerator()); teamRepository.save(team7B);
        Team team8B = new Team("ArgentinaB"); team8B.getPlayers().addAll(playerGenerator()); teamRepository.save(team8B);
        Team team9B = new Team("CanadaB"); team9B.getPlayers().addAll(playerGenerator()); teamRepository.save(team9B);
        Team team10B = new Team("MexicoB"); team10B.getPlayers().addAll(playerGenerator()); teamRepository.save(team10B);
        Team team11B = new Team("UnitedStatesB"); team11B.getPlayers().addAll(playerGenerator()); teamRepository.save(team11B);
        Team team12B = new Team("ChinaB"); team12B.getPlayers().addAll(playerGenerator()); teamRepository.save(team12B);
        Team team13B = new Team("JapanB"); team13B.getPlayers().addAll(playerGenerator()); teamRepository.save(team13B);
        Team team14B = new Team("RussiaB"); team14B.getPlayers().addAll(playerGenerator()); teamRepository.save(team14B);
        Team team15B = new Team("GermanyB"); team15B.getPlayers().addAll(playerGenerator()); teamRepository.save(team15B);
        Team team16B = new Team("DenmarkB"); team16B.getPlayers().addAll(playerGenerator()); teamRepository.save(team16B);
        Team team17B = new Team("FinlandB"); team17B.getPlayers().addAll(playerGenerator()); teamRepository.save(team17B);
        Team team18B = new Team("SouthAfricaB"); team18B.getPlayers().addAll(playerGenerator()); teamRepository.save(team18B);
		
		//
		//
		//---------------------TOURNAMENTS ZONE
		//
		//
				
		Tournament tournament1 = new Tournament("DavisCup"); tournamentRepository.save(tournament1);
		Tournament tournament2 = new Tournament("TestCup");  tournamentRepository.save(tournament2);
		
		//
		//
		//---------------------MATCHES ZONE
		//
		//

		Match m1 = new Match(3, 0); m1.setTeam1(team1); m1.setTeam1(team3); m1.setTournament(tournament1); matchRepository.save(m1);
		Match m2 = new Match(3, 0); m2.setTeam1(team3); m2.setTeam1(team2); m2.setTournament(tournament1); matchRepository.save(m2);
		Match m3 = new Match(3, 0); m3.setTeam1(team2); m3.setTeam1(team1); m3.setTournament(tournament1); matchRepository.save(m3);	
		Match m4 = new Match(3, 0); m4.setTeam1(team4); m4.setTeam1(team5); m4.setTournament(tournament1); matchRepository.save(m4);
		Match m5 = new Match(3, 0); m5.setTeam1(team6); m5.setTeam1(team4); m5.setTournament(tournament1); matchRepository.save(m5);
		Match m6 = new Match(3, 0); m6.setTeam1(team5); m6.setTeam1(team6); m6.setTournament(tournament1); matchRepository.save(m6);			
		Match m7 = new Match(3, 0); m7.setTeam1(team7); m7.setTeam1(team8); m7.setTournament(tournament1); matchRepository.save(m7);
		Match m8 = new Match(3, 0); m8.setTeam1(team9); m8.setTeam1(team7); m8.setTournament(tournament1); matchRepository.save(m8);
		Match m9 = new Match(3, 0); m9.setTeam1(team8); m9.setTeam1(team9); m9.setTournament(tournament1); matchRepository.save(m9);		
		Match m10 = new Match(3, 0); m10.setTeam1(team10); m10.setTeam1(team11); m10.setTournament(tournament1); matchRepository.save(m10);
		Match m11 = new Match(3, 0); m11.setTeam1(team12); m11.setTeam1(team10); m11.setTournament(tournament1); matchRepository.save(m11);
		Match m12 = new Match(3, 0); m12.setTeam1(team11); m12.setTeam1(team12); m12.setTournament(tournament1); matchRepository.save(m12);		
		Match m13 = new Match(3, 0); m13.setTeam1(team13); m13.setTeam1(team14); m13.setTournament(tournament1); matchRepository.save(m13);
		Match m14 = new Match(3, 0); m14.setTeam1(team15); m14.setTeam1(team13); m14.setTournament(tournament1); matchRepository.save(m14);
		Match m15 = new Match(3, 0); m15.setTeam1(team14); m15.setTeam1(team15); m15.setTournament(tournament1); matchRepository.save(m15);		
		Match m16 = new Match(3, 0); m16.setTeam1(team16); m16.setTeam1(team17); m16.setTournament(tournament1); matchRepository.save(m16);
		Match m17 = new Match(3, 0); m17.setTeam1(team18); m17.setTeam1(team16); m17.setTournament(tournament1); matchRepository.save(m17);
		Match m18 = new Match(3, 0); m18.setTeam1(team17); m18.setTeam1(team18); m18.setTournament(tournament1); matchRepository.save(m18);
		
		Match m1B = new Match(3, 0); m1B.setTeam1(team1); m1B.setTeam1(team3B); m1B.setTournament(tournament2); matchRepository.save(m1B);
		Match m2B = new Match(3, 0); m2B.setTeam1(team3B); m2B.setTeam1(team2); m2B.setTournament(tournament2); matchRepository.save(m2B);
		Match m3B = new Match(3, 0); m3B.setTeam1(team2); m3B.setTeam1(team1); m3B.setTournament(tournament2); matchRepository.save(m3B);
		Match m4B = new Match(3, 0); m4B.setTeam1(team4B); m4B.setTeam1(team5B); m4B.setTournament(tournament2); matchRepository.save(m4B);
		Match m5B = new Match(3, 0); m5B.setTeam1(team6B); m5B.setTeam1(team4B); m5B.setTournament(tournament2); matchRepository.save(m5B);
		Match m6B = new Match(3, 0); m6B.setTeam1(team5B); m6B.setTeam1(team6B); m6B.setTournament(tournament2); matchRepository.save(m6B);			
		Match m7B = new Match(3, 0); m7B.setTeam1(team7B); m7B.setTeam1(team8B); m7B.setTournament(tournament2); matchRepository.save(m7B);
		Match m8B = new Match(3, 0); m8B.setTeam1(team9B); m8B.setTeam1(team7B); m8B.setTournament(tournament2); matchRepository.save(m8B);
		Match m9B = new Match(3, 0); m9B.setTeam1(team8B); m9B.setTeam1(team9B); m9B.setTournament(tournament2); matchRepository.save(m9B);		
		Match m10B = new Match(3, 0); m10B.setTeam1(team10B); m10B.setTeam1(team11B); m10B.setTournament(tournament2); matchRepository.save(m10B);
		Match m11B = new Match(3, 0); m11B.setTeam1(team12B); m11B.setTeam1(team10B); m11B.setTournament(tournament2); matchRepository.save(m11B);
		Match m12B = new Match(3, 0); m12B.setTeam1(team11B); m12B.setTeam1(team12B); m12B.setTournament(tournament2); matchRepository.save(m12B);		
		Match m13B = new Match(3, 0); m13B.setTeam1(team13B); m13B.setTeam1(team14B); m13B.setTournament(tournament2); matchRepository.save(m13B);
		Match m14B = new Match(3, 0); m14B.setTeam1(team15B); m14B.setTeam1(team13B); m14B.setTournament(tournament2); matchRepository.save(m14B);
		Match m15B = new Match(3, 0); m15B.setTeam1(team14B); m15B.setTeam1(team15B); m15B.setTournament(tournament2); matchRepository.save(m15B);	
		Match m16B = new Match(3, 0); m16B.setTeam1(team16B); m16B.setTeam1(team17B); m16B.setTournament(tournament2); matchRepository.save(m16B);
		Match m17B = new Match(3, 0); m17B.setTeam1(team18B); m17B.setTeam1(team16B); m17B.setTournament(tournament2); matchRepository.save(m17B);
		Match m18B = new Match(3, 0); m18B.setTeam1(team17B); m18B.setTeam1(team18B); m18B.setTournament(tournament2); matchRepository.save(m18B);		
	}	
}
