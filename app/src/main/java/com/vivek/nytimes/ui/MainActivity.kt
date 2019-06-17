package com.vivek.nytimes.ui

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.vivek.nytimes.R
import com.vivek.nytimes.ui.detail.DetailFragment
import com.vivek.nytimes.ui.list.ListFragment
import com.vivek.nytimes.ui.list.ListViewModel
import com.vivek.nytimes.utils.Logger

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.vivek.nytimes.R.layout.activity_main)
        setSupportActionBar(toolbar)
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        showHome()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.navigation.observe(this, Observer {
            it?.let {
                if(it == DetailFragment.TAG) {
                    showDetail()
                }
            }
        })
    }

    private fun showHome() {

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val fragment = ListFragment.newInstance()
        fragmentTransaction.replace(R.id.containerFragment, fragment)
        fragmentTransaction.commit()
    }

    private fun showDetail() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val fragment = DetailFragment.newInstance()
        fragmentTransaction.replace(R.id.containerFragment, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
        } else {
            super.onBackPressed()
        }
    }
}
