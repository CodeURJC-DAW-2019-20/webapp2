package com.practica.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

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

	@PostMapping("/TenniShip/SignUp")
	public ResponseEntity<User> newUser(@RequestBody String user, @RequestBody String email, @RequestBody String password,
			@RequestBody String passwordCheck, @RequestBody String team, @RequestBody String player1, @RequestBody String player2,
			@RequestBody String player3, @RequestBody String player4, @RequestBody String player5,
			@RequestParam List<MultipartFile> imageFile) throws IOException {

			if (userRepository.findByUserName(user).isPresent() || (password != passwordCheck) || user.isEmpty() || password.isEmpty() 
					|| teamRepository.findById(team).isPresent() || team.isEmpty() || player1.isEmpty() || player2.isEmpty() || player3.isEmpty()
					|| player4.isEmpty() || player5.isEmpty()) {
				
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
		
			List<String> l = new LinkedList<>();
			l.add("ROLE_USER");
			User userNew = new User(user, team, email, password, l);
			userRepository.save(userNew);

			Team teamNew = new Team(team);
			teamNew.getPlayers().add(new Player(player1));
			teamNew.getPlayers().add(new Player(player2));
			teamNew.getPlayers().add(new Player(player3));
			teamNew.getPlayers().add(new Player(player4));
			teamNew.getPlayers().add(new Player(player5));

			// Saving team icon
//			teamNew.setTeamImage(true);
//			teamRepository.save(teamNew);
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
