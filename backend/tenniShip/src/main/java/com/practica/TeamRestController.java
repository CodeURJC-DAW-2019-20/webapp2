package com.practica;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;
import com.practica.model.Match;
import com.practica.model.Player;
import com.practica.model.Team;
import com.practica.model.Tournament;
import com.practica.security.User;
import com.practica.security.UserComponent;

@RestController
@RequestMapping("/api")

public class TeamRestController {

	@Autowired
	private TeamService teamService;

	@Autowired
	private ImageService imgService;

	@Autowired
	private UserComponent userComponent;

//	@GetMapping("/{id}/image")
//	public ResponseEntity<Object> getTeamImage(@PathVariable String id) throws IOException {
//		Optional<Team> team = teamRepository.findById(id);
//		if (team.isPresent()) {
//			if (team.get().hasTeamImage()) {
//				return this.imgService.createResponseFromImage("teams", id);
//			} else {
//				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//			}
//		} else {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//	}
//
//	@GetMapping("/{id}")
//	public ResponseEntity<Team> seeTeam(@PathVariable String id) {
//
//		Optional<Team> team = teamRepository.findById(id);
//		if (team.isPresent()) {
//			return new ResponseEntity<>(team.get(), HttpStatus.OK);
//		} else {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//	}

	public static class TeamFileData {

		interface Basic {
		}

		@JsonView(Basic.class)
		private String teamName;

		@JsonView(Basic.class)
		private List<String> imageListTournaments = new ArrayList<>();

		@JsonView(Basic.class)
		private boolean teamImage;

		@JsonView(Basic.class)
		private double percentageLostMatches;

		@JsonView(Basic.class)
		private double percentageWonMatches;

		@JsonView(Basic.class)
		private List<Player> playerList = new ArrayList<>();

		@JsonView(Basic.class)
		private List<Match> matchesList = new ArrayList<>();

		public TeamFileData(String teamName, List<String> imageListTournaments, boolean teamImage,
				double percentageLostMatches, double percentageWonMatches, List<Player> playerList,
				List<Match> matchesList) {

			this.teamName = teamName;
			this.imageListTournaments = imageListTournaments;
			this.teamImage = teamImage;
			this.percentageLostMatches = percentageLostMatches;
			this.percentageWonMatches = percentageWonMatches;
			this.playerList = playerList;
			this.matchesList = matchesList;
		}

		public TeamFileData(Team team) {
			this.teamName = team.getName();
			this.teamImage = team.hasTeamImage();
			this.playerList = team.getPlayers();
		}

	}

	public static class listMatches {
		public List<Match> listMatchesFinished = new ArrayList<>();

		public listMatches(List<Match> listMatchesFinished) {
			this.listMatchesFinished = listMatchesFinished;
		}
	}

	interface TeamDetails extends TeamFileData.Basic, Match.Basic, Team.Basic, Tournament.Basic, Player.Basic {
	}

	@JsonView(TeamDetails.class)
	@GetMapping("/TenniShip/Team/{team}")
	public ResponseEntity<TeamFileData> team(@PathVariable String team) {
		Optional<Team> t = teamService.findById(team);

		if (t.isPresent()) {

			TeamFileData teamfiledata = teamProfile(t.get());
			return new ResponseEntity<>(teamfiledata, HttpStatus.OK);

		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/TenniShip/Team/{team}/ListMatches/{position}/{end}")
	public ResponseEntity<listMatches> listTeams(@PathVariable String team, @PathVariable int position,
			@PathVariable int end) {
		Optional<Team> t = teamService.findById(team);

		if (t.isPresent()) {
			List<Match> matches = teamService.getRecentMatches(t.get());

			end = Integer.min(matches.size(), end);
			// This spot is prepared for the Ajax pagination for the matches...
			// Dont know what to do here

			listMatches listMatches = new listMatches(matches);
			return new ResponseEntity<>(listMatches, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	public TeamFileData teamProfile(Team team) {

		double percentageMatchesLost = 0.0;
		double percentageMatchesWon = 0.0;
		double totalMatches = 0.0;

		percentageMatchesLost = teamService.getMatchesLost(team.getName());
		percentageMatchesWon = teamService.getMatchesWon(team.getName());

		totalMatches = percentageMatchesLost + percentageMatchesWon;

		percentageMatchesLost = (percentageMatchesLost / totalMatches) * 100;
		percentageMatchesWon = (percentageMatchesWon / totalMatches) * 100;

		List<String> tournamentsList = teamService.getTournamentsName(team);

		List<Player> players = team.getPlayers();

		List<Match> recentMatches = teamService.getRecentMatches(team);

		return new TeamFileData(team.getName(), tournamentsList, team.hasTeamImage(), percentageMatchesLost,
				percentageMatchesWon, players, recentMatches);
	}

}