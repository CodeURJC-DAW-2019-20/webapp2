# TenniShip

TenniShip is a web app for managing tennis tournaments inspired in Davis Cup rules. It allows users to find teams, matches, keep track of scores, and check their standings in tourneys. 

![Home Page](BizPage/img/screenshots/index.png "Home Page")
Home page has two search bars: one for tournaments and another one for teams. This search bars are only for queries, so they redirect you to the team or tournament information sheet. Another slide below, Home page explains TenniShip web functions.



![Sign Up](BizPage/img/screenshots/signup.png "Sign Up")
Users can sign up in TenniShip as teams. Such as in Davis Cup, every team must have five players.



![Tournament Sheet](BizPage/img/screenshots/tsheetGP.png "Tournament Sheet")
![Tournament Sheet](BizPage/img/screenshots/tsheetFP.png "Tournament Sheet")
Tournament information sheets indicates the actual phase of the tournament, its teams and its progress.



![Register Match](BizPage/img/screenshots/registerMatch.png "Register Match")
When a game is played, teams must conclude its result. In case of disagreement, tournament's administrator will be noticed so they decide.



![Team Sheet](BizPage/img/screenshots/teamSheet.png "Team Sheet")
Team Sheet contains information about a team. It includes a team's logo, its players with their picture and name (hoover), the tournaments played by them and their most recent results. This screenshot was taken with zoom out.



## Built With

TenniShip has been made with HTML, CSS and Javascript. BizPage Bootstrap template has been used and modified.

## Technical details
### Entities
TenniShip has four different entities: match, tournament, team and user rol. 
![Entities](BizPage/img/diagrams/entities_dependencies.png)

### Users
TenniShip has three different users: administrator, registered user and non-registered user.
+ _Non-registered users_ can only see tournaments and teams information.
+ _Registered users_ can only see tournaments and teams information on those tournaments that they are not its administrator.
+ _Administrators_ can modify tournaments information on those who they are its administrator. For the rest of tournaments they are simple _registered users_.

### Pictures
+ Tournament has an image as its logo.
+ Teams has 5 images: the team logo, and a profile picture for the five team members.

### Charts
In tournament's details appears a vertical bar chart. Horizontal axis contains the participating teams, and vertical axis contains the points gotten.

### Additional technology
An e-mail will be sent in three different situations: when you register your TenniShip account, then a team is disqualified from a tournament your are playing in and when a tournament that you are into is deleted.

### Advanced algorithm or query
Tournaments will automatically reorganize themselves when a match is played.


## Authors

* Iván Fernández Llorente - [Ivan's GitHub](https://github.com/IvanFernandezLlorente/ "IvanFernandezLlorente") - [Ivan's e-mail](mailto:i.fernandezl.2017@alumnos.urjc.es "Send e-mail")
* Santiago González Martin - [Santi's GitHub](https://github.com/SantiagoGnzlz/ "SantiagoGnzlz") - [Santi's e-mail](mailto:s.gonzalezm.2016@alumnos.urjc.es "Send e-mail")
* Diego Pascual Ferrer - [Diego's GitHub](https://github.com/Diegopasfer1909/ "Diegopasfer1909") - [Diego's e-mail](mailto:d.pascual.2017@alumnos.urjc.es "Send e-mail")
* Alvaro Justo Rivas Alcobendas - [Álvaro's GitHub](https://github.com/Varo412/ "Varo412") - [Álvaro's e-mail](mailto:aj.rivas.2017@alumnos.urjc.es "Send e-mail")
* Marcos Villacañas Flores - [Marcos' GitHub](https://github.com/MarcosVillacanas/ "MarcosVillacanas") - [Marcos' e-mail](mailto:m.villacanas.2017@alumnos.urjc.es "Send e-mail")

## License
© BizPage Bootstrap template has been designed by BootstrapMade. [Original Bizpage Template](https://bootstrapmade.com/demo/BizPage/ "Bizpage").

