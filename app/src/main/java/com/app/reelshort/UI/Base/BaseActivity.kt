package test.app.gallery.UI1.Base

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.app.reelshort.Model.LoginRequest
import com.app.reelshort.Utils.AdminPreference
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.app.reelshort.Ads.AdsGoogle
import com.app.reelshort.App.ReelShortApp
import com.app.reelshort.Model.SignUpResponse
import com.app.reelshort.ViewModel.ApiResult
import com.app.reelshort.ViewModel.UserViewModel
import kotlinx.coroutines.launch

abstract class BaseActivity : AppCompatActivity() {

    val pref: AdminPreference get() = AdminPreference(this)

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT


        val isLightMode = (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) != Configuration.UI_MODE_NIGHT_YES

        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    if (isLightMode) {


                        0
                    } else {
                        0


                    }
                    )

    }
}
