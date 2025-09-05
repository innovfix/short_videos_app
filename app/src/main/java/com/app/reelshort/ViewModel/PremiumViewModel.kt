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
class PremiumViewModel @Inject constructor(
    val repository: UserRepository,
) : ViewModel() {
    val pref: DPreferences get() = DPreferences(BaseApplication.getInstance())

    private val _premiumPlansResponse = MutableLiveData<PremiumPlansResponse>()
    val premiumPlansResponse: LiveData<PremiumPlansResponse> = _premiumPlansResponse

    private val _premiumPlansError = MutableLiveData<ApiResult.Error>()
    val premiumPlansError: LiveData<ApiResult.Error> = _premiumPlansError

    private val _premiumPlansVideoResponse = MutableLiveData<PremiumPlansVideoResponse>()
    val premiumPlansVideoResponse: LiveData<PremiumPlansVideoResponse> = _premiumPlansVideoResponse

    private val _premiumPlansVideoError = MutableLiveData<ApiResult.Error>()
    val premiumPlansVideoError: LiveData<ApiResult.Error> = _premiumPlansVideoError

    private val _premiumPlansUsersResponse = MutableLiveData<PremiumPlansUsersResponse>()
    val premiumPlansUsersResponse: LiveData<PremiumPlansUsersResponse> = _premiumPlansUsersResponse

    private val _premiumPlansUsersError = MutableLiveData<ApiResult.Error>()
    val premiumPlansUsersError: LiveData<ApiResult.Error> = _premiumPlansUsersError

    init {
        getPremiumPlans(pref.authToken)
        getPremiumPlansVideo(pref.authToken)
        getPremiumPlansUsers(pref.authToken)
    }

    fun getPremiumPlans(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = repository.getPremiumPlans(authToken)
            if (result is ApiResult.Success) {
                _premiumPlansResponse.value = result.data
            } else if (result is ApiResult.Error) {
                _premiumPlansError.value = result
            }
        }
    }

    fun getPremiumPlansVideo(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = repository.getPremiumPlansVideo(authToken)
            if (result is ApiResult.Success) {
                _premiumPlansVideoResponse.value = result.data
            } else if (result is ApiResult.Error) {
                _premiumPlansVideoError.value = result
            }
        }
    }

    fun getPremiumPlansUsers(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = repository.getPremiumPlansUsers(authToken)
            if (result is ApiResult.Success) {
                _premiumPlansUsersResponse.value = result.data
            } else if (result is ApiResult.Error) {
                _premiumPlansUsersError.value = result
            }
        }
    }
}
