package com.batarilo.vinylcollectionPremium.ui

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.batarilo.vinylcollectionPremium.R
import com.batarilo.vinylcollectionPremium.util.ConnectivityManager
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

    private lateinit var navController:NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var actionbar: ActionBarDrawerToggle
    private lateinit var toolbar :androidx.appcompat.widget.Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        setContentView(R.layout.activity_main)

        toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView= findViewById(R.id.nav_view)


        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_folder_open_24)



        initNavigation()


    }

    override fun onResume() {
        super.onResume()
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

        appBarConfiguration = AppBarConfiguration.Builder(
            navController.graph
        ).setDrawerLayout(drawerLayout)
            .build()



        setupActionBarWithNavController(navController,appBarConfiguration)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when(destination.id){
               R.id.searchFragment->  { supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_icon)
               setTitleTop("Search online")}
                R.id.historyFragment-> setTitleTop("History")
                R.id.wishlistFragment -> setTitleTop("Wishlist")
                R.id.myCollectionFragment -> setTitleTop("My Collection")
                R.id.settingsFragment-> setTitleTop("Settings")
                R.id.infoFragment-> setTitleTop("Details")

            }

        }

    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_home -> {
                Navigation.findNavController(this, R.id.fragment)
                    .navigate(R.id.searchFragment)
            }
            R.id.nav_my_collection -> {
                Navigation.findNavController(this, R.id.fragment)
                    .navigate(R.id.myCollectionFragment)

            }
            R.id.nav_wishlist -> {
                Navigation.findNavController(this, R.id.fragment)
                    .navigate(R.id.wishlistFragment)


            }
            R.id.nav_history -> {
                Navigation.findNavController(this, R.id.fragment)
                    .navigate(R.id.historyFragment)
            }
            R.id.nav_settings->{
                Navigation.findNavController(this, R.id.fragment)
                    .navigate(R.id.settingsFragment)

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



