Feature: When I am adding a link reading tip, I can get the information from a link automatically

  Scenario: user can get URL information automatically
    Given test user is logged in and creating a link tip
    When fetching information from our own login page
    Then fetched title contains "Lukuvinkit" and description contains "palvelu"

  Scenario: user can get ISBN information automatically
    Given test user is logged in and creating a book tip
    When fetching information from ISBN "951-1-14445-6"
    Then fetched title contains "Suomen kielioppi" and author contains "Leino" or there is an alert
