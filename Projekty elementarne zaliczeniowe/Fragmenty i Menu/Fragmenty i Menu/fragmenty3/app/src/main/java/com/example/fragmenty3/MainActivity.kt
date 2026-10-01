package com.example.fragmenty3

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.example.fragmenty3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        showFragments(1)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_2_fragments -> showFragments(2)
            R.id.action_3_fragments -> showFragments(3)
            R.id.action_4_fragments -> showFragments(4)
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun showFragments(fragmentCount: Int) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        for (i in 1..4) {
            val fragment = fragmentManager.findFragmentByTag("fragment$i")
            if (fragment != null) {
                fragmentTransaction.remove(fragment)
            }
        }

        for (i in 1..fragmentCount) {
            when (i) {
                1 -> {

                    if (fragmentCount > 1) {
                        fragmentTransaction.add(R.id.fragment1Container, FirstFragment(), "fragment$i")
                    }
                }
                2 -> fragmentTransaction.add(R.id.fragment2Container, SecondFragment(), "fragment$i")
                3 -> fragmentTransaction.add(R.id.fragment3Container, ThirdFragment(), "fragment$i")
                4 -> fragmentTransaction.add(R.id.fragment4Container, FourthFragment(), "fragment$i")
            }
        }

        fragmentTransaction.commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

}