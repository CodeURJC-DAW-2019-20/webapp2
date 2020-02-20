package com.practica.security;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.practica.TournamentRepository;
import com.practica.model.Tournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.practica.ImageService;
import com.practica.MailSenderXX;
import com.practica.TeamRepository;
import com.practica.model.Player;
import com.practica.model.Team;

@Controller
public class UserController {

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
	
	@PostMapping("/register")
	public String newUser(Model model,@ModelAttribute("personUser") User user,@RequestParam String passwordCheck,
			@RequestParam Player player1,@RequestParam Player player2,@RequestParam Player player3,
			@RequestParam Player player4,@RequestParam Player player5, @RequestParam List<MultipartFile> imageFile) throws IOException {
		
		
		boolean canContinue = true;
		
		//User
		User userExist = userRepository.findByUserName(user.getUserName());
		
		boolean userNameAlready = (userExist != null);
		boolean userEmpty = user.getUserName().isEmpty();
		boolean userReady = !(userNameAlready || userEmpty);
		model.addAttribute("userNameAlreadyExist",userNameAlready);
		model.addAttribute("userNameEmpty", userEmpty);
		
		//Email
		User emailExist = userRepository.findByEmail(user.getEmail());
		
		boolean emailAlready = (emailExist != null);
		boolean emailEmpty = user.getEmail().isEmpty();
		boolean emailGood = (user.getEmail().contains("@") ) && (user.getEmail().length() > 10);
		boolean emailReady = !(emailAlready || emailEmpty) && emailGood;
		model.addAttribute("emailAlreadyExist", emailExist);
		model.addAttribute("emailEmpty", emailEmpty);	
		model.addAttribute("emailNotGood", !emailGood);
		
		//PassWord
		boolean passEmpty = user.getPasswordHash().isEmpty();
		boolean passNotGood = user.getPasswordHash().length()<8;
		boolean passReady = !(passEmpty || passNotGood);
		model.addAttribute("passwordEmpty", passEmpty);
		model.addAttribute("passwordNotGood", passNotGood);
		
		//PassConfirm
		boolean samePassword = user.getPasswordHash().equals(passwordCheck);
		model.addAttribute("passNotSame", !samePassword);
		
		//Team
		Optional<Team> teamExist = teamRepository.findById(user.getTeam());
		
		boolean teamAlready = (teamExist.isPresent());
		boolean teamEmty = user.getTeam().isEmpty();
		boolean teamReady = !(teamAlready || teamEmty);
		model.addAttribute("teamAlreadyExist", teamAlready);
		model.addAttribute("teamEmpty", teamEmty);
		
		
	
		boolean player1NotEmpty = !(player1.getName().isEmpty());
		boolean player2NotEmpty = !(player2.getName().isEmpty());
		boolean player3NotEmpty = !(player3.getName().isEmpty());
		boolean player4NotEmpty = !(player4.getName().isEmpty());
		boolean player5NotEmpty = !(player5.getName().isEmpty());
		model.addAttribute("player1NameEmpty", true);
		model.addAttribute("player2NameEmpty", true);
		model.addAttribute("player3NameEmpty", true);
		model.addAttribute("player4NameEmpty", true);
		model.addAttribute("player5NameEmpty", true);
		System.out.println("el player1 es : "+player1.getName());
		System.out.println("el player2 es : "+player2.getName());
		System.out.println("el player3 es : "+player3.getName());
		System.out.println("el player4 es : "+player4.getName());
		System.out.println("el player5 es : "+player5.getName());
		

		canContinue = (userReady && emailReady && passReady && samePassword && teamReady && player1NotEmpty && player2NotEmpty && player3NotEmpty && player4NotEmpty && player5NotEmpty);
		if(canContinue) {
			User userNew = new User(user.getUserName(), user.getTeam(), user.getEmail(), user.getPasswordHash(), "ROLE_USER");
			userRepository.save(userNew);
			
			Team teamNew = new Team(user.getTeam());
			teamNew.getPlayers().add(player1);
			teamNew.getPlayers().add(player2);
			teamNew.getPlayers().add(player3);
			teamNew.getPlayers().add(player4);
			teamNew.getPlayers().add(player5);
			teamRepository.save(teamNew);
			
			//Saving Images
			teamNew.setImage(true);
			teamRepository.save(teamNew);
			imgService.saveImage("teams", teamNew.getName(), imageFile.get(0));
			for (int i = 1; i < 6; i++) {
				imgService.saveImage("players", teamNew.getName() + String.format("player%d", i), imageFile.get(i));
			}
			
			// Sending Confirmation Mail
			MailSenderXX ms=(MailSenderXX) appContext.getBean("mailSenderXX");
			ms.sendConfirmationEmail(user);
			
			userComponent.setLoggedUser(userNew);
			userComponent.setTeam(userNew);
			return "index";
		} else {
			return "registerAccount";
		}		
	}
	
	@GetMapping("/TenniShip")
	public String index (Model model) {

		if(userComponent.isLoggedUser()) {
			String teamUser = userComponent.getTeam();
			model.addAttribute("team", teamUser);
		}
		model.addAttribute("registered",userComponent.isLoggedUser());

		List<Tournament> allTournaments = tournamentRepository.getAllTournaments();
		List<Team> allTeams = teamRepository.getAllTeams();
		List<String> tournamentNames = new ArrayList<>();
		List<String> teamNames = new ArrayList<>();
		for (Tournament t : allTournaments) {
			tournamentNames.add(t.getName());
		}
		for (Team t : allTeams) {
			teamNames.add(t.getName());
		}
		model.addAttribute("tournamentNames", tournamentNames);
		model.addAttribute("teamNames", teamNames);

		return "index"; 
	}

	@GetMapping("/TenniShip/SignIn")
	public String sign_in (Model model) {
		
		if(userComponent.isLoggedUser()) {
			return index(model);
		}
		return "login";
	}

	@GetMapping("/TenniShip/loginerror")
    	public String sign_in_wrong (Model model) {

    		if(userComponent.isLoggedUser()) {
    			return index(model);
    		}
    		model.addAttribute("wrongData", true);
    		return "login";
    	} 
	
//	@GetMapping("/")
//	private String redirect (Model model) {
//		return index(model);
//	}
	
	@GetMapping("/TenniShip/SignUp")
	public String sign_up (Model model) {

		User user = new User();
		model.addAttribute("personUser", user);
		return "registerAccount";
	}
}
