package com.practica;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.practica.model.Match;
import com.practica.model.Team;
import com.practica.model.Tournament;
import com.practica.security.UserComponent;

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

	@Autowired
	private MatchService matchService;

	@GetMapping("/Tournament/{tournamentId}/image")
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

//	@GetMapping("/Tournament/{tournamentId}")
//	public ResponseEntity<Tournament> seeTournament(@PathVariable String tournamentId) {
//
//		Optional<Tournament> tournament = tournamentService.findById(tournamentId);
//		if (tournament.isPresent()) {
//			return new ResponseEntity<>(tournament.get(), HttpStatus.OK);
//		} else {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//	}

	@GetMapping("/RegisterMatch/Tournament")
	public ResponseEntity<List<Tournament>> selectTournament(HttpServletRequest request) {
		if (userComponent.isLoggedUser() && !request.isUserInRole("ADMIN")) {
			String team = userComponent.getTeam();
			Optional<Team> t = teamService.findById(team);

			return new ResponseEntity<>(teamService.getTournaments(t.get()), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}

	public static class RegisterNewMatchAuxiliarClass {

		private String teamHome;
		private int quantityHome;
		private String teamAway;
		private int quantityAway;

		public RegisterNewMatchAuxiliarClass() {

		}

		public String getTeamHome() {
			return teamHome;
		}

		public int getQuantityHome() {
			return quantityHome;
		}

		public String getTeamAway() {
			return teamAway;
		}

		public int getQuantityAway() {
			return quantityAway;
		}
	}

	interface PutMatch extends Match.Basic, Team.Basic, Tournament.Basic {
	}

	@JsonView(PutMatch.class)
	@PutMapping("/RegisterMatch/Tournament/{tournament}/Submission")
	public ResponseEntity<Match> submitMatch(@PathVariable String tournament,
			@RequestBody RegisterNewMatchAuxiliarClass newMatch, HttpServletRequest request)
			throws InterruptedException {

		if (userComponent.isLoggedUser() && !request.isUserInRole("ADMIN")) {
			Optional<Tournament> t = tournamentService.findById(tournament);
			Optional<Team> home = teamService.findById(newMatch.getTeamHome());
			Optional<Team> away = teamService.findById(newMatch.getTeamAway());
			if (t.isPresent() && home.isPresent() && away.isPresent()) {
				if (tournamentService.getTeams(t.get()).contains(teamService.findById(userComponent.getTeam()).get())) {
					if (newMatch.getQuantityHome() == 3 ^ newMatch.getQuantityAway() == 3) { // XOR
						Match match = matchService.findMatch(home.get(), away.get(), t.get()).get(0);
						if (match != null) {
							matchService.getOne(match.getId()).setHomePoints(newMatch.getQuantityHome());
							matchService.getOne(match.getId()).setAwayPoints(newMatch.getQuantityAway());

							matchService.save(match);
							return new ResponseEntity<>(match, HttpStatus.CREATED);
						}
						return new ResponseEntity<>(HttpStatus.NOT_FOUND);
					} else {
						return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
					}
				} else {
					return new ResponseEntity<>(HttpStatus.FORBIDDEN);
				}
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
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
	@GetMapping("/RegisterMatch/Tournament/{tournament}")
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
		if (!(userComponent.isLoggedUser()))
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}