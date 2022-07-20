package com.syafei.usergithubaplication.ui.main.darkmode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.syafei.usergithubaplication.R
import com.syafei.usergithubaplication.ui.main.MainActivity

class DarkModeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dark_mode)

        if (supportActionBar != null) {
            (supportActionBar)?.title = "Change Theme"
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}