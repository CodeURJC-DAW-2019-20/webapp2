# TenniShip - REST API Documentation

All API queries are based off the accessible URLs through the website, except that they begin with /api. They allow for the same functionality available through the website, and return information through JSON either to display the required elements or to check if the operation was done successfully.

This page is separated on groups of actions, related on aspects of the website.



# User management

## Sign in

Log into an already existing account.

* **URL:** ``/api/TenniShip/SignIn``

* **Method:** ``GET``

* **Success response:** ``200 OK``

* **Error responses:** ``401 UNAUTHORIZED``

* **Response entity:** User 

**Response example:**

````json
{
    "userName": "userSpain",
    "email": "tennishipdaw1@gmail.com",
    "team": "Spain",
    "roles": [
        "ROLE_USER"
    ]
}
````



## Log out

Log out of the current account you've signed into.

* **URL:** ``/api/TenniShip/logout``

* **Method:** ``GET``

* **Success response:** ``200 OK``

* **Error responses:** ``401 UNAUTHORIZED`` if the user didn't log in previously.

* **Response entity:** A single boolean, ``true``, if everything went well.

**Response example:**

````json
true
````



## Sign up

Create a brand new account.

* **URL:** ``/api/TenniShip/SignUp``

* **Method:** ``POST``

* **Success response:** ``201 CREATED``

* **Error responses:** ``409 CONFLICT`` if the user is logged in, there's already another user with the same user name, email, or team name, if there's no user name or password, or the team doesn't have 5 players. 

* **Response entity:** User 

**Request body example:**

````json
{
    "userName": "userNew",
    "passwordHash": "newpassword0928",
    "email": "example@gmail.com",
    "teamName": "TeamChamps",
    "roles": [
        "ROLE_USER"
    ],
    "players": [ "Anderson Stevens", "Brew Moore", "Pete Shen", "Pam Locke", "Mia Fey" ]
}
````

**Response example:**

````json
{
    "userName": "userNew",
    "email": "example@gmail.com",
    "team": "TeamChamps",
    "roles": [
        "ROLE_USER"
    ]
}
````



# Team requests

## Get team data

Obtain information related to a specific team, including a list of matches played by them.

* **URL:** ``/api/TenniShip/Team/{team}/?NumberOfMatchesListed={end}``

* **URL parameters:** 
    * ``{team}`` with the team name. 
    * ``{end}`` with the maximum amount of matches the user wants to receive.

* **Method:** ``GET``

* **Success response:** ``200 OK``

* **Error responses:** ``404 NOT FOUND`` if there isn't a team with that name.

* **Request example:** ``https://localhost:8443/api/TenniShip/Team/Spain?NumberOfMatchesListed=2``

**Response example:**

````json
{
    "teamName": "Spain",
    "imageListTournaments": [
        "Davis Cup",
        "Champions League",
        "Eurocup"
    ],
    "teamImage": false,
    "percentageLostMatches": 50.0,
    "percentageWonMatches": 50.0,
    "playerList": [
        {
            "playerName": "Varo"
        },
        {
            "playerName": "Ivan"
        },
        {
            "playerName": "Santi"
        },
        {
            "playerName": "Marcos"
        },
        {
            "playerName": "Diego"
        }
    ],
    "matchesList": [
        {
            "homePoints": 3,
            "awayPoints": 0,
            "team1": {
                "teamName": "Spain"
            },
            "team2": {
                "teamName": "Serbia"
            },
            "tournament": {
                "name": "Davis Cup"
            }
        },
        {
            "homePoints": 3,
            "awayPoints": 1,
            "team1": {
                "teamName": "France"
            },
            "team2": {
                "teamName": "Spain"
            },
            "tournament": {
                "name": "Davis Cup"
            }
        }
    ]
}
````



## Get team images

This request gets either the team logo, or one of the players' images.

* **URL:** ``/api/TenniShip/Team/{team}/image/{npic}``

* **URL parameters:** 
    * ``{team}`` with the team name. 
    * ``{npic}`` is a number that represents the team logo with ``0`` or each of the 5 players with the values ``1`` to ``5``.

* **Method:** ``GET``

* **Success response:** ``200 OK``

* **Error responses:** ``404 NOT FOUND`` if the system couldn't find the image, or there isn't one.

* **Request example:** ``https://localhost:8443/api/TenniShip/Team/elTeam25/image/5``

