# Ylläpitodokumentti 

## Sovellus
Projektissa on toteutettu Java-sovellus, johon käyttäjä voi muun muassa tallentaa lukuvinkkejä. Lukuvinkit talletetaan tietokantaan.

Sovelluksen pohjana on gradle+spring+cucumber+travis esimerkkikonfiguraatio https://github.com/mluukkai/spring-cucumber.

## Käyttö ja järjestelmävaatimukset
Sovellus voidaan suorittaa komentoriviltä komennolla gradle run. Tällä hetkellä sovellus toimii ainakin Linuxilla.

## Ominaisuudet
Sovellukseen on toteutettu seuraavat ominaisuudet: 

Käyttäjän toiminnallisuuksia:
* vinkin tarkastelu
* vinkkien lisääminen
* vinkkien poistaminen
* vinkkien selaaminen
* luetuksi merkkaus
* tilin luominen
* kirjautuminen

Tietokannasta haku:
* advanced haku (tyypit, kirjoittajat, otsikot...)
* hakeminen otsikolla, kuvauksella, kirjoittajalla

Yleisiä toiminnallisuuksia:
* tyypitys (kirja, artikkeli)
* automaattinen tietojenhaku URL-osoitteista
* automaattinen tietojenhaku ISBN-tunnuksesta
* URL-linkit
* ISBN-linkki Helkaan
* listaus ulos lukuvinkeistä
* lisäys sivupalkista
* URL validointi
* ISBN validointi
* selaus tyyppipohjaisesti

## Tietokanta
Sovelluksessa on käytössä Spring Data JPA (https://spring.io/projects/spring-data-jpa) ja H2-tietokanta (http://www.h2database.com/html/main.html). H2-tietokanta on paikallinen, ja säilyttää tiedot ohjelman sammuttamisen jälkeenkin. Sovellus osaa hakea tietoja tietokannasta automaattisesti käynnistyttyään. Tietokannan sijainti on määritelty tiedostossa /build/resources/main/application.properties.

Sovelluksen tietokantaan liittyvät osiot on määritelty Spring Data JPA:n mukaisesti @-alkuisilla selityksillä. Luokan ilmentymät on luotu asettamalla luokalle selite @Entity ja ottamalla käyttöön AbstractPersistable, jonka kautta luokat yksilöityvät.

Tietokantaan tallennettavaa luokkaa taas käytetään luokkien rajapintojen (Repository) kautta, jotka taasen käyttävät pohjana JpaRepositoryä. Controlliluokat käyttävät rajapintaluokkia annotaatiolla @Autowired ja pääsevät tietokantaan käsiksi.

Tietokantakaaviossa on kuvattu tietokantaan talletetut luokat ja niiden suhteet.
<img src="https://github.com/ohtu-takarivi/lukuvinkit/blob/master/documentation/maintenance/tietokantakaavio.png" width="500">

## ISBN Tarkistus
ISBN-10 koostuu neljästä osasta, joiden tulee koostua numeroista. ISBN-13 sisältää näiden neljän osan lisäksi numerosarjan alussa numeronsarjan 978 tai 979. Neljä yhteistä osaa ovat seuraavat: alkuperäismaa tai kieliryhmä, kustantajatunnus, julkaisutunnus ja tarkistusmerkki.

ISBN-13 tarkistuksessa otetaan 12 ensimmäistä numeroa, joista lasketaan viimeinen numero eli tarkistusmerkki. Ensimmäinen numero kerrotaan yhdellä, jonka jälkeen seuraava kolmella, ja sitä seuraava taas yhdellä jatkaen kunnes kaikki numerot on kerrottu. Lopputulosta verrataan viimeiseen merkkiin. Mikäli tulos ei vastaa tarkistusmerkkiä, hylätään ISBN virheellisenä.

ISBN-10 tarkistuksessa otetaan 9 ensimmäistä numeroa, joista lasketaan viimeinen numero eli tarkistusmerkki. Jokainen numero summataan yhteen. Jokaisen summauksen jälkeen summa lisätään muuttujaan summa. Lopputulos haetaan tarkistussarjasta 0123456789X. Mikäli tulos ei vastaa tarkistusmerkkiä, hylätään ISBN virheellisenä.

## Testit
Tietokantaan saa lisättyä testidataa tiedostosta /src/main/resources/testdata.txt. Tämä testidata voidaan ottaa käyttöön menemällä osoitteeseen http://localhost:8080/testDataInsert, jolloin otetaan käyttöön käyttäjä testuser/testuser, johon testidata liitetään. Tuotantoversiossa testidatan luominen ei saa olla käytössä.

Sovelluksessa käytetään Seleniumia, Cucumberia ja JUnit-testejä. Pääsosin testaus tapahtuu Cucumberin määritysten pohjalta. Cucumberin testit taas on määritelty User Storyjen pohjalta, ja nämä löytyvät polusta /src/test/resources/ohtu/takarivi/lukuvinkit/ *.feature-tiedostoina.

## Jatkokehitys
Tällä hetkellä sovellus on vielä kehityksessä, ja sovellusta voidaankin parantaa esimerkiksi lisäämällä uusia toiminnallisuuksia. Lisättäväksi ominaisuuksiksi on suunniteltu muun muassa tägäystä sekä kommentteja. Näitä ei ole vielä kirjoitushetkellä (2.12.2018) toteutettu.