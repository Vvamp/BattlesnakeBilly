[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/4FZB5KC8)
# Billy - BEP1 Eindopdracht 2024
Door: Vincent van Setten (1734729)

## Billy algoritme
Billy gebruikt het MCTS algoritme te bewegen. Dit leek me een leuk algoritme om uit te proberen in Java. Het werkt redelijk. Vooral op mijn local machine kan ik de tijd lekker ruim houden en is de redelijk slim. Op azure, waar het wat langzamer is, geef ik hem wat minder tijd(250ms ongeveer) omdat die anders te langzaam is. Daardoor is die dus ook wat minder slim en gaat het wel eens mis.

## Frontend
Om goed gebruik te maken van de frontend, heb je een account nodig. Ik heb besloten geen registreer functie in te bouwen, omdat ik niet dacht dat dat momenteel heel nuttig zou zijn. Er zijn 2 gebruikersaccounts in de LoginManager klasse gedefinieerd waar u gebruik van kunt maken.
Dit zijn:
- Gebruiker 'Vvamp', wachtwoord 'admin'
- Gebruiker 'NietVvamp', wachtwoord 'anders'

Elke gebruiker heeft een eigen slang.
Door in te loggen krijg je de mogelijkheid de slang aan te passen en je eigen games te zien.

## API
De api heeft een baseurl van {url}/api. 
Hieronder heb ik de resources
- /snakes
- /games
- /user

Om de snake te registreren op battlesnake heb je de resource:
- /api/snakes/{gebruikersnaam} nodig.
Deze gebruikt dus de paden
- /api/snakes/{gebruikersnaam}/start
- /api/snakes/{gebruikersnaam}/move
- /api/snakes/{gebruikersnaam}/end

## Persistence
Ik heb persistentie geimplementeerd door gewoon voor elke user een json bestand op te slaan, met daarin informatie over de gespeelde games en snake instellingen.
Dit wordt gedaan op linux onder: /home/{user}/billy/users/{username}. Op windows zou dit moeten zijn: c:/users/{user}/Documents/billy/users/{username}.
