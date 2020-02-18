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
	private MatchRepository matchRepository;
	
	//@GetMapping("/Tournament/{tournament}")
		static int matchesWon = 0, pointsWon = 0;
		static int totalGroupMatchesPlayed = 0, totalRound8MatchesPlayed = 0, totalRound4MatchesPlayed = 0 ;
		static int j;
		//@GetMapping("/Tournament/{tournament}")
		
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
    			System.out.println(progressPercentage);
    			progressPercentage=(progressPercentage/TOTAL_MATCHES)*100;
                model.addAttribute("tournamentName", t.get().getName());
                model.addAttribute("completion",progressPercentage);
                totalGroupMatchesPlayed = 0; totalRound8MatchesPlayed = 0; totalRound4MatchesPlayed = 0;
			//GROUPS-------
				String [] groups = {"A", "B", "C", "D", "E", "F"};
				ArrayList<AuxiliarClass>[] sortedGroups = new ArrayList[6];
				for	(int i = 0; i < 6; i++) { sortedGroups[i] = new ArrayList<>();}
				
				for (int i = 0; i < 6; i++) {
					tournamentRepository.getPhaseTeams(t.get(), groups[i]).forEach(MyTeam -> {
						matchesWon = 0; pointsWon = 0;
						for (Match Match : tournamentRepository.getPhaseMatches(t.get(), groups[j])) {
							if (!(Match.getHomePoints() == 0 && Match.getAwayPoints() == 0)) {
								totalGroupMatchesPlayed++;
							} if (MyTeam.equals(Match.getTeam1()) && Match.getHomePoints() > Match.getAwayPoints()) {
								matchesWon++; pointsWon += Match.getHomePoints();							
							} else if (MyTeam.equals(Match.getTeam2()) && Match.getHomePoints() < Match.getAwayPoints()) {
								matchesWon++; pointsWon += Match.getAwayPoints();
							} else if (MyTeam.equals(Match.getTeam1()) || MyTeam.equals(Match.getTeam2())){
								pointsWon += Math.min(Match.getHomePoints(), Match.getAwayPoints());
							}
						}
						sortedGroups[j].add(new AuxiliarClass(MyTeam, matchesWon, pointsWon));
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
				//-------GROUPS
				
				//FINAL PHASE--
				
				List<Team> last8 = new ArrayList<>();
				List<Team> last4 = new ArrayList<>();
				List<Team> last2 = new ArrayList<>();
				System.out.println(totalGroupMatchesPlayed);
					if ((totalGroupMatchesPlayed/3) == 18) { //Groups Played						
						if (tournamentRepository.getPhaseTeams(t.get(), "X").isEmpty()) { //RoundOf8 has to be created
							List<AuxiliarClass> secondPlaceTeams = new ArrayList<>();
							secondPlaceTeams.add(sortedGroups[0].get(1)); secondPlaceTeams.add(sortedGroups[1].get(1)); secondPlaceTeams.add(sortedGroups[2].get(1));
							secondPlaceTeams.add(sortedGroups[3].get(1)); secondPlaceTeams.add(sortedGroups[4].get(1)); secondPlaceTeams.add(sortedGroups[5].get(1));
							Collections.sort(secondPlaceTeams);

							last8.add(sortedGroups[0].get(0).t); last8.add(sortedGroups[1].get(0).t); last8.add(sortedGroups[2].get(0).t);
							last8.add(sortedGroups[3].get(0).t); last8.add(sortedGroups[4].get(0).t); last8.add(sortedGroups[5].get(0).t);
							last8.add(secondPlaceTeams.get(0).t); last8.add(secondPlaceTeams.get(1).t);
							Collections.shuffle(last8);
							
							Match mroundOf81 = new Match(3, 2, "X"); mroundOf81.setTeam1(last8.get(0)); mroundOf81.setTeam2(last8.get(1)); 
								mroundOf81.setTournament(t.get()); matchRepository.save(mroundOf81);
							Match mroundOf82 = new Match(3, 1, "X"); mroundOf82.setTeam1(last8.get(2)); mroundOf82.setTeam2(last8.get(3));
								mroundOf82.setTournament(t.get()); matchRepository.save(mroundOf82);
							Match mroundOf83 = new Match(3, 2, "X"); mroundOf83.setTeam1(last8.get(4)); mroundOf83.setTeam2(last8.get(5));
								mroundOf83.setTournament(t.get()); matchRepository.save(mroundOf83);
							Match mroundOf84 = new Match(3, 2, "X"); mroundOf84.setTeam1(last8.get(6)); mroundOf84.setTeam2(last8.get(7));
								mroundOf84.setTournament(t.get()); matchRepository.save(mroundOf84);
						}

						else { //RoundOf8 played partially or completed
							
							tournamentRepository.getPhaseMatches(t.get(), "X").forEach(Match -> {
								if (!(Match.getHomePoints() == 0 && Match.getAwayPoints() == 0)) 
									totalRound8MatchesPlayed++;							
							});					
						}
						for (int k = 0; k < 8; k+=2) { //RoundOf8
							model.addAttribute(String.format("team%dQuarters", k), tournamentRepository.getPhaseMatches(t.get(), "X").get(k/2).getTeam1().getName());
							model.addAttribute(String.format("team%dQuartersPoints", k), tournamentRepository.getPhaseMatches(t.get(), "X").get(k/2).getHomePoints());
							model.addAttribute(String.format("team%dQuarters", k+1), tournamentRepository.getPhaseMatches(t.get(), "X").get(k/2).getTeam2().getName());
							model.addAttribute(String.format("team%dQuartersPoints", k+1), tournamentRepository.getPhaseMatches(t.get(), "X").get(k/2).getAwayPoints());
						}	
					}
					
					
					
					if (totalRound8MatchesPlayed == 4) { //RoundOf8 Played
						if (tournamentRepository.getPhaseTeams(t.get(), "Y").isEmpty()) { //RoundOf4 has to be created
							tournamentRepository.getPhaseMatches(t.get(), "X").forEach(Match -> {
								if (Match.getHomePoints() > Match.getAwayPoints())
									last4.add(Match.getTeam1());							
								else 
									last4.add(Match.getTeam2());
							});

							Match mroundOf41 = new Match(2, 3, "Y"); mroundOf41.setTeam1(last4.get(0)); mroundOf41.setTeam2(last4.get(1)); 
								mroundOf41.setTournament(t.get()); matchRepository.save(mroundOf41);
							Match mroundOf42 = new Match(1, 3, "Y"); mroundOf42.setTeam1(last4.get(2)); mroundOf42.setTeam2(last4.get(3));
								mroundOf42.setTournament(t.get()); matchRepository.save(mroundOf42);
						}
						else { //RoundOf4 played partially or completed
							tournamentRepository.getPhaseMatches(t.get(), "Y").forEach(Match -> {
								if (!(Match.getHomePoints() == 0 && Match.getAwayPoints() == 0)) {
									totalRound4MatchesPlayed++;
								}
							});					
						}
						for (int k = 0; k < 4; k+=2) { //RoundOf4
							model.addAttribute(String.format("team%dSemi", k), tournamentRepository.getPhaseMatches(t.get(), "Y").get(k/2).getTeam1().getName());
							model.addAttribute(String.format("team%dSemiPoints", k), tournamentRepository.getPhaseMatches(t.get(), "Y").get(k/2).getHomePoints());
							model.addAttribute(String.format("team%dSemi", k+1), tournamentRepository.getPhaseMatches(t.get(), "Y").get(k/2).getTeam2().getName());
							model.addAttribute(String.format("team%dSemiPoints", k+1), tournamentRepository.getPhaseMatches(t.get(), "Y").get(k/2).getAwayPoints());
						}					
					}
					
					
					
					if (totalRound4MatchesPlayed == 2) { //RoundOf4 Played
						if (tournamentRepository.getPhaseTeams(t.get(), "Z").isEmpty()) { //RoundOf4 has to be created
							tournamentRepository.getPhaseMatches(t.get(), "Y").forEach(Match -> {
								if (Match.getHomePoints() > Match.getAwayPoints())
									last2.add(Match.getTeam1());							
								else 
									last2.add(Match.getTeam2());
							});
							
							Match mroundOf2 = new Match(0, 0, "Z"); mroundOf2.setTeam1(last2.get(0)); mroundOf2.setTeam2(last2.get(1)); 
								mroundOf2.setTournament(t.get()); matchRepository.save(mroundOf2);
						}
						for (int k = 0; k < 2; k+=2) { //RoundOf2
							model.addAttribute(String.format("team%dFinal", k), tournamentRepository.getPhaseMatches(t.get(), "Z").get(k/2).getTeam1().getName());
							model.addAttribute(String.format("team%dFinalPoints", k), tournamentRepository.getPhaseMatches(t.get(), "Z").get(k/2).getHomePoints());
							model.addAttribute(String.format("team%dFinal", k+1), tournamentRepository.getPhaseMatches(t.get(), "Z").get(k/2).getTeam2().getName());
							model.addAttribute(String.format("team%dFinalPoints", k+1), tournamentRepository.getPhaseMatches(t.get(), "Z").get(k/2).getAwayPoints());
						}					
					}
				//--FINAL PHASE
				
				
			} //if (t.isPresent())

			return "tournamentSheet";
		}

}
