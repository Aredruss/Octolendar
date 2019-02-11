# Octolendar
*Note*: Current version supports Android API 27 and higher, and that's probably not gonna change for some time.
-
Octolendar is a calendar application which will allow you to manage your schedule.
-
* This app can calculate the current month's progression and display the information in percentages.
* While in the process of adding a new event to your schedule, you are able to mark the event with a special tag: "It's Important", "Ugh" or "You can skip it".
* This app has a *cool* name.
* It implements a simple SQL-database for storing information about your planned events.
* You have an ability to edit and delete already existing events.
* Events can be reviewed via timwline activity.

## Things left to do are:

* Adding a functional activity for managing application settings.
* Adding an ability to swipe between days via DayActivity.
* Adding notifications for important events.
* Possible GoogleDisk integration.

## Database entries are presented as depicted here:

| id  | day | month | year | time | title | comment | urgency | completed |
| ------------- | ------------- | -------------  | ------------- | ------------- | ------------- | ------------- | ------------- |------------- |
| 1 | 31 | 10 | 2019 | 22:30 | Halloween Party| At Ashley's House | It's important| 0 |
