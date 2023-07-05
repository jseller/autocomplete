# autocomplete
autocomplete addresses in a scalable way

Feature: Suggest valid street names as users type

  Scenario: User starts typing a street name
    Given a user begins entering text into the street address field
    When the system provides a list of matching street names starting with the first few characters the user entered
    And the user continues to type additional characters to refine the selection
    Then the system updates the list of matched street names to show only those starting with the newly added characters
    
  Scenario: User receives accurate results while typing
    Given a user enters some text resembling a street name
    When the system recognizes the intended street name and presents it as a top result
    And the user accepts the suggested street name without further changes
    Then the street name is correctly displayed in the final saved address
    
  Scenario: User completes partially entered street names
    Given a user starts typing part of a well-known street name
    When the system offers the correct full street name among its suggestions
    And the user finishes entering the rest of the name and accepts the suggested match
    Then the completed street name appears as part of the final saved address
    
  Scenario: System displays contextually appropriate street options
    Given a user initiates an address entry process while located at a particular location
    When the system lists relevant streets based on proximity to the user's current position
    Then the provided street names include common destinations within reasonable walking distance


Scenario: User starts typing a street name

Given a user has logged in and provided proper authentication credentials
When the user navigates to the address input page or section
And the user begins typing letters related to a known street name
Then the system should display a dropdown menu showing matches based on the typed prefix after a short delay (<0.5 seconds)
The system should also provide a small number of options (e.g., 5), ensuring response time remains below 2 seconds for most connections
And the accuracy of these options should be greater than 80% compared against existing addresses stored in the database

Scenario: User receives accurate results while typing

Given a user is searching for a specific street name with no typos or misspellings
When the user types enough characters to trigger the autocomplete search feature
Then the suggested street names should prioritize exact matches over partial ones, with the latter appearing farther down the list
In addition, the system should display 90%+ accuracy when comparing the top three suggestions to previously saved addresses

Scenario: User completes partially entered street names

Given a user is attempting to save an incomplete street address they remember
When the user inputs at least four consecutive characters representing a popular street name
Then the system should suggest the remaining characters needed to complete the name accurately
Furthermore, the system should offer this suggestion as one of the top two choices presented to the user
Within 2 seconds of receiving this prompt, the user should accept the proposed completion without needing to enter additional keystrokes or scroll through another set of options



# Solution

## Usability

Accuracy
* browser gives timezone, asking for location can have friction

Recognition Reliability
* user can type what the word sounds like, so exact match can miss. Fuzzy match
* * use fuzzy matching with levenstien distance


Cross-Functional
System Diagram

# Performance

Response Time 
* list is being loaded with each request
* * build index when app starts to search in-memory 
* * use a trie to build graph

## Java

Apache Commons has lev-distance, but need to index the list
https://github.com/fmmfonseca/completely/blob/master/README.md

# Nest

