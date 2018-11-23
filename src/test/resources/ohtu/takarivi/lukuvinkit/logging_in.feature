Feature: As a registered user, I can log in with a valid username/password combination

  Scenario: user can login with correct password
    Given user is at the login page
    When correct username "nolla" and password "yksi" are given
    Then user is logged in

  Scenario: user cannot login with incorrect password
    Given user is at the login page
    When correct username "nolla" and incorrect password "kaksi" are given
    Then user is not logged in

  Scenario: cannot login to an user that does not exist
    Given user is at the login page
    When incorrect username "kolme" and password "viisi" are given
    Then user is not logged in
