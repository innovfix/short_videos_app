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
class UserViewModel @Inject constructor(
    val repository: UserRepository,
) : ViewModel() {
    val pref: DPreferences get() = DPreferences(BaseApplication.getInstance())

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

    private val _myListResponse = MutableLiveData<MyListResponse>()
    val myListResponse: LiveData<MyListResponse> = _myListResponse

    private val _myListError = MutableLiveData<ApiResult.Error>()
    val myListError: LiveData<ApiResult.Error> = _myListError

    private val _historyResponse = MutableLiveData<MyListResponse>()
    val historyResponse: LiveData<MyListResponse> = _historyResponse

    private val _historyError = MutableLiveData<ApiResult.Error>()
    val historyError: LiveData<ApiResult.Error> = _historyError

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

    fun getMyList(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = repository.getMyList(authToken)
            if (result is ApiResult.Success) {
                _myListResponse.value = result.data
            } else if (result is ApiResult.Error) {
                _myListError.value = result
            }
        }
    }

    fun getHistory(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = repository.getHistory(authToken)
            if (result is ApiResult.Success) {
                _historyResponse.value = result.data
            } else if (result is ApiResult.Error) {
                _historyError.value = result
            }
        }
    }

}
