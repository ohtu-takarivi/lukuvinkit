Feature: After logging in, I can create tips with tags

  Scenario: user can create an article tip with valid information and a tag
    Given test user is logged in
    When creating an article tip and correct title "Best Title" and description "Super description" and author "John Doe" and tags "testing124" are given
    Then new article tip with title "Best Title" and description "Super description" and author "John Doe" and tag "testing124" is created
