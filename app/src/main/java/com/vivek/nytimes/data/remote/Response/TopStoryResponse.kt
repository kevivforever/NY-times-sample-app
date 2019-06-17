package com.vivek.nytimes.data.remote.Response

import com.google.gson.annotations.SerializedName
import com.vivek.nytimes.data.model.Story

data class TopStoryResponse(
    @SerializedName("copyright")
    val copyright: String,
    @SerializedName("last_updated")
    val lastUpdated: String,
    @SerializedName("num_results")
    val numResults: Int,
    @SerializedName("results")
    val results: List<Story>,
    @SerializedName("section")
    val section: String,
    @SerializedName("status")
    val status: String
)