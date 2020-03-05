package com.practica.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.practica.ImageService;
import com.practica.MailSenderXX;
import com.practica.TeamService;
import com.practica.model.Player;
import com.practica.model.Team;

@RestController
@RequestMapping("/api")
public class UserRestController {

	@Autowired
	private UserService userService;

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private TeamService teamService;

	@Autowired
	private ImageService imgService;

	public static class UserCreatedRest {
		private String userName;

		private String passwordHash;

		private String email;

		private String teamName;

		private List<String> roles;

		private List<String> players = new ArrayList<>();

		public String getUserName() {
			return userName;
		}

		public String getPasswordHash() {
			return passwordHash;
		}

		public String getEmail() {
			return email;
		}

		public String getTeamName() {
			return teamName;
		}

		public List<String> getRoles() {
			return roles;
		}

		public List<String> getPlayers() {
			return players;
		}

	}

	@PostMapping("/TenniShip/SignUp")
	public ResponseEntity<User> newUser(@RequestBody UserCreatedRest userWithTeam) {
		if (userService.findByUserName(userWithTeam.getUserName()).isPresent()
				|| userService.findByEmail(userWithTeam.getEmail()).isPresent() || userWithTeam.getEmail().isEmpty()
				|| userWithTeam.getPasswordHash().isEmpty()
				|| teamService.findById(userWithTeam.getTeamName()).isPresent() || userWithTeam.getTeamName().isEmpty()
				|| userWithTeam.getTeamName().isEmpty() || userWithTeam.getPlayers().size() != 5) {

			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		User userNew = new User(userWithTeam.getUserName(), userWithTeam.getTeamName(), userWithTeam.getEmail(),
				userWithTeam.getPasswordHash(), userWithTeam.getRoles());
		userService.save(userNew);

		Team teamNew = new Team(userWithTeam.getTeamName());
		teamNew.getPlayers().add(new Player(userWithTeam.getPlayers().get(0)));
		teamNew.getPlayers().add(new Player(userWithTeam.getPlayers().get(1)));
		teamNew.getPlayers().add(new Player(userWithTeam.getPlayers().get(2)));
		teamNew.getPlayers().add(new Player(userWithTeam.getPlayers().get(3)));
		teamNew.getPlayers().add(new Player(userWithTeam.getPlayers().get(4)));

		teamService.save(teamNew);

		// Sending Confirmation Mail
		MailSenderXX ms = (MailSenderXX) appContext.getBean("mailSenderXX");
		ms.sendConfirmationEmail(userNew);

		return new ResponseEntity<>(userNew, HttpStatus.OK);
	}
	@PostMapping("/TenniShip/Team/{teamID}/image")
    	public ResponseEntity<Team> newTeamImg(@PathVariable String teamID, @RequestParam List<MultipartFile> imageFile)
    			throws IOException {
    		Optional<Team> team = teamService.findById(teamID);
    		if (team.isPresent()) {
    			team.get().setTeamImage(true);
    			teamService.save(team.get());
    			imgService.saveImage("teams", team.get().getName(), imageFile.get(0));
    			for (int i = 1; i < 6; i++) {
    				imgService.saveImage("players", team.get().getName() + String.format("Player%d", i), imageFile.get(i));
    			}
    			return new ResponseEntity<>(HttpStatus.CREATED);
    		} else
    			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
}
