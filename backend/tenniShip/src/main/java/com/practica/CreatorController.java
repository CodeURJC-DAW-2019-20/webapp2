package com.practica;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.practica.model.Match;
import com.practica.model.Team;
import com.practica.model.Tournament;
import com.practica.security.UserComponent;

@Controller
public class CreatorController {

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private TournamentRepository tournamentRepository;

	@Autowired
	private ImageService imgService;

	@Autowired
	private UserComponent userComponent;
	
	@Autowired
	private CreatorService creatorService;

	private Tournament finalTournament = new Tournament();
	private String[] teamList = new String[18];
	private int numberTeams = 0;

	@PostMapping("/TenniShip/Creator/Tournament")
	public String tournament(Model model, @ModelAttribute("newTournament") Tournament tour, MultipartFile imageFile,
			HttpServletRequest request) throws IOException {

		model.addAttribute("admin", userComponent.isLoggedUser() && request.isUserInRole("ADMIN"));

		if (userComponent.isLoggedUser() && !request.isUserInRole("ADMIN")) {
			String teamUser = userComponent.getTeam();
			model.addAttribute("team", teamUser);
		}

		Optional<Tournament> tourExist = tournamentRepository.findById(tour.getName());
		boolean tournamenAlreadyExist = tourExist.isPresent();
		boolean tourEmpty = tour.getName().isEmpty();
		boolean tourReady = (!tournamenAlreadyExist && !tourEmpty);
		model.addAttribute("tourAlready", tournamenAlreadyExist);
		model.addAttribute("tourEmpty", tourEmpty);
		model.addAttribute("next1", tourReady && !(imageFile.isEmpty()));
		model.addAttribute("tourFinal", tour);

		// Image

		model.addAttribute("imageEmpty", imageFile.isEmpty());

		if (tourReady && !(imageFile.isEmpty())) {
			finalTournament.setName(tour.getName());

			// Save TournamentImage
			finalTournament.setImage(true);
			imgService.saveImage("tournaments", finalTournament.getName(), imageFile);
		}

		/* Adding a list of all teams for the autocomplete */
		model.addAttribute("teamNames", teamNames());

		return "tournamentCreator";
	}

	private void exist(Model model, String[] array) {
		int aux = 0;
		for (int j = 0; j < 18; j++) {
			aux = 0;
			for (int i = 0; i < 18; i++) {
				if (array[i] != null) {
					if (array[i].equals(array[j])) {
						aux++;
					}
				}
			}
			if (aux > 1) {
				model.addAttribute(String.format("teamInList%d", j), true);
			}
		}
	}

	private int exist2(Model model, String[] array, String team) {
		int aux = 0;
		for (int i = 0; i < 18; i++) {
			if (array[i] != null) {
				if (array[i].equals(team)) {
					aux++;
				}
			}
		}

		return aux;
	}

	private void check(Model model, String team, int id) {
		Optional<Team> team1Exist = teamRepository.findById(team);
		boolean team1NoExist = team1Exist.isPresent();
		boolean team1Empty = team.isEmpty();
		boolean team1Ready = (team1NoExist && !team1Empty);

		if (team.isEmpty() && (teamList[id - 1] != null)) {
			Optional<Team> team1finalExist = teamRepository.findById(teamList[id - 1]);
			team1NoExist = team1finalExist.isPresent();
			if (team1NoExist) {
				if (exist2(model, teamList, teamList[id - 1]) == 1) {// aux == 1 
					numberTeams--;
				}
			}
			teamList[id - 1] = null;
		} else {
			if (team1Ready) {
				if (teamList[id - 1] != null) {
					if (!(teamList[id - 1].equals(team))) {
						String aux = teamList[id - 1];
						teamList[id - 1] = team;
						if ((exist2(model, teamList, team)) < 2) {// aux < 2
							numberTeams++;
						}
						if ((teamRepository.findById(aux).isPresent()) && ((exist2(model, teamList, team) == 0))) {// aux == 0
							numberTeams--;
						}
					}
				} else {
					if ((exist2(model, teamList, team)) == 0) {// aux == 0
						numberTeams++;
					}
					teamList[id - 1] = team;
				}
			}

			if (teamList[id - 1] != null) {
				if (!team.isEmpty() && !(teamList[id - 1].equals(team))) {
					numberTeams--;
					teamList[id - 1] = team;
				}
			}
		}

		model.addAttribute(String.format("teamNo%d", id - 1), !team1NoExist);
		model.addAttribute(String.format("teamEmpty%d", id - 1), team1Empty);
		model.addAttribute(String.format("teamFinal%d", id - 1), teamList[id - 1]);
	}

