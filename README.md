Droid Monkeys - Kathryn Thomas, Archer Zhang 14 November 2012
Design for Journal Map Application

Summary
Journal Map is a personal, location-based scrapbook + journal mobile Android application designed for the traveler. It allows a user to take pictures or videos of the attraction with her phone, add a written comment, record an audio journal entry, and tag that media to a location using GPS or NFC tags (for indoor locations). After saving a place to the application, the user can go back to see all their travels pinpointed on a map and then they can review/relive their travel experiences by looking at the photos, reading their comments, and hearing their notes.

Features
In order to accomplish this, Journal map requires the following on-device features:
• Camera
• Audio Recorder
• Video capability
• Google Map access
• Touch screen

Development/Production Environment
To meet all of the features for the application, the desired hardware is an Android phone, version 2.3.3 or higher. Development will be done in Java using Eclipse as the IDE. The Google API v10 will be used in order to ensure that the map features work. This means that each developer must install a Google API key to test the application. A GitHub repository is used to track the code.

Code Organization
The source code will be organized into the following packages:
• edu.cmu.journalmap.activities – for all Activities in the application
• edu.cmu.journalmap.map – for the map-specific features like adding pins on the map and
selecting pins
• edu.cmu.journalmap.models – for the classes like Place
• edu.cmu.journalmap.utilities – for accessing camera-specific features like the camera, audio
recorder/playback
