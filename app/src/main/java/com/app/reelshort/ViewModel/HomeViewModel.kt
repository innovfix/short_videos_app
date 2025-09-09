package com.app.reelshort.ViewModel

import android.annotation.SuppressLint
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.Keep
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.reelshort.App.BaseApplication
import com.app.reelshort.Model.CoinDataResponse
import com.app.reelshort.Model.EpisodeListResponse
import com.app.reelshort.Model.EpisodeRequest
import com.app.reelshort.Model.LoginRequest
import com.app.reelshort.Model.LoginResponse
import com.app.reelshort.Model.MyListResponse
import com.app.reelshort.Model.PlanListResponse
import com.app.reelshort.Model.PremiumPlansResponse
import com.app.reelshort.Model.PremiumPlansUsersResponse
import com.app.reelshort.Model.PremiumPlansVideoResponse
import com.app.reelshort.Model.RewardHistoryResponse
import com.app.reelshort.Model.SavedStatusResponse
import com.app.reelshort.Model.SendOtpRequest
import com.app.reelshort.Model.SendOtpResponse
import com.app.reelshort.Model.SeriesListResponse
import com.app.reelshort.Model.Shorts
import com.app.reelshort.Model.ShortsResponse
import com.app.reelshort.Model.SighInRequest
import com.app.reelshort.Model.SignUpResponse
import com.app.reelshort.Utils.DPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@Keep
@HiltViewModel
class HomeViewModel @Inject constructor(
    val repository: UserRepository,
) : ViewModel() {
    val pref: DPreferences get() = DPreferences(BaseApplication.getInstance())

    private val _homeList = MutableLiveData<ShortsResponse>()
    val homeList: LiveData<ShortsResponse> = _homeList

    private val _homeListError = MutableLiveData<ApiResult.Error>()
    val homeListError: LiveData<ApiResult.Error> = _homeListError

    private val _top10List = MutableLiveData<ShortsResponse>()
    val top10List: LiveData<ShortsResponse> = _top10List

    private val _top10ListError = MutableLiveData<ApiResult.Error>()
    val top10ListError: LiveData<ApiResult.Error> = _top10ListError

    private val _loveAffairsList = MutableLiveData<ShortsResponse>()
    val loveAffairsList: LiveData<ShortsResponse> = _loveAffairsList

    private val _loveAffairsListError = MutableLiveData<ApiResult.Error>()
    val loveAffairsListError: LiveData<ApiResult.Error> = _loveAffairsListError

    private val _specialsList = MutableLiveData<ShortsResponse>()
    val specialsList: LiveData<ShortsResponse> = _specialsList

    private val _specialsListError = MutableLiveData<ApiResult.Error>()
    val specialsListError: LiveData<ApiResult.Error> = _specialsListError

    private val _trendingNowList = MutableLiveData<ShortsResponse>()
    val trendingNowList: LiveData<ShortsResponse> = _trendingNowList

    private val _trendingNowListError = MutableLiveData<ApiResult.Error>()
    val trendingNowListError: LiveData<ApiResult.Error> = _trendingNowListError

    private val _topOriginalsList = MutableLiveData<ShortsResponse>()
    val topOriginalsList: LiveData<ShortsResponse> = _topOriginalsList

    private val _topOriginalsListError = MutableLiveData<ApiResult.Error>()
    val topOriginalsListError: LiveData<ApiResult.Error> = _topOriginalsListError

    private val _top10NewReleasesList = MutableLiveData<ShortsResponse>()
    val top10NewReleasesList: LiveData<ShortsResponse> = _top10NewReleasesList

    private val _top10NewReleasesListError = MutableLiveData<ApiResult.Error>()
    val top10NewReleasesListError: LiveData<ApiResult.Error> = _top10NewReleasesListError

    private val _ceoBillionaireList = MutableLiveData<ShortsResponse>()
    val ceoBillionaireList: LiveData<ShortsResponse> = _ceoBillionaireList

    private val _ceoBillionaireListError = MutableLiveData<ApiResult.Error>()
    val ceoBillionaireListError: LiveData<ApiResult.Error> = _ceoBillionaireListError

    private val _justLaunchedList = MutableLiveData<ShortsResponse>()
    val justLaunchedList: LiveData<ShortsResponse> = _justLaunchedList

    private val _justLaunchedListError = MutableLiveData<ApiResult.Error>()
    val justLaunchedListError: LiveData<ApiResult.Error> = _justLaunchedListError

    private val _hiddenIdentityList = MutableLiveData<ShortsResponse>()
    val hiddenIdentityList: LiveData<ShortsResponse> = _hiddenIdentityList

    private val _hiddenIdentityListError = MutableLiveData<ApiResult.Error>()
    val hiddenIdentityListError: LiveData<ApiResult.Error> = _hiddenIdentityListError

    private val _newHotList = MutableLiveData<ShortsResponse>()
    val newHotList: LiveData<ShortsResponse> = _newHotList

    private val _newHotListError = MutableLiveData<ApiResult.Error>()
    val newHotListError: LiveData<ApiResult.Error> = _newHotListError

    private val _revengeAndDhokaList = MutableLiveData<ShortsResponse>()
    val revengeAndDhokaList: LiveData<ShortsResponse> = _revengeAndDhokaList

    private val _revengeAndDhokaListError = MutableLiveData<ApiResult.Error>()
    val revengeAndDhokaListError: LiveData<ApiResult.Error> = _revengeAndDhokaListError

    private val _allEpisodes = MutableLiveData<ShortsResponse>()
    val allEpisodes: LiveData<ShortsResponse> = _allEpisodes

    private val _allEpisodesError = MutableLiveData<ApiResult<ApiResult.Error>>()
    val allEpisodesError: LiveData<ApiResult<ApiResult.Error>> = _allEpisodesError


    private val _myList = MutableLiveData<ApiResult<MyListResponse>>()
    val myList: LiveData<ApiResult<MyListResponse>> = _myList

    private val _planList = MutableLiveData<ApiResult<PlanListResponse>>()
    val planList: LiveData<ApiResult<PlanListResponse>> = _planList

    private val _savedStatus = MutableLiveData<SavedStatusResponse>()
    val savedStatus: LiveData<SavedStatusResponse> = _savedStatus


    fun initialise() {
        getHomeShorts(pref.authToken)
        getTop10(pref.authToken)
        getLoveAffairs(pref.authToken)
        getSpecials(pref.authToken)
        getTrendingNow(pref.authToken)
        getTopOriginals(pref.authToken)
        getTop10NewReleases(pref.authToken)
        getCeoBillionaire(pref.authToken)
        getJustLaunched(pref.authToken)
        getHiddenIdentity(pref.authToken)
        getNewHot(pref.authToken)
        getRevengeAndDhoka(pref.authToken)
        getAllShorts(pref.authToken)
    }

    fun getHomeShorts(authToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getShorts(authToken, "is_home_shorts")
            if (result is ApiResult.Success) {
                var shorts = result.data
                _homeList.postValue(shorts)
            } else if (result is ApiResult.Error) {
                _homeListError.postValue(result)
            }
        }
    }

    fun getTop10(authToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getShorts(authToken, "is_top_10")
            if (result is ApiResult.Success) {
                val shorts = result.data
                _top10List.postValue(shorts)
            } else if (result is ApiResult.Error) {
                _top10ListError.postValue(result)
            }
        }
    }

    fun getLoveAffairs(authToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getShorts(authToken, "is_love_affairs")
            if (result is ApiResult.Success) {
                val shorts = result.data
                _loveAffairsList.postValue(shorts)
            } else if (result is ApiResult.Error) {
                _loveAffairsListError.postValue(result)
            }
        }
    }

    fun getSpecials(authToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getShorts(authToken, "is_specials")
            if (result is ApiResult.Success) {
                val shorts = result.data
                _specialsList.postValue(shorts)
            } else if (result is ApiResult.Error) {
                _specialsListError.postValue(result)
            }
        }
    }

    fun getTrendingNow(authToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getShorts(authToken, "is_trending_now")
            if (result is ApiResult.Success) {
                val shorts = result.data
                _trendingNowList.postValue(shorts)
            } else if (result is ApiResult.Error) {
                _trendingNowListError.postValue(result)
            }
        }
    }

    fun getTopOriginals(authToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getShorts(authToken, "is_top_originals")
            if (result is ApiResult.Success) {
                val shorts = result.data
                _topOriginalsList.postValue(shorts)
            } else if (result is ApiResult.Error) {
                _topOriginalsListError.postValue(result)
            }
        }
    }

    fun getTop10NewReleases(authToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getShorts(authToken, "is_top_10_new_releases")
            if (result is ApiResult.Success) {
                val shorts = result.data
                _top10NewReleasesList.postValue(shorts)
            } else if (result is ApiResult.Error) {
                _top10NewReleasesListError.postValue(result)
            }
        }
    }

    fun getCeoBillionaire(authToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getShorts(authToken, "is_ceo_billionaire")
            if (result is ApiResult.Success) {
                val shorts = result.data
                _ceoBillionaireList.postValue(shorts)
            } else if (result is ApiResult.Error) {
                _ceoBillionaireListError.postValue(result)
            }
        }
    }

    fun getJustLaunched(authToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getShorts(authToken, "is_just_launched")
            if (result is ApiResult.Success) {
                val shorts = result.data
                _justLaunchedList.postValue(shorts)
            } else if (result is ApiResult.Error) {
                _justLaunchedListError.postValue(result)
            }
        }
    }

    fun getHiddenIdentity(authToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getShorts(authToken, "is_hidden_identity")
            if (result is ApiResult.Success) {
                val shorts = result.data
                _hiddenIdentityList.postValue(shorts)
            } else if (result is ApiResult.Error) {
                _hiddenIdentityListError.postValue(result)
            }
        }
    }

    fun getNewHot(authToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getShorts(authToken, "is_new_hot")
            if (result is ApiResult.Success) {
                val shorts = result.data
                _newHotList.postValue(shorts)
            } else if (result is ApiResult.Error) {
                _newHotListError.postValue(result)
            }
        }
    }

    fun getRevengeAndDhoka(authToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getShorts(authToken, "is_revenge_and_dhoka")
            if (result is ApiResult.Success) {
                val shorts = result.data
                _revengeAndDhokaList.postValue(shorts)
            } else if (result is ApiResult.Error) {
                _revengeAndDhokaListError.postValue(result)
            }
        }
    }

    fun getAllShorts(authToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getAllShorts(authToken)
            if (result is ApiResult.Success) {
                val shorts = result.data
                _allEpisodes.postValue(shorts)
            } else if (result is ApiResult.Error) {
                _allEpisodesError.postValue(result)
            }
        }
    }

    fun getPlanList(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _planList.value = repository.getPlanList(authToken)
        }
    }

    fun getSavedStatus(authToken: String, shortId: Int): Deferred<Unit> {
        return viewModelScope.async(Dispatchers.Main) {
            val result = repository.getSavedStatus(authToken, shortId)
            if (result is ApiResult.Success) {
                val status = result.data
                _savedStatus.postValue(status)
            }
        }
    }

    fun saveListStatus(authToken: String, shortId: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            repository.saveListStatus(authToken, shortId)
        }
    }

    fun saveHistory(authToken: String, shortId: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            repository.saveHistory(authToken, shortId)
        }
    }

    fun removeListStatus(authToken: String, shortId: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            repository.removeListStatus(authToken, shortId)
        }
    }

    fun removeHistory(authToken: String, shortId: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            repository.removeHistory(authToken, shortId)
        }
    }
}