	@PostMapping("/TenniShip/Creator/Teams")
	public String teams(Model model, @RequestParam String team1, @RequestParam String team2,
			@RequestParam String team3, @RequestParam String team4, @RequestParam String team5,
			@RequestParam String team6, @RequestParam String team7, @RequestParam String team8,
			@RequestParam String team9, @RequestParam String team10, @RequestParam String team11,
			@RequestParam String team12, @RequestParam String team13, @RequestParam String team14,
			@RequestParam String team15, @RequestParam String team16, @RequestParam String team17,
			@RequestParam String team18, HttpServletRequest request) {

		model.addAttribute("admin", userComponent.isLoggedUser() && request.isUserInRole("ADMIN"));

		if (userComponent.isLoggedUser() && !request.isUserInRole("ADMIN")) {
			String teamUser = userComponent.getTeam();
			model.addAttribute("team", teamUser);
		}

		model.addAttribute("tourFinal", finalTournament);
		model.addAttribute("next1", !finalTournament.getName().isEmpty());
		
		check(model, team1, 1);
		check(model, team2, 2);
		check(model, team3, 3);
		check(model, team4, 4);
		check(model, team5, 5);
		check(model, team6, 6);
		check(model, team7, 7);
		check(model, team8, 8);
		check(model, team9, 9);
		check(model, team10, 10);
		check(model, team11, 11);
		check(model, team12, 12);
		check(model, team13, 13);
		check(model, team14, 14);
		check(model, team15, 15);
		check(model, team16, 16);
		check(model, team17, 17);
		check(model, team18, 18);
		exist(model, teamList);

		model.addAttribute("next2", numberTeams == 18);
		if (numberTeams == 18)
			model.addAttribute("listTeam", teamList);

		/* Adding a list of all teams for the autocomplete */
		model.addAttribute("teamNames", teamNames());

		return "tournamentCreator";
	}

	private List<String> teamNames() {
		List<Team> allTeams = teamRepository.getAllTeams();
		List<String> teamNames = new ArrayList<>();
		for (Team t : allTeams) {
			teamNames.add(t.getName());
		}
		return teamNames;
	}

	@PostMapping("/TenniShip/Creator/Raffle")
	public String raffle(Model model, HttpServletRequest request) {
		model.addAttribute("admin", userComponent.isLoggedUser() && request.isUserInRole("ADMIN"));
		String userTeam = userComponent.getTeam();
		model.addAttribute("team", userTeam);

		model.addAttribute("next3Raffle", true);
		model.addAttribute("tourFinal", finalTournament);
		tournamentRepository.save(finalTournament);
		List<Team> teamListFinal = new ArrayList<>();
		for (String x : teamList) {
			Optional<Team> team = teamRepository.findById(x);
			teamListFinal.add(team.get());
		}
		creatorService.raffleTeamsCreateMatches(finalTournament, teamListFinal);

		/* Adding a list of all teams for the autocomplete */
		model.addAttribute("teamNames", teamNames());

		return "tournamentCreatorRaffle";
	}

	@GetMapping("/TenniShip/Creator")
	public String create(Model model, HttpServletRequest request) {

		if (userComponent.isLoggedUser()) {
			Tournament tour = new Tournament();
			model.addAttribute("newTournament", tour);
			model.addAttribute("admin", userComponent.isLoggedUser() && request.isUserInRole("ADMIN"));

			if (!request.isUserInRole("ADMIN")) {
				String teamUser = userComponent.getTeam();
				model.addAttribute("team", teamUser);
			}
			return "tournamentCreator";
		} else {
			return "redirect:/TenniShip/SignIn";
		}

	}

}
