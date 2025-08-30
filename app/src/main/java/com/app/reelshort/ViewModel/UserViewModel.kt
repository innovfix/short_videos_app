package com.app.reelshort.ViewModel

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
import com.app.reelshort.Model.RewardHistoryResponse
import com.app.reelshort.Model.SendOtpRequest
import com.app.reelshort.Model.SendOtpResponse
import com.app.reelshort.Model.SeriesListResponse
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
class UserViewModel @Inject constructor(
    val repository: UserRepository,
) : ViewModel() {
    val pref: DPreferences get() = DPreferences(BaseApplication.getInstance())

    private val _homeList = MutableLiveData<ApiResult<ShortsResponse>>()
    val homeList: LiveData<ApiResult<ShortsResponse>> = _homeList

    private val _top10List = MutableLiveData<ApiResult<ShortsResponse>>()
    val top10List: LiveData<ApiResult<ShortsResponse>> = _top10List

    private val _loveAffairsList = MutableLiveData<ApiResult<ShortsResponse>>()
    val loveAffairsList: LiveData<ApiResult<ShortsResponse>> = _loveAffairsList

    private val _specialsList = MutableLiveData<ApiResult<ShortsResponse>>()
    val specialsList: LiveData<ApiResult<ShortsResponse>> = _specialsList

    private val _trendingNowList = MutableLiveData<ApiResult<ShortsResponse>>()
    val trendingNowList: LiveData<ApiResult<ShortsResponse>> = _trendingNowList

    private val _topOriginalsList = MutableLiveData<ApiResult<ShortsResponse>>()
    val topOriginalsList: LiveData<ApiResult<ShortsResponse>> = _topOriginalsList

    private val _top10NewReleasesList = MutableLiveData<ApiResult<ShortsResponse>>()
    val top10NewReleasesList: LiveData<ApiResult<ShortsResponse>> = _top10NewReleasesList

    private val _ceoBillionaireList = MutableLiveData<ApiResult<ShortsResponse>>()
    val ceoBillionaireList: LiveData<ApiResult<ShortsResponse>> = _ceoBillionaireList

    private val _justLaunchedList = MutableLiveData<ApiResult<ShortsResponse>>()
    val justLaunchedList: LiveData<ApiResult<ShortsResponse>> = _justLaunchedList

    private val _hiddenIdentityList = MutableLiveData<ApiResult<ShortsResponse>>()
    val hiddenIdentityList: LiveData<ApiResult<ShortsResponse>> = _hiddenIdentityList

    private val _newHotList = MutableLiveData<ApiResult<ShortsResponse>>()
    val newHotList: LiveData<ApiResult<ShortsResponse>> = _newHotList

    private val _revengeAndDhokaList = MutableLiveData<ApiResult<ShortsResponse>>()
    val revengeAndDhokaList: LiveData<ApiResult<ShortsResponse>> = _revengeAndDhokaList

    private val _allEpisodes = MutableLiveData<ApiResult<EpisodeListResponse>>()
    val allEpisodes: LiveData<ApiResult<EpisodeListResponse>> = _allEpisodes


    private val _myList = MutableLiveData<ApiResult<MyListResponse>>()
    val myList: LiveData<ApiResult<MyListResponse>> = _myList

    private val _planList = MutableLiveData<ApiResult<PlanListResponse>>()
    val planList: LiveData<ApiResult<PlanListResponse>> = _planList


    private val _rewardHistory = MutableLiveData<ApiResult<RewardHistoryResponse>>()
    val rewardHistory: LiveData<ApiResult<RewardHistoryResponse>> = _rewardHistory

    private val _sendOtpResponse = MutableLiveData<SendOtpResponse>()
    val sendOtpResponse: LiveData<SendOtpResponse> = _sendOtpResponse

    private val _sendOtpError = MutableLiveData<ApiResult.Error>()
    val sendOtpError: LiveData<ApiResult.Error> = _sendOtpError

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    private val _loginError = MutableLiveData<ApiResult.Error>()
    val loginError: LiveData<ApiResult.Error> = _loginError

    val deviceId = Settings.Secure.getString(BaseApplication.getInstance().contentResolver, Settings.Secure.ANDROID_ID)

    init {
        getHomeShorts(pref.authToken)
        getTop10(pref.authToken)
        getLoveAffairs(pref.authToken)
        getPlanList(pref.authToken)
        getPlanList(pref.authToken)
        getSpecials( pref.authToken)
        getTrendingNow(pref.authToken)
        getTopOriginals(pref.authToken)
        getTop10NewReleases( pref.authToken)
        getCeoBillionaire( pref.authToken)
        getJustLaunched( pref.authToken)
        getHiddenIdentity( pref.authToken)
        getNewHot( pref.authToken)
        getRevengeAndDhoka( pref.authToken)
    }

    fun getHomeShorts(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = repository.getShorts(authToken, "is_home_shorts")
            if (result is ApiResult.Success) {
                _.value = result.data
            }
        }
    }

    fun getTop10(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _top10List.value = repository.getShorts(authToken, "is_top_10")
        }
    }

    fun getLoveAffairs(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _loveAffairsList.value = repository.getShorts(authToken, "is_love_affairs")
        }
    }

    fun getSpecials(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _specialsList.value = repository.getShorts(authToken, "is_specials")
        }
    }

    fun getTrendingNow(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _trendingNowList.value = repository.getShorts(authToken, "is_trending_now")
        }
    }

    fun getTopOriginals(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _topOriginalsList.value = repository.getShorts(authToken, "is_top_originals")
        }
    }

    fun getTop10NewReleases(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _top10NewReleasesList.value = repository.getShorts(authToken, "is_top_10_new_releases")
        }
    }

    fun getCeoBillionaire(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _ceoBillionaireList.value = repository.getShorts(authToken, "is_ceo_billionaire")
        }
    }

    fun getJustLaunched(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _justLaunchedList.value = repository.getShorts(authToken, "is_just_launched")
        }
    }

    fun getHiddenIdentity(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _hiddenIdentityList.value = repository.getShorts(authToken, "is_hidden_identity")
        }
    }

    fun getNewHot(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _newHotList.value = repository.getShorts(authToken, "is_new_hot")
        }
    }

    fun getRevengeAndDhoka(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _revengeAndDhokaList.value = repository.getShorts(authToken, "is_revenge_and_dhoka")
        }
    }

    fun getPlanList(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _planList.value = repository.getPlanList(authToken)
        }
    }


    fun sendOtp(request: SendOtpRequest) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = repository.sendOtp(request)
            if (result is ApiResult.Success) {
                _sendOtpResponse.value = result.data
            } else if (result is ApiResult.Error) {
                _sendOtpError.value = result
            }
        }
    }

    fun login(request: LoginRequest) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = repository.logIn(request)
            if (result is ApiResult.Success) {
                _loginResponse.value = result.data
            } else if (result is ApiResult.Error) {
                _loginError.value = result
            }
        }
    }
}
