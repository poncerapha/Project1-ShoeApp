package com.udacity.shoestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.udacity.shoestore.R.id
import com.udacity.shoestore.databinding.ActivityMainBinding
import com.udacity.shoestore.fragments.ShoeListFragment
import com.udacity.shoestore.viewmodel.ShoeViewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var viewModel: ShoeViewModel
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        activityMainBinding.toolbar.title = getString(R.string.app_name)

        setSupportActionBar(activityMainBinding.toolbar)
        appBarConfiguration = AppBarConfiguration(findNavController(id.nav_host_fragment).graph)
        NavigationUI.setupActionBarWithNavController(this, findNavController(id.nav_host_fragment), appBarConfiguration)

        viewModel = ViewModelProvider(this).get(ShoeViewModel::class.java)
    }

    fun hideNavigationArrow(){
        activityMainBinding.toolbar.navigationIcon = null
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //Inflate the menu; this adds items to the action bar if it is present.
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val fragment = navHostFragment?.childFragmentManager?.findFragmentById(R.id.nav_host_fragment)
        if (fragment is ShoeListFragment) {
            menuInflater.inflate(R.menu.menu_main, menu)
        }else{
            menu.clear()
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController: NavController = Navigation.findNavController(
            this,
            R.id.nav_host_fragment
        )
        return (NavigationUI.navigateUp(navController, appBarConfiguration)
            || super.onSupportNavigateUp())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        return if (id == R.id.logout) {
            findNavController(R.id.nav_host_fragment).popBackStack()
            findNavController(R.id.nav_host_fragment).navigate(R.id.loginFragment)
            return true
        } else super.onOptionsItemSelected(item)

    }
}
