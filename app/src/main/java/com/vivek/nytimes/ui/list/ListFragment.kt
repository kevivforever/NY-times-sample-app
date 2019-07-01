package com.vivek.nytimes.ui.list

import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.vivek.nytimes.R
import com.vivek.nytimes.TimesApplication
import com.vivek.nytimes.data.model.Story
import com.vivek.nytimes.ui.detail.DetailFragment
import com.vivek.nytimes.utils.Constants
import com.vivek.nytimes.utils.Logger
import com.yummic.di.component.DaggerFragmentComponent
import com.yummic.di.module.ActivityModule
import com.yummic.di.module.FragmentModule
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject

class ListFragment: Fragment(), StoryItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    companion object {
        const val TAG = "HomeFragment"

        fun newInstance(): ListFragment {
            return ListFragment()
        }
    }

    private lateinit var storyListAdapter: StoryListAdapter

    @Inject
    lateinit var viewModel: ListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        setupObservers()
    }

    private fun injectDependencies() {
        DaggerFragmentComponent
            .builder()
            .applicationComponent((context?.applicationContext as TimesApplication).applicationComponent)
            .fragmentModule(FragmentModule(this))
            .activityModule(ActivityModule(activity as  AppCompatActivity))
            .build()
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storylist_swipe_refresh_layout.apply {
            setOnRefreshListener(this@ListFragment)
        }

        storyListAdapter = StoryListAdapter(ArrayList(), this)

        storylist_recyclerview.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
            adapter = storyListAdapter
        }

    }

    override fun onRefresh() {
        viewModel.getTopStories()
    }

    private fun setupObservers() {
        viewModel.getMessage().observe(this, Observer {
            it.data?.run { showMessage(this) }
        })

        viewModel.getMessageStringId().observe(this, Observer {
            it.data?.run { showMessage(this) }
        })

        viewModel.isStoriesFetching().observe(this, Observer {
            storylist_swipe_refresh_layout.isRefreshing = it
        })

        viewModel.getStories().observe(this, Observer {
            it?.run {
                storyListAdapter.appendData(this)
            }
        })

        viewModel.getSelectedSection().observe(this, Observer {
            viewModel.getTopStories()
        })
    }

    private fun showMessage(@StringRes resId: Int) = showMessage(getString(resId))

    private fun showMessage(message: String) = Toast.makeText(context, message, Toast.LENGTH_SHORT)

    override fun onStoryItemClick(name: Story) {
        viewModel.setSelectedStory(name)
        viewModel.showStorydetail(DetailFragment.TAG)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_science -> {
                viewModel.setSelectedSection(Constants.SECTION_SCIENCE)
                return true
            }
            R.id.action_technology -> {
                viewModel.setSelectedSection(Constants.SECTION_TECHNOLOGY)
                return true
            }
            R.id.action_world -> {
                viewModel.setSelectedSection(Constants.SECTION_WORLD)
                return true
            }
            R.id.action_business -> {
                viewModel.setSelectedSection(Constants.SECTION_BUSINESS)
                return true
            }
            R.id.action_travel -> {
                viewModel.setSelectedSection(Constants.SECTION_TRAVEL)
                return true
            }
            R.id.action_movies -> {
                viewModel.setSelectedSection(Constants.SECTION_MOVIES)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}