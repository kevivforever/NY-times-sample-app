package com.vivek.nytimes.data.remote

import com.vivek.nytimes.data.model.Story
import com.vivek.nytimes.data.remote.Response.TopStoryResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton
import kotlin.text.Typography.section

@Singleton
interface NetworkService {

    @GET("topstories/v2/{section}.json")
    fun getTopStories(@Path("section") section: String): Observable<TopStoryResponse>

}