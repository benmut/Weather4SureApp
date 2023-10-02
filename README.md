# Weather4SureApp
This is a simple single module Android App that allows a user to pin a location on the map and displays a 5-day forecast weather information.

# Build with
- Android Studio
- Kotlin

# Architecture
- MVVM

# Libraries
- Google Play Service for Maps.
- Google Places for places.
- Retrofit for remote calls.
- Hilt for dependency injection.
- Coroutines for asynchronous execution.
- Preference.
- Room for local database.

# Implementation & consideration
- The project makes use of XML for UI components and ViewBinding.
- Since the forecast5 API returns weather info every 3 hours, weather info for 12:00 was picked and used.

# Tested with
- Emulator Nexus 6 API 29

# Usage
It is better to test it on an emulator to better test different locations' weather information. (See demo)

# Improvement
- Fine tune the UI design;
- Zoom map camera to user's current location instead the one specified in code;
- Check for internet connection before fetching data;
- Create a file to centralise the versions of all the libraries used;
- Make the App multi-module with each layer in its own module, etc.