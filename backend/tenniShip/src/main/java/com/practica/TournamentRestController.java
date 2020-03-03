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

import com.practica.model.Match;
import com.practica.model.Team;
import com.practica.model.Tournament;
import com.practica.security.UserComponent;

@RestController
@RequestMapping("/api")
public class TournamentRestController {

	@Autowired
	private TournamentRepository tournamentRepository;
	
	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private ImageService imgService;
	
	@Autowired
	private UserComponent userComponent;

	@PostMapping("/tournaments")
	@ResponseStatus(HttpStatus.CREATED)
	public Tournament newTournament(@RequestBody Tournament tournament) {
		tournamentRepository.save(tournament);
		return tournament;
	}

	@PostMapping("/tournaments/{id}/image")
	public ResponseEntity<Tournament> newTournamentImage(@PathVariable String id, @RequestParam MultipartFile imageFile)
			throws IOException {

		Optional<Tournament> tournament = tournamentRepository.findById(id);

		if (tournament.isPresent()) {

			tournament.get().setImage(true);
			tournamentRepository.save(tournament.get());

			imgService.saveImage("tournaments", tournament.get().getName(), imageFile);
			return new ResponseEntity<>(HttpStatus.CREATED);

		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/tournaments/{id}/image")
	public ResponseEntity<Object> getTournamentImage(@PathVariable String id) throws IOException {
		Optional<Tournament> tournament = tournamentRepository.findById(id);
		if (tournament.isPresent()) {
			if (tournament.get().hasImage()) {
				return this.imgService.createResponseFromImage("tournaments", id);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/tournaments/{id}")
	public ResponseEntity<Tournament> seeTournament(@PathVariable String id) {

		Optional<Tournament> tournament = tournamentRepository.findById(id);
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
			Optional<Team> t = teamRepository.findById(team);

			return new ResponseEntity<>(teamRepository.getTournaments(t.get()), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
	
	public static class SelectMatchAuxiliarClass {
		String round;
		List<Match> matches = new ArrayList<>();
		
		public SelectMatchAuxiliarClass(String round, List<Match> matches) {
			this.round = round;
			this.matches = matches;
		}
	}
	
	@GetMapping("/TenniShip/RegisterMatch/Tournament/{tournament}")
	public ResponseEntity<SelectMatchAuxiliarClass> selectMatch(@PathVariable String tournament) {

		Optional<Tournament> t = tournamentRepository.findById(tournament);// check if that team play this tournament

		if (t.isPresent() && userComponent.isLoggedUser() && tournamentRepository.getTeams(t.get())
				.contains(teamRepository.findById(userComponent.getTeam()).get())) {
			String team = userComponent.getTeam();
			Optional<Team> tm = teamRepository.findById(team);

			if (!(tournamentRepository.getNextMatches(t.get(), tm.get()).isEmpty())) {
				SelectMatchAuxiliarClass objectToReturn = new SelectMatchAuxiliarClass(tournamentRepository.getNextMatches(t.get(), tm.get()).get(0).getStringType()
						, tournamentRepository.getNextMatches(t.get(), tm.get()));
				return new ResponseEntity<>(objectToReturn, HttpStatus.OK);
			}
		}
		if (!(userComponent.isLoggedUser() ))
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}