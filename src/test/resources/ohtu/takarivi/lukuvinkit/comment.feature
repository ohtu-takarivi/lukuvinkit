Feature: As an user, I can set the comment of a reading tip

  Scenario: user can set a valid comment to a reading tip
    Given test user is logged in
    And viewing a book tip with the title "test reading tip 4"
    When the comment is set to "test comment"
    Then the comment of the reading tip is "test comment"

  Scenario: user can remove the comment to a reading tip
    Given test user is logged in
    And viewing a book tip with the title "test reading tip 4"
    When the comment is set to ""
    Then the comment of the reading tip is ""

  Scenario: user can't set an invalid comment to a reading tip
    Given test user is logged in
    And viewing a book tip with the title "test reading tip 4"
    When the comment is set to "P6HZOG3m9uHpPjUaE7mvYIfLMifD2m62M0MuK9iAO7MAgRz0UXEl2Es75VRlWcx5FW1nvW0jFPkaAdNMkPDlK7RMww7xgwWemEFftasWekH5l9cKW0FppXixjDkN6ET1Q56ua9eyKEPTtV8BTk4CYYx2OTiQ6b749f3SKcPJdmPCCpqHLZqgieehg7vR4tZGj9g1fcQU3xZf3y6Ui6CoprKAztL13QaNovNnqto7aMC6MukfzyyU25z03YaK9N10"
    And viewing a book tip with the title "test reading tip 4"
    Then the comment of the reading tip is ""
