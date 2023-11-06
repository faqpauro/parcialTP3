package com.example.parcialtp3.activities

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.parcialtp3.R
import com.example.parcialtp3.viewmodels.SharedViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity2 : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {

    private val sharedViewModel: SharedViewModel by viewModels()

    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var navHostFragment2: NavHostFragment
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView2: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        drawerLayout = findViewById(R.id.drawer_layout)

        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        navHostFragment2 = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment

        bottomNavView = findViewById(R.id.nav_view)
        navigationView2 = findViewById(R.id.nav_view2)


        val navController = navHostFragment2.navController
        NavigationUI.setupWithNavController(bottomNavView, navController)

        navigationView2.setNavigationItemSelectedListener(this)



        val isDarkMode = sharedViewModel.getDarkModeState(this)
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }


    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.perfil -> {
                val intent = Intent(this, PerfilActivity::class.java)
                startActivity(intent)
            }
            R.id.configuracion -> {
                val intent = Intent(this, ConfiguracionActivity::class.java)
                startActivity(intent)
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}