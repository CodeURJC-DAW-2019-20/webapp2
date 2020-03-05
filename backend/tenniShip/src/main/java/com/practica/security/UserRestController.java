package com.practica.security;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

		teamService.save(teamNew);// in the future put in the line after "teamNew.setTeamImage(true);"

		// Saving team icon
//			teamNew.setTeamImage(true);
//			
//			imgService.saveImage("teams", teamNew.getName(), imageFile.get(0));
//			for (int i = 1; i < 6; i++) {
//				imgService.saveImage("players", teamNew.getName() + String.format("Player%d", i), imageFile.get(i));
//			}

		// Sending Confirmation Mail
		MailSenderXX ms = (MailSenderXX) appContext.getBean("mailSenderXX");
		ms.sendConfirmationEmail(userNew);

		return new ResponseEntity<>(userNew, HttpStatus.OK);
	}
}
