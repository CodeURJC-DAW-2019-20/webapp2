package com.practica.team;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.practica.ImageService;
import com.practica.model.Match;
import com.practica.model.Player;
import com.practica.model.Team;
import com.practica.model.Tournament;

@RestController
@RequestMapping("/api/TenniShip")

public class TeamRestController {

	@Autowired
	private TeamService teamService;

	@Autowired
	private ImageService imgService;

	@Autowired
	private TeamRepository teamRepository;

	@GetMapping("/Teams")
	public ResponseEntity<List<Team>> teams() {
		return new ResponseEntity<>(teamRepository.getAllTeams(), HttpStatus.OK);
	}

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
				double percentageLostMatches, double percentageWonMatches, List<Player> playerList) {

			this.teamName = teamName;
			this.imageListTournaments = imageListTournaments;
			this.teamImage = teamImage;
			this.percentageLostMatches = percentageLostMatches;
			this.percentageWonMatches = percentageWonMatches;
			this.playerList = playerList;
		}

		public List<Match> getMatchesList() {
			return matchesList;
		}

		public void setMatchesList(List<Match> matchesList) {
			this.matchesList = matchesList;
		}

	}

	interface MatchDetails extends TeamFileData.Basic, Match.Basic, Team.Basic, Tournament.Basic, Player.Basic {
	}

	@JsonView(MatchDetails.class)
	@GetMapping("/Team/{team}")
	// https://localhost:8443/api/TenniShip/Team/{team}/?NumberOfMatchesListed=end
	public ResponseEntity<TeamFileData> getTeam(@PathVariable String team, Pageable page,
			@RequestParam("NumberOfMatchesListed") int end) {

		Optional<Team> t = teamRepository.findById(team);
		
		if(t.isPresent()) {
		
			Page<Match> pages = teamService.getPagesInMatches(t.get(), page,end);			
			
			List<Match> listmatches = teamService.getListMatches(pages);
			
			TeamFileData teamfiledata = teamService.teamProfile(t.get());
			teamfiledata.setMatchesList(listmatches);
			return new ResponseEntity<>(teamfiledata, HttpStatus.OK);

		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/Team/{teamID}/image/{npic}")
	public ResponseEntity<Object> getTeamImage(@PathVariable String teamID, @PathVariable String npic)
			throws IOException {
		/* nPic value is 0 for team pic, and 1-5 for players */
		Optional<Team> team = teamService.findById(teamID);
		if (team.isPresent()) {
			if (team.get().hasTeamImage()) {
				switch (npic) {
				case "0":
					return this.imgService.createResponseFromImage("teams", teamID);

				default:
					return this.imgService.createResponseFromImage("players", teamID + "player" + npic);
				}
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}