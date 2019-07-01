package com.vivek.nytimes.ui.list

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.*
import com.vivek.nytimes.R
import com.vivek.nytimes.data.local.DatabaseService
import com.vivek.nytimes.data.model.Story
import com.vivek.nytimes.data.remote.Networking
import com.vivek.nytimes.data.repo.StoryRepository
import com.vivek.nytimes.utils.Constants
import com.vivek.nytimes.utils.Logger
import com.vivek.nytimes.utils.Resource
import com.vivek.nytimes.utils.Status
import com.vivek.nytimes.utils.network.NetworkHelper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.net.ssl.HttpsURLConnection

class ListViewModel(private val networkHelper: NetworkHelper,
                    private val storyRepository: StoryRepository,
                    private val compositeDisposable: CompositeDisposable) : ViewModel() {

    private val messageStringIdLiveData: MutableLiveData<Resource<Int>> = MutableLiveData()
    private val messageLiveData: MutableLiveData<Resource<String>> = MutableLiveData()
    private val storiesLiveData: MutableLiveData<Resource<List<Story>>> = MutableLiveData()
    private val selectedStoryLiveData: MutableLiveData<Story> = MutableLiveData()
    private val selectedSectionLiveData: MutableLiveData<String> = MutableLiveData()
    val navigation: MutableLiveData<String> = MutableLiveData()
    val categoriesCountLiveData: MutableLiveData<Int> = MutableLiveData()

    init {
        selectedSectionLiveData.value = Constants.SECTION_SCIENCE
    }

    fun getMessageStringId(): LiveData<Resource<Int>> = messageStringIdLiveData

    fun getMessage(): LiveData<Resource<String>> = messageLiveData

    fun getStories(): LiveData<List<Story>> =
        Transformations.map(storiesLiveData) { it.data }

    fun isStoriesFetching(): LiveData<Boolean> =
        Transformations.map(storiesLiveData) { it.status == Status.LOADING }

    fun getSelectedSection(): LiveData<String> = selectedSectionLiveData

    fun getSelectedStory(): LiveData<Story> = selectedStoryLiveData

    fun setSelectedStory(story: Story) {
        selectedStoryLiveData.value = story
    }

    fun setSelectedSection(section: String) {
        selectedSectionLiveData.value = section
    }

    private fun checkInternetConnectionWithMessage(): Boolean =
        if (networkHelper.isNetworkConnected()) {
            true
        } else {
            messageStringIdLiveData.postValue(Resource.error(R.string.network_connection_error))
            false
        }

    private fun handleNetworkError(err: Throwable?) =
        err?.let {
            networkHelper.castToNetworkError(it).run {
                when (status) {
                    -1 -> messageStringIdLiveData.postValue(Resource.error(R.string.network_default_error))
                    0 -> messageStringIdLiveData.postValue(Resource.error(R.string.server_connection_error))
                    HttpsURLConnection.HTTP_UNAUTHORIZED -> {
                        messageStringIdLiveData.postValue(Resource.error(R.string.server_connection_error))
                    }
                    HttpsURLConnection.HTTP_INTERNAL_ERROR ->
                        messageStringIdLiveData.postValue(Resource.error(R.string.network_internal_error))
                    HttpsURLConnection.HTTP_UNAVAILABLE ->
                        messageStringIdLiveData.postValue(Resource.error(R.string.network_server_not_available))
                    else -> messageLiveData.postValue(Resource.error(message))
                }
            }
        }

    fun getTopStories() {
        if (checkInternetConnectionWithMessage()) {
            storiesLiveData.postValue(Resource.loading())
            compositeDisposable.add(
                storyRepository.getTopStories(selectedSectionLiveData.value!!)
                    .flatMap {
                        storyRepository.saveStories(it)
                        return@flatMap Observable.just(it)
                    }
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            storiesLiveData.postValue(Resource.success(it))
                        },
                        {
                            handleNetworkError(it)
                            storiesLiveData.postValue(Resource.error())
                        }
                    )
            )
        }
    }

    fun onViewCreated() {
        compositeDisposable.add(
            storyRepository.totalstories()
            .subscribeOn(Schedulers.io())
            .subscribe( {
                categoriesCountLiveData.value = it
            }, {

            })
        )
    }

    fun showStorydetail(tag : String) {
        navigation.value = tag
    }

}