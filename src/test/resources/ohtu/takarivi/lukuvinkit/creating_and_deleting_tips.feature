Feature: After logging in, I can create, browse and delete tips with valid information

  Scenario: user can create a tip with valid information
    Given test user is logged in
    When correct title "Best Title" and description "Super description" and url "www.mustread.com" are given
    Then new tip with title "Best Title" description "Super description" and url "www.mustread.com" is created

  Scenario: user can't create a tip without title
    Given test user is logged in
    When no title "" and description "Not working" and url "www.didnthappen.com" are given
    Then new tip with "Not working" and "www.didnthappen.com" is not created

  Scenario: user can't create a tip without description
    Given test user is logged in
    When title "Good title" and no description "" and url "www.notgonnawork.com" are given
    Then new tip with "Good title" and "www.notgonnawork.com" is not created

  Scenario: user can't create a tip without url
    Given test user is logged in
    When title "Bad title" and description "I wonder" and no url "" are given
    Then new tip with "Bad title" and "I wonder" is not created 

  Scenario: user can see and delete existent tip
    Given test user is logged in
    When tip with title "test reading tip 1" is deleted
    Then tip with title "test reading tip 1" is no longer visible