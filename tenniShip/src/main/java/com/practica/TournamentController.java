package com.practica;

import java.util.Optional;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.practica.model.Team;
import com.practica.model.Tournament;
import com.practica.security.User;
import com.practica.security.UserComponent;
import com.practica.security.UserRepository;
import com.practica.model.Match;

@Controller
public class TournamentController {

	@Autowired
	private TournamentRepository tournamentRepository;

	@Autowired
	private MatchRepository matchRepository;

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserComponent userComponent;
	
	//@GetMapping("/Tournament/{tournament}")
	static int matchesWon = 0;
	static int pointsWon = 0;
	static int totalMatchesPlayed = 0;
	static int j;
	//@GetMapping("/Tournament/{tournament}")
	
	private static class Pair implements Comparable<Pair> {
		private Team t;
		private Integer matchesWon; //Spain 3 - 2 France
		private Integer pointsWon; //Spain matchesWon += 1, Spain pointsWon += 3
		
		public Pair(Team team, int matchesWon, int pointsWon) {
			this.t = team; this.matchesWon = matchesWon; this.pointsWon = pointsWon;
		}

		@Override
		public int compareTo(Pair p2) {
			if (this.matchesWon > p2.matchesWon) return -1; else if (this.pointsWon > p2.pointsWon) return -1; else return 0;
		}
	}


	@GetMapping("/Tournament/{tournament}")
	public String tournament(Model model, @PathVariable String tournament) {

		Optional<Tournament> t = tournamentRepository.findById(tournament);
		int progressPercentage;
		final int TOTAL_MATCHES=25;

		if (t.isPresent()) {
			progressPercentage=tournamentRepository.getPlayedMatches(t.get());
			progressPercentage=(progressPercentage/TOTAL_MATCHES)*100;
			model.addAttribute("tournamentName", t.get().getName());
			model.addAttribute("completion",progressPercentage);
			//GRUPOS-------
			totalMatchesPlayed = 0;
			String [] groups = {"A", "B", "C", "D", "E", "F"};
			ArrayList<Pair>[] sortedGroups = new ArrayList[6];
			for	(int i = 0; i < 6; i++) { sortedGroups[i] = new ArrayList<>();}
			
			for (int i = 0; i < 6; i++) {
				tournamentRepository.getPhaseTeams(t.get(), groups[i]).forEach(Team -> {
					matchesWon = 0; pointsWon = 0;
					matchRepository.findMatches(Team, t.get()).forEach(Match -> {
						if (!(Match.getHomePoints() == 0 && Match.getAwayPoints() == 0)) {
							totalMatchesPlayed++;
						} if (Team.equals(Match.getTeam1()) && Match.getHomePoints() > Match.getAwayPoints()) {
							matchesWon++; pointsWon += Match.getHomePoints();							
						} else if (Team.equals(Match.getTeam2()) && Match.getHomePoints() < Match.getAwayPoints()) {
							matchesWon++; pointsWon += Match.getAwayPoints();
						} else
							pointsWon += Math.min(Match.getHomePoints(), Match.getAwayPoints());
					});
					sortedGroups[j].add(new Pair(Team, matchesWon, pointsWon));
				});
				Collections.sort(sortedGroups[j]); 
				model.addAttribute(String.format("team0Group%s", groups[j]), sortedGroups[j].get(0).t.getName());
				model.addAttribute(String.format("team0Group%sMatchesWon", groups[j]), sortedGroups[j].get(0).matchesWon);
				model.addAttribute(String.format("team0Group%sPointsWon", groups[j]), sortedGroups[j].get(0).pointsWon);
				model.addAttribute(String.format("team1Group%s", groups[j]), sortedGroups[j].get(1).t.getName());
				model.addAttribute(String.format("team1Group%sMatchesWon", groups[j]), sortedGroups[j].get(1).matchesWon);
				model.addAttribute(String.format("team1Group%sPointsWon", groups[j]), sortedGroups[j].get(1).pointsWon);
				model.addAttribute(String.format("team2Group%s", groups[j]), sortedGroups[j].get(2).t.getName());
				model.addAttribute(String.format("team2Group%sMatchesWon", groups[j]), sortedGroups[j].get(2).matchesWon);
				model.addAttribute(String.format("team2Group%sPointsWon", groups[j]), sortedGroups[j].get(2).pointsWon);
				j++;
			}
			j = 0;
			//-------GRUPOS
			
			//FINAL PHASE--
				if ((totalMatchesPlayed/2) == 18) { //Groups Played
					
					if (tournamentRepository.getPhaseTeams(t.get(), "X").isEmpty()) { //RoundOf8 has to be created
						List<Pair> secondPlaceTeams = new ArrayList<>();
						secondPlaceTeams.add(sortedGroups[0].get(1)); secondPlaceTeams.add(sortedGroups[1].get(1)); secondPlaceTeams.add(sortedGroups[2].get(1));
						secondPlaceTeams.add(sortedGroups[3].get(1)); secondPlaceTeams.add(sortedGroups[4].get(1)); secondPlaceTeams.add(sortedGroups[5].get(1));
						Collections.sort(secondPlaceTeams);
						
						List<Team> last8 = new ArrayList<>();
						last8.add(sortedGroups[0].get(0).t); last8.add(sortedGroups[1].get(0).t); last8.add(sortedGroups[2].get(0).t);
						last8.add(sortedGroups[3].get(0).t); last8.add(sortedGroups[4].get(0).t); last8.add(sortedGroups[5].get(0).t);
						last8.add(secondPlaceTeams.get(0).t); last8.add(secondPlaceTeams.get(1).t);
						Collections.shuffle(last8);
						
						Match mroundOf81 = new Match(0, 0, "X"); mroundOf81.setTeam1(last8.get(0)); mroundOf81.setTeam2(last8.get(1)); 
							mroundOf81.setTournament(t.get()); matchRepository.save(mroundOf81);
						Match mroundOf82 = new Match(0, 0, "X"); mroundOf82.setTeam1(last8.get(2)); mroundOf82.setTeam2(last8.get(3));
							mroundOf82.setTournament(t.get()); matchRepository.save(mroundOf82);
						Match mroundOf83 = new Match(0, 0, "X"); mroundOf83.setTeam1(last8.get(4)); mroundOf83.setTeam2(last8.get(5));
							mroundOf83.setTournament(t.get()); matchRepository.save(mroundOf83);
						Match mroundOf84 = new Match(0, 0, "X"); mroundOf84.setTeam1(last8.get(6)); mroundOf84.setTeam2(last8.get(7));
							mroundOf84.setTournament(t.get()); matchRepository.save(mroundOf84);
					}
					for (int k = 0; k < 8; k+=2) { //RoundOf8
						model.addAttribute(String.format("team%dQuarters", k), tournamentRepository.getPhaseMatches(t.get(), "X").get(k/2).getTeam1().getName());
						model.addAttribute(String.format("team%dQuartersPoints", k), tournamentRepository.getPhaseMatches(t.get(), "X").get(k/2).getHomePoints());
						model.addAttribute(String.format("team%dQuarters", k+1), tournamentRepository.getPhaseMatches(t.get(), "X").get(k/2).getTeam2().getName());
						model.addAttribute(String.format("team%dQuartersPoints", k+1), tournamentRepository.getPhaseMatches(t.get(), "X").get(k/2).getAwayPoints());
					}
				}
			//--FINAL PHASE
			
			
		}

		return "tournamentSheet";
	}

