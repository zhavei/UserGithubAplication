package com.syafei.usergithubaplication.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.syafei.usergithubaplication.databinding.ActivitySplashscreenBinding
import com.syafei.usergithubaplication.ui.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashscreenBinding

    companion object {
        const val TIME_WAITING: Long = 3000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setDelayedMode()
        binding.tvSplash.text = "Github User Aplication"

        Glide.with(this).load(getImage("github_splash")).into(binding.ivSplashscreeen)
    }

    private fun setDelayedMode() {

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, TIME_WAITING)
    }

    private fun getImage(imageName: String): Int {
        return resources.getIdentifier(imageName, "drawable", packageName)
    }

}