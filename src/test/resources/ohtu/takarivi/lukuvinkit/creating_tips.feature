Feature: After logging in, I can create tips with valid information

  Scenario: user can create a tip with valid information
    Given test user is logged in
    When correct title "Best Title" and type "books" and description "Super description" and url "www.mustread.com" and author "John Doe" are given
    Then new tip with title "Best Title" and type "books" and description "Super description" and url "www.mustread.com" and author "John Doe" is created

  Scenario: user can't create a tip without title
    Given test user is logged in
    When no title and type "books" and description "Not working" and url "www.didnthappen.com" and author "John Doe" are given
    Then new tip with "Not working" and "www.didnthappen.com" is not created

  Scenario: user can't create a tip without type
    Given test user is logged in
    When title "Good title" and no type and description "I wonder" and url "www.notgonnawork.com" and author "John Doe" are given
    Then new tip with "Good title" and "www.notgonnawork.com" is not created

  Scenario: user can't create a tip without description
    Given test user is logged in
    When title "Good title" and type "books" and no description and url "www.notgonnawork.com" and author "John Doe" are given
    Then new tip with "Good title" and "www.notgonnawork.com" is not created

  Scenario: user can't create a tip without url
    Given test user is logged in
    When title "Bad title" and type "books" and description "I wonder" and no url and author "John Doe" are given
    Then new tip with "Bad title" and "I wonder" is not created 

  Scenario: user can't create a tip without author
    Given test user is logged in
    When title "Bad title" and type "books" and description "I wonder" and url "www.nobleeffort.com" and no author are given
    Then new tip with "Bad title" and "I wonder" is not created 