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
import com.app.reelshort.Model.SettingsResponse
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
class SettingsViewModel @Inject constructor(
    val repository: UserRepository,
) : ViewModel() {
    val pref: DPreferences get() = DPreferences(BaseApplication.getInstance())

    private val _settingsList = MutableLiveData<SettingsResponse>()
    val settingsList: LiveData<SettingsResponse> = _settingsList

    init {
        getSettingsList(pref.authToken)
    }

    fun getSettingsList(authToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = repository.getSettingsList(authToken)
            if (result is ApiResult.Success) {
                _settingsList.value = result.data
            }
        }
    }
}
