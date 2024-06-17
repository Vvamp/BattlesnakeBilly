# BEP1 Eindopdracht 2024

## Battlesnake

### Inleiding

[Battlesnake](https://play.battlesnake.com) is een multiplayer code-challenge waarbij je je eigen Snake '*AI*' moet
programmeren als webservice.

Het is een spelletje dat de meesten van jullie vast wel een keer gezien zullen hebben: je bestuurt een slang die door
blokjes te eten steeds langer wordt. De slang mag nooit zichzelf, de rand of een andere slang raken.

### Onderdelen

De opdracht heeft een aantal verplichte onderdelen:

1. De Battlesnake moet op je eigen account geregistreerd kunnen worden op https://play.battlesnake.com. Hiervoor zul je
dus de applicatie echt toegankelijk moeten maken (via bijv. Heroku, Azure, of je eigen slimme idee...) en de
[Battlesnake API](https://docs.battlesnake.com/references/api#the-battlesnake-api) moeten implementeren. Focus je eerst 
op het GET request. Je zou bijv. in eerste instantie een hard-coded response kunnen teruggeven (wat je in het volgende
onderdeel direct moet verbeteren). De documentatie is er niet helemaal duidelijk over, maar je GET request hoeft niet op
een 'top-level-url' gehost te worden, je mag je slang prima parkeren op `/restservices/bepslang` (of iets dergelijks).
2. Het uiterlijk van je slang moet via de snake.html (of vergelijkbare) frontend aangepast kunnen worden. Het is oké om
daarna handmatig op het refresh-knopje op Battlesnake.com te klikken, dat hoeft, en kan waarschijnlijk ook niet automatisch!.
Oftewel, na een aanpassing moet het GET request van onderdeel 1 een nieuw uiterlijk opleveren.
3. Uiteraard moet je ook een potje kunnen spelen! Hiervoor zul je de rest van de 
[Battlesnake API](https://docs.battlesnake.com/references/api#the-battlesnake-api) moeten implementeren. Dit houdt in dat
je naast dat ene GET request, ook 3 POST-requests moet kunnen ontvangen. Het is hierbij niet van groot belang hoe slim je slang is, maar
of je deze requests netjes kunt opvangen en afhandelen. Er staat in **DummyBody.json** een voorbeeld-body voor je klaar zodat
je die zo in Postman kan plakken en niet de requests van Battlesnake hoeft te reverse-engineeren.
4. Informatie van gespeelde potjes moet (per potje) inzichtelijk zijn via de games.html (of vergelijkbare) frontend.
Welke informatie is niet heel belangrijk (denk bijv. aan 'aantal beurten', 'welke moves zijn er gedaan', etc.), het gaat 
er om dat je makkelijk kan bewijzen dat je alle moves van #3 goed kan opvangen en er data over kan verzamelen.
5. De informatie van een gespeelde potje moet verwijderd kunnen worden (omdat deze bijv. niet interessant was). Hiervoor
staat ook al een beginnetje klaar op de pagina: games.html (uiteraard staat het je vrij dit te verbeteren)
6. Waar logisch moet een gebruiker eerst inloggen voordat er slangen gewijzigd en gegevens ingezien en/of verwijderd kunnen worden.

Dit startproject bevat een aantal bestanden om je alvast op weg te helpen. Schrik niet van de verhouding Java en Javascript,
we geven veel Javascript code mee, omdat juist Javascript primair bij FEP hoort, terwijl wij puur de koppeling tussen de 
twee willen zien. Zo goed als alle Java zul je zelf moeten schrijven.
De urls die je gebruikt zijn vrij, en zowel aan de Battlesnake als de frontend kant los te configureren.

### Rubric

De Rubric is pas van toepassing als de applicatie in de basis functioneel is, en dus een potje Snake kan spelen.
De Rubric is op Canvas te vinden (om te voorkomen dat er twee tegenstrijdige versies zijn).

### FAQ

* *"Is dit niet eigenlijk een AI-opdracht? Ik dacht dat ik SD deed..."*

De details van de domein-logica, dus wat er -achter- je resources gebeurt, is inderdaad AI-spul. 90% van de eisen van 
deze opdracht gaan echter over het aanbieden van die logica als een webservice. Je hebt immers al een 'op niveau' voor 
gedrag te pakken als je slang niet meer als een kamikaze op de muur af stormt:)

* *"Is dit niet te moeilijk?"*

Het kan best intimiderend zijn om ineens 'in het wild' te programmeren. Probeer de onderdelen vooral stapje voor stapje
te maken. Dan zul je zien dat het wel mee valt. Ga je vooral niet 'gokken wat er mis is', maar ga bijv. op zoek naar extra
logs op Heroku om er achter te komen wat er echt aan de hand is.

* *"Moet ik echt die hele voorbeeld-JSON kunnen uitlezen?? Heb je wel gezien hoe groot die is?"*

Gelukkig is dat niet nodig. Als je javax.json gebruikt kun je gericht zoeken naar wat je nodig hebt, maar als je Jackson
gebruikt kun je door ``@JsonIgnoreProperties(ignoreUnknown = true)`` boven je class te plaatsen er voor zorgen dat je
alleen hoeft te mappen wat je daadwerkelijk wil gebruiken.

* *"Is dit niet te makkelijk?"*

Er liggen zat 'extra uitdagingen' in het verlengde. Probeer bijv. maar eens meerdere verschillende Battlesnakes op 1 
applicatie te hosten. En misschien kun je zelfs als je er meerdere hebt hybrides configureren.

* *"Inloggen is best lastig, maar moet ik dat vanwege onderdeel #5 nu niet als eerste bouwen?"*

Nee, dat valt wel mee. Als alles prima werkt, maar nergens security toegepast is telt dat als een-of-twee kleine 
tekortkomingen (er is geen security EN het inloggen doet het niet). Inloggen is echt een ideale 'laatste' feature om 
in deze opdracht te bouwen.

* *"Kan ik mijn docent nog om hulp vragen?"*

Nou ja, we sturen graag een beetje bij, of wijzen je naar opdrachten uit BEP1 waar 'het trucje' eerder langs is gekomen.
We kunnen je alleen niet zoveel helpen als bij de formatieve opdrachten, het is immers een toets. Probeer vooral 'het probleem dat je hebt', zo
goed mogelijk na te bouwen in de formatieve opdrachten, dan kunnen we daar je mee helpen. (klinkt flauw, maar je zult 
merken dat het nabouwen van je probleem in de formatieve opdracht 95% van de tijd je ook het antwoord al geeft!)
