# TenniShip

TenniShip is a web app for managing tennis tournaments inspired in Davis Cup rules. It allows users to find teams, matches, keep track of scores, and check their standings in tourneys. 

![Home Page](tenniShip\src\main\resources\static\img\screenshots\index.png "Home Page")
The home page has two search bars: one for tournaments and another one for teams. This search bars are only for queries, so they redirect you to the team or tournament information sheet. There's another slide below, that explains what TenniShip can do and links the user to various actions.




![Sign Up](tenniShip/src/main/resources/static/img/screenshots/signup.png "Sign Up")
Users can sign up in TenniShip as teams. Such as in Davis Cup, every team must have five players.



![Tournament Sheet](tenniShip/src/main/resources/static/img/screenshots/tsheetGP.png "Tournament Sheet")
![Tournament Sheet](tenniShip/src/main/resources/static/img/screenshots/tsheetFP.png "Tournament Sheet")
Tournament information sheets indicate the actual phase of the tournament, its teams and its progress.



![Register Match](tenniShip/src/main/resources/static/img/screenshots/registerMatch.png "Register Match")
When a game is played, teams must log the result. In case of disagreement, tournament's administrator will be noticed so they decide.



![Team Sheet](tenniShip/src/main/resources/static/img/screenshots/teamFile.png "Team Sheet")
Team Sheet contains information about a team. It includes the team's logo, its players with their picture and name (on mouse hover), the tournaments played by them and their most recent results. There's a pie chart that showcases the percentage of matches played in each tournament. This screenshot was taken with zoom out.



## Built With

TenniShip has been made with HTML, CSS and JavaScript. BizPage Bootstrap template has been used and modified.

## Technical details
### Entities
TenniShip has four different entities: match, tournament, team and user role. 
![Entities](tenniShip/src/main/resources/static/img/diagrams/MySQL_DB.png)

### Users
TenniShip has three different users: administrator, registered user and non-registered user.
+ _Non-registered users_ can only see tournaments and teams information.
+ _Registered users_ can only see tournaments and teams information on those tournaments that they are not its administrator.
+ _Administrators_ can modify tournaments information on those who they are its administrator. For the rest of tournaments they are simple _registered users_.

