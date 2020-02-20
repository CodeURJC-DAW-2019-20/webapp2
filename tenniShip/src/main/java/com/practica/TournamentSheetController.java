package com.practica;

import java.util.Optional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.practica.model.Team;
import com.practica.model.Tournament;
import com.practica.model.Match;

@Controller
public class TournamentSheetController {

	@Autowired
	private TournamentRepository tournamentRepository;
	
	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private MatchRepository matchRepository;
	
		static int matchesWon = 0, pointsWon = 0, j;
		
		private static class AuxiliarClass implements Comparable<AuxiliarClass> {
			private Team t;
			private Integer matchesWon; //Spain 3 - 2 France
			private Integer pointsWon; //Spain matchesWon += 1, Spain pointsWon += 3
			
			public AuxiliarClass(Team team, int matchesWon, int pointsWon) {
				this.t = team; this.matchesWon = matchesWon; this.pointsWon = pointsWon;
			}

			@Override
			public int compareTo(AuxiliarClass p2) {
				if (this.matchesWon > p2.matchesWon) return -1; else if (this.pointsWon > p2.pointsWon) return -1; else return 0;
			}
		}

		@GetMapping("/TenniShip/Tournament/{tournament}")
        public String tournament(Model model, @PathVariable String tournament) {

            Optional<Tournament> t = tournamentRepository.findById(tournament);
            double progressPercentage;
    		final int TOTAL_MATCHES=25;

            if (t.isPresent()) {
    			progressPercentage=tournamentRepository.getPlayedMatches(t.get().getName());
    			progressPercentage=(progressPercentage/TOTAL_MATCHES)*100;
                model.addAttribute("tournamentName", t.get().getName());
                model.addAttribute("completion",progressPercentage);
                
                //GROUPS-------
				String [] groups = {"A", "B", "C", "D", "E", "F"};
				@SuppressWarnings("unchecked")
				ArrayList<AuxiliarClass>[] sortedGroups = new ArrayList[6];
				for	(int i = 0; i < 6; i++) { sortedGroups[i] = new ArrayList<>();}
				
				for (int i = 0; i < 6; i++) {
					tournamentRepository.getPhaseTeams(t.get(), groups[i]).forEach(tm -> {
						sortedGroups[j].add(new AuxiliarClass(tm, teamRepository.getWonGroupMatches(t.get(), tm, groups[j]),
								teamRepository.getWonGroupPointsPlayingHome(t.get(), tm, groups[j]) 
								+ teamRepository.getWonGroupPointsPlayingAway(t.get(), tm, groups[j]) ));
					}); Collections.sort(sortedGroups[j]); 
					
					for (int k = 0;  k < 3; k++) {
						model.addAttribute(String.format("team%dGroup%s", k, groups[j]), sortedGroups[j].get(k).t.getName());
						model.addAttribute(String.format("team%dGroup%sMatchesWon", k, groups[j]), sortedGroups[j].get(k).matchesWon);
						model.addAttribute(String.format("team%dGroup%sPointsWon", k, groups[j]), sortedGroups[j].get(k).pointsWon);
					}
					j++;
				}
				j = 0;
				//-------GROUPS
				
				//FINAL PHASE--
				
					List<Team> last8 = new ArrayList<>(); List<Team> last4 = new ArrayList<>(); List<Team> last2 = new ArrayList<>();
				
					if (tournamentRepository.getPlayedMatchesJQL(t.get()) >= 18) { //Groups Played						
						if (tournamentRepository.getPhaseTeams(t.get(), "X").isEmpty()) { //RoundOf8 has to be created
							List<AuxiliarClass> secondPlaceTeams = new ArrayList<>();
							secondPlaceTeams.add(sortedGroups[0].get(1)); secondPlaceTeams.add(sortedGroups[1].get(1)); secondPlaceTeams.add(sortedGroups[2].get(1));
							secondPlaceTeams.add(sortedGroups[3].get(1)); secondPlaceTeams.add(sortedGroups[4].get(1)); secondPlaceTeams.add(sortedGroups[5].get(1));
							Collections.sort(secondPlaceTeams);

							last8.add(sortedGroups[0].get(0).t); last8.add(sortedGroups[1].get(0).t); last8.add(sortedGroups[2].get(0).t);
							last8.add(sortedGroups[3].get(0).t); last8.add(sortedGroups[4].get(0).t); last8.add(sortedGroups[5].get(0).t);
							last8.add(secondPlaceTeams.get(0).t); last8.add(secondPlaceTeams.get(1).t); Collections.shuffle(last8);
							
							Match a = new Match(0, 3, "X"); a.setTeam1(last8.get(0)); a.setTeam2(last8.get(1)); a.setTournament(t.get()); matchRepository.save(a);
							Match b = new Match(0, 3, "X"); b.setTeam1(last8.get(2)); b.setTeam2(last8.get(3)); b.setTournament(t.get()); matchRepository.save(b);
							Match c = new Match(0, 3, "X"); c.setTeam1(last8.get(4)); c.setTeam2(last8.get(5)); c.setTournament(t.get()); matchRepository.save(c);
							Match d = new Match(0, 3, "X"); d.setTeam1(last8.get(6)); d.setTeam2(last8.get(7)); d.setTournament(t.get()); matchRepository.save(d);
						}
						for (int k = 0; k < 8; k+=2) { //RoundOf8
							if (tournamentRepository.getPhaseMatches(t.get(), "X").get(k/2).getHomePoints() > tournamentRepository.getPhaseMatches(t.get(), "X").get(k/2).getAwayPoints()) {
								model.addAttribute(String.format("team%dQuarters", k), tournamentRepository.getPhaseMatches(t.get(), "X").get(k/2).getTeam1().getName());
								model.addAttribute(String.format("team%dQuartersPoints", k), tournamentRepository.getPhaseMatches(t.get(), "X").get(k/2).getHomePoints());
								model.addAttribute(String.format("team%dQuarters", k+1), tournamentRepository.getPhaseMatches(t.get(), "X").get(k/2).getTeam2().getName());
								model.addAttribute(String.format("team%dQuartersPoints", k+1), tournamentRepository.getPhaseMatches(t.get(), "X").get(k/2).getAwayPoints());
							} else {
								model.addAttribute(String.format("team%dQuarters", k+1), tournamentRepository.getPhaseMatches(t.get(), "X").get(k/2).getTeam1().getName());
								model.addAttribute(String.format("team%dQuartersPoints", k+1), tournamentRepository.getPhaseMatches(t.get(), "X").get(k/2).getHomePoints());
								model.addAttribute(String.format("team%dQuarters", k), tournamentRepository.getPhaseMatches(t.get(), "X").get(k/2).getTeam2().getName());
								model.addAttribute(String.format("team%dQuartersPoints", k), tournamentRepository.getPhaseMatches(t.get(), "X").get(k/2).getAwayPoints());
							}
						}	
					}
					else {
						for (int k = 0; k < 8; k+=2) { //RoundOf8
							model.addAttribute(String.format("team%dQuarters", k), "-Keep Playing");
							model.addAttribute(String.format("team%dQuartersPoints", k), "");
							model.addAttribute(String.format("team%dQuarters", k+1), "-Keep Playing");
							model.addAttribute(String.format("team%dQuartersPoints", k+1), "");
						}
					}
						
					
					if (tournamentRepository.getPlayedMatchesJQL(t.get()) >= 22) { //RoundOf8 Played
						if (tournamentRepository.getPhaseTeams(t.get(), "Y").isEmpty()) { //RoundOf4 has to be created
							tournamentRepository.getPhaseMatches(t.get(), "X").forEach(Match -> {
								if (Match.getHomePoints() > Match.getAwayPoints())
									last4.add(Match.getTeam1());							
								else 
									last4.add(Match.getTeam2());
							});

							Match a = new Match(0, 3, "Y"); a.setTeam1(last4.get(0)); a.setTeam2(last4.get(1)); a.setTournament(t.get()); matchRepository.save(a);
							Match b = new Match(3, 0, "Y"); b.setTeam1(last4.get(2)); b.setTeam2(last4.get(3)); b.setTournament(t.get()); matchRepository.save(b);
						}
						for (int k = 0; k < 4; k+=2) { //RoundOf4 
							if (tournamentRepository.getPhaseMatches(t.get(), "Y").get(k/2).getHomePoints() > tournamentRepository.getPhaseMatches(t.get(), "Y").get(k/2).getAwayPoints()) {
								model.addAttribute(String.format("team%dSemi", k), tournamentRepository.getPhaseMatches(t.get(), "Y").get(k/2).getTeam1().getName());
								model.addAttribute(String.format("team%dSemiPoints", k), tournamentRepository.getPhaseMatches(t.get(), "Y").get(k/2).getHomePoints());
								model.addAttribute(String.format("team%dSemi", k+1), tournamentRepository.getPhaseMatches(t.get(), "Y").get(k/2).getTeam2().getName());
								model.addAttribute(String.format("team%dSemiPoints", k+1), tournamentRepository.getPhaseMatches(t.get(), "Y").get(k/2).getAwayPoints());
							} else {
								model.addAttribute(String.format("team%dSemi", k+1), tournamentRepository.getPhaseMatches(t.get(), "Y").get(k/2).getTeam1().getName());
								model.addAttribute(String.format("team%dSemiPoints", k+1), tournamentRepository.getPhaseMatches(t.get(), "Y").get(k/2).getHomePoints());
								model.addAttribute(String.format("team%dSemi", k), tournamentRepository.getPhaseMatches(t.get(), "Y").get(k/2).getTeam2().getName());
								model.addAttribute(String.format("team%dSemiPoints", k), tournamentRepository.getPhaseMatches(t.get(), "Y").get(k/2).getAwayPoints());
							}
						}					
					}
					else {
						for (int k = 0; k < 4; k+=2) { //RoundOf4
							model.addAttribute(String.format("team%dSemi", k), "-Keep Playing");
							model.addAttribute(String.format("team%dSemiPoints", k), "");
							model.addAttribute(String.format("team%dSemi", k+1), "-Keep Playing");
							model.addAttribute(String.format("team%dSemiPoints", k+1), "");
						}
					}
					
					
					if (tournamentRepository.getPlayedMatchesJQL(t.get()) >= 24) { //RoundOf4 Played
						if (tournamentRepository.getPhaseTeams(t.get(), "Z").isEmpty()) { //RoundOf2 has to be created
							tournamentRepository.getPhaseMatches(t.get(), "Y").forEach(Match -> {
								if (Match.getHomePoints() > Match.getAwayPoints())
									last2.add(Match.getTeam1());							
								else 
									last2.add(Match.getTeam2());
							});
							
							Match a = new Match(3, 2, "Z"); a.setTeam1(last2.get(0)); a.setTeam2(last2.get(1)); a.setTournament(t.get()); matchRepository.save(a);
						}
						for (int k = 0; k < 2; k+=2) { //RoundOf2 
							if (tournamentRepository.getPhaseMatches(t.get(), "Z").get(k/2).getHomePoints() > tournamentRepository.getPhaseMatches(t.get(), "Z").get(k/2).getAwayPoints()) {
								model.addAttribute(String.format("team%dFinal", k), tournamentRepository.getPhaseMatches(t.get(), "Z").get(k/2).getTeam1().getName());
								model.addAttribute(String.format("team%dFinalPoints", k), tournamentRepository.getPhaseMatches(t.get(), "Z").get(k/2).getHomePoints());
								model.addAttribute(String.format("team%dFinal", k+1), tournamentRepository.getPhaseMatches(t.get(), "Z").get(k/2).getTeam2().getName());
								model.addAttribute(String.format("team%dFinalPoints", k+1), tournamentRepository.getPhaseMatches(t.get(), "Z").get(k/2).getAwayPoints());
							} else {
								model.addAttribute(String.format("team%dFinal", k+1), tournamentRepository.getPhaseMatches(t.get(), "Z").get(k/2).getTeam1().getName());
								model.addAttribute(String.format("team%dFinalPoints", k+1), tournamentRepository.getPhaseMatches(t.get(), "Z").get(k/2).getHomePoints());
								model.addAttribute(String.format("team%dFinal", k), tournamentRepository.getPhaseMatches(t.get(), "Z").get(k/2).getTeam2().getName());
								model.addAttribute(String.format("team%dFinalPoints", k), tournamentRepository.getPhaseMatches(t.get(), "Z").get(k/2).getAwayPoints());
							}
						}					
					}
					else {
						for (int k = 0; k < 2; k+=2) { //RoundOf2
							model.addAttribute(String.format("team%dFinal", k), "-Keep Playing");
							model.addAttribute(String.format("team%dFinalPoints", k), "");
							model.addAttribute(String.format("team%dFinal", k+1), "-Keep Playing");
							model.addAttribute(String.format("team%dFinalPoints", k+1), "");
						}
					}
				//--FINAL PHASE	
					
			} //if (t.isPresent())
			
			return "tournamentSheet";
		}
}
