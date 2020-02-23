package com.practica;

import java.io.IOException;
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

import com.practica.model.Tournament;

@RestController
@RequestMapping("api/tournaments")

public class TournamentRestController {

	@Autowired
	private TournamentRepository tournamentRepository;

	@Autowired
	private ImageService imgService;

	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public Tournament newTournament(@RequestBody Tournament tournament) {
		tournamentRepository.save(tournament);
		return tournament;
	}

	@PostMapping("/{id}/image")
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

	@GetMapping("/{id}/image")
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

	@GetMapping("/{id}")
	public ResponseEntity<Tournament> seeTournament(@PathVariable String id) {

		Optional<Tournament> tournament = tournamentRepository.findById(id);
		if (tournament.isPresent()) {
			return new ResponseEntity<>(tournament.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}