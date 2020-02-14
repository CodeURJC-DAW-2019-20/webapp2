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
		//---------------------ZONA EQUIPOS
		//
		//

		//Tournament 1
		Team team1 = new Team("Spain"); team1.getPlayers().addAll(playerGenerator());
        Team team2 = new Team("France"); team2.getPlayers().addAll(playerGenerator());
        Team team3 = new Team("Serbia"); team3.getPlayers().addAll(playerGenerator());
        Team team4 = new Team("Italy"); team4.getPlayers().addAll(playerGenerator());
        Team team5 = new Team("Norway"); team5.getPlayers().addAll(playerGenerator());
        Team team6 = new Team("Australia"); team6.getPlayers().addAll(playerGenerator());
        Team team7 = new Team("Brazil"); team7.getPlayers().addAll(playerGenerator());
        Team team8 = new Team("Argentina"); team8.getPlayers().addAll(playerGenerator());
        Team team9 = new Team("Canada"); team9.getPlayers().addAll(playerGenerator());
        Team team10 = new Team("Mexico"); team10.getPlayers().addAll(playerGenerator());
        Team team11 = new Team("UnitedStates"); team11.getPlayers().addAll(playerGenerator());
        Team team12 = new Team("China"); team12.getPlayers().addAll(playerGenerator());
        Team team13 = new Team("Japan"); team13.getPlayers().addAll(playerGenerator());
        Team team14 = new Team("Russia"); team14.getPlayers().addAll(playerGenerator());
        Team team15 = new Team("Germany"); team15.getPlayers().addAll(playerGenerator());
        Team team16 = new Team("Denmark"); team16.getPlayers().addAll(playerGenerator());
        Team team17 = new Team("Finland"); team17.getPlayers().addAll(playerGenerator());
        Team team18 = new Team("SouthAfrica"); team18.getPlayers().addAll(playerGenerator());

		teamRepository.save(team1); teamRepository.save(team7); teamRepository.save(team13);
		teamRepository.save(team2); teamRepository.save(team8); teamRepository.save(team14);
		teamRepository.save(team3); teamRepository.save(team9); teamRepository.save(team15);
		teamRepository.save(team4); teamRepository.save(team10); teamRepository.save(team16);
		teamRepository.save(team5); teamRepository.save(team11); teamRepository.save(team17);
		teamRepository.save(team6); teamRepository.save(team12); teamRepository.save(team18);

		//Tournament 2
        Team team3B = new Team("SerbiaB"); team3B.getPlayers().addAll(playerGenerator());
        Team team4B = new Team("ItalyB"); team4B.getPlayers().addAll(playerGenerator());
        Team team5B = new Team("NorwayB"); team5B.getPlayers().addAll(playerGenerator());
        Team team6B = new Team("AustraliaB"); team6B.getPlayers().addAll(playerGenerator());
        Team team7B = new Team("BrazilB"); team7B.getPlayers().addAll(playerGenerator());
        Team team8B = new Team("ArgentinaB"); team8B.getPlayers().addAll(playerGenerator());
        Team team9B = new Team("CanadaB"); team9B.getPlayers().addAll(playerGenerator());
        Team team10B = new Team("MexicoB"); team10B.getPlayers().addAll(playerGenerator());
        Team team11B = new Team("UnitedStatesB"); team11B.getPlayers().addAll(playerGenerator());
        Team team12B = new Team("ChinaB"); team12B.getPlayers().addAll(playerGenerator());
        Team team13B = new Team("JapanB"); team13B.getPlayers().addAll(playerGenerator());
        Team team14B = new Team("RussiaB"); team14B.getPlayers().addAll(playerGenerator());
        Team team15B = new Team("GermanyB"); team15B.getPlayers().addAll(playerGenerator());
        Team team16B = new Team("DenmarkB"); team16B.getPlayers().addAll(playerGenerator());
        Team team17B = new Team("FinlandB"); team17B.getPlayers().addAll(playerGenerator());
        Team team18B = new Team("SouthAfricaB"); team18B.getPlayers().addAll(playerGenerator());

		/*team1 is added later*/ teamRepository.save(team7B); teamRepository.save(team13B);
		/*team2 is added later*/ teamRepository.save(team8B); teamRepository.save(team14B);
		teamRepository.save(team3B); teamRepository.save(team9B); teamRepository.save(team15B);
		teamRepository.save(team4B); teamRepository.save(team10B); teamRepository.save(team16B);
		teamRepository.save(team5B); teamRepository.save(team11B); teamRepository.save(team17B);
		teamRepository.save(team6B); teamRepository.save(team12B); teamRepository.save(team18B);
				
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
		
		//
		//
		//---------------------ZONA PARTIDOS
		//
		//

		Match m0 = new Match( tournament1.getTournamentTeams().get(0).getName(), tournament1.getTournamentTeams().get(1).getName(), 3, 0); matchRepository.save(m0); tournament1.getTournamentMatchs().add(m0);
		Match m1 = new Match( tournament1.getTournamentTeams().get(2).getName(), tournament1.getTournamentTeams().get(0).getName(), 1, 3); matchRepository.save(m1); tournament1.getTournamentMatchs().add(m1);
		Match m2 = new Match( tournament1.getTournamentTeams().get(1).getName(), tournament1.getTournamentTeams().get(2).getName(), 0, 3); matchRepository.save(m2); tournament1.getTournamentMatchs().add(m2);
		Match m3 = new Match( tournament1.getTournamentTeams().get(3).getName(), tournament1.getTournamentTeams().get(4).getName(), 3, 2); matchRepository.save(m3); tournament1.getTournamentMatchs().add(m3);
		Match m4 = new Match( tournament1.getTournamentTeams().get(5).getName(), tournament1.getTournamentTeams().get(3).getName(), 2, 3); matchRepository.save(m4); tournament1.getTournamentMatchs().add(m4);
		Match m5 = new Match( tournament1.getTournamentTeams().get(4).getName(), tournament1.getTournamentTeams().get(5).getName(), 0, 3); matchRepository.save(m5); tournament1.getTournamentMatchs().add(m5);
		Match m6 = new Match( tournament1.getTournamentTeams().get(6).getName(), tournament1.getTournamentTeams().get(7).getName(), 3, 0); matchRepository.save(m6); tournament1.getTournamentMatchs().add(m6);
		Match m7 = new Match( tournament1.getTournamentTeams().get(8).getName(), tournament1.getTournamentTeams().get(6).getName(), 1, 3); matchRepository.save(m7); tournament1.getTournamentMatchs().add(m7);
		Match m8 = new Match( tournament1.getTournamentTeams().get(7).getName(), tournament1.getTournamentTeams().get(8).getName(), 0, 3); matchRepository.save(m8); tournament1.getTournamentMatchs().add(m8);
		Match m9 = new Match( tournament1.getTournamentTeams().get(9).getName(), tournament1.getTournamentTeams().get(10).getName(), 3, 1); matchRepository.save(m9); tournament1.getTournamentMatchs().add(m9);
		Match m10 = new Match( tournament1.getTournamentTeams().get(11).getName(), tournament1.getTournamentTeams().get(9).getName(), 2, 3); matchRepository.save(m10); tournament1.getTournamentMatchs().add(m10);
		Match m11 = new Match( tournament1.getTournamentTeams().get(10).getName(), tournament1.getTournamentTeams().get(11).getName(), 0, 3); matchRepository.save(m11); tournament1.getTournamentMatchs().add(m11);
		Match m12 = new Match( tournament1.getTournamentTeams().get(12).getName(), tournament1.getTournamentTeams().get(13).getName(), 3, 0); matchRepository.save(m12); tournament1.getTournamentMatchs().add(m12);
		Match m13 = new Match( tournament1.getTournamentTeams().get(14).getName(), tournament1.getTournamentTeams().get(12).getName(), 1, 3); matchRepository.save(m13); tournament1.getTournamentMatchs().add(m13);
		Match m14 = new Match( tournament1.getTournamentTeams().get(13).getName(), tournament1.getTournamentTeams().get(14).getName(), 0, 3); matchRepository.save(m14); tournament1.getTournamentMatchs().add(m14);
		Match m15 = new Match( tournament1.getTournamentTeams().get(15).getName(), tournament1.getTournamentTeams().get(16).getName(), 3, 2); matchRepository.save(m15); tournament1.getTournamentMatchs().add(m15);
		Match m16 = new Match( tournament1.getTournamentTeams().get(17).getName(), tournament1.getTournamentTeams().get(15).getName(), 1, 3); matchRepository.save(m16); tournament1.getTournamentMatchs().add(m16);
		Match m17 = new Match( tournament1.getTournamentTeams().get(16).getName(), tournament1.getTournamentTeams().get(17).getName(), 2, 3); matchRepository.save(m17); tournament1.getTournamentMatchs().add(m17);

		Match m0B = new Match( tournament2.getTournamentTeams().get(0).getName(), tournament2.getTournamentTeams().get(1).getName(), 3, 0); matchRepository.save(m0B); tournament2.getTournamentMatchs().add(m0B);
		Match m1B = new Match( tournament2.getTournamentTeams().get(2).getName(), tournament2.getTournamentTeams().get(0).getName(), 1, 3); matchRepository.save(m1B); tournament2.getTournamentMatchs().add(m1B);
		Match m2B = new Match( tournament2.getTournamentTeams().get(1).getName(), tournament2.getTournamentTeams().get(2).getName(), 0, 3); matchRepository.save(m2B); tournament2.getTournamentMatchs().add(m2B);
		Match m3B = new Match( tournament2.getTournamentTeams().get(3).getName(), tournament2.getTournamentTeams().get(4).getName(), 3, 2); matchRepository.save(m3B); tournament2.getTournamentMatchs().add(m3B);
		Match m4B = new Match( tournament2.getTournamentTeams().get(5).getName(), tournament2.getTournamentTeams().get(3).getName(), 2, 3); matchRepository.save(m4B); tournament2.getTournamentMatchs().add(m4B);
		Match m5B = new Match( tournament2.getTournamentTeams().get(4).getName(), tournament2.getTournamentTeams().get(5).getName(), 0, 3); matchRepository.save(m5B); tournament2.getTournamentMatchs().add(m5B);
		Match m6B = new Match( tournament2.getTournamentTeams().get(6).getName(), tournament2.getTournamentTeams().get(7).getName(), 3, 0); matchRepository.save(m6B); tournament2.getTournamentMatchs().add(m6B);
		Match m7B = new Match( tournament2.getTournamentTeams().get(8).getName(), tournament2.getTournamentTeams().get(6).getName(), 1, 3); matchRepository.save(m7B); tournament2.getTournamentMatchs().add(m7B);
		Match m8B = new Match( tournament2.getTournamentTeams().get(7).getName(), tournament2.getTournamentTeams().get(8).getName(), 0, 3); matchRepository.save(m8B); tournament2.getTournamentMatchs().add(m8B);
		Match m9B = new Match( tournament2.getTournamentTeams().get(9).getName(), tournament2.getTournamentTeams().get(10).getName(), 3, 1); matchRepository.save(m9B); tournament2.getTournamentMatchs().add(m9B);
		Match m10B = new Match( tournament2.getTournamentTeams().get(11).getName(), tournament2.getTournamentTeams().get(9).getName(), 2, 3); matchRepository.save(m10B); tournament2.getTournamentMatchs().add(m10B);
		Match m11B = new Match( tournament2.getTournamentTeams().get(10).getName(), tournament2.getTournamentTeams().get(11).getName(), 0, 3); matchRepository.save(m11B); tournament2.getTournamentMatchs().add(m11B);
		Match m12B = new Match( tournament2.getTournamentTeams().get(12).getName(), tournament2.getTournamentTeams().get(13).getName(), 3, 0); matchRepository.save(m12B); tournament2.getTournamentMatchs().add(m12B);
		Match m13B = new Match( tournament2.getTournamentTeams().get(14).getName(), tournament2.getTournamentTeams().get(12).getName(), 1, 3); matchRepository.save(m13B); tournament2.getTournamentMatchs().add(m13B);
		Match m14B = new Match( tournament2.getTournamentTeams().get(13).getName(), tournament2.getTournamentTeams().get(14).getName(), 0, 3); matchRepository.save(m14B); tournament2.getTournamentMatchs().add(m14B);
		Match m15B = new Match( tournament2.getTournamentTeams().get(15).getName(), tournament2.getTournamentTeams().get(16).getName(), 3, 2); matchRepository.save(m15B); tournament2.getTournamentMatchs().add(m15B);
		Match m16B = new Match( tournament2.getTournamentTeams().get(17).getName(), tournament2.getTournamentTeams().get(15).getName(), 1, 3); matchRepository.save(m16B); tournament2.getTournamentMatchs().add(m16B);
		Match m17B = new Match( tournament2.getTournamentTeams().get(16).getName(), tournament2.getTournamentTeams().get(17).getName(), 2, 3); matchRepository.save(m17B); tournament2.getTournamentMatchs().add(m17B);

		tournamentRepository.save(tournament1);
		tournamentRepository.save(tournament2);	
	}	
}
