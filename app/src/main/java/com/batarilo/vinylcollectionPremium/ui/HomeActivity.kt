package com.batarilo.vinylcollectionPremium.ui

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
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
import androidx.preference.PreferenceManager
import com.android.billingclient.api.*
import com.android.billingclient.api.BillingClient.ProductType.INAPP
import com.batarilo.vinylcollectionPremium.R
import com.batarilo.vinylcollectionPremium.util.ConnectivityManager
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, PurchasesUpdatedListener  {

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
    private var billingClient: BillingClient? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        setContentView(R.layout.activity_main)

        billingClient = BillingClient.newBuilder(this)
            .enablePendingPurchases().setListener(this).build()

        toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView= findViewById(R.id.nav_view)


        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_folder_open_24)



        initNavigation()
        ratingCheck()

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

        navController.addOnDestinationChangedListener { _, destination, _ ->
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
            R.id.nav_coffee->{
                openDialogCoffee()
            }
        }
        item.isChecked = true
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

     fun setTitleTop(s: String) {
        findViewById<TextView>(R.id.toolbar_title).text = s
    }

    private fun openDialog(){

        AlertDialog.Builder(this)
            .setTitle("Thank you!")
            .setMessage("If Vinco is useful to you, would you consider rating it 5 stars?") // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setPositiveButton("Yes, Rate 5 stars") { _, _ ->
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
                } catch (e: ActivityNotFoundException) {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName")))
                }
            } // A null listener allows the button to dismiss the dialog and take no further action.
            .setNegativeButton("Never", null)
            .setIcon(R.drawable.ic_heart_svgrepo_com)
            .show()
    }
    private fun ratingCheck(){
        var count = PreferenceManager.getDefaultSharedPreferences(applicationContext).getInt("countApp", 0)

        if(count == 6) {
            openDialog()
        }
        if((count+1)%10==0){
            openDialogCoffee()
        }
        val sp = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val editor = sp.edit()
        editor.putInt("countApp", count+1)
        editor.commit()
    }




    private fun openDialogCoffee(){

        AlertDialog.Builder(this)
            .setTitle("Enjoying my work?")
            .setMessage("Would you buy me a coffee to support my work?") // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setPositiveButton("Yes, enjoy.") { _, _ ->
                purchase()
            } // A null listener allows the button to dismiss the dialog and take no further action.
            .setNegativeButton("No", null)
            .setIcon(R.drawable.ic_coffee_cup_tea_svgrepo_com__1_)
            .show()
    }


    override fun onPurchasesUpdated(billingResult: BillingResult, purchases: List<Purchase>?) {
        //if item newly purchased
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            Toast.makeText(this, "Thank you very much!", Toast.LENGTH_LONG).show()
        }
    }

    private fun purchase() {
        //check if service is already connected

        if (billingClient!!.isReady) {
            initiatePurchase()
        }
        //else reconnect service
        else {
            billingClient = BillingClient.newBuilder(this).enablePendingPurchases().setListener(this).build()
            billingClient!!.startConnection(object : BillingClientStateListener {
                override fun onBillingSetupFinished(billingResult: BillingResult) {
                    if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                        initiatePurchase()
                    } else {
                        Toast.makeText(applicationContext, "Error " + billingResult.debugMessage, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onBillingServiceDisconnected() {}
            })
        }
    }
    private fun initiatePurchase() {

        val skuList: MutableList<String> = ArrayList()
        skuList.add("coffee")
        val params = SkuDetailsParams.newBuilder()
        params.setSkusList(skuList).setType(INAPP)

        billingClient!!.querySkuDetailsAsync(params.build())
        { billingResult, skuDetailsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                if (skuDetailsList != null && skuDetailsList.size > 0) {
                    val flowParams = BillingFlowParams.newBuilder()
                        .setSkuDetails(skuDetailsList[0])
                        .build()
                    billingClient!!.launchBillingFlow(this@HomeActivity, flowParams)
                } else {
                    //try to add item/product id "purchase" inside managed product in google play console

                    Toast.makeText(applicationContext, "Purchase Item not Found", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(applicationContext,
                    " Error " + billingResult.debugMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

}



