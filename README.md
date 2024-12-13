# **JamGenie**  
JamGenie uses LastFm's API to recommend songs tailored to your taste. Whether you're vibing to chill beats, rocking out to energetic tracks, or exploring new genres, JamGenie is here to enhance your music discovery experience.  

This program was developed as a project for the 'Intelligent User Interfaces' course during my **2nd Year Computer Science BSc at Maastricht University**. The goal of this project was to study the difference in user reception, analyzed through statistical analysis and emotion models, to two interfaces performing the same task—one with automation and the other without.  

## *Features*
- Search for songs and albums manually.  
- Save your favorites with a "like" button.  
- Get AI-recommended tracks based on your favorites.  

---

## **Requirements**  
To run this project, you will need the following:  
1. **Java Development Kit (JDK)**: Version 8 or higher.  
   - Download and install JDK [here](https://www.oracle.com/java/technologies/javase-downloads.html) if not already installed.  
2. **JavaFX**: Ensure that your JDK installation supports JavaFX. If not, the necessary JavaFX .jar files are included in the `/lib` folder.  
3. **Gson Library**: The Gson library .jar file is also included in the `/lib` folder for JSON processing.  

> **Note:** This project does not use Maven or Gradle, so dependencies are manually managed.

---

## **Setup Instructions**  
1. **Clone the Repository**:  
   Clone this repository to your local machine using:  
   ```bash
   git clone [[repository URL]](https://github.com/JonoHub/JamGenie.git)
   ```  

2. **Set Up the Project**:  
   - Open your favorite IDE (e.g., IntelliJ, Eclipse, or VSCode).  
   - Import the project as a standard Java project.  
   - Add the .jar files located in the `/lib` folder (JavaFX and Gson) to your project’s build path.  

3. **Run the Program**:  
   - Locate the main class (e.g., `Main.java`).  
   - Run the program through your IDE.  

---

## **Task Instructions for User Testing**  
### Follow these steps to test the application:  

1. **Search for Tracks or Albums**:  
   - Use the search bar on the main screen to search for **two tracks** and **two albums** of your choice.  
   - Toggle between the "Track" and "Album" buttons to switch search modes.  

2. **Add and Remove Favorites**:  
   - Add a track or album to your favorites using the "Like" button.  
   - Navigate to your profile by clicking the profile icon in the top right corner.  
   - In the profile tab, remove all albums from the favorites list to test the "unfavoriting" functionality.  

3. **Get AI Recommendations**:  
   - From the profile tab, click the **"Get AI Recommended"** button.  
   - Browse the recommended tracks and "Like" any tracks you enjoy. Keep track of how many you like.  

4. **Evaluate the Experience**:  
   - Note how intuitive and efficient the features feel during use.  
   - Return to the provided survey form and answer the questions based on your experience.
  
---

***Thank you for reading!*
