package com.vivek.nytimes.ui

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
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
    private var activeFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.vivek.nytimes.R.layout.activity_main)
        setSupportActionBar(toolbar)
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        showList()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.navigation.observe(this, Observer {
            it?.let {
                if(it == DetailFragment.TAG) {
                    showStoryDetail()
                }
            }
        })
    }

    private fun showList() {

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        var fragment = supportFragmentManager.findFragmentByTag(ListFragment.TAG) as ListFragment?

        if (fragment == null) {
            fragment = ListFragment.newInstance()
            fragmentTransaction.add(R.id.containerFragment, fragment, ListFragment.TAG)
            fragmentTransaction.addToBackStack(ListFragment.TAG)
        } else {
            fragmentTransaction.show(fragment)
        }

        fragmentTransaction.commit()

    }

    private fun showStoryDetail() {

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        var fragment = supportFragmentManager.findFragmentByTag(DetailFragment.TAG) as DetailFragment?
        val listFragment = supportFragmentManager.findFragmentByTag(ListFragment.TAG) as ListFragment?

        if (fragment == null) {
            fragment = DetailFragment.newInstance()
            fragmentTransaction.add(R.id.containerFragment, fragment, DetailFragment.TAG)
            fragmentTransaction.addToBackStack(DetailFragment.TAG)
        } else {
            fragmentTransaction.show(fragment)
        }
        listFragment?.let { fragmentTransaction.hide(it) }

        fragmentTransaction.commit()

    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStackImmediate()
        } else {
            super.onBackPressed()
        }
    }
}