* **Response:** An object with the information of the corresponding image. In the example, it would be the image of the fifth player of the team.



# Match register

When a team plays a match, they're able to log the result. When they're logged on the appropiate user account, they can do a request to get a list of tournaments, pick the one that has the match they played, and then in another request see a list of matches that they can log in that tournament. With a final request, they can log the result of the corresponding match.

## Get tournament list to register match

This request gives a list of tournaments the user's team participates in. This request requires that the user logs into a team account previously.

* **URL:** ``/api/TenniShip/RegisterMatch/Tournament?NumberOfTournamentsDisplayed={n}``

* **URL parameters:** ``{n}`` indicates the maximum number of tournaments the user wants to receive.

* **Method:** ``GET``

* **Success response:** ``200 OK``

* **Error responses:** ``403 FORBIDDEN`` if the user isn't logged in, or isn't a regular user.

* **Request example:** ``https://localhost:8443/api/TenniShip/RegisterMatch/Tournament/?NumberOfTournamentsDisplayed=3``

* **Response entity:** Tournament list

**Response example:**

````json
[
    {
        "name": "Champions League"
    },
    {
        "name": "Davis Cup"
    },
    {
        "name": "Eurocup"
    }
]
````



## See available match list of tournament

After picking a tournament, users can use this request to see a list of matches whose score they can submit, all belonging to this tournament.

* **URL:** ``/api/TenniShip/RegisterMatch/Tournament/{tournament}``

* **URL parameters:** ``{tournament}`` should have the name of the tournament we chose.

* **Method:** ``GET``

* **Success response:** ``200 OK``

* **Error responses:** 
    * ``403 FORBIDDEN`` if the user isn't logged in, or isn't a regular user. 
    * ``404 NOT FOUND`` if the system couldn't return a list of matches.

* **Request example:** ``https://localhost:8443/api/TenniShip/RegisterMatch/Tournament/Champions League``

* **Response entity:** Object with an indicator of the current round in that tournament, and a list of matches.

**Response example:**

````json
{
    "round": "Group Stage",
    "matches": [
        {
            "homePoints": 0,
            "awayPoints": 0,
            "team1": {
                "teamName": "Spain"
            },
            "team2": {
                "teamName": "CSKA Moscow"
            },
            "tournament": {
                "name": "Champions League"
            }
        }
    ]
}
````



## Submit match result

When the user picks a specific match, they just have to to this last request to submit the score.

* **URL:** ``/api/TenniShip/RegisterMatch/Tournament/{tournament}/Submission``

* **URL parameters:** ``{tournament}`` should have the name of the tournament we chose.

* **Method:** ``PUT``

* **Success response:** ``201 CREATED`` if everything worked correctly.

* **Error responses:** 
    * ``404 NOT FOUND`` if the tournament, teams, or the match weren't found in the system. ``
    * ``403 FORBIDDEN``	 if the user either isn't an admin, or doesn't have one of the teams in the match they want to change.
    * ``400 BAD REQUEST`` if none of the teams, or both, have 3 points.

* **Request example:** ``https://localhost:8443/api/TenniShip/RegisterMatch/Tournament/Champions League/Submission``

* **Response entity:** The same Match object that has been submitted, with the updated info (that means, it will look the same as the request if it worked correctly).

**Request body example:**

````json
{
    "homePoints": 0,
    "awayPoints": 3,
    "team1": {
        "teamName": "Spain"
    },
    "team2": {
        "teamName": "CSKA Moscow"
    },
    "tournament": {
        "name": "Champions League"
    }
}
````

**Response example:** (the same as the request in this case)

````json
{
    "homePoints": 0,
    "awayPoints": 3,
    "team1": {
        "teamName": "Spain"
    },
    "team2": {
        "teamName": "CSKA Moscow"
    },
    "tournament": {
        "name": "Champions League"
    }
}
````



## See list of matches in tournament group (admin only)

The system administrator, that is, the user with an admin role, is able to see a list of **all** the matches in a given group of a tournament. This request does that, so that the admin can choose a specific match, and change or submit the score in the next request, in a similar manner to how regular users do.

* **URL:** ``/api/TenniShip/ADMIN/Tournament/{tournament}/EditMatches/{group}``

* **URL parameters:** 
    * ``{tournament}`` should have the name of the tournament we chose.
    * ``{group}`` should be the code of the group we choose (A, B, C, D, E, F, X, Y, or Z).

* **Method:** ``GET``

