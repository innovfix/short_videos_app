package com.app.reelshort.ViewModel

import android.text.TextUtils
import androidx.annotation.Keep
import com.app.reelshort.Model.EpisodeRequest
import com.app.reelshort.APIs.ApiService
import com.app.reelshort.App.BaseApplication
import com.app.reelshort.Model.ApiErrorResponse
import com.app.reelshort.Model.AutoUnlockSettingRequest
import com.app.reelshort.Model.BindEmailRequest
import com.app.reelshort.Model.CreatePaymentRequest
import com.app.reelshort.Model.CreateRazorPayCreateOrderRequest
import com.app.reelshort.Model.CreateTicketRequest
import com.app.reelshort.Model.DailyCheckedInRequest
import com.app.reelshort.Model.DeleteRequest
import com.app.reelshort.Model.FavouriteRequest
import com.app.reelshort.Model.IdRequest
import com.app.reelshort.Model.LikeRequest
import com.app.reelshort.Model.LoginRequest
import com.app.reelshort.Model.PaymentUpdateRequest
import com.app.reelshort.Model.SearchRequest
import com.app.reelshort.Model.SendOtpRequest
import com.app.reelshort.Model.Shorts
import com.app.reelshort.Model.ShortsResponse
import com.app.reelshort.Model.SighInRequest
import com.app.reelshort.Model.StripePaymentIntentRequest
import com.app.reelshort.Model.UnlockEpisodeRequest
import com.app.reelshort.Model.UpdateRewardRequest
import com.app.reelshort.Model.WatchEpisodeRequest
import com.app.reelshort.Utils.DPreferences
import com.app.reelshort.Utils.Helper
import com.google.gson.Gson
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.jvm.java

@Keep
@Singleton
class UserRepository @Inject constructor(val apiService: ApiService) {
    var allShortsResponse: ApiResult<ShortsResponse>? = null

    @Keep
    enum class HttpCode(val code: Int, val message: String) {
        OK(200, "OK"), BAD_REQUEST(400, "Bad Request"), UNAUTHORIZED(
            401, "Unauthorized access"
        ),
        FORBIDDEN(403, "Forbidden"), NOT_FOUND(404, "Not Found"), SERVER_ERROR(
            500, "Server Error"
        ),
        UNKNOWN(-1, "Something went wrong"), NETWORK_ERROR(-1, "Network error");

        companion object {
            fun fromCode(code: Int): HttpCode {
                return HttpCode.entries.find { it.code == code } ?: UNKNOWN
            }
        }
    }

    val pref = DPreferences(BaseApplication.getInstance())

    private val token by lazy {
        pref.authToken
    }


