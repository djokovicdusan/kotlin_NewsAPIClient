package com.anushka.newsapiclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent

import androidx.activity.ComponentActivity
import com.anushka.newsapiclient.presentation.News2Fragment

class ComposeTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) { // Ensure this is only done once
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, News2Fragment())
                .commit()
        }
    }
}


