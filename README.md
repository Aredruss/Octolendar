# Octolendar
*Note*: Current version supports Android API 27 and higher, and that's probably not gonna change for some time.
-
Octolendar is a calendar application which will allow you to manage your schedule.
At the momement of speaking (06.01.2019) this application has the following features:
-
1) It can calculate the current month's progression and display the information in percentages.
2) While in the process of adding a new event to your schedule, you are able to mark the event with a special tag: "It's Important", "Ugh" or "You can skip it".
3) A *cool* name.
4) Activities are now able to detect swiping gestures (Several features will be based on this).

Things left to do are:
-
1) Creating a generator for CardView items, which will contain all information about your planned events.
2) Setting up a simple SQL-database for storing information about your planned events.
3) Adding an activity for managing application settings.
4) An ability to edit and delete existing information.
5) Adding some logic to the DayActivity, so that the yser will be able to swipe between days;

After completing my to-do list I am planning on cleaning up my code and adding some minor features, such as an optional countdown for some events and etc.

Also, my friend suggested that I should consider adding a login activity via Firebase and setting up a PostegreSQ: server for storing user data. 
