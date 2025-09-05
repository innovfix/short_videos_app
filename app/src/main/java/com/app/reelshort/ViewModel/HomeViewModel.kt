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
import com.app.reelshort.Model.PlanListResponse
import com.app.reelshort.Model.PremiumPlansResponse
import com.app.reelshort.Model.PremiumPlansUsersResponse
import com.app.reelshort.Model.PremiumPlansVideoResponse
import com.app.reelshort.Model.RewardHistoryResponse
import com.app.reelshort.Model.SendOtpRequest
import com.app.reelshort.Model.SendOtpResponse
import com.app.reelshort.Model.SeriesListResponse
import com.app.reelshort.Model.Shorts
import com.app.reelshort.Model.ShortsResponse
import com.app.reelshort.Model.SighInRequest
import com.app.reelshort.Model.SignUpResponse
import com.app.reelshort.Utils.DPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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

    private val _top10List = MutableLiveData<ShortsResponse>()
    val top10List: LiveData<ShortsResponse> = _top10List

    private val _loveAffairsList = MutableLiveData<ShortsResponse>()
    val loveAffairsList: LiveData<ShortsResponse> = _loveAffairsList

    private val _specialsList = MutableLiveData<ShortsResponse>()
    val specialsList: LiveData<ShortsResponse> = _specialsList

    private val _trendingNowList = MutableLiveData<ShortsResponse>()
    val trendingNowList: LiveData<ShortsResponse> = _trendingNowList

    private val _topOriginalsList = MutableLiveData<ShortsResponse>()
    val topOriginalsList: LiveData<ShortsResponse> = _topOriginalsList

    private val _top10NewReleasesList = MutableLiveData<ShortsResponse>()
    val top10NewReleasesList: LiveData<ShortsResponse> = _top10NewReleasesList

    private val _ceoBillionaireList = MutableLiveData<ShortsResponse>()
    val ceoBillionaireList: LiveData<ShortsResponse> = _ceoBillionaireList

    private val _justLaunchedList = MutableLiveData<ShortsResponse>()
    val justLaunchedList: LiveData<ShortsResponse> = _justLaunchedList

    private val _hiddenIdentityList = MutableLiveData<ShortsResponse>()
    val hiddenIdentityList: LiveData<ShortsResponse> = _hiddenIdentityList

    private val _newHotList = MutableLiveData<ShortsResponse>()
    val newHotList: LiveData<ShortsResponse> = _newHotList

    private val _revengeAndDhokaList = MutableLiveData<ShortsResponse>()
    val revengeAndDhokaList: LiveData<ShortsResponse> = _revengeAndDhokaList

    private val _allEpisodes = MutableLiveData<ApiResult<EpisodeListResponse>>()
    val allEpisodes: LiveData<ApiResult<EpisodeListResponse>> = _allEpisodes


    private val _myList = MutableLiveData<ApiResult<MyListResponse>>()
    val myList: LiveData<ApiResult<MyListResponse>> = _myList

    private val _planList = MutableLiveData<ApiResult<PlanListResponse>>()
    val planList: LiveData<ApiResult<PlanListResponse>> = _planList


    init {
        getHomeShorts(pref.authToken)
        getTop10(pref.authToken)
        getLoveAffairs(pref.authToken)
        getPlanList(pref.authToken)
        getPlanList(pref.authToken)
        getSpecials(pref.authToken)
        getTrendingNow(pref.authToken)
        getTopOriginals(pref.authToken)
        getTop10NewReleases(pref.authToken)
        getCeoBillionaire(pref.authToken)
        getJustLaunched(pref.authToken)
        getHiddenIdentity(pref.authToken)
        getNewHot(pref.authToken)
        getRevengeAndDhoka(pref.authToken)
    }

    fun getHomeShorts(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = repository.getShorts(authToken, "is_home_shorts")
            if (result is ApiResult.Success) {
                var shorts = result.data
                _homeList.value = shorts
            }
        }
    }

    fun getTop10(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = repository.getShorts(authToken, "is_top_10")
            if (result is ApiResult.Success) {
                val shorts = result.data
                _top10List.value = shorts
            }
        }
    }

    fun getLoveAffairs(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = repository.getShorts(authToken, "is_love_affairs")
            if (result is ApiResult.Success) {
                val shorts = result.data
                _loveAffairsList.value = shorts
            }
        }
    }

    fun getSpecials(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = repository.getShorts(authToken, "is_specials")
            if (result is ApiResult.Success) {
                val shorts = result.data
                _specialsList.value = shorts
            }
        }
    }

    fun getTrendingNow(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = repository.getShorts(authToken, "is_trending_now")
            if (result is ApiResult.Success) {
                val shorts = result.data
                _trendingNowList.value = shorts
            }
        }
    }

    fun getTopOriginals(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = repository.getShorts(authToken, "is_top_originals")
            if (result is ApiResult.Success) {
                val shorts = result.data
                _topOriginalsList.value = shorts
            }
        }
    }

    fun getTop10NewReleases(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = repository.getShorts(authToken, "is_top_10_new_releases")
            if (result is ApiResult.Success) {
                val shorts = result.data
                _top10NewReleasesList.value = shorts
            }
        }
    }

    fun getCeoBillionaire(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = repository.getShorts(authToken, "is_ceo_billionaire")
            if (result is ApiResult.Success) {
                val shorts = result.data
                _ceoBillionaireList.value = shorts
            }
        }
    }

    fun getJustLaunched(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = repository.getShorts(authToken, "is_just_launched")
            if (result is ApiResult.Success) {
                val shorts = result.data
                _justLaunchedList.value = shorts
            }
        }
    }

    fun getHiddenIdentity(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = repository.getShorts(authToken, "is_hidden_identity")
            if (result is ApiResult.Success) {
                val shorts = result.data
                _hiddenIdentityList.value = shorts
            }
        }
    }

    fun getNewHot(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = repository.getShorts(authToken, "is_new_hot")
            if (result is ApiResult.Success) {
                val shorts = result.data
                _newHotList.value = shorts
            }
        }
    }

    fun getRevengeAndDhoka(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = repository.getShorts(authToken, "is_revenge_and_dhoka")
            if (result is ApiResult.Success) {
                val shorts = result.data
                _revengeAndDhokaList.value = shorts
            }
        }
    }

    fun getPlanList(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _planList.value = repository.getPlanList(authToken)
        }
    }
}
