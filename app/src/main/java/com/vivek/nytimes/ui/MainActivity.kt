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
import com.vivek.nytimes.TimesApplication
import com.vivek.nytimes.ui.detail.DetailFragment
import com.vivek.nytimes.ui.list.ListFragment
import com.vivek.nytimes.ui.list.ListViewModel
import com.vivek.nytimes.utils.Logger
import com.yummic.di.component.DaggerActivityComponent
import com.yummic.di.module.ActivityModule

import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: ListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        setContentView(com.vivek.nytimes.R.layout.activity_main)
        setSupportActionBar(toolbar)
        showList()
        setupObservers()
    }

    private fun injectDependencies() {
        DaggerActivityComponent
            .builder()
            .applicationComponent((application as TimesApplication).applicationComponent)
            .activityModule(ActivityModule(this))
            .build()
            .inject(this)
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
