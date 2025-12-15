# -MAD204-ASSIGNMENT3-Jennyfer
This Android application is a simple notes management system built using Kotlin and Android Studio. The app allows users to create, edit, delete, and favorite notes, with data stored locally using Room Database.

Features
- Create new notes  
- Edit existing notes  
- Delete notes  
- Mark notes as favorites  
- View favorite notes separately  
- Save user settings using SharedPreferences  
- Reminder notification using a background Service  
- RecyclerView for displaying notes  
- Edge-to-edge UI support

Technologies Used
- Kotlin
- Android SDK
- Room Database (SQLite)
- RecyclerView
- SharedPreferences
- Services & Notifications
- XML Layouts

- Project Structure

Activities
- `MainActivity` – Displays all notes and favorite notes  
- `NoteEditorActivity` – Add or edit notes  
- `SettingsActivity` – Save user

Database
- Notes – Data class representing a note
- Favorite – Data class for favorite notes
- NoteDao – Interface for database operations
- AppDatabase – Room database configuration

UI

- activity_main.xml
- Activity_note_editor.xml
- activity_settings.xml
- Row_note.xml

Services

- ReminderService – Displays reminder notification

How to Run

Clone the repository
Open the project in Android Studio
Sync Gradle files
Run the app on an emulator or physical device
