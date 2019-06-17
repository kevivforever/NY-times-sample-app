package com.vivek.nytimes.ui.list

import com.vivek.nytimes.data.model.Story

interface StoryItemClickListener {
    fun onStoryItemClick(name: Story)
}