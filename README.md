# Octolendar
*Note*: Current version supports Android API 27 and higher, and that's probably not gonna change for some time.
-
Octolendar is a calendar application which will allow you to manage your schedule.
-
* It can calculate the current month's progression and display the information in percentages.
* While in the process of adding a new event to your schedule, you are able to mark the event with a special tag: "It's Important", "Ugh" or "You can skip it".
* A *cool* name.
* Activities are now able to detect swiping gestures (Several features will be based on this).
* It implements a simple SQL-database for storing information about your planned events.

## Things left to do are:

* Creating a generator for CardView items, which will contain all information about your planned events.
* Adding an activity for managing application settings.
* An ability to edit and delete existing information.

## Database entries are presented as depicted here:

| id  | date | time | title | comment | urgency | 
| ------------- | ------------- | ------------- | ------------- | ------------- | ------------- |
| 1 | 31.10.2019 | 22:30 | Halloween Party| At Ashley's House | It's important|
