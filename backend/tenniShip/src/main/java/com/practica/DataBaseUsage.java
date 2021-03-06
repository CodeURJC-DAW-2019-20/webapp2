package com.practica;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import com.practica.match.MatchRepository;
import com.practica.model.Match;
import com.practica.model.Player;
import com.practica.model.Team;
import com.practica.model.Tournament;
import com.practica.security.User;
import com.practica.security.UserRepository;
import com.practica.team.TeamRepository;
import com.practica.tournament.TournamentRepository;

@Controller
public class DataBaseUsage implements CommandLineRunner {

	@Autowired
	private TournamentRepository tournamentRepository;

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private MatchRepository matchRepository;

	@Autowired
	private UserRepository userRepository;

	private static LinkedList<Player> playerGenerator() {
		LinkedList<Player> list = new LinkedList<>();
		list.add(new Player("Varo"));
		list.add(new Player("Ivan"));
		list.add(new Player("Santi"));
		list.add(new Player("Marcos"));
		list.add(new Player("Diego"));
		return list;
	}

	@Override
	public void run(String... args) throws Exception {

		//
		//
		// ---------------------USERS ZONE
		//
		//

		List<String> l = new LinkedList<>();
		l.add("ROLE_USER");
		List<String> lAdmin = new LinkedList<>();
		lAdmin.add("ROLE_ADMIN");
		lAdmin.add("ROLE_USER");

		if (teamRepository.findAll().isEmpty()) {

			userRepository.save(new User("admin", "admin@gmail.com", "pass", lAdmin));

			userRepository.save(new User("userSpain", "Spain", "tennishipdaw1@gmail.com", "pass", l));
			userRepository.save(new User("userDenmark", "Denmark", "tennishipdaw2@gmail.com", "pass", l));

			//
			//
			// ---------------------TEAMS ZONE
			//
			//

			/* TEAMS TO COMPLETE DAVIS CUP (and Eurocup) SAMPLE */
			Team team1 = new Team("Spain");
			team1.getPlayers().addAll(playerGenerator());
			team1.setTeamImage(true);
			teamRepository.save(team1);
			Team team2 = new Team("France");
			team2.getPlayers().addAll(playerGenerator());
			team2.setTeamImage(true);
			teamRepository.save(team2);
			Team team3 = new Team("Serbia");
			team3.getPlayers().addAll(playerGenerator());
			team3.setTeamImage(true);
			teamRepository.save(team3);
			Team team4 = new Team("Italy");
			team4.getPlayers().addAll(playerGenerator());
			team4.setTeamImage(true);
			teamRepository.save(team4);
			Team team5 = new Team("Norway");
			team5.getPlayers().addAll(playerGenerator());
			team5.setTeamImage(true);
			teamRepository.save(team5);
			Team team6 = new Team("Australia");
			team6.getPlayers().addAll(playerGenerator());
			team6.setTeamImage(true);
			teamRepository.save(team6);
			Team team7 = new Team("Brazil");
			team7.getPlayers().addAll(playerGenerator());
			team7.setTeamImage(true);
			teamRepository.save(team7);
			Team team8 = new Team("Argentina");
			team8.getPlayers().addAll(playerGenerator());
			team8.setTeamImage(true);
			teamRepository.save(team8);
			Team team9 = new Team("Canada");
			team9.getPlayers().addAll(playerGenerator());
			team9.setTeamImage(true);
			teamRepository.save(team9);
			Team team10 = new Team("Mexico");
			team10.getPlayers().addAll(playerGenerator());
			team10.setTeamImage(true);
			teamRepository.save(team10);
			Team team11 = new Team("UnitedStates");
			team11.getPlayers().addAll(playerGenerator());
			team11.setTeamImage(true);
			teamRepository.save(team11);
			Team team12 = new Team("China");
			team12.getPlayers().addAll(playerGenerator());
			team12.setTeamImage(true);
			teamRepository.save(team12);
			Team team13 = new Team("Japan");
			team13.getPlayers().addAll(playerGenerator());
			team13.setTeamImage(true);
			teamRepository.save(team13);
			Team team14 = new Team("Russia");
			team14.getPlayers().addAll(playerGenerator());
			team14.setTeamImage(true);
			teamRepository.save(team14);
			Team team15 = new Team("Germany");
			team15.getPlayers().addAll(playerGenerator());
			team15.setTeamImage(true);
			teamRepository.save(team15);
			Team team16 = new Team("Denmark");
			team16.getPlayers().addAll(playerGenerator());
			team16.setTeamImage(true);
			teamRepository.save(team16);
			Team team17 = new Team("Finland");
			team17.getPlayers().addAll(playerGenerator());
			team17.setTeamImage(true);
			teamRepository.save(team17);
			Team team18 = new Team("SouthAfrica");
			team18.getPlayers().addAll(playerGenerator());
			team18.setTeamImage(true);
			teamRepository.save(team18);

			/* TEAMS TO PLAY CHAMPIONS LEAGUE */
			Team team1B = new Team("Atlético de Madrid");
			team1B.getPlayers().addAll(playerGenerator());
			team1B.setTeamImage(true);
			teamRepository.save(team1B);
			Team team2B = new Team("PSG");
			team2B.getPlayers().addAll(playerGenerator());
			team2B.setTeamImage(true);
			teamRepository.save(team2B);
			Team team3B = new Team("CSKA Moscow");
			team3B.getPlayers().addAll(playerGenerator());
			team3B.setTeamImage(true);
			teamRepository.save(team3B);
			Team team4B = new Team("Galatasaray");
			team4B.getPlayers().addAll(playerGenerator());
			team4B.setTeamImage(true);
			teamRepository.save(team4B);
			Team team5B = new Team("Inter Milan");
			team5B.getPlayers().addAll(playerGenerator());
			team5B.setTeamImage(true);
			teamRepository.save(team5B);
			Team team6B = new Team("Porto");
			team6B.getPlayers().addAll(playerGenerator());
			team6B.setTeamImage(true);
			teamRepository.save(team6B);
			Team team7B = new Team("Tottenham");
			team7B.getPlayers().addAll(playerGenerator());
			team7B.setTeamImage(true);
			teamRepository.save(team7B);
			Team team8B = new Team("Valencia");
			team8B.getPlayers().addAll(playerGenerator());
			team8B.setTeamImage(true);
			teamRepository.save(team8B);
			Team team9B = new Team("Chelsea");
			team9B.getPlayers().addAll(playerGenerator());
			team9B.setTeamImage(true);
			teamRepository.save(team9B);
			Team team10B = new Team("Liverpool");
			team10B.getPlayers().addAll(playerGenerator());
			team10B.setTeamImage(true);
			teamRepository.save(team10B);
			Team team11B = new Team("Ajax");
			team11B.getPlayers().addAll(playerGenerator());
			team11B.setTeamImage(true);
			teamRepository.save(team11B);
			Team team12B = new Team("Napoli");
			team12B.getPlayers().addAll(playerGenerator());
			team12B.setTeamImage(true);
			teamRepository.save(team12B);
			Team team13B = new Team("Juventus");
			team13B.getPlayers().addAll(playerGenerator());
			team13B.setTeamImage(true);
			teamRepository.save(team13B);
			Team team14B = new Team("Barcelona");
			team14B.getPlayers().addAll(playerGenerator());
			team14B.setTeamImage(true);
			teamRepository.save(team14B);
			Team team15B = new Team("Borussia Dortmund");
			team15B.getPlayers().addAll(playerGenerator());
			team15B.setTeamImage(true);
			teamRepository.save(team15B);
			Team team16B = new Team("Bayern Munich");
			team16B.getPlayers().addAll(playerGenerator());
			team16B.setTeamImage(true);
			teamRepository.save(team16B);
			Team team17B = new Team("Real Madrid");
			team17B.getPlayers().addAll(playerGenerator());
			team17B.setTeamImage(true);
			teamRepository.save(team17B);
			Team team18B = new Team("Manchester City");
			team18B.getPlayers().addAll(playerGenerator());
			team18B.setTeamImage(true);
			teamRepository.save(team18B);

			/* Teams to complete Eurocup sample tournament */
			Team team1C = new Team("Hungary");
			team1C.getPlayers().addAll(playerGenerator());
			team1C.setTeamImage(true);
			teamRepository.save(team1C);
			Team team2C = new Team("Croatia");
			team2C.getPlayers().addAll(playerGenerator());
			team2C.setTeamImage(true);
			teamRepository.save(team2C);
			Team team3C = new Team("Netherland");
			team3C.getPlayers().addAll(playerGenerator());
			team3C.setTeamImage(true);
			teamRepository.save(team3C);
			Team team4C = new Team("Belgium");
			team4C.getPlayers().addAll(playerGenerator());
			team4C.setTeamImage(true);
			teamRepository.save(team4C);
			Team team5C = new Team("United Kingdom");
			team5C.getPlayers().addAll(playerGenerator());
			team5C.setTeamImage(true);
			teamRepository.save(team5C);
			Team team6C = new Team("Switzerland");
			team6C.getPlayers().addAll(playerGenerator());
			team6C.setTeamImage(true);
			teamRepository.save(team6C);
			Team team7C = new Team("Poland");
			team7C.getPlayers().addAll(playerGenerator());
			team7C.setTeamImage(true);
			teamRepository.save(team7C);
			Team team8C = new Team("Portugal");
			team8C.getPlayers().addAll(playerGenerator());
			team8C.setTeamImage(true);
			teamRepository.save(team8C);
			Team team9C = new Team("Andorra");
			team9C.getPlayers().addAll(playerGenerator());
			team9C.setTeamImage(true);
			teamRepository.save(team9C);

			//
			//
			// ---------------------TOURNAMENTS ZONE
			//
			//

			Tournament tournament1 = new Tournament("Davis Cup");
			tournament1.setImage(true);
			tournamentRepository.save(tournament1);

			Tournament tournament2 = new Tournament("Champions League");
			tournament2.setImage(true);
			tournamentRepository.save(tournament2);

			Tournament tournament3 = new Tournament("Eurocup");
			tournament3.setImage(true);
			tournamentRepository.save(tournament3);

			//
			//
			// ---------------------MATCHES ZONE
			//
			//

			Match m1 = new Match(3, 0, "A");
			m1.setTeam1(team1);
			m1.setTeam2(team3);
			m1.setTournament(tournament1);
			matchRepository.save(m1);
			Match m2 = new Match(3, 0, "A");
			m2.setTeam1(team3);
			m2.setTeam2(team2);
			m2.setTournament(tournament1);
			matchRepository.save(m2);
			Match m3 = new Match(3, 1, "A");
			m3.setTeam1(team2);
			m3.setTeam2(team1);
			m3.setTournament(tournament1);
			matchRepository.save(m3);
			Match m4 = new Match(3, 0, "B");
			m4.setTeam1(team4);
			m4.setTeam2(team5);
			m4.setTournament(tournament1);
			matchRepository.save(m4);
			Match m5 = new Match(3, 0, "B");
			m5.setTeam1(team6);
			m5.setTeam2(team4);
			m5.setTournament(tournament1);
			matchRepository.save(m5);
			Match m6 = new Match(3, 1, "B");
			m6.setTeam1(team5);
			m6.setTeam2(team6);
			m6.setTournament(tournament1);
			matchRepository.save(m6);
			Match m7 = new Match(3, 0, "C");
			m7.setTeam1(team7);
			m7.setTeam2(team8);
			m7.setTournament(tournament1);
			matchRepository.save(m7);
			Match m8 = new Match(3, 0, "C");
			m8.setTeam1(team9);
			m8.setTeam2(team7);
			m8.setTournament(tournament1);
			matchRepository.save(m8);
			Match m9 = new Match(3, 1, "C");
			m9.setTeam1(team8);
			m9.setTeam2(team9);
			m9.setTournament(tournament1);
			matchRepository.save(m9);
			Match m10 = new Match(3, 0, "D");
			m10.setTeam1(team10);
			m10.setTeam2(team11);
			m10.setTournament(tournament1);
			matchRepository.save(m10);
			Match m11 = new Match(3, 0, "D");
			m11.setTeam1(team12);
			m11.setTeam2(team10);
			m11.setTournament(tournament1);
			matchRepository.save(m11);
			Match m12 = new Match(3, 1, "D");
			m12.setTeam1(team11);
			m12.setTeam2(team12);
			m12.setTournament(tournament1);
			matchRepository.save(m12);
			Match m13 = new Match(3, 2, "E");
			m13.setTeam1(team13);
			m13.setTeam2(team14);
			m13.setTournament(tournament1);
			matchRepository.save(m13);
			Match m14 = new Match(0, 3, "E");
			m14.setTeam1(team15);
			m14.setTeam2(team13);
			m14.setTournament(tournament1);
			matchRepository.save(m14);
			Match m15 = new Match(3, 0, "E");
			m15.setTeam1(team14);
			m15.setTeam2(team15);
			m15.setTournament(tournament1);
			matchRepository.save(m15);
			Match m16 = new Match(3, 2, "F");
			m16.setTeam1(team16);
			m16.setTeam2(team17);
			m16.setTournament(tournament1);
			matchRepository.save(m16);
			Match m17 = new Match(0, 3, "F");
			m17.setTeam1(team18);
			m17.setTeam2(team16);
			m17.setTournament(tournament1);
			matchRepository.save(m17);
			Match m18 = new Match(3, 0, "F");
			m18.setTeam1(team17);
			m18.setTeam2(team18);
			m18.setTournament(tournament1);
			matchRepository.save(m18);

			Match m19 = new Match(3, 0, "X");
			m19.setTeam1(team6);
			m19.setTeam2(team13);
			m19.setTournament(tournament1);
			matchRepository.save(m19);
			Match m20 = new Match(3, 0, "X");
			m20.setTeam1(team12);
			m20.setTeam2(team9);
			m20.setTournament(tournament1);
			matchRepository.save(m20);
			Match m21 = new Match(3, 0, "X");
			m21.setTeam1(team16);
			m21.setTeam2(team1);
			m21.setTournament(tournament1);
			matchRepository.save(m21);
			Match m22 = new Match(3, 0, "X");
			m22.setTeam1(team14);
			m22.setTeam2(team17);
			m22.setTournament(tournament1);
			matchRepository.save(m22);
			Match m23 = new Match(3, 0, "Y");
			m23.setTeam1(team6);
			m23.setTeam2(team12);
			m23.setTournament(tournament1);
			matchRepository.save(m23);
			Match m24 = new Match(3, 0, "Y");
			m24.setTeam1(team16);
			m24.setTeam2(team14);
			m24.setTournament(tournament1);
			matchRepository.save(m24);
			Match m25 = new Match(0, 0, "Z");
			m25.setTeam1(team6);
			m25.setTeam2(team16);
			m25.setTournament(tournament1);
			matchRepository.save(m25);

			Match m1B = new Match(0, 0, "A");
			m1B.setTeam1(team1);
			m1B.setTeam2(team3B);
			m1B.setTournament(tournament2);
			matchRepository.save(m1B);
			Match m2B = new Match(3, 2, "A");
			m2B.setTeam1(team3B);
			m2B.setTeam2(team16);
			m2B.setTournament(tournament2);
			matchRepository.save(m2B);
			Match m3B = new Match(1, 3, "A");
			m3B.setTeam1(team16);
			m3B.setTeam2(team1);
			m3B.setTournament(tournament2);
			matchRepository.save(m3B);
			Match m4B = new Match(0, 3, "B");
			m4B.setTeam1(team4B);
			m4B.setTeam2(team5B);
			m4B.setTournament(tournament2);
			matchRepository.save(m4B);
			Match m5B = new Match(3, 2, "B");
			m5B.setTeam1(team6B);
			m5B.setTeam2(team4B);
			m5B.setTournament(tournament2);
			matchRepository.save(m5B);
			Match m6B = new Match(3, 2, "B");
			m6B.setTeam1(team5B);
			m6B.setTeam2(team6B);
			m6B.setTournament(tournament2);
			matchRepository.save(m6B);
			Match m7B = new Match(2, 3, "C");
			m7B.setTeam1(team7B);
			m7B.setTeam2(team8B);
			m7B.setTournament(tournament2);
			matchRepository.save(m7B);
			Match m8B = new Match(1, 3, "C");
			m8B.setTeam1(team9B);
			m8B.setTeam2(team7B);
			m8B.setTournament(tournament2);
			matchRepository.save(m8B);
			Match m9B = new Match(3, 0, "C");
			m9B.setTeam1(team8B);
			m9B.setTeam2(team9B);
			m9B.setTournament(tournament2);
			matchRepository.save(m9B);
			Match m10B = new Match(3, 0, "D");
			m10B.setTeam1(team10B);
			m10B.setTeam2(team11B);
			m10B.setTournament(tournament2);
			matchRepository.save(m10B);
			Match m11B = new Match(2, 3, "D");
			m11B.setTeam1(team12B);
			m11B.setTeam2(team10B);
			m11B.setTournament(tournament2);
			matchRepository.save(m11B);
			Match m12B = new Match(0, 3, "D");
			m12B.setTeam1(team11B);
			m12B.setTeam2(team12B);
			m12B.setTournament(tournament2);
			matchRepository.save(m12B);
			Match m13B = new Match(1, 3, "E");
			m13B.setTeam1(team13B);
			m13B.setTeam2(team14B);
			m13B.setTournament(tournament2);
			matchRepository.save(m13B);
			Match m14B = new Match(3, 2, "E");
			m14B.setTeam1(team15B);
			m14B.setTeam2(team13B);
			m14B.setTournament(tournament2);
			matchRepository.save(m14B);
			Match m15B = new Match(3, 0, "E");
			m15B.setTeam1(team14B);
			m15B.setTeam2(team15B);
			m15B.setTournament(tournament2);
			matchRepository.save(m15B);
			Match m16B = new Match(2, 3, "F");
			m16B.setTeam1(team16B);
			m16B.setTeam2(team17B);
			m16B.setTournament(tournament2);
			matchRepository.save(m16B);
			Match m17B = new Match(0, 3, "F");
			m17B.setTeam1(team18B);
			m17B.setTeam2(team16B);
			m17B.setTournament(tournament2);
			matchRepository.save(m17B);
			Match m18B = new Match(3, 0, "F");
			m18B.setTeam1(team17B);
			m18B.setTeam2(team18B);
			m18B.setTournament(tournament2);
			matchRepository.save(m18B);

			Match m1C = new Match(0, 0, "A");
			m1C.setTeam1(team1);
			m1C.setTeam2(team1C);
			m1C.setTournament(tournament3);
			matchRepository.save(m1C);
			Match m2C = new Match(0, 0, "A");
			m2C.setTeam1(team1);
			m2C.setTeam2(team17);
			m2C.setTournament(tournament3);
			matchRepository.save(m2C);
			Match m3C = new Match(0, 0, "A");
			m3C.setTeam1(team1C);
			m3C.setTeam2(team17);
			m3C.setTournament(tournament3);
			matchRepository.save(m3C);

			Match m4C = new Match(0, 0, "B");
			m4C.setTeam1(team4);
			m4C.setTeam2(team4C);
			m4C.setTournament(tournament3);
			matchRepository.save(m4C);
			Match m5C = new Match(0, 0, "B");
			m5C.setTeam1(team4);
			m5C.setTeam2(team5);
			m5C.setTournament(tournament3);
			matchRepository.save(m5C);
			Match m6C = new Match(0, 0, "B");
			m6C.setTeam1(team4C);
			m6C.setTeam2(team5);
			m6C.setTournament(tournament3);
			matchRepository.save(m6C);

			Match m7C = new Match(0, 0, "C");
			m7C.setTeam1(team14);
			m7C.setTeam2(team9C);
			m7C.setTournament(tournament3);
			matchRepository.save(m7C);
			Match m8C = new Match(0, 0, "C");
			m8C.setTeam1(team14);
			m8C.setTeam2(team15);
			m8C.setTournament(tournament3);
			matchRepository.save(m8C);
			Match m9C = new Match(0, 0, "C");
			m9C.setTeam1(team9C);
			m9C.setTeam2(team15);
			m9C.setTournament(tournament3);
			matchRepository.save(m9C);

			Match m10C = new Match(0, 0, "D");
			m10C.setTeam1(team2C);
			m10C.setTeam2(team16);
			m10C.setTournament(tournament3);
			matchRepository.save(m10C);
			Match m11C = new Match(0, 0, "D");
			m11C.setTeam1(team2C);
			m11C.setTeam2(team5C);
			m11C.setTournament(tournament3);
			matchRepository.save(m11C);
			Match m12C = new Match(0, 0, "D");
			m12C.setTeam1(team16);
			m12C.setTeam2(team5C);
			m12C.setTournament(tournament3);
			matchRepository.save(m12C);

			Match m13C = new Match(0, 0, "E");
			m13C.setTeam1(team2);
			m13C.setTeam2(team3);
			m13C.setTournament(tournament3);
			matchRepository.save(m13C);
			Match m14C = new Match(0, 0, "E");
			m14C.setTeam1(team2);
			m14C.setTeam2(team7C);
			m14C.setTournament(tournament3);
			matchRepository.save(m14C);
			Match m15C = new Match(0, 0, "E");
			m15C.setTeam1(team3);
			m15C.setTeam2(team7C);
			m15C.setTournament(tournament3);
			matchRepository.save(m15C);

			Match m16C = new Match(0, 0, "F");
			m16C.setTeam1(team3C);
			m16C.setTeam2(team8C);
			m16C.setTournament(tournament3);
			matchRepository.save(m16C);
			Match m17C = new Match(0, 0, "F");
			m17C.setTeam1(team3C);
			m17C.setTeam2(team6C);
			m17C.setTournament(tournament3);
			matchRepository.save(m17C);
			Match m18C = new Match(0, 0, "F");
			m18C.setTeam1(team8C);
			m18C.setTeam2(team6C);
			m18C.setTournament(tournament3);
			matchRepository.save(m18C);
		}

	}
}
