package com.practica.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

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
import com.practica.TeamService;
import com.practica.TournamentService;
import com.practica.model.Player;
import com.practica.model.Team;
import com.practica.model.Tournament;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ImageService imgService;

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private UserComponent userComponent;

	@Autowired
	private TeamService teamService;

	@Autowired
	private TournamentService tournamentService;

	@PostMapping("/register")
	public String newUser(Model model, @ModelAttribute("personUser") User user, @RequestParam String passwordCheck,
			@RequestParam Player player1, @RequestParam Player player2, @RequestParam Player player3,
			@RequestParam Player player4, @RequestParam Player player5, @RequestParam List<MultipartFile> imageFile)
			throws IOException {

		boolean canContinue = true;

		// User
		Optional<User> userExist = userService.findByUserName(user.getUserName());

		boolean userNameAlready = userExist.isPresent();
		boolean userEmpty = user.getUserName().isEmpty();
		boolean userReady = !(userNameAlready || userEmpty);
		model.addAttribute("userNameAlreadyExist", userNameAlready);
		model.addAttribute("userNameEmpty", userEmpty);

		// Email
		Optional<User> emailExist = userService.findByEmail(user.getEmail());

		boolean emailAlready = emailExist.isPresent();
		boolean emailEmpty = user.getEmail().isEmpty();
		boolean emailGood = (user.getEmail().contains("@")) && (user.getEmail().length() > 10);
		boolean emailReady = !(emailAlready || emailEmpty) && emailGood;
		model.addAttribute("emailAlreadyExist", emailAlready);
		model.addAttribute("emailEmpty", emailEmpty);
		model.addAttribute("emailNotGood", !emailGood);

		// PassWord
		boolean passEmpty = user.getPasswordHash().isEmpty();
		boolean passNotGood = user.getPasswordHash().length() < 8;
		boolean passReady = !(passEmpty || passNotGood);
		model.addAttribute("passwordEmpty", passEmpty);
		model.addAttribute("passwordNotGood", passNotGood);

		// PassConfirm
		boolean samePassword = user.getPasswordHash().equals(passwordCheck);
		model.addAttribute("passNotSame", !samePassword);

		// Team
		Optional<Team> teamExist = teamService.findById(user.getTeam());

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

		boolean missingPics = false;
		for (MultipartFile mf : imageFile) {
			if (mf.isEmpty())
				missingPics = true;
		}
		model.addAttribute("ImageEmpty", missingPics);

		canContinue = (userReady && emailReady && passReady && samePassword && teamReady && player1NotEmpty
				&& player2NotEmpty && player3NotEmpty && player4NotEmpty && player5NotEmpty && !(missingPics));
		if (canContinue) {
			List<String> l = new LinkedList<>();
			l.add("ROLE_USER");
			User userNew = new User(user.getUserName(), user.getTeam(), user.getEmail(), user.getPasswordHash(), l);
			userService.save(userNew);

			Team teamNew = new Team(user.getTeam());
			teamNew.getPlayers().add(player1);
			teamNew.getPlayers().add(player2);
			teamNew.getPlayers().add(player3);
			teamNew.getPlayers().add(player4);
			teamNew.getPlayers().add(player5);

			// Saving team icon
			teamNew.setTeamImage(true);
			teamService.save(teamNew);
			imgService.saveImage("teams", teamNew.getName(), imageFile.get(0));
			for (int i = 1; i < 6; i++) {
				imgService.saveImage("players", teamNew.getName() + String.format("Player%d", i), imageFile.get(i));
			}

			// Sending Confirmation Mail
			MailSenderXX ms = (MailSenderXX) appContext.getBean("mailSenderXX");
			ms.sendConfirmationEmail(user);

			model.addAttribute("registeredSuccessful", true);
			return signIn(model);

		} else {
			return "registerAccount";
		}
	}

	@GetMapping("/TenniShip")
	public String index(Model model, HttpServletRequest request) {

		if (userComponent.isLoggedUser() && !request.isUserInRole("ADMIN")) {
			String teamUser = userComponent.getTeam();
			model.addAttribute("team", teamUser);
		}
		model.addAttribute("registered", userComponent.isLoggedUser() && !request.isUserInRole("ADMIN"));
		model.addAttribute("admin", userComponent.isLoggedUser() && request.isUserInRole("ADMIN"));
		List<Tournament> allTournaments = tournamentService.getAllTournaments();
		List<Team> allTeams = teamService.getAllTeams();
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
	public String signIn(Model model) {

		if (userComponent.isLoggedUser()) {
			return "redirect:/TenniShip";
		}
		return "login";
	}

	@GetMapping("/TenniShip/loginerror")
	public String signInWrong(Model model) {

		if (userComponent.isLoggedUser()) {
			return "redirect:/TenniShip";
		}

		model.addAttribute("wrongData", true);
		return "login";
	}

	@GetMapping("/TenniShip/SignUp")
	public String signUp(Model model) {

		if (userComponent.isLoggedUser()) {
			return "redirect:/TenniShip";
		}

		User user = new User();
		model.addAttribute("personUser", user);
		return "registerAccount";
	}
}
