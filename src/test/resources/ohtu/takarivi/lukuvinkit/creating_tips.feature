Feature: After logging in, I can create tips with valid information

  Scenario: user can create a book tip with valid information
    Given test user is logged in
    When correct title "Best Title" and description "Super description" and url "www.mustread.com" and author "John Doe" and isbn "978-3-16-148410-0" are given
    Then new book tip with title "Best Title" and description "Super description" and author "John Doe" is created

  Scenario: user can't create a book tip without title
    Given test user is logged in
    When no title and description "Not working" and url "www.didnthappen.com" and author "John Doe" and isbn "978-3-16-148410-0" are given
    Then new book tip with "Not working" and "www.didnthappen.com" is not created

  Scenario: user can't create a book tip without description
    Given test user is logged in
    When title "Good title" and no description and url "www.notgonnawork.com" and author "John Doe" and isbn "978-3-16-148410-0" are given
    Then new book tip with "Good title" and "www.notgonnawork.com" is not created

  Scenario: user can't create a book tip without author
    Given test user is logged in
    When title "Good title" and description "I wonder" and url "www.nobleeffort.com" and no author and isbn "978-3-16-148410-0" are given
    Then new book tip with "Bad title" and "I wonder" is not created 

  Scenario: user can't create a book tip without ISBN
    Given test user is logged in
    When title "Good title" and description "I wonder" and url "www.nobleeffort.com" and author "John Doe" and no isbn are given
    Then new book tip with "Good title" and "I wonder" is not created 
