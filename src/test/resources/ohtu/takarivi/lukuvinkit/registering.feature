Feature: As an unregistered user, I can register a user account

  Scenario: user can register an account with valid information
    Given user is at the register page
    When username "uusi23732373" and password "salasana" are registered
    Then user account is created

  Scenario: user cannot register an account with a taken username
    Given user is at the register page
    When username "nolla" and password "salasana2" are registered
    Then user account is not created

  Scenario: user cannot register an account with an invalid username
    Given user is at the register page
    When username "aa" and password "hei maailma" are registered
    Then user account is not created
