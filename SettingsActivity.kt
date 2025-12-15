/*
name : Jennyfer Parmar
Student Id: A00201240
date 12/12/25

Description:
This activity saves user settings using SharedPreferences (example: username).
*/
package com.example.project_3_jennyferparmar

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enables edge-to-edge content
        enableEdgeToEdge()

        // Loads the settings layout
        setContentView(R.layout.activity_settings)

        // Applies padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etUsername = findViewById<EditText>(R.id.etUsername)

        // Read saved preferences
        val sp = getSharedPreferences(Prefs.FILE, MODE_PRIVATE)
        etUsername.setText(sp.getString(Prefs.KEY_USERNAME, ""))

        // Save preferences when button clicked
        findViewById<View>(R.id.btnSaveSettings).setOnClickListener {
            sp.edit()
                .putString(Prefs.KEY_USERNAME, etUsername.text.toString().trim())
                .apply()

            finish() // Return to main screen
        }
    }
}
