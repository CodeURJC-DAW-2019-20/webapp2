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

import com.practica.model.Team;


@RestController
@RequestMapping("api/teams")

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
	public ResponseEntity<Team> newTeamImage(@PathVariable String id, @RequestParam MultipartFile imageFile)
			throws IOException {

		Optional<Team> team = teamRepository.findById(id);

		if (team.isPresent()) {

			team.get().setImage(true);
			teamRepository.save(team.get());

			imgService.saveImage("teams", team.get().getName(), imageFile);
			return new ResponseEntity<>(HttpStatus.CREATED);

		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/{id}/image")
	public ResponseEntity<Object> getTeamImage(@PathVariable String id) throws IOException {
		Optional<Team> team = teamRepository.findById(id);
		if (team.isPresent()) {
			if(team.get().hasImage()) {
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