* **Success response:** ``200 OK``

* **Error responses:** 
    * ``403 FORBIDDEN`` if the user isn't logged in, or isn't an admin.
    * ``404 NOT FOUND`` if the system couldn't find the tournament.

* **Request example:** ``https://localhost:8443/api/ADMIN/Tournament/Champions League/EditMatches/B``

* **Response entity:** Object with an indicator of the current round in that tournament, and a list of matches.

**Response example:**

````json
{
    "admin": true,
    "round": "Group Stage",
    "matches": [
        {
            "homePoints": 0,
            "awayPoints": 3,
            "team1": {
                "teamName": "Galatasaray"
            },
            "team2": {
                "teamName": "Inter Milan"
            },
            "tournament": {
                "name": "Champions League"
            }
        },
        {
            "homePoints": 3,
            "awayPoints": 2,
            "team1": {
                "teamName": "Porto"
            },
            "team2": {
                "teamName": "Galatasaray"
            },
            "tournament": {
                "name": "Champions League"
            }
        },
        {
            "homePoints": 3,
            "awayPoints": 2,
            "team1": {
                "teamName": "Inter Milan"
            },
            "team2": {
                "teamName": "Porto"
            },
            "tournament": {
                "name": "Champions League"
            }
        }
    ]
}
````



## Submit match result (admin version)

The admin has a specific request to do the same as above. In this case, it's also required that the admin provides both tournament name and group. After that, in the body of the request, the appopiate match data will be sent. This request sends back that same data if everything went correctly.

* **URL:** ``/api/TenniShip/ADMIN/Tournament/{tournament}/EditMatches/{group}/Submission``

* **URL parameters:** 
    * ``{tournament}`` should have the name of the tournament we chose.
    * ``{group}`` should be the code of the group we choose (A, B, C, D, E, F, X, Y, or Z).

* **Method:** ``PUT``

* **Success response:** ``201 CREATED`` if everything worked correctly.

* **Error responses:** 
    * ``404 NOT FOUND`` if the tournament, teams, or the match weren't found in the system. 
    * ``403 FORBIDDEN``	 if the user either isn't an admin, or doesn't have one of the teams in the match they want to change.
    * ``400 BAD REQUEST`` if none of the teams, or both, have 3 points.

* **Request example:** ``https://localhost:8443/api/TenniShip/ADMIN/Tournament/Champions League/EditMatches/B/Submission``

* **Response entity:** The same Match object that has been submitted, with the updated info (that means, it will look the same as the request if it worked correctly).

**Request body example:**

````json
{
    "homePoints": 3,
    "awayPoints": 1,
    "team1": {
        "teamName": "Porto"
    },
    "team2": {
        "teamName": "Galatasaray"
    },
    "tournament": {
        "name": "Champions League"
    }
}
````

**Response example:** (the same as the request in this case)

````json
{
    "homePoints": 3,
    "awayPoints": 1,
    "team1": {
        "teamName": "Porto"
    },
    "team2": {
        "teamName": "Galatasaray"
    },
    "tournament": {
        "name": "Champions League"
    }
}
````



# Tournament requests

The following requests let users see, create, and delete tournaments, and get or upload their logos.

## Get tournament info

This request returns a bunch of information of the specified tournament.

* **URL:** ``/api/TenniShip/Tournament/{tournament}``

* **URL parameters:** ``{tournament}`` should have the name of the tournament we want to see.

* **Method:** ``GET``

* **Success response:** ``200 OK``

* **Error responses:** ``404 NOT FOUND`` if the tournament hasn't been found in the system.

* **Request example:** ``https://localhost:8443/api/TenniShip/Tournament/Davis Cup``

* **Response entity:** An object with the tournament, a list of groups with all the teams and their points, and several lists of matches of the final phases, plus the completion percentage.

**Response example:** (some parts have been omitted with ``(...)`` because it was too long)

````
{
    "tournament": {
        "name": "Davis Cup"
    },
    "groups": [
        [
            {
                "team": {
                    "teamName": "Spain"
                },
                "matchesWon": 1,
                "pointsWon": 4,
                "group": "A"
            },
            (more teams...)
        ],
        (more groups...)
    ],
    "quarters": [
        {
            "homePoints": 3,
            "awayPoints": 0,
            "team1": {
                "teamName": "Australia"
            },
            "team2": {
                "teamName": "Japan"
            },
            "tournament": {
                "name": "Davis Cup"
            }
        },
        (3 more matches...)
    ],
    "theSemiFinals": [
        (two matches like the ones in quarters...)
    ],
    "theFinal": [
        {
            "homePoints": 0,
            "awayPoints": 0,
            "team1": {
                "teamName": "Australia"
            },
            "team2": {
                "teamName": "Denmark"
            },
            "tournament": {
                "name": "Davis Cup"
            }
        }
    ],
    "completion": 96.0
}
````