    suspend inline fun <reified T> safeApiCall(
        crossinline call: suspend () -> Response<T>,
    ): ApiResult<T> {
        return try {
            if (Helper.checkNetworkConnection()) {

                val response = call()
                val body = response.body()
                val token = response.headers().get("Authorization")
                if (token != null) {
                    pref.authToken = token
                }
                if (response.isSuccessful && body != null) {
                    ApiResult.Success(body)
                } else {
//                    val errorBody = response.errorBody()?.string()
//                    val message = try {
//                        errorBody?.let {
//                            Gson().fromJson(it, ApiErrorResponse::class.java)?.responseMessage
//                        }
//                    } catch (e: Exception) {
//                        null
//                    }
//                    if (response.code() == 500) {
//                        BaseApplication.getInstance().networkManager.showServerErrorDialog { }
//                    }
                    ApiResult.Error(
                        HttpCode.fromCode(response.code()), response.code(), response.message()
                    )
                }
            } else {
                ApiResult.Error(HttpCode.NETWORK_ERROR, message = "Network Error")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ApiResult.Error(HttpCode.UNKNOWN, message = e.message ?: "Unknown error")
        }
    }


    suspend fun sendOtp(
        request: SendOtpRequest,
    ) = safeApiCall {
        apiService.sendOtp(request)
    }

    suspend fun logIn(
        request: LoginRequest,
    ) = safeApiCall {
        apiService.logIn(request)
    }

    suspend fun getPremiumPlans(
        authToken: String = pref.authToken,
    ) = safeApiCall {
        apiService.getPremiumPlans(authToken)
    }

    suspend fun getPremiumPlansVideo(
        authToken: String = pref.authToken,
    ) = safeApiCall {
        apiService.getPremiumPlansVideo(authToken)
    }

    suspend fun getPremiumPlansUsers(
        authToken: String = pref.authToken,
    ) = safeApiCall {
        apiService.getPremiumPlansUsers(authToken)
    }

    suspend fun signUp(
        request: SighInRequest,
        authToken: String = pref.authToken,
    ) = safeApiCall {
        apiService.signUp(request, authToken)
    }

    suspend fun getShorts(authToken: String = pref.authToken, tag: String) = safeApiCall {
        apiService.getShorts(authToken, authToken)
    }

    suspend fun getAllShorts(authToken: String = pref.authToken): ApiResult<ShortsResponse> {
        if (allShortsResponse == null) {
            allShortsResponse = safeApiCall {
                apiService.getShorts(authToken, "tag")
            }
            return allShortsResponse as ApiResult<ShortsResponse>
        } else {
            return allShortsResponse as ApiResult<ShortsResponse>
        }
    }

    suspend fun getAllEpisodeList(
        request: EpisodeRequest,
        authToken: String = pref.authToken,
    ) = safeApiCall { apiService.getAllEpisodeList(request, authToken) }

    suspend fun getMyList(authToken: String = pref.authToken) =
        safeApiCall { apiService.getMyList(authToken) }

    suspend fun getHistory(authToken: String = pref.authToken) =
        safeApiCall { apiService.getHistory(authToken) }

    suspend fun setLikeEpisode(
        authToken: String = pref.authToken,
        likeRequest: LikeRequest,
    ) = safeApiCall { apiService.setLikeEpisode(authToken, likeRequest) }

    suspend fun getPlanList(authToken: String = pref.authToken) =
        safeApiCall { apiService.getPlanList(authToken) }

    suspend fun setDailyWatchAds(authToken: String = token) =
        safeApiCall { apiService.setDailyWatchAds(authToken) }

    suspend fun getSeriesList(
        random: Int = 1,
        authToken: String = pref.authToken,
    ) = safeApiCall { apiService.getSeriesList(random, authToken) }

    suspend fun setFavourite(
        favouriteRequest: FavouriteRequest,
        authToken: String = pref.authToken,
    ) = safeApiCall { apiService.setFavourite(favouriteRequest, authToken) }

    suspend fun getCoinData(authToken: String = pref.authToken) =
        safeApiCall { apiService.getCoinData(authToken) }

    suspend fun getRewardHistory(authToken: String = pref.authToken) =
        safeApiCall { apiService.getRewardHistory(authToken) }

    suspend fun setBindEmail(
        request: String,
        authToken: String = pref.authToken,
    ) = safeApiCall { apiService.setBindEmail(BindEmailRequest(request), authToken) }

    suspend fun getSearch(
        request: String,
        authToken: String = pref.authToken,
    ) = safeApiCall { apiService.getSearch(SearchRequest(request), authToken) }

    suspend fun setDailyCheckedIn(
        request: Int,
        authToken: String = pref.authToken,
    ) = safeApiCall { apiService.setDailyCheckedIn(DailyCheckedInRequest(request), authToken) }

    suspend fun setUpdateReward(
        request: String,
        authToken: String = pref.authToken,
    ) = safeApiCall { apiService.setUpdateReward(UpdateRewardRequest(request), authToken) }

    suspend fun setWatchEpisode(
        request: WatchEpisodeRequest,
        authToken: String = pref.authToken,
    ) = safeApiCall { apiService.setWatchEpisode(request, authToken) }

    suspend fun setUnlockEpisode(
        request: UnlockEpisodeRequest,
        authToken: String = pref.authToken,
    ) = safeApiCall { apiService.setUnlockEpisode(request, authToken) }

    suspend fun getFaq(authToken: String = pref.authToken) =
        safeApiCall { apiService.getFaq(authToken) }

    suspend fun setAutoUnlockSetting(
        request: AutoUnlockSettingRequest,
        authToken: String = pref.authToken,
    ) = safeApiCall { apiService.setAutoUnlockSetting(request, authToken) }

    suspend fun getTagWiseList(
        id: String,
        authToken: String = pref.authToken,
    ) = safeApiCall { apiService.getTagWiseList(IdRequest(id), authToken) }

    suspend fun getPaymentOption(authToken: String = pref.authToken) =
        safeApiCall { apiService.getPaymentOption(authToken) }

    suspend fun createPayment(
        request: CreatePaymentRequest,
        authToken: String = pref.authToken,
    ) = safeApiCall { apiService.createPayment(request, authToken) }

    suspend fun setPaymentUpdate(
        request: PaymentUpdateRequest,
        authToken: String = pref.authToken,
    ) = safeApiCall { apiService.setPaymentUpdate(request, authToken) }

    suspend fun createStripePaymentIntent(
        request: StripePaymentIntentRequest,
        authToken: String = pref.authToken,
    ) = safeApiCall { apiService.createStripePaymentIntent(request, authToken) }

    suspend fun getTransactionList(authToken: String = pref.authToken) =
        safeApiCall { apiService.getTransactionList(authToken) }

    suspend fun getUnlockedEpisodeList(authToken: String = pref.authToken) =
        safeApiCall { apiService.getUnlockedEpisodeList(authToken) }

    suspend fun getUserWatchedSeriesList(authToken: String = pref.authToken) =
        safeApiCall { apiService.getUserWatchedSeriesList(authToken) }

    suspend fun getSocialLinks(authToken: String = pref.authToken) =
        safeApiCall { apiService.getSocialLinks(authToken) }

    suspend fun deleteWatchHistory(
        request: DeleteRequest,
        authToken: String = pref.authToken,
    ) = safeApiCall { apiService.deleteWatchHistory(request, authToken) }

    suspend fun deleteFavouriteSeries(
        request: DeleteRequest,
        authToken: String = pref.authToken,
    ) = safeApiCall { apiService.deleteFavouriteSeries(request, authToken) }


    suspend fun getAdTimer(
        request: EpisodeRequest,
        authToken: String = pref.authToken,
    ) = safeApiCall { apiService.getAdTimer(request, authToken) }


    suspend fun getAdsIdData(
        authToken: String = pref.authToken,
    ) = safeApiCall { apiService.getAdsIdData(authToken) }


    suspend fun getTicket(
        email: String,
        authToken: String = pref.authToken,
    ) = safeApiCall { apiService.getTicket(BindEmailRequest(email), authToken) }

    suspend fun createTicket(
        request: CreateTicketRequest,
        authToken: String = pref.authToken,
    ) = safeApiCall { apiService.createTicket(request, authToken) }


    suspend fun reasonsList(
        authToken: String = pref.authToken,
    ) = safeApiCall { apiService.reasonsList(authToken) }

    suspend fun getAppDetails(
        authToken: String = pref.authToken,
    ) = safeApiCall { apiService.getAppDetails(authToken) }

    suspend fun deleteAccount(
        request: String,
        authToken: String = pref.authToken,
    ) = safeApiCall { apiService.deleteAccount(authToken) }

}
