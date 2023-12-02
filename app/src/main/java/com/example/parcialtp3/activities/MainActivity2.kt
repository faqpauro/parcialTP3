package com.example.parcialtp3.activities

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.example.parcialtp3.R
import com.example.parcialtp3.viewmodels.SharedViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity2 : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {

    private lateinit var sharedViewModel: SharedViewModel

    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var navHostFragment2: NavHostFragment
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView2: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var nameUser: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        setAppTheme()

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


        val navigationView: NavigationView = findViewById(R.id.nav_view2)
        val headerView = navigationView.getHeaderView(0)
        val nameUserTextView: TextView = headerView.findViewById(R.id.nombre_usuario)
        val imagePerfil: ImageView = headerView.findViewById(R.id.nav_header_imageView)
        val user = sharedViewModel.getUserData(this)
        if (user != null) {
            nameUserTextView.text = user.username
            if (user.avatar_url != null) {
                Glide.with(this) // Contexto
                    .load(user.avatar_url) // URL de la imagen
                    .circleCrop() // Esta línea hace el recorte circular
                    .into(imagePerfil) // ImageView donde se cargará la imagen
            }
        }
    }

    override fun onResume(){
        if(sharedViewModel.getUserData(this@MainActivity2)?.darkModeSelection != sharedViewModel.getDarkModeState(this@MainActivity2)){
            setAppTheme()
            var user = sharedViewModel.getUserData(this)
            if (user != null) {
                user.darkModeSelection = sharedViewModel.getDarkModeState(this@MainActivity2)
                sharedViewModel.setUserData(this, user)
            }
            recreate()
        }
        super.onResume()
        val navigationView: NavigationView = findViewById(R.id.nav_view2)
        val headerView = navigationView.getHeaderView(0)
        val nameUserTextView: TextView = headerView.findViewById(R.id.nombre_usuario)
        val imagePerfil: ImageView = headerView.findViewById(R.id.nav_header_imageView)
        val user = sharedViewModel.getUserData(this)
        if(user!=null){
            nameUserTextView.text = user.username
            if (user.avatar_url != null) {
                Glide.with(this) // Contexto
                    .load(user.avatar_url) // URL de la imagen
                    .circleCrop() // Esta línea hace el recorte circular
                    .into(imagePerfil) // ImageView donde se cargará la imagen
            }
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
            R.id.cerrar_sesion -> {
                sharedViewModel.setUserData(this, null)
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
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

    private fun setAppTheme() {
        val isDarkMode = sharedViewModel.getDarkModeState(this)
        setTheme(if (isDarkMode) R.style.Base_Theme_ParcialTP3_Dark else R.style.Base_Theme_ParcialTP3)
        if(isDarkMode){sharedViewModel.saveDarkModeState(this, true)} else  {sharedViewModel.saveDarkModeState(this, false)}
    }


}