## Get tournament image

This request returns the tournament logo, in case it has one.

* **URL:** ``/Tournament/{tournamentID}/image``

* **URL parameters:** ``{tournamentID}`` should have the name of the tournament we chose.

* **Method:** ``GET``

* **Success response:** ``200 OK``

* **Error responses:** ``404 NOT FOUND`` if the tournament hasn't been found, or it doesn't have an image.

* **Request example:** ``https://localhost:8443/api/TenniShip/Tournament/Champions League/image``

* **Response entity:** Content-type is image/jpg, and the body is the apporpiate data of the image.



## Upload tournament image

This requests lets us upload an image to be assigned to the tournament.

* **URL:** ``/Tournament/{tournamentId}/image``

* **URL parameters:** ``{tournamentId}`` should have the name of the tournament we chose.

* **Method:** ``POST``

* **Success response:** ``201 CREATED`` if it was uploaded successfully.

* **Error responses:** 
    * ``404 NOT FOUND`` if there's no tournament with that name.
    * ``401 UNAUTHORIZED``	 if the user either isn't an admin, or doesn't have the team whose image they want to upload.

* **Request example:** ``https://localhost:8443/api/TenniShip/Tournament/URJCup/image``



## Create a new tournament

This request lets users create a new tournament from scratch. All the necessary info is sent on the request body.

* **URL:** ``/api/TenniShip/Creator``

* **Method:** ``POST``

* **Success response:** ``201 CREATED`` if the tournament was created successfully.

* **Error responses:** 
    * ``401 UNAUTHORIZED`` if the user isn't logged in.
    * ``409 CONFLICT``	 if there's no tournament name, or any of the team names don't correspond to existing teams.

* **Response entity:** An object with the tournament name, and a list of team objects with their names.

**Request body example:**

````json
{
    "tournamentName": "UrjCup",
    "teams": [
        "Valencia",
        "Atlético de Madrid",
        "Serbia",
        "PSG",
        "Portugal",
        "Poland",
        "Ajax",
        "Andorra",
        "Argentina",
        "Finland",
        "Australia",
        "Barcelona",
        "Germany",
        "Napoli",
        "Norway",
        "France",
        "Italy",
        "Real Madrid"
    ]
}
````

**Response example:**

````json
{
    "tournamentName": {
        "name": "UrjCup"
    },
    "teams": [
        {
            "teamName": "Napoli"
        },
        {
            "teamName": "Finland"
        },
        {
            "teamName": "Norway"
        },
        {
            "teamName": "France"
        },
        {
            "teamName": "Australia"
        },
        {
            "teamName": "Serbia"
        },
        {
            "teamName": "Real Madrid"
        },
        {
            "teamName": "Germany"
        },
        {
            "teamName": "Italy"
        },
        {
            "teamName": "Ajax"
        },
        {
            "teamName": "Atlético de Madrid"
        },
        {
            "teamName": "Andorra"
        },
        {
            "teamName": "Valencia"
        },
        {
            "teamName": "Argentina"
        },
        {
            "teamName": "Barcelona"
        },
        {
            "teamName": "Portugal"
        },
        {
            "teamName": "Poland"
        },
        {
            "teamName": "PSG"
        }
    ]
}
````



## Delete tournament (admin only)

The admin is able to delete tournaments through this request.

* **URL:** ``/api/TenniShip/ADMIN/Tournament/{tournament}/Deleted``

* **URL parameters:** ``{tournament}`` should have the name of the tournament we want to delete.

* **Method:** ``DELETE``

* **Success response:** ``200 OK`` if the tournament was deleted successfully.

* **Error responses:** 
    * ``403 FORBIDDEN`` if the user isn't logged into an admin account.
    * ``404 NOT FOUND`` if there's no tournament with that name.

* **Request example:** ``https://localhost:8443/api/TenniShip/ADMIN/Tournament/URJCUP/Deleted``

**Response example:**

````json
{
    "name": "URJCUP"
}
````