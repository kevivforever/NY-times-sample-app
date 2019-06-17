package com.vivek.nytimes.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatCheckedTextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.vivek.nytimes.R
import com.vivek.nytimes.data.model.Story

class StoryListAdapter(private val stories: ArrayList<Story>, private val itemClickListener: StoryItemClickListener)
    : RecyclerView.Adapter<StoryListAdapter.StoryItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryItemViewHolder {
        return StoryItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return stories.size
    }

    override fun onBindViewHolder(holder: StoryItemViewHolder, position: Int) {
        holder.bind(stories[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onStoryItemClick(stories[position])
        }
    }

    fun appendData(list: List<Story>) {
            this.stories.clear()
            this.stories.addAll(list)
            notifyDataSetChanged()
    }


    class StoryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val author: TextView = itemView.findViewById(R.id.list_item_author)
        private val title: TextView = itemView.findViewById(R.id.list_item_title)
        private val thumbnail: ImageView = itemView.findViewById(R.id.list_item_story_thumbnail)

        fun bind(story: Story) {
            this.author.text = story.byline
            this.title.text = story.title

            val media = story.multimedia.filter {
                it.format == "Normal"
            }.single()

            Glide
                .with(itemView.context)
                .load(media.url)
                .apply(RequestOptions().centerCrop())
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(thumbnail)

        }
    }
}