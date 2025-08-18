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
import com.app.reelshort.Model.HomeListResponse
import com.app.reelshort.Model.LoginRequest
import com.app.reelshort.Model.MyListResponse
import com.app.reelshort.Model.PlanListResponse
import com.app.reelshort.Model.RewardHistoryResponse
import com.app.reelshort.Model.SendOtpRequest
import com.app.reelshort.Model.SendOtpResponse
import com.app.reelshort.Model.SeriesListResponse
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
    val isLoggedIn = MutableLiveData<Boolean>()

    val pref: DPreferences get() = DPreferences(BaseApplication.getInstance())

    private val _homeList = MutableLiveData<ApiResult<HomeListResponse>>()
    val homeList: LiveData<ApiResult<HomeListResponse>> = _homeList


    private val _allEpisodes = MutableLiveData<ApiResult<EpisodeListResponse>>()
    val allEpisodes: LiveData<ApiResult<EpisodeListResponse>> = _allEpisodes


    private val _myList = MutableLiveData<ApiResult<MyListResponse>>()
    val myList: LiveData<ApiResult<MyListResponse>> = _myList

    private val _planList = MutableLiveData<ApiResult<PlanListResponse>>()
    val planList: LiveData<ApiResult<PlanListResponse>> = _planList


    private val _seriesList = MutableLiveData<ApiResult<SeriesListResponse>>()
    val seriesList: LiveData<ApiResult<SeriesListResponse>> = _seriesList

    private val _coinData = MutableLiveData<ApiResult<CoinDataResponse>>()
    val coinData: LiveData<ApiResult<CoinDataResponse>> = _coinData

    private val _rewardHistory = MutableLiveData<ApiResult<RewardHistoryResponse>>()
    val rewardHistory: LiveData<ApiResult<RewardHistoryResponse>> = _rewardHistory

    private val _signUp = MutableLiveData<SignUpResponse.ResponseDetails>()
    val signUp: LiveData<SignUpResponse.ResponseDetails> = _signUp

    private val _sendOtpResponse = MutableLiveData<SendOtpResponse>()
    val sendOtpResponse: LiveData<SendOtpResponse> = _sendOtpResponse

    private val _sendOtpError = MutableLiveData<ApiResult.Error>()
    val sendOtpError: LiveData<ApiResult.Error> = _sendOtpError

    val deviceId = Settings.Secure.getString(BaseApplication.getInstance().contentResolver, Settings.Secure.ANDROID_ID)

    val random = (10000..99999).random().toString()

//    val loginRequest = SighInRequest(
//        email = pref.email.ifBlank { com.app.reelshort.BuildConfig.HOST_EMAIL },
//        loginType = pref.loginType.ifBlank { com.app.reelshort.BuildConfig.HOST_LOGIN_TYPE_GUEST },
//        loginTypeId = pref.loginTypeId.ifBlank { random },
//        name = pref.name.ifBlank { com.app.reelshort.BuildConfig.HOST_DISPLAY_NAME },
//        deviceId = deviceId
//    )
//
//    init {
//        getHomeList(pref.authToken)
//        fetchUsers(pref.authToken)
//        getMyList(pref.authToken)
//        getPlanList(pref.authToken)
//        getPlanList(pref.authToken)
////        getSeriesList(1, pref.authToken)
//        getCoinData(pref.authToken)
//        getRewardHistory(pref.authToken)
////        login(loginRequest, pref.authToken)
//    }

    fun loadEpisodes(episodeId: Int, authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _allEpisodes.value = repository.getAllEpisodeList(EpisodeRequest(episodeId), authToken)
        }
    }


    fun fetchUsers(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _homeList.value = repository.getHomeList(authToken)
        }
    }

    fun getMyList(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _myList.value = repository.getMyList(authToken)
        }
    }


    fun getHomeList(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _homeList.value = repository.getHomeList(authToken)
        }
    }

    fun getPlanList(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _planList.value = repository.getPlanList(authToken)
        }
    }


    fun getSeriesList(random: Int = 1, authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _seriesList.value = repository.getSeriesList(random, authToken)
        }
    }

    fun getCoinData(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _coinData.value = repository.getCoinData(authToken)
        }
    }

    fun getCoinData(authToken: String, callback: (first: CoinDataResponse.ResponseDetail) -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = repository.getCoinData(authToken)
            if (result is ApiResult.Success) {
                result.data.responseDetails?.let { coinData ->
                    callback(coinData.filterNotNull().first())
                }
            } else if (result is ApiResult.Error) {
            }
        }
    }


    fun getRewardHistory(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _rewardHistory.value = repository.getRewardHistory(authToken)
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

    var counter = 0
    fun login(loginRequest: SighInRequest, authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = repository.signUp(loginRequest, authToken)
            if (result is ApiResult.Success) {

                counter = 0

                result.data.responseDetails?.let { responseDetails ->
//                    pref.email = result.data.responseDetails.email.toString()
//                    pref.isLogin = true
//                    pref.authToken = result.data.responseDetails.token?.accessToken.toString()
//                    pref.uid = result.data.responseDetails.uid.toString()
//                    pref.loginType = result.data.responseDetails.loginType.toString()
////                    pref.profilePicture = result.data.responseDetails.profilePicture.toString()
                }
                _signUp.value = result.data.responseDetails!!
            } else if (result is ApiResult.Error) {
                val random = (10000..99999).random().toString()

                Toast.makeText(BaseApplication.getInstance(), result.code.message, Toast.LENGTH_LONG).show()
                val guestRequest2 = SighInRequest(
                    email = com.app.reelshort.BuildConfig.HOST_EMAIL,
                    loginType = com.app.reelshort.BuildConfig.HOST_LOGIN_TYPE_GUEST,
                    loginTypeId = random,
                    name = com.app.reelshort.BuildConfig.HOST_DISPLAY_NAME,
                    deviceId = deviceId
                )
                if (counter == 4) {
                    return@launch
                }
                counter++
                login(guestRequest2, authToken)
            }
        }
    }
}
