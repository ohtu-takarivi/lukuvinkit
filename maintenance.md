# Ylläpitodokumentti 

## Sovellus
Projektissa on toteutettu Java-sovellus, johon käyttäjä voi muun muassa tallentaa lukuvinkkejä. Lukuvinkit talletetaan tietokantaan.

Sovelluksen pohjana on gradle+spring+cucumber+travis esimerkkikonfiguraatio https://github.com/mluukkai/spring-cucumber.

## Käyttö ja järjestelmävaatimukset
Sovellus voidaan suorittaa komentoriviltä komennolla gradle run. Tällä hetkellä sovellus toimii kaikilla käyttöjärjestelmillä, joilla gradlekin toimii eli Windowsilla, macOS:llä ja Linuxilla. (https://docs.gradle.org/current/userguide/userguide.html)

## Ominaisuudet
Sovellukseen on toteutettu seuraavat ominaisuudet: 

Käyttäjän toiminnallisuuksia:
* vinkin tarkastelu
* vinkkien lisääminen
* vinkkien poistaminen
* vinkkien selaaminen
* luetuksi merkkaus
* kommentin lisäys
* kommentin poisto ja muokkaus
* tagin lisäys
* tilin luominen
* kirjautuminen

Tietokannasta haku:
* advanced haku (tyypit, kirjoittajat, otsikot...)
* hakeminen otsikolla, kuvauksella, kirjoittajalla, tagilla

Yleisiä toiminnallisuuksia:
* tyypitys (kirja, artikkeli)
* automaattinen tietojenhaku URL-osoitteista
* automaattinen tietojenhaku ISBN-tunnuksesta
* URL-linkit
* ISBN-linkki Helkaan
* listaus ulos lukuvinkeistä
* lisäys sivupalkista
* URL validointi
* ISBN-10 ja ISBN-13 validointi
* selaus tyyppipohjaisesti

## Tietokanta
Sovelluksessa on käytössä Spring Data JPA (https://spring.io/projects/spring-data-jpa) ja H2-tietokanta (http://www.h2database.com/html/main.html). H2-tietokanta on paikallinen, ja säilyttää tiedot ohjelman sammuttamisen jälkeenkin. Sovellus osaa hakea tietoja tietokannasta automaattisesti käynnistyttyään. Tietokannan sijainti on määritelty tiedostossa /build/resources/main/application.properties.

Sovelluksen tietokantaan liittyvät osiot on määritelty Spring Data JPA:n mukaisesti @-alkuisilla selityksillä. Luokan ilmentymät on luotu asettamalla luokalle selite @Entity ja ottamalla käyttöön AbstractPersistable, jonka kautta luokat yksilöityvät.

Tietokantaan tallennettavaa luokkaa taas käytetään luokkien rajapintojen (Repository) kautta, jotka taasen käyttävät pohjana JpaRepositoryä. Controlliluokat käyttävät rajapintaluokkia annotaatiolla @Autowired ja pääsevät tietokantaan käsiksi.

Tietokantakaaviossa on kuvattu tietokantaan talletetut luokat ja niiden suhteet.
<img src="https://github.com/ohtu-takarivi/lukuvinkit/blob/master/documentation/maintenance/tietokantakaavio.png" width="500">


## Tarkistukset
Sovellukseen on luotu tarkistukset ISBN-numerosarjoille, sekä URL-osoitteille. Nämä löytyvät polusta (/src/main/java/ohtu/takarivi/lukuvinkit/forms/FormUtils.java).

### ISBN Tarkistus
ISBN-10 koostuu neljästä osasta, joiden tulee koostua numeroista. ISBN-13 sisältää näiden neljän osan lisäksi numerosarjan alussa numeronsarjan 978 tai 979. Neljä yhteistä osaa ovat seuraavat: alkuperäismaa tai kieliryhmä, kustantajatunnus, julkaisutunnus ja tarkistusmerkki.

ISBN-13 tarkistuksessa otetaan 12 ensimmäistä numeroa, joista lasketaan viimeinen numero eli tarkistusmerkki. Ensimmäinen numero kerrotaan yhdellä, jonka jälkeen seuraava kolmella, ja sitä seuraava taas yhdellä jatkaen kunnes kaikki numerot on kerrottu. Lopputulosta verrataan viimeiseen merkkiin. Mikäli tulos ei vastaa tarkistusmerkkiä, hylätään ISBN virheellisenä.

ISBN-10 tarkistuksessa otetaan 9 ensimmäistä numeroa, joista lasketaan viimeinen numero eli tarkistusmerkki. Jokainen numero summataan yhteen. Jokaisen summauksen jälkeen summa lisätään muuttujaan summa. Lopputulos haetaan tarkistussarjasta 0123456789X. Mikäli tulos ei vastaa tarkistusmerkkiä, hylätään ISBN virheellisenä.

### URL Tarkistus
Sovellukseen on luotu URL-osoitteiden tarkistus. Tarkistus tehdään vahvistamalla URL-osoitteen noudattavan Regexissä määriteltyä formaattia. 
(^(http|https|ftp):\\/\\/([a-zA-Z0-9_\\.\\-]+\\.([A-Za-z]{2,20})|localhost)(:[0-9]{1,5})?[a-zA-Z0-9_\\/\\&\\?\\=\\.\\~\\%\\-]*)
Vaatimukset ovat seuraavat:
* Tulee alkaa sanalla http, https tai ftp. Tulee koostua kirjaimista tai numeroista. Päätteen tulee olla 2-20 merkkiä pitkä.
tai
* Tulee alkaa sanalla 'localhost:', jonka jälkeen pitää seurata 1-5 numeroa.

## Testidata
Tietokantaan saa lisättyä testidataa tiedostosta (/src/main/resources/testdata.txt). Tämä testidata voidaan ottaa käyttöön menemällä osoitteeseen http://localhost:8080/testDataInsert, jolloin otetaan käyttöön käyttäjä testuser/testuser, johon testidata liitetään. Tuotantoversiossa testidatan luominen ei saa olla käytössä.

## Testit
Sovelluksessa käytetään Seleniumia, Cucumberia ja JUnit-testejä. Pääsosin testaus tapahtuu Cucumberin määritysten pohjalta. Cucumberin testit taas on määritelty User Storyjen pohjalta, ja nämä löytyvät polusta (/src/test/resources/ohtu/takarivi/lukuvinkit/) *.feature-tiedostoina.

## Jatkokehitys
Sovellusta voidaan jatkossa parantaa esimerkiksi lisäämällä uusia toiminnallisuuksia. 

Vielä toteuttamattomia, lisättäväksi suunniteltuja ominaisuuksia ovat:
* kurssit & hakeminen kursseilla	
* lukuvinkkien muokkaaminen	
* tilin muokkaaminen, profiilit	
* YouTube-API:n käyttö videon tietojen hakemiseen
