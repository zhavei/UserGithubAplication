package com.syafei.usergithubaplication.ui.main.darkmode

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.syafei.usergithubaplication.R
import com.syafei.usergithubaplication.databinding.ActivityDarkModeBinding
import kotlinx.coroutines.launch

class DarkModeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDarkModeBinding

    private lateinit var settingDataStore: SettingDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDarkModeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingDataStore = SettingDataStore(this)
        setupAppBar()

        supportActionBar?.hide()

        //region Dark Mode
        observeUIPreferences()
        initViewSwitch()
        //endregion
    }

    //region Dark Mode
    private fun observeUIPreferences() {
        settingDataStore.uIModeFLow.asLiveData().observe(this) { uiMode ->
            setCheckedMode(uiMode)
        }
    }

    private fun setCheckedMode(uiMode: UIMode?) {
        if (uiMode == UIMode.LIGHT) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            binding.switchMain.isChecked = false
        } else if (uiMode == UIMode.DARK) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            binding.switchMain.isChecked = true
        }
    }

    private fun initViewSwitch() {
        binding.switchMain.setOnCheckedChangeListener { _, isChecked ->
            lifecycleScope.launch {
                when (isChecked) {
                    true -> settingDataStore.setDarkMode(UIMode.DARK)
                    false -> settingDataStore.setDarkMode(UIMode.LIGHT)
                }
            }
        }
    }
    //endregion

    private fun setupAppBar() {
        binding.appBarMain.toolbarMain.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.darkmode -> {
                    val intentDark = Intent(this, DarkModeActivity::class.java)
                    startActivity(intentDark)
                    true
                }
                else -> false
            }
        }
    }

}