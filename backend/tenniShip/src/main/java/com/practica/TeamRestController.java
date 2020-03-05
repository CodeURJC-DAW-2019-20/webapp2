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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

	@GetMapping("/{teamId}/image/{npic}")
	public ResponseEntity<Object> getTeamImage(@PathVariable String teamId, @PathVariable String npic) throws IOException {
		/* nPic value is 0 for team pic, and 1-5 for players*/		
		Optional<Team> team = teamRepository.findById(teamId);
		if (team.isPresent()) {
			if (team.get().hasTeamImage()) {
				switch (npic) {
				case "0":
					return this.imgService.createResponseFromImage("teams", teamId);

				default:
					return this.imgService.createResponseFromImage("players", teamId + "player" + npic);
				}
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Team> seeTeam(@PathVariable String teamId) {

		Optional<Team> team = teamRepository.findById(teamId);
		if (team.isPresent()) {
			return new ResponseEntity<>(team.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}