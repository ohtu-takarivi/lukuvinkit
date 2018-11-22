Feature: After logging in, I can change my password

  Scenario: user can change password
    Given test user is logged in
    When new password "yksi" and for verifying new password "yksi" are given
    Then the password is changed
