# Dokumentasjon
PGR200 Eksamen

## Forberedelser
1. Last ned eller klon prosjektet
2. Pass på at både Java og Maven er installert
4. sett riktige verdier inn i src/main/java/resources/db.properties slik at det stemmer med din database (prekonfigurert docker-compose kjører med riktige properties).

## Krav
* Java 11 (JDK)
* Docker (Eller manuelt sette opp postgres database..)
    
## Instruksjoner til å kjøre programmet:
```
1. kjør mvn package inni prosjektmappen
2. kjør server-class i egen terminal.
3. start client-class i egen terminal.
4. følg instruksjonene som skrives ut i terminalen.
``` 

## Notater til innleveringen
Vi har valgt å ikke prioritere mye tid på client-klassen, og har heller lagt tiden andre steder vi følte ville vise
frem kompetansen bedre.


## Egenvurdering
Siden eksamensoppgaven ble lagt ut, har vi jobbet nesten konstant uten pause til fristen. Vi har dermed investert utrolig mye tid til å skape en god løsning som representerer
kunnskapsnivået vårt i emnet. Da vi ikke forventet at oppgaven i utgangspunktet måtte gjøres i par, ble det brukt en del tid på starten for å møte opp og planlegge hvordan
vi skulle takle utfordringene. Vi ble da enige om å møte opp og  planlegge overnatting for å optimalisere all den tiden vi hadde. Samarbeidet vårt har vært god, 
og vi har klart å både jobbe sammen via parprogrammering og hver for oss, basert på hva som måtte gjøres til den tid.
Det er normalt å stå fast på et problem når du koder for deg selv, så det har vært hjelpsomt og ekstremt nyttig å ha noen å løse de større problemene sammen med. 
Løsningen vår er uferdig, den oppfyller de fleste kravene godt, og vi føler oss fornøyd med hva vi har oppnådd i løpet av tiden.
Vi mener at innsatsen vår og resultatet vi har klart å komme opp med tross alle utfordringene bør rettferdigjøre en god karakter.


## Vedlegg ()
Parprogrammeringen ble utført og filmet under en lang økt med koding, så vi beklager på forkant for det 
lave energinivået.

## UML
![Uml](doc/UML.png)

## Datamodel
![Datamodel](doc/datamodel.png)


# PGR200 Hovedinnlevering

## Oppgave

Du skal lage en klient-server-løsning for å definere oppgaver i et prosjekt. En oppgave skal
ha navn, status og kunne tilordnes en eller flere personer. Dere skal lage en klient som lar
brukeren opprette, liste opp, se på detaljer og endre oppgaver. Klienten skal kommunisere
med en server over http, der dere skal bruke POST for å opprette og oppdatere oppgaver og
GET for å hente oppgavene. Serveren skal lagre oppgavene i Postgresql med JDBC.
Connection url, brukernavn og passord til databasen skal ligge i en .properties-fil. Det er ikke
påkrevd å ha flere tabeller i databasen, men det trekker opp.
Eksamen skal leveres inn i par og dere skal demonstrere bruk av Github. Bruk av Travis-CI i
tillegg trekker opp. Besvarelsen skal inneholde README.md-fil en brukerveiledning som
beskriver hvordan man starter serveren, oppretter nye oppgaver fra klienten, lister ut og
oppdaterer data. Bruk gjerne kjøreeksempler for å beskrive bruken. README.md fil skal
også inneholde en egenvurdering, en kort designbeskrivelse og bør inneholde et diagram
som beskriver hvordan delene i programmet fungerer. Det skal også legges ved en link til en
screencast video der dere parprogrammerer på en del av oppgaven.
Eksamensleveringen skal ha tester skrevet i JUnit og gode tester med god tekstdekning
trekker opp. Innleveringen skal være i form av et Maven-prosjekt og det trekker opp om dette
prosjektet har en fornuftig struktur med undermoduler. Kommunikasjonen mellom klient og
server skal fungere over http slik at curl kan benyttes som klient. Dårlig feilhåndtering av
uventede requests trekker opp (spesielt dersom det er mulig for en klient å få serveren til å
krasje så hardt at den ikke lenger svarer på flere requests). Database-koden skal følge DAO-
patternet.
Oppgaven skal utføres i Github classroom og dere skal eksportere en zip-fil av Git-repository
til Wiseflow. Det er Wiseflow som er den offisielle eksamensleveransen.

### Programflyt






