package com.batarilo.vinylcollection.ui

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.batarilo.vinylcollection.R
import com.batarilo.vinylcollection.util.ConnectivityManager
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {

    //Observing network connection in activity
    @Inject
    lateinit var connectivityManager: ConnectivityManager


    override fun onStart() {
        super.onStart()
        connectivityManager.registerConnectionObserver(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregisterConnectionObserver(this)
    }


    lateinit var navController:NavController
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView= findViewById(R.id.nav_view)

            initNavigation()


    }

    override fun onSupportNavigateUp(): Boolean {
        val navController= findNavController(R.id.fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

    }

    private fun initNavigation(){
        navController = Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupActionBarWithNavController(this,navController, drawerLayout)
        NavigationUI.setupWithNavController(navigationView, navController)
        navigationView.setNavigationItemSelectedListener(this)

        appBarConfiguration = AppBarConfiguration(navController.graph,drawerLayout)

        setupActionBarWithNavController(navController,appBarConfiguration)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_home -> {
                Navigation.findNavController(this, R.id.fragment)
                    .navigate(R.id.searchFragment)
                setTitleTop("Home")
            }
            R.id.nav_my_collection -> {
                Navigation.findNavController(this, R.id.fragment)
                    .navigate(R.id.myCollectionFragment)
                setTitleTop("My Collection")

            }
            R.id.nav_wishlist -> {
                Navigation.findNavController(this, R.id.fragment)
                    .navigate(R.id.wishlistFragment)
                setTitleTop("Wishlist")

            }
            R.id.nav_history -> {
                Navigation.findNavController(this, R.id.fragment)
                    .navigate(R.id.historyFragment)
                setTitleTop("History")

            }
            R.id.nav_settings->{
                Navigation.findNavController(this, R.id.fragment)
                    .navigate(R.id.settingsFragment)
                setTitleTop("Settings")

            }
        }
        item.isChecked = true
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

     fun setTitleTop(s: String) {
        findViewById<TextView>(R.id.toolbar_title).text = s
    }





}



