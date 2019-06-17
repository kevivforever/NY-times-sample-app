package com.vivek.nytimes.data.remote

import com.vivek.nytimes.data.model.Story
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton
import kotlin.text.Typography.section

@Singleton
interface NetworkService {

    @GET("topstories/v2/{section}.json?api-key=xI4AA4gcMj9JyFlyQn2dSAj689PGjKjA")
    fun getTopStories(@Path("section") section: String): MutableList<Story>
}