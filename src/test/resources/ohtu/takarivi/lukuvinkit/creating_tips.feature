Feature: After logging in, I can create tips with valid information

  Scenario: user can create a book tip with valid information
    Given test user is logged in
    When creating a book tip and correct title "Best Title" and description "Super description" and url "http://www.mustread.com" and author "John Doe" and isbn "978-3-16-148410-0" are given
    Then new book tip with title "Best Title" and description "Super description" and author "John Doe" is created

  Scenario: user can't create a book tip without title
    Given test user is logged in
    When creating a book tip and no title and description "Not working" and url "http://www.didnthappen.com" and author "John Doe" and isbn "978-3-16-148410-0" are given
    Then new book tip with "Not working" and "www.didnthappen.com" is not created

  Scenario: user can't create a book tip without description
    Given test user is logged in
    When creating a book tip and title "Good title" and no description and url "http://www.notgonnawork.com" and author "John Doe" and isbn "978-3-16-148410-0" are given
    Then new book tip with "Good title" and "www.notgonnawork.com" is not created

  Scenario: user can't create a book tip without author
    Given test user is logged in
    When creating a book tip and title "Good title" and description "I wonder" and url "http://www.nobleeffort.com" and no author and isbn "978-3-16-148410-0" are given
    Then new book tip with "Bad title" and "I wonder" is not created 

  Scenario: user can't create a book tip without ISBN
    Given test user is logged in
    When creating a book tip and title "Good title" and description "I wonder" and url "http://www.nobleeffort.com" and author "John Doe" and no isbn are given
    Then new book tip with "Good title" and "I wonder" is not created 

  Scenario: user can't create a book tip with invalid ISBN
    Given test user is logged in
    When creating a book tip and correct title "Best Title" and description "Super description" and url "http://www.mustread.com" and author "John Doe" and isbn "978-3-16-148410-4" are given
    Then new book tip with "Good title" and "I wonder" is not created 

  Scenario: user can create an article tip with valid information
    Given test user is logged in
    When creating an article tip and correct title "Best Title" and description "Super description" and author "John Doe" are given
    Then new article tip with title "Best Title" and description "Super description" and author "John Doe" is created

  Scenario: user can create a link tip with valid information
    Given test user is logged in
    When creating a link tip and title "Best Title" and description "Super description" and url "http://www.mustread.com" and author "John Doe" are given
    Then new link tip with title "Best Title" and description "Super description" and author "John Doe" is created

  Scenario: user can't create a link tip with empty URL
    Given test user is logged in
    When creating a link tip and title "Best Title" and description "Super description" and no url and author "John Doe" are given
    Then new link tip with "Best title" and "I wonder" is not created 

  Scenario: user can't create a link tip with invalid URL
    Given test user is logged in
    When creating a link tip and title "Best Title" and description "Super description" and url "https://<b>mustread.com" and author "John Doe" are given
    Then new link tip with "Best title" and "I wonder" is not created 

  Scenario: user can create a video tip with valid information
    Given test user is logged in
    When creating a video tip and title "Best Title" and description "Super description" and url "http://www.youtube.com/watch?v=" and author "John Doe" are given
    Then new video tip with title "Best Title" and description "Super description" and author "John Doe" is created

  Scenario: user can't create a video tip with invalid URL
    Given test user is logged in
    When creating a video tip and title "Best Title" and description "Super description" and url "https://<b>mustread.com" and author "John Doe" are given
    Then new link tip with "Best title" and "I wonder" is not created 
@quick
  Scenario: user can create a book tip with valid information through the sidebar
    Given test user is logged in
    When creating a book tip through the sidebar and correct title "Best Title" and description "Super description" and url "http://www.mustread.com" and author "John Doe" and isbn "978-3-16-148410-0" are given
    Then new book tip with title "Best Title" and description "Super description" and author "John Doe" is created
@quick
  Scenario: user can create an article tip with valid information through the sidebar
    Given test user is logged in
    When creating an article tip through the sidebar and correct title "Best Title" and description "Super description" and author "John Doe" are given
    Then new article tip with title "Best Title" and description "Super description" and author "John Doe" is created
@quick
  Scenario: user can create a link tip with valid information through the sidebar
    Given test user is logged in
    When creating a link tip through the sidebar and title "Best Title" and description "Super description" and url "http://www.mustread.com" and author "John Doe" are given
    Then new link tip with title "Best Title" and description "Super description" and author "John Doe" is created
@quick
  Scenario: user can create a video tip with valid information through the sidebar
    Given test user is logged in
    When creating a video tip through the sidebar and title "Best Title" and description "Super description" and url "http://www.youtube.com/watch?v=" and author "John Doe" are given
    Then new video tip with title "Best Title" and description "Super description" and author "John Doe" is created
