package com.practica;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.practica.model.Match;
import com.practica.model.Team;
import com.practica.model.Tournament;
import com.practica.security.UserComponent;

@RestController
public class CreatorRestController {

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private TournamentRepository tournamentRepository;

	@Autowired
	private ImageService imgService;

	@Autowired
	private UserComponent userComponent;

	@Autowired
	private MatchRepository matchRepository;

	@PostMapping("/api/TenniShip/Creator")
	public ResponseEntity<Tournament> createRest(@RequestParam String tournament, @RequestParam String team1,
			@RequestParam String team2, @RequestParam String team3, @RequestParam String team4,
			@RequestParam String team5, @RequestParam String team6, @RequestParam String team7,
			@RequestParam String team8, @RequestParam String team9, @RequestParam String team10,
			@RequestParam String team11, @RequestParam String team12, @RequestParam String team13,
			@RequestParam String team14, @RequestParam String team15, @RequestParam String team16,
			@RequestParam String team17, @RequestParam String team18) {

		if (userComponent.isLoggedUser()) {

			if (tournamentRepository.findById(tournament).isPresent()) {
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			} else {
				Set<Team> teams = new HashSet<>();
				for (int i = 1; i < 19; i++) {
					Optional<Team> team = teamRepository.findById(String.format("team%d", i));
					if (team.isPresent() && !(teams.contains(team.get()))) {
						teams.add(team.get());
					} else {
						return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
					}
				}
				Tournament tournamentFinal = new Tournament(tournament);
				raffleTeamsCreateMatches(tournamentFinal, teams.stream().collect(Collectors.toList()));
				return new ResponseEntity<>(tournamentFinal,HttpStatus.OK);
			}

		} else {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}

	public void raffleTeamsCreateMatches(Tournament tournament, List<Team> teams) {

		Collections.shuffle(teams);

		Match m1 = new Match(0, 0, "A");
		m1.setTeam1(teams.get(0));
		m1.setTeam2(teams.get(2));
		m1.setTournament(tournament);
		matchRepository.save(m1);
		Match m2 = new Match(0, 0, "A");
		m2.setTeam1(teams.get(2));
		m2.setTeam2(teams.get(1));
		m2.setTournament(tournament);
		matchRepository.save(m2);
		Match m3 = new Match(0, 0, "A");
		m3.setTeam1(teams.get(1));
		m3.setTeam2(teams.get(0));
		m3.setTournament(tournament);
		matchRepository.save(m3);
		Match m4 = new Match(0, 0, "B");
		m4.setTeam1(teams.get(3));
		m4.setTeam2(teams.get(4));
		m4.setTournament(tournament);
		matchRepository.save(m4);
		Match m5 = new Match(0, 0, "B");
		m5.setTeam1(teams.get(5));
		m5.setTeam2(teams.get(3));
		m5.setTournament(tournament);
		matchRepository.save(m5);
		Match m6 = new Match(0, 0, "B");
		m6.setTeam1(teams.get(4));
		m6.setTeam2(teams.get(5));
		m6.setTournament(tournament);
		matchRepository.save(m6);
		Match m7 = new Match(0, 0, "C");
		m7.setTeam1(teams.get(6));
		m7.setTeam2(teams.get(7));
		m7.setTournament(tournament);
		matchRepository.save(m7);
		Match m8 = new Match(0, 0, "C");
		m8.setTeam1(teams.get(8));
		m8.setTeam2(teams.get(6));
		m8.setTournament(tournament);
		matchRepository.save(m8);
		Match m9 = new Match(0, 0, "C");
		m9.setTeam1(teams.get(7));
		m9.setTeam2(teams.get(8));
		m9.setTournament(tournament);
		matchRepository.save(m9);
		Match m10 = new Match(0, 0, "D");
		m10.setTeam1(teams.get(9));
		m10.setTeam2(teams.get(10));
		m10.setTournament(tournament);
		matchRepository.save(m10);
		Match m11 = new Match(0, 0, "D");
		m11.setTeam1(teams.get(11));
		m11.setTeam2(teams.get(9));
		m11.setTournament(tournament);
		matchRepository.save(m11);
		Match m12 = new Match(0, 0, "D");
		m12.setTeam1(teams.get(10));
		m12.setTeam2(teams.get(11));
		m12.setTournament(tournament);
		matchRepository.save(m12);
		Match m13 = new Match(0, 0, "E");
		m13.setTeam1(teams.get(12));
		m13.setTeam2(teams.get(13));
		m13.setTournament(tournament);
		matchRepository.save(m13);
		Match m14 = new Match(0, 0, "E");
		m14.setTeam1(teams.get(14));
		m14.setTeam2(teams.get(12));
		m14.setTournament(tournament);
		matchRepository.save(m14);
		Match m15 = new Match(0, 0, "E");
		m15.setTeam1(teams.get(13));
		m15.setTeam2(teams.get(14));
		m15.setTournament(tournament);
		matchRepository.save(m15);
		Match m16 = new Match(0, 0, "F");
		m16.setTeam1(teams.get(15));
		m16.setTeam2(teams.get(16));
		m16.setTournament(tournament);
		matchRepository.save(m16);
		Match m17 = new Match(0, 0, "F");
		m17.setTeam1(teams.get(17));
		m17.setTeam2(teams.get(15));
		m17.setTournament(tournament);
		matchRepository.save(m17);
		Match m18 = new Match(0, 0, "F");
		m18.setTeam1(teams.get(16));
		m18.setTeam2(teams.get(17));
		m18.setTournament(tournament);
		matchRepository.save(m18);
	}

}
