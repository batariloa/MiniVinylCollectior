package com.batarilo.vinylcollection.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batarilo.vinylcollection.R
import com.batarilo.vinylcollection.data.retrofit.RecordApiService
import com.batarilo.vinylcollection.data.retrofit.RetrofitInstance
import com.batarilo.vinylcollection.ui.home.recycle.RecordAdapterSearch
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException
import java.io.IOException

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {


    lateinit var  navController:NavController
    lateinit var  drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))




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
                println("HOME CLICKED")
            }
            R.id.nav_my_collection -> {
                Navigation.findNavController(this, R.id.fragment)
                    .navigate(R.id.myCollectionFragment)
                println("CLICKED MY COLLECTION")

            }
            R.id.nav_wishlist -> {
                Navigation.findNavController(this, R.id.fragment)
                    .navigate(R.id.wishlistFragment)
            }


        }
        item.isChecked = true
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }




}



