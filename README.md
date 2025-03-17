<h1 align="center">CityArtWalk</h1>
<br/>
<br/>
<img align="right" src="./city-art-walk.gif" width="20%" height="auto"/>

<div id="user-content-toc">
  <ul align="left" style="list-style: none;">
    <summary>
      <h2>Project description</h2>
    </summary>
  </ul>
</div>

CityArtWalk is an Android app written in <b>Kotlin</b> designed to help users discover and document public artworks <br/>in their city. Users can add, edit, and delete artworks, view details, and navigate to their locations using GPS integration. 

<div id="user-content-toc">
  <ul align="left" style="list-style: none;">
    <summary>
      <h2>Features</h2>
    </summary>
  </ul>
</div>
<ul>
  <li><b>📍 View and Explore Artworks:</b> Browse a list of artworks with details.</li>
  <li><b>🎨 Add New Artworks:</b> Create new artwork entries with title, date, and location.</li>
  <li><b>📸 Photo Capture:</b> Take and attach photos of artworks using the camera.</li>
  <li><b>📌 GPS Location:</b> Get the artwork's location coordinates and open in Google Maps.</li>
  <li><b>🗑️ Manage Artworks:</b> Edit or delete existing artworks from the list.</li>
  <li><b>🔄 Data Persistence:</b> Uses Room Database for storing artwork information.</li>
</ul>

<h2>Technologies Used</h2>
<a href="https://kotlinlang.org" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/kotlinlang/kotlinlang-icon.svg" alt="kotlin" width="40" height="40"/> </a>
<a href="https://developer.android.com" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/android/android-original-wordmark.svg" alt="android" width="40" height="40"/> </a><br/>
Room Database<br/>
Google Play Services

<h2>Getting Started</h2>
To get a local copy up and running, follow these simple steps.

<h3>Prerequisites</h3>
<h5>Before running CityArtWalk, ensure you have the following:</h5>
- Android Studio <br/>
- Java 8+ or Kotlin support installed in Android Studio<br/>
- Android SDK 30+ <br/>
- A physical Android device or an Android Emulator<br/>

<h3>Installation</h3>
<h5>Clone the repository:</h5>
https://github.com/Yuliia-Kruta/city-art-walk.git

<h5>Open the project in Android Studio.</h5>

<h5>Sync Gradle and install dependencies.</h5>

<h5>Configure Maps API Key:</h5>
Go to Google Cloud Console and create a new API key.<br/>
Enable Maps SDK for  Android.<br/>
Open or create local.properties file in your project directory.<br/>
Add the following line:<br/>
<code>MAPS_API_KEY=your_api_key_here</code>


<h5>Build and run the app on an emulator or physical device.</h5>

<h2>How to Use</h2>
1. Launch the app to see a list of artworks.<br/>
2. Tap the "+" button to add a new artwork.<br/>
3. Enter artwork details and capture a photo.<br/>
4. Use "GET GPS" button to get the current location coordinates.<br/>
5. Open the map to see the artwork’s location.<br/>
6. Return to the list of artworks.<br/>
7. Tap on an artwork to view details or delete it.<br/>
8. Tap the "?" button to be redirected to Street Art website.<br/>

<h2>License</h2>
Distributed under the MIT License. See LICENSE for more information.
