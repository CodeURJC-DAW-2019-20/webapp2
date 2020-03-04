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
import org.springframework.web.bind.annotation.RequestBody;
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
	private CreatorService creatorService;
	
	private static class CreatorAuxClass {
		private String tournamentName;
		private List<String> teams;
		
		public String getTournamentName() {
			return tournamentName;
		}
		public List<String> getTeams() {
			return teams;
		}	
	}

	@PostMapping("/api/TenniShip/Creator")
	public ResponseEntity<Tournament> createRest(@RequestBody CreatorAuxClass creatorAuxObject ) {

		if (userComponent.isLoggedUser()) {

			if (tournamentRepository.findById(creatorAuxObject.getTournamentName()).isPresent()) {
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			} else {
				Set<Team> teams = new HashSet<>();
				for (int i = 1; i < 19; i++) {
					Optional<Team> team = teamRepository.findById(creatorAuxObject.getTeams().get(i-1));
					if (team.isPresent()) { //A team is not accepted a team twice
						teams.add(team.get());
					} else {
						return new ResponseEntity<>(HttpStatus.CONFLICT);
					}
				}
				Tournament tournamentFinal = new Tournament(creatorAuxObject.getTournamentName());
				tournamentRepository.save(tournamentFinal);
				creatorService.raffleTeamsCreateMatches(tournamentFinal, teams.stream().collect(Collectors.toList()));
				return new ResponseEntity<>(tournamentFinal,HttpStatus.OK);
			}

		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

}
