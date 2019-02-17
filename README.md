# Octolendar
### *Note*: Current version supports Android API 27 and higher, and that's probably not gonna change.

Watch project's development process [https://trello.com/b/mrR4UjZy/octolendar]

Octolendar is a calendar-based application which allows you to manage your schedule.
-
* This app can calculate the current month's progression and display the information in percentages.
* While in the process of adding a new event to your schedule, you are able to mark the event with a special tag: "It's Important", "Ugh" or "You can skip it".
* This app has a *cool* name.
* It implements a simple SQL-database for storing information about your planned events.
* You have an ability to edit and delete already existing events.
* Events can be reviewed via timeline activity.

## To-do:
* Add notifications for important events.
* Replace activities with fragments.
* GoogleDisk ang Google services integration.

## Database entries are presented as depicted here:

| id  | day | month | year | time | title | comment | urgency | completed |
| ------------- | ------------- | -------------  | ------------- | ------------- | ------------- | ------------- | ------------- |------------- |
| 1 | 31 | 10 | 2019 | 22:30 | Halloween Party| At Ashley's House | It's important| 0 |
