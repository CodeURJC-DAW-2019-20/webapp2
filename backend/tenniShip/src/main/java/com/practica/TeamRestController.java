package com.practica;

import java.io.IOException;
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

import com.practica.model.Team;

@RestController
@RequestMapping("/api/teams")

public class TeamRestController {

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private ImageService imgService;

	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public Team newTeam(@RequestBody Team team) {
		teamRepository.save(team);
		return team;
	}

	@PostMapping("/{id}/image")
	public ResponseEntity<Team> newTeamImage(@PathVariable String id, @RequestParam List<MultipartFile> imageFile)
			throws IOException {

		Optional<Team> team = teamRepository.findById(id);

		if (team.isPresent()) {

			team.get().setTeamImage(true);
			teamRepository.save(team.get());

			imgService.saveImage("teams", team.get().getName(), imageFile.get(0));
			for (int i = 1; i < 6; i++) {
				imgService.saveImage("players", team.get().getName() + String.format("player%d", i), imageFile.get(i));
			}
			return new ResponseEntity<>(HttpStatus.CREATED);

		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/{id}/image")
	public ResponseEntity<Object> getTeamImage(@PathVariable String id) throws IOException {
		Optional<Team> team = teamRepository.findById(id);
		if (team.isPresent()) {
			if (team.get().hasTeamImage()) {
				return this.imgService.createResponseFromImage("teams", id);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Team> seeTeam(@PathVariable String id) {

		Optional<Team> team = teamRepository.findById(id);
		if (team.isPresent()) {
			return new ResponseEntity<>(team.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}