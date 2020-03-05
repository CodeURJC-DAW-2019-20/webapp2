package com.practica;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;
import com.practica.model.Match;
import com.practica.model.Team;
import com.practica.model.Tournament;
import com.practica.security.UserComponent;

@RestController
@RequestMapping("/api")
public class TournamentRestController {

	@Autowired
	private TournamentService tournamentService;
	
	@Autowired
	private TeamService teamService;

	@Autowired
	private ImageService imgService;
	
	@Autowired
	private UserComponent userComponent;

	@PostMapping("/tournaments")
	@ResponseStatus(HttpStatus.CREATED)
	public Tournament newTournament(@RequestBody Tournament tournament) {
		tournamentService.save(tournament);
		return tournament;
	}

	@PostMapping("/tournaments/{id}/image")
	public ResponseEntity<Tournament> newTournamentImage(@PathVariable String id, @RequestParam MultipartFile imageFile)
			throws IOException {

		Optional<Tournament> tournament = tournamentService.findById(id);

		if (tournament.isPresent()) {

			tournament.get().setImage(true);
			tournamentService.save(tournament.get());

			imgService.saveImage("tournaments", tournament.get().getName(), imageFile);
			return new ResponseEntity<>(HttpStatus.CREATED);

		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/TenniShip/Tournaments/{tournamentId}/image")
	public ResponseEntity<Object> getTournamentImage(@PathVariable String tournamentId) throws IOException {
		Optional<Tournament> tournament = tournamentService.findById(tournamentId);
		if (tournament.isPresent()) {
			if (tournament.get().hasImage()) {
				return this.imgService.createResponseFromImage("tournaments", tournamentId);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

//	@GetMapping("/{teamId}/image/{npic}")
//	public ResponseEntity<Object> getTeamImage(@PathVariable String teamId, @PathVariable String npic) throws IOException {
//		/* nPic value is 0 for team pic, and 1-5 for players*/
//		Optional<Team> team = teamRepository.findById(teamId);
//		if (team.isPresent()) {
//			if (team.get().hasTeamImage()) {
//				switch (npic) {
//				case "0":
//					return this.imgService.createResponseFromImage("teams", teamId);
//
//				default:
//					return this.imgService.createResponseFromImage("players", teamId + "player" + npic);
//				}
//			} else {
//				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//			}
//		} else {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//	}

	@GetMapping("/tournaments/{id}")
	public ResponseEntity<Tournament> seeTournament(@PathVariable String id) {

		Optional<Tournament> tournament = tournamentService.findById(id);
		if (tournament.isPresent()) {
			return new ResponseEntity<>(tournament.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@GetMapping("/TenniShip/RegisterMatch/Tournament")
	public ResponseEntity<List<Tournament>> selectTournament() {
		if (userComponent.isLoggedUser()) {
			String team = userComponent.getTeam();
			Optional<Team> t = teamService.findById(team);

			return new ResponseEntity<>(teamService.getTournaments(t.get()), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
	
	public static class SelectMatchAuxiliarClass {
		interface Basic{}
		
		@JsonView(Basic.class)
		private String round;
		
		@JsonView(Basic.class)
		private List<Match> matches = new ArrayList<>();
		
		public SelectMatchAuxiliarClass() {
			
		}

		public SelectMatchAuxiliarClass(String round, List<Match> matches) {
			this.round = round;
			this.matches = matches;
		}
	}
	
	interface selectMatch extends  Match.Basic, SelectMatchAuxiliarClass.Basic, Team.Basic, Tournament.Basic {}
	
	@JsonView(selectMatch.class)
	@GetMapping("/TenniShip/RegisterMatch/Tournament/{tournament}")
	public ResponseEntity<SelectMatchAuxiliarClass> selectMatch(@PathVariable String tournament) {

		Optional<Tournament> t = tournamentService.findById(tournament);// check if that team play this tournament

		if (t.isPresent() && userComponent.isLoggedUser() && tournamentService.getTeams(t.get())
				.contains(teamService.findById(userComponent.getTeam()).get())) {
			String team = userComponent.getTeam();
			Optional<Team> tm = teamService.findById(team);

			if (!(tournamentService.getNextMatches(t.get(), tm.get()).isEmpty())) {
				SelectMatchAuxiliarClass objectToReturn = new SelectMatchAuxiliarClass(tournamentService.getNextMatches(t.get(), tm.get()).get(0).getStringType()
						, tournamentService.getNextMatches(t.get(), tm.get()));
				return new ResponseEntity<>(objectToReturn, HttpStatus.OK);
			}
		}
		if (!(userComponent.isLoggedUser() ))
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}