Feature: After logging in, I can create tips with tags and view tags

  Scenario: user can create an article tip with valid information and a tag
    Given test user is logged in
    When creating an article tip and correct title "Best Title" and description "Super description" and author "John Doe" and tags "testing124" are given
    Then new article tip with title "Best Title" and description "Super description" and author "John Doe" and tag "testing124" is created

  Scenario: user can create list tips by tag
    Given test user is logged in
    When listing all tips with the tag "testing123"
    Then there are 2 tips listed

  Scenario: user can create list tags
    Given test user is logged in
    When listing all tags
    Then one of the tags is "testing123"