### Development
#### Source Code
[Source Code Repository](https://trello.com/b/uJDmvvK9/daw-tareas/ "GitHub").

#### Development tools
+ Spring Tool Suite 4.
+ MySQL Server 8.0.
+ MySQL WorkBench 8.0.
#### Dependencies
+ MSQL Server 8.0.

#### Useful commands
Generate MySQL schema:
``` shell
insert commands here
```
Build application:
``` shell
insert commands here
```
Run application:
``` shell
insert commands here
```
### Pictures
+ Tournament has an image as its logo.
+ Each team has 6 images: the team logo, and a profile picture for the five team members.

### Charts
In team's details appears a pie chart that automatically calculates and shows the percentage of matches played in each tournament.

### Additional technology
An e-mail will be sent in three different situations: when you register your TenniShip account, then a team is disqualified from a tournament your are playing in and when a tournament that you are into is deleted.

### Advanced algorithm or query
Tournaments will automatically reorganize themselves when a match is played.

## Screen transitions.

+ __Straight__ arrows show how _non-registered users_ can navigate throw the website.

+ Arrows that start with a __circle__ show how _registered users_ can navigate throw the website.
+ Arrows that start with a __rhombus__ show how _administrator users_ can navigate throw the website.



![Login/up](tenniShip/src/main/resources/static/img/screen_transitions/fromHome.jpeg)

![Login/up](tenniShip/src/main/resources/static/img/screen_transitions/fromSB.jpeg)

![Login/up](tenniShip/src/main/resources/static/img/screen_transitions/fromActions.jpeg)

![Login/up](tenniShip/src/main/resources/static/img/screen_transitions/fromActions2.jpeg)

![Login/up](tenniShip/src/main/resources/static/img/screen_transitions/fromActions3.jpeg)

## Process Tools
We used [Trello](https://trello.com/b/uJDmvvK9/daw-tareas/ "TenniShip Trello") for tasks management.

## Authors

* Iván Fernández Llorente - [Ivan's GitHub](https://github.com/IvanFernandezLlorente/ "IvanFernandezLlorente") - [Ivan's e-mail](mailto:i.fernandezl.2017@alumnos.urjc.es "Send e-mail")
* Santiago González Martin - [Santi's GitHub](https://github.com/SantiagoGnzlz/ "SantiagoGnzlz") - [Santi's e-mail](mailto:s.gonzalezm.2016@alumnos.urjc.es "Send e-mail")
* Diego Pascual Ferrer - [Diego's GitHub](https://github.com/Diegopasfer1909/ "Diegopasfer1909") - [Diego's e-mail](mailto:d.pascual.2017@alumnos.urjc.es "Send e-mail")
* Alvaro Justo Rivas Alcobendas - [Álvaro's GitHub](https://github.com/Varo412/ "Varo412") - [Álvaro's e-mail](mailto:aj.rivas.2017@alumnos.urjc.es "Send e-mail")
* Marcos Villacañas Flores - [Marcos' GitHub](https://github.com/MarcosVillacanas/ "MarcosVillacanas") - [Marcos' e-mail](mailto:m.villacanas.2017@alumnos.urjc.es "Send e-mail")

#### Authors' participation
* Iván: 
	- Tasks:
		- d
	- Top 5 most important commits in 'Second Phase':
		- d
	- Top 5 most modified files:
		- d
* Santi: 
	- Tasks:
		- d
	- Top 5 most important commits in 'Second Phase':
		- d
	- Top 5 most modified files:
		- d
* Diego: 
	- Tasks:
		- d
	- Top 5 most important commits in 'Second Phase':
		- d
	- Top 5 most modified files:
		- d
* Álvaro: 
	- Tasks:
		- Images upload.
		- Images visualization.
		- E-Mail sender: e-mail sent when a new user signs up and when a team is disqualified.
		- Documentation: Readme and diagrams.
		- Insertion of sample data for demo.
	- Top 5 most important commits in 'Second Phase':
		- [Email implemented!](https://github.com/CodeURJC-DAW-2019-20/webapp2/commit/ed623ab115cc423e3d9282339be99007238e21cc "de0ce05") 
		- [Images implemented!](https://github.com/CodeURJC-DAW-2019-20/webapp2/commit/ed623ab115cc423e3d9282339be99007238e21cc "ed623ab") 
		- [Images implemented on header and Register Match!](https://github.com/CodeURJC-DAW-2019-20/webapp2/commit/f37bb40a44d591178751cc6f5f487d5578646f2c "f37bb40") 
		- [Project Fixed](https://github.com/CodeURJC-DAW-2019-20/webapp2/commit/25c47d3700c9e54fc956d58e9690c6a86463518e "25c47d3") 
		- [Tournament Sheet images, default avatar and minor fixes](https://github.com/CodeURJC-DAW-2019-20/webapp2/commit/26e9e5e5468259ccd846bb6973fa5b3a65deb495 "26e9e5e") 
	- Top 5 most modified files in 'Second Phase':
		- [Mail Sender](https://github.com/CodeURJC-DAW-2019-20/webapp2/blob/master/tenniShip/src/main/java/com/practica/MailSenderXX.java "MailSenderXX.java") 
		- [Images Service](https://github.com/CodeURJC-DAW-2019-20/webapp2/blob/master/tenniShip/src/main/java/com/practica/ImageService.java "Image Service") 
		- [Data Base Usage](https://github.com/CodeURJC-DAW-2019-20/webapp2/blob/master/tenniShip/src/main/java/com/practica/DataBaseUsage.java "DataBaseUsage.java") 
		- [Tournament Rest Controller](https://github.com/CodeURJC-DAW-2019-20/webapp2/blob/master/tenniShip/src/main/java/com/practica/TournamentRestController.java "TournamentRestController.java") 
		- [Team Rest Controller](https://github.com/CodeURJC-DAW-2019-20/webapp2/blob/master/tenniShip/src/main/java/com/practica/TeamRestController.java "TeamRestController.java") 
* Marcos: 
	- Tasks:
		- d
	- Top 5 most important commits in 'Second Phase':
		- d
	- Top 5 most modified files:
		- d

## License
© BizPage Bootstrap template has been designed by BootstrapMade. [Original Bizpage Template](https://bootstrapmade.com/demo/BizPage/ "Bizpage").

