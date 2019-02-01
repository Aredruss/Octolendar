# Octolendar
*Note*: Current version supports Android API 27 and higher, and that's probably not gonna change for some time.
-
Octolendar is a calendar application which will allow you to manage your schedule.
-
* It can calculate the current month's progression and display the information in percentages.
* While in the process of adding a new event to your schedule, you are able to mark the event with a special tag: "It's Important", "Ugh" or "You can skip it".
* A *cool* name.
* Activities are now able to detect swiping gestures (Several features will be based on this).
* A model for the future database.
* Setting up a simple SQL-database for storing information about your planned events.
-
## Things left to do are:
* Creating a generator for CardView items, which will contain all information about your planned events.
* Adding an Adapter for the recycler view and for the database.
* Adding an activity for managing application settings.
* An ability to edit and delete existing information.

After completing my to-do list I am planning on cleaning up my code and adding some minor features, such as an optional countdown for some events and etc.

Also, my friend suggested that I should consider adding a login activity via Firebase and setting up a PostegreSQ: server for storing user data. 
