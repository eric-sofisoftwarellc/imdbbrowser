About this project
==================

This is a small project to demonstrate use of the omdbapi service. Given a search query, the service returns
a list of items. Each item has a title, a year, a poster URL, and an id (we ignore the id in this app).

3rd party libraries used
========================

To make implementation faster, we're using some libraries. These include:

  * Standard android libraries such as RecyclerView, annotations
  * AndroidX components: ViewModel, LiveData.
  * Retrofit for networking
  * Picasso for loading images from URL into ImageView
  * Gson for parsing JSON
  * Roboelectric and Mockito for unit tests
  * Mockwebserver, Mockwebserverplus and Snakeyaml for unit testing networking

Architecture
============

This is a simple one-activity app.

The user enters a query into a text field and presses the action button on the soft keyboard to initiate the query.

The activity instantiates a ViewModel which manages the server query, and adds an observer to act when data is fetched and parsed.

Retrofit is used to make the REST call in a thread, uses Gson to parse the data, and returns data through a callback which then is forwarded using LiveData to the activity's observer.

Choices
=======

Using ViewModel means if the activity is destroyed temporarily (such as rotating the screen), the next activity will use the same ViewModel instance which means the network call won't be duplicated.
The network request also won't hold a reference to the old activity instance (which would waste memory).

Using Retrofit makes it really easy to call a web service and parse the data. Also if we wanted to add additional web services, it would similarly be very easy to add new calls.

Gson is a fast and easy-to-use Json parser. Note that while it is possible to use the field names to encode the json keys, we embedded the field names in annotations so that any future use of proguard won't mangle the keys.

Using RecyclerView is more efficient than ListView, especially for long lists of data. It enforces the ViewHolder pattern and reuses views that scroll offscreen effectively.

Use of Mockwebserverplus means we can easily add test cases for real data, including simulating any http errors.

Limitations
===========

This is a quick project and is missing a few things that a production app would want to handle. These include:

  * Error handling for some http errors, to help the user understand why a query failed
  * Additional caching of data, possibly using Room, to make repeated queries faster
  * Paging for additional items from the service. Right now we show only the first 10 items.
  * Maybe some way to show additional detail for a movie, such as tapping on an item to open a detail screen
  * An introductory help screen, explaining what the app does and how to use it