	@GetMapping("/TenniShip/RegisterMatch/Tournament/{tournament}")
	public String selectMatch(Model model, @PathVariable String tournament) {

		String team = userComponent.getTeam();

		Optional<Tournament> t = tournamentRepository.findById(tournament);//check if that team play this tournament
		Optional<Team> tm = teamRepository.findById(team);

		if (t.isPresent() && tm.isPresent()) {
			for (int i = 0; i < Math.min(matchRepository.findMatches(tm.get(), t.get()).size(), 3); i++) {
				model.addAttribute(String.format("teamNameHome%d", i), matchRepository.findMatches(tm.get(), t.get()).get(i).getTeam1().getName());
				model.addAttribute(String.format("teamHomePoints%d", i), Integer.toString(matchRepository.findMatches(tm.get(), t.get()).get(i).getHomePoints()));
				model.addAttribute(String.format("teamAwayPoints%d", i), Integer.toString(matchRepository.findMatches(tm.get(), t.get()).get(i).getAwayPoints()));
				model.addAttribute(String.format("teamNameAway%d", i), matchRepository.findMatches(tm.get(), t.get()).get(i).getTeam2().getName());
			}
		}

		return "registerMatch";
	}

	@GetMapping("/TenniShip/RegisterMatch/Tournament")
	public String selectTournament(Model model) {

		String team = userComponent.getTeam();

		Optional<Team> t = teamRepository.findById(team);

        model.addAttribute("registered", userComponent.getLoggedUser());
        model.addAttribute("team", team);

		if (t.isPresent()) {
			for (int i = 0; i < Math.min(teamRepository.getTournaments(t.get()).size(), 6); i++) {
				model.addAttribute(String.format("tournamentName%d", i), teamRepository.getTournaments(t.get()).get(i).getName());
			}

			model.addAttribute("listTournaments", teamRepository.getTournaments(t.get()));
		}
		return "selectTournament";
	}
	
	/*protected void raffleGP(Model model) {
		List<String> teams = teamRepository.findAllTeams();
		Collections.shuffle(teams);
		for (int i = 0; i < 18; i++) {
			model.addAttribute(String.format("team%d", i), teams.get(i));
		}
	}*/




	@GetMapping("/{team}/Creator")
	public String create (Model model, @PathVariable String team) {

		Optional <Team> t = teamRepository.findById(team);

		if(t.isPresent()) {
			String tournamentName;
			tournamentName = "CopaDavis"; //cambiar futuro
			List<Team> teamsProvisional = new ArrayList<>();
			teamsProvisional.addAll(teamRepository.findAll()); //futura query


			model.addAttribute("tournament", tournamentName);

			for(int i=0; i< Math.min(18, teamsProvisional.size())/*teamList.size() Poner esto*/;i++) {
	    		model.addAttribute(String.format("team%d", i),teamsProvisional.get(i).getName());
	    	}
		}

		return "tournamentCreator";
	}

	@GetMapping("/TenniShip")
	public String index (Model model) {

		if(userComponent.isLoggedUser()) {
			String teamUser = userComponent.getTeam();
			model.addAttribute("team", teamUser);
		}
		model.addAttribute("registered",userComponent.isLoggedUser());

		return "index";
	}

	@GetMapping("/TenniShip/SignIn")
	public String sign_in (Model model) {


		return "login";
	}

	@GetMapping("/TenniShip/SignUp")
	public String sign_up (Model model) {


		return "registerAccount";
	}
}
