# autocomplete
autocomplete addresses in a scalable way

Feature: Suggest valid street names as users type

Scenario: User starts typing a street name
  * Given a user begins entering text into the street address field
  * When the system provides a list of matching street names starting with the first few characters the user entered
  * And the user continues to type additional characters to refine the selection
  * Then the system updates the list of matched street names to show only those starting with the newly added characters
    
Scenario: User receives accurate results while typing
  * Given a user enters some text resembling a street name
  * When the system recognizes the intended street name and presents it as a top result
  * And the user accepts the suggested street name without further changes
  * Then the street name is correctly displayed in the final saved address
  * And the system should suggest the remaining characters needed to complete the name accurately
    
Scenario: User completes partially entered street names
  * Given a user starts typing part of a well-known street name
  * When the system offers the correct full street name among its suggestions
  * And the user finishes entering the rest of the name and accepts the suggested match
  * Then the completed street name appears as part of the final saved address
    
Scenario: System displays contextually appropriate street options
  * Given a user initiates an address entry process while located at a particular location
  * When the system lists relevant streets based on proximity to the user's current position
  * Then the provided street names are in order matching the inputs
  * And the suggested street names should prioritize exact matches over partial ones, with the latter appearing farther down the list

# Solution

Accuracy
* browser gives timezone, asking for location can have friction

Recognition Reliability
* user can type what the word sounds like, so exact match can miss. Fuzzy match
* * use fuzzy matching with levenstien distance


# Cross-Functional

## Usability

## Performance

Response Time 
* list is being loaded with each request
* * build index when app starts to search in-memory 
* * use a trie to build graph

# Implementation
## System Diagram

## Java
Apache Commons has lev-distance, but need to index the list
https://github.com/fmmfonseca/completely/blob/master/README.md

# Nest

