package com.practica.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.practica.ImageService;
import com.practica.MailSenderXX;
import com.practica.TeamRepository;
import com.practica.TournamentRepository;
import com.practica.model.Match;
import com.practica.model.Player;
import com.practica.model.Team;
import com.practica.model.Tournament;

@RestController
@RequestMapping("/api")
public class UserRestController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ImageService imgService;

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private UserComponent userComponent;

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private TournamentRepository tournamentRepository;

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



		public void setUserName(String userName) {
			this.userName = userName;
		}



		public String getPasswordHash() {
			return passwordHash;
		}



		public void setPasswordHash(String passwordHash) {
			this.passwordHash = passwordHash;
		}



		public String getEmail() {
			return email;
		}



		public void setEmail(String email) {
			this.email = email;
		}



		public String getTeamName() {
			return teamName;
		}



		public void setTeamName(String teamName) {
			this.teamName = teamName;
		}



		public List<String> getRoles() {
			return roles;
		}



		public void setRoles(List<String> roles) {
			this.roles = roles;
		}



		public List<String> getPlayers() {
			return players;
		}



		public void setPlayers(List<String> players) {
			this.players = players;
		}

		
	}

	//Crear un nuevo user para la contraseña y guardar ese
	
	@PostMapping("/TenniShip/SignUp")
//	public ResponseEntity<User> newUser(@RequestBody String user, @RequestBody String email, @RequestBody String password,
//			@RequestBody String passwordCheck, @RequestBody String team, @RequestBody String player1, @RequestBody String player2,
//			@RequestBody String player3, @RequestBody String player4, @RequestBody String player5,
//			@RequestParam List<MultipartFile> imageFile) throws IOException {

	public ResponseEntity<User> newUser(@RequestBody UserCreatedRest userWithTeam) {
//			if (userRepository.findByUserName(user).isPresent() || (password != passwordCheck) || user.isEmpty() || password.isEmpty() 
//					|| teamRepository.findById(team).isPresent() || team.isEmpty() || player1.isEmpty() || player2.isEmpty() || player3.isEmpty()
//					|| player4.isEmpty() || player5.isEmpty()) {
//				
//				return new ResponseEntity<>(HttpStatus.CONFLICT);
//			}
		if (userRepository.findByUserName(userWithTeam.getUserName()).isPresent()
				|| userRepository.findByEmail(userWithTeam.getEmail()).isPresent() || userWithTeam.getEmail().isEmpty()
				|| userWithTeam.getPasswordHash().isEmpty() 
				|| teamRepository.findById(userWithTeam.getTeamName()).isPresent() || userWithTeam.getTeamName().isEmpty()
				|| userWithTeam.getTeamName().isEmpty() || userWithTeam.getPlayers().size() != 5
				) {

			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

//		List<String> l = new LinkedList<>();
//		l.add("ROLE_USER");
//		User userNew = new User(user, team, email, password, l);
		User userNew = new User(userWithTeam.getUserName(), userWithTeam.getTeamName(), userWithTeam.getEmail(), userWithTeam.getPasswordHash(), userWithTeam.getRoles());
		userRepository.save(userNew);

		Team teamNew = new Team(userWithTeam.getTeamName());
		teamNew.getPlayers().add(new Player(userWithTeam.getPlayers().get(0)));
		teamNew.getPlayers().add(new Player(userWithTeam.getPlayers().get(1)));
		teamNew.getPlayers().add(new Player(userWithTeam.getPlayers().get(2)));
		teamNew.getPlayers().add(new Player(userWithTeam.getPlayers().get(3)));
		teamNew.getPlayers().add(new Player(userWithTeam.getPlayers().get(4)));
		
		teamRepository.save(teamNew);// in the future put in the line after "teamNew.setTeamImage(true);"

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

	@GetMapping("/TenniShip/SignIn")
	public ResponseEntity<User> signIn() {
		if (!userComponent.isLoggedUser()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} else {
			User loggedUser = userComponent.getLoggedUser();
			return new ResponseEntity<>(loggedUser, HttpStatus.OK);
		}
	}

	@GetMapping(value = "/logout")
	public ResponseEntity<Boolean> logOut(HttpSession session) {
		if (!userComponent.isLoggedUser()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} else {
			session.invalidate();
			return new ResponseEntity<>(true, HttpStatus.OK);
		}
	}
}
