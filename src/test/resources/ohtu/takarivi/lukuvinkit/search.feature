Feature: After logging in, I can search for tips

  Scenario: user can search for tips with the search bar
    Given test user is logged in
    When searching for tips with "Alpha"
    Then there are 3 search results and one of them is "Book Tip Alpha 2"

  Scenario: user can search for tips by title with the search form
    Given test user is logged in
    When searching for tips with title "Beta"
    Then there are 2 search results and one of them is "Book Tip Beta 2"

  Scenario: user can search for tips by description with the search form
    Given test user is logged in
    When searching for tips with description "DescSearch"
    Then there is 1 search result and it is "Book Tip Alpha 1"

  Scenario: user can search for tips by author with the search form
    Given test user is logged in
    When searching for tips with author "AuthorSearch"
    Then there is 1 search result and it is "Book Tip Beta 1"

  Scenario: user can search for article tips only with the search form
    Given test user is logged in
    When searching for article tips only
    Then there are no search results

  Scenario: user can search for video tips only with the search form
    Given test user is logged in
    When searching for video tips only
    Then there are 2 search results and one of them is "Video Tip 2"

  Scenario: user can search for link tips only with the search form
    Given test user is logged in
    When searching for link tips only
    Then there is 1 search result and it is "test reading tip 3"

  Scenario: user can search for tips already read only with the search form
    Given test user is logged in
    When searching for read tips only
    Then there are no search results

  Scenario: user can search for tips by tags with the search form
    Given test user is logged in
    When searching for tips with tag "testing123"
    Then there are 2 search results and one of them is "Book Tip Alpha 3"
