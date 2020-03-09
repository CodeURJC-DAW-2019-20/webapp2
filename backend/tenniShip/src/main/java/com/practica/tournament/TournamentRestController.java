package com.practica.tournament;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.practica.ImageService;
import com.practica.model.Match;
import com.practica.model.Team;
import com.practica.model.Tournament;
import com.practica.security.UserComponent;
import com.practica.team.TeamService;

@RestController
@RequestMapping("/api/TenniShip")
public class TournamentRestController {

	@Autowired
	private TournamentService tournamentService;

	@Autowired
	private TeamService teamService;

	@Autowired
	private ImageService imgService;

	@Autowired
	private UserComponent userComponent;

	@GetMapping("/Tournaments")
	public ResponseEntity<List<Tournament>> tournaments() {
		return new ResponseEntity<>(tournamentService.getAllTournaments(), HttpStatus.OK);
	}

	@GetMapping("/Tournaments/{tournamentId}/image")
	public ResponseEntity<Object> getTournamentImage(@PathVariable String tournamentId) throws IOException {
		Optional<Tournament> tournament = tournamentService.findById(tournamentId);
		if (tournament.isPresent()) {
			if (tournament.get().hasImage()) {
				return this.imgService.createResponseFromImage("tournaments", tournamentId);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	//https://localhost:8443/api/TenniShip/RegisterMatch/Tournaments/?NumberOfTournamentsDisplayed=3
	@GetMapping("/RegisterMatch/Tournaments")
	public ResponseEntity<List<Tournament>> selectTournament(HttpServletRequest request, Pageable page, @RequestParam("NumberOfTournamentsDisplayed") int end ) {
		if (userComponent.isLoggedUser() && !request.isUserInRole("ADMIN")) {
			String team = userComponent.getTeam();
			Optional<Team> t = teamService.findById(team);
			
			Page<Tournament> pages = teamService.getPagesInTournaments(t.get(), page,end);			
			
			List<Tournament> listtournaments = teamService.getListTournaments(pages);

			return new ResponseEntity<>(listtournaments, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}

	public interface PutMatch extends Match.Basic, Team.Basic, Tournament.Basic {
	}


	@JsonView(PutMatch.class)
	@PutMapping("/RegisterMatch/Tournaments/{tournament}/Submission")
	public ResponseEntity<Match> submitMatch(@PathVariable String tournament, @RequestBody Match newMatch,
			HttpServletRequest request) throws InterruptedException {

		if (userComponent.isLoggedUser() && !request.isUserInRole("ADMIN")) {
			return tournamentService.putTheMatch(tournament, newMatch,false);
		} else
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}

	public static class SelectMatchAuxiliarClass {
		interface Basic {
		}

		@JsonView(Basic.class)
		private String round;

		@JsonView(Basic.class)
		private List<Match> matches = new ArrayList<>();

		public SelectMatchAuxiliarClass() {

		}

		public SelectMatchAuxiliarClass(String round, List<Match> matches) {
			this.round = round;
			this.matches = matches;
		}
	}

	interface selectMatch extends Match.Basic, SelectMatchAuxiliarClass.Basic, Team.Basic, Tournament.Basic {
	}

	@JsonView(selectMatch.class)
	@GetMapping("/RegisterMatch/Tournaments/{tournament}")
	public ResponseEntity<SelectMatchAuxiliarClass> selectMatch(@PathVariable String tournament,
			HttpServletRequest request) {

		Optional<Tournament> t = tournamentService.findById(tournament);// check if that team play this tournament

		if (!request.isUserInRole("ADMIN") && t.isPresent() && userComponent.isLoggedUser()
				&& tournamentService.getTeams(t.get()).contains(teamService.findById(userComponent.getTeam()).get())) {
			String team = userComponent.getTeam();
			Optional<Team> tm = teamService.findById(team);

			if (!(tournamentService.getNextMatches(t.get(), tm.get()).isEmpty())) {
				SelectMatchAuxiliarClass objectToReturn = new SelectMatchAuxiliarClass(
						tournamentService.getNextMatches(t.get(), tm.get()).get(0).getStringType(),
						tournamentService.getNextMatches(t.get(), tm.get()));
				return new ResponseEntity<>(objectToReturn, HttpStatus.OK);
			}
		}
		if (!(userComponent.isLoggedUser()) || request.isUserInRole("ADMIN"))
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}