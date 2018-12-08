Feature: After logging in, I can select tips, browse them and export a listing in text or HTML

  Scenario: user can see selected tips
    Given test user is logged in and browsing book tips
    When selecting tip with title "test reading tip 4" and browsing is selected
    Then tip with title "test reading tip 4" is visible

  Scenario: user can see selected tips as text
    Given test user is logged in and browsing book tips
    When selecting tip with title "test reading tip 4" and exportText is selected
    Then tip with title "test reading tip 4" is visible

  Scenario: user can see selected tips as HTML when a link tip is selected
    Given test user is logged in and browsing link tips
    When selecting tip with title "test reading tip 4" and exportHTML is selected
    Then tip with title "test reading tip 4" is visible

  Scenario: user can see selected tips as HTML when a book tip is selected
    Given test user is logged in and browsing link tips
    When selecting tip with title "test reading tip 3" and exportHTML is selected
    Then tip with title "test reading tip 3" is visible
