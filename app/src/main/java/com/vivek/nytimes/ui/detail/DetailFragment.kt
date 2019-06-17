package com.vivek.nytimes.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.vivek.nytimes.data.model.Story
import com.vivek.nytimes.ui.list.ListViewModel
import kotlinx.android.synthetic.main.fragment_detail.*
import android.content.Intent
import android.net.Uri
import android.text.format.DateUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class DetailFragment: Fragment() {

    companion object {
        const val TAG = "DetailFragment"
        fun newInstance(): DetailFragment {
            return DetailFragment()
        }
    }

    private lateinit var viewModel: ListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel = ViewModelProviders.of(activity!!).get(ListViewModel::class.java)
        setupObservers()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.vivek.nytimes.R.layout.fragment_detail, container, false)
    }

    private fun setupObservers() {
        viewModel.getSelectedStory().observe(this, Observer {
            it?.let {
                initializeUI(it)
            }
        })

    }

    private fun initializeUI(story: Story) {
        val media = story.multimedia.filter {
            it.format == "superJumbo"
        }.single()

        context?.let {
            Glide
                .with(it)
                .load(media.url)
                .apply(RequestOptions().centerCrop())
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(detail_story_cover_image)
        }
        detail_title.text = story.title
        detail_author.text = story.byline
        detail_abstract.text = story.abstract
        detail_article_link.setOnClickListener {
            val uri = Uri.parse(story.url)
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
        val simpledateformatyyyyMMdd = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
        simpledateformatyyyyMMdd.timeZone = TimeZone.getTimeZone("IST")
        try {
            val date = simpledateformatyyyyMMdd.parse(story.publishedDate)
            val epoch = date.time
            val timepassed = DateUtils.getRelativeTimeSpanString(epoch, System.currentTimeMillis(),
                DateUtils.SECOND_IN_MILLIS).toString()
            detail_publish_date.text = timepassed
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }
}