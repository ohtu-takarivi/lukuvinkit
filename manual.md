# Käyttöohje  

## Sovelluksen lataaminen 
Sovelluksen voi ladata tietokoneelle osoitteesta [https://github.com/ohtu-takarivi/lukuvinkit](https://github.com/ohtu-takarivi/lukuvinkit); tämä suositellaan tehtäväksi komennolla (terminaalissa) `git clone https://github.com/ohtu-takarivi/lukuvinkit.git`.

## Sovelluksen käynnistäminen ja sammuttaminen  
Kun sovellus on ladattu laitteelle, sitä voi suoraan käyttää. Käynnistämiseen tarvitaan vain yksi komento; `gradle run` (tai `gradlew run`). Nyt sovellus toimii ja sitä pääsee käyttämään osoitteessa http://localhost:8080. Sovelluksen sammuttaminen onnistuu painamalla terminaalissa _Ctrl+C_-näppäinyhdistelmää.

## Uuden käyttäjän luominen ja kirjautuminen omalle sivulle  
Osoitteessa http://localhost:8080 avautuu ensimmäiseksi kirjautumisnäyttö. 

<img src="https://github.com/ohtu-takarivi/lukuvinkit/blob/master/documentation/pictures/loginScreen.png" width="500">  

Jos voimassa olevaa käyttäjätunnusta ei vielä ole, kirjautumisnäkymästä on mahdollista siirtyä uuden käyttäjän luomisnäkymään klikkaamalla _Luo tunnus_. Uuden käyttäjän luominen onnistuu täyttämällä kaikki kentät: käyttäjänimen tulee kuitenkin olla uniikki, salasanan vähintään 8 merkkiä pitkä, käyttäjänimen 3-32 merkkiä sekä nimen 2-64 merkkiä pitkä.

<img src="https://github.com/ohtu-takarivi/lukuvinkit/blob/master/documentation/pictures/newUserRegistration.png" width="500">   

Jos mitään virheilmoituksia ei tule näkyviin, voidaan siirtyä omalle sivulle käyttäen voimassa olevaa käyttäjätunnusta. Kirjautumisen jälkeen pääset omalle sivulle.  

<img src="https://github.com/ohtu-takarivi/lukuvinkit/blob/master/documentation/pictures/userMainPage.png" width="500">  

Kyseiseltä sivulta pääsee selaamaan lukuvinkkejä tyypeittäin sekä siirtymään hakuun, jos jokin tietty lukuvinkki tulee löytää.

## Lukuvinkin lisäys  
Lukuvinkin lisäys onnistuu valitsemalla navigointipalkkissa _Lisää_-valikosta uuden lukuvinkin tyyppi (Artikkeli, Kirja, Linkki, Video).  

<img src="https://github.com/ohtu-takarivi/lukuvinkit/blob/master/documentation/pictures/newTip.png" width="200">   

Uudessa näkymässä pitäisi antaa uuden lukuvinkin tiedot ja painaa _Lisää_.  

<img src="https://github.com/ohtu-takarivi/lukuvinkit/blob/master/documentation/pictures/newBook.png" width="500">   

Uusi lukuvinkki on lisätty, kun _Lisää_-nappia on painettu.
