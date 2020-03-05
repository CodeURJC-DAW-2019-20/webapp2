package com.practica;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;
import com.practica.model.Team;
import com.practica.model.Tournament;
import com.practica.security.UserComponent;

@RestController
@RequestMapping("/api/TenniShip")
public class CreatorRestController {

	@Autowired
	private TeamService teamService;

	@Autowired
	private TournamentService tournamentService;

	@Autowired
	private ImageService imgService;

	@Autowired
	private UserComponent userComponent;

	@Autowired
	private CreatorService creatorService;

	private static class CreatorAuxClass {
		private String tournamentName;
		private List<String> teams;

		@SuppressWarnings("unused")
		public CreatorAuxClass() {

		}

		public String getTournamentName() {
			return tournamentName;
		}

		public List<String> getTeams() {
			return teams;
		}
	}

	private static class CreatorAuxClassToReturn {
		interface Basic {
		}

		@JsonView(Basic.class)
		private Tournament tournamentName;

		@JsonView(Basic.class)
		private List<Team> teams;

		@SuppressWarnings("unused")
		public CreatorAuxClassToReturn() {

		}

		public CreatorAuxClassToReturn(Tournament tournamentName, List<Team> teams) {
			this.tournamentName = tournamentName;
			this.teams = teams;
		}

	}

	interface creator extends CreatorAuxClassToReturn.Basic, Team.Basic, Tournament.Basic {
	}

	@JsonView(creator.class)
	@PostMapping("/Creator")
	public ResponseEntity<CreatorAuxClassToReturn> createRest(@RequestBody CreatorAuxClass creatorAuxObject) {

		if (userComponent.isLoggedUser()) {

			if (tournamentService.findById(creatorAuxObject.getTournamentName()).isPresent()) {
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			} else {
				Set<Team> teams = new HashSet<>();
				for (int i = 1; i < 19; i++) {
					Optional<Team> team = teamService.findById(creatorAuxObject.getTeams().get(i - 1));
					if (team.isPresent()) { // A team is not accepted a team twice
						teams.add(team.get());
					} else {
						return new ResponseEntity<>(HttpStatus.CONFLICT);
					}
				}
				Tournament tournamentFinal = new Tournament(creatorAuxObject.getTournamentName());
				tournamentService.save(tournamentFinal);
				List<Team> teamList = new ArrayList<>();
				teamList = teams.stream().collect(Collectors.toList());
				creatorService.raffleTeamsCreateMatches(tournamentFinal, teamList);
				CreatorAuxClassToReturn creatorAuxClassToReturn = new CreatorAuxClassToReturn(tournamentFinal,
						teamList);
				return new ResponseEntity<>(creatorAuxClassToReturn, HttpStatus.CREATED);
			}

		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	@PostMapping("/Tournament/{tournamentID}/image")
	public ResponseEntity<Tournament> newTournamentImg(@PathVariable String tournamentID, @RequestParam MultipartFile imageFile)
			throws IOException {
		Optional<Tournament> tournament = tournamentService.findById(tournamentID);
		if (tournament.isPresent()) {
			tournament.get().setImage(true);
			tournamentService.save(tournament.get());
			imgService.saveImage("tournaments", tournament.get().getName(), imageFile);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
