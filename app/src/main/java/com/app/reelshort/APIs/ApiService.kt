package com.app.reelshort.APIs

import com.app.reelshort.Model.AdResponse
import com.app.reelshort.Model.AdTimerResponse
import com.app.reelshort.Model.AppDetailsResponse
import com.app.reelshort.Model.AutoUnlockSettingRequest
import com.app.reelshort.Model.AutoUnlockSettingResponse
import com.app.reelshort.Model.BindEmailRequest
import com.app.reelshort.Model.CoinDataResponse
import com.app.reelshort.Model.CreatePaymentRequest
import com.app.reelshort.Model.CreateRazorPayCreateOrderRequest
import com.app.reelshort.Model.CreateRazorPayCreateOrderResponse
import com.app.reelshort.Model.DailyCheckedInRequest
import com.app.reelshort.Model.DailyWatchAdsResponse
import com.app.reelshort.Model.DeleteRequest
import com.app.reelshort.Model.EpisodeListResponse
import com.app.reelshort.Model.EpisodeRequest
import com.app.reelshort.Model.FavouriteRequest
import com.app.reelshort.Model.HomeListResponse
import com.app.reelshort.Model.IdRequest
import com.app.reelshort.Model.LikeRequest
import com.app.reelshort.Model.CommonsResponse
import com.app.reelshort.Model.CreateTicketRequest
import com.app.reelshort.Model.CreateTicketResponse
import com.app.reelshort.Model.DeleteAccountResponse
import com.app.reelshort.Model.FqaResponse
import com.app.reelshort.Model.MyListResponse
import com.app.reelshort.Model.PaymentOptionResponse
import com.app.reelshort.Model.PaymentUpdateRequest
import com.app.reelshort.Model.PlanListResponse
import com.app.reelshort.Model.ReasonsListResponse
import com.app.reelshort.Model.RewardHistoryResponse
import com.app.reelshort.Model.SearchRequest
import com.app.reelshort.Model.SearchResponse
import com.app.reelshort.Model.SeriesListResponse
import com.app.reelshort.Model.SighInRequest
import com.app.reelshort.Model.SignUpResponse
import com.app.reelshort.Model.SocialLinksResponse
import com.app.reelshort.Model.StripePaymentIntentRequest
import com.app.reelshort.Model.StripePaymentIntentResponse
import com.app.reelshort.Model.TagWiseResponse
import com.app.reelshort.Model.TermConditionResponse
import com.app.reelshort.Model.TicketResponse
import com.app.reelshort.Model.TransactionListResponse
import com.app.reelshort.Model.UnlockEpisodeRequest
import com.app.reelshort.Model.UnlockEpisodeResponse
import com.app.reelshort.Model.UnlockedEpisodeListResponse
import com.app.reelshort.Model.UpdateRewardRequest
import com.app.reelshort.Model.UpdateRewardResponse
import com.app.reelshort.Model.UserWatchedSeriesListResponse
import com.app.reelshort.Model.WatchEpisodeResponse
import com.app.reelshort.Model.WatchEpisodeRequest
import com.app.reelshort.Model.createPaymentResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {


    @POST("/user/v1/signup-mobile")
    suspend fun signIn(
        @Body request: SighInRequest,
        @Header("Authorization") authToken: String,
    ): Response<SignUpResponse>


    @POST("/user/v1/signup-mobile")
    suspend fun signUp(
        @Body request: SighInRequest,
        @Header("Authorization") authToken: String,
    ): Response<SignUpResponse>


    @GET("user/v1/home/list")
    suspend fun getHomeList(
        @Header("Authorization") authToken: String,
    ): Response<HomeListResponse>


    @GET("user/v1/home/my-list")
    suspend fun getMyList(
        @Header("Authorization") authToken: String,
    ): Response<MyListResponse>

    @GET("user/v1/plan-list")
    suspend fun getPlanList(
        @Header("Authorization") authToken: String,
    ): Response<PlanListResponse>


    @POST("user/v1/series/like-episode")
    suspend fun setLikeEpisode(
        @Header("Authorization") authToken: String,
        @Body likeRequest: LikeRequest,
    ): Response<CommonsResponse>


    @POST("user/v1/home/search")
    suspend fun getSearch(
        @Body likeRequest: SearchRequest,
        @Header("Authorization") authToken: String,
    ): Response<SearchResponse>

    @GET("user/v1/daily-watch-ads")
    suspend fun setDailyWatchAds(
        @Header("Authorization") authToken: String,
    ): Response<DailyWatchAdsResponse>

    @GET("user/v1/home/series-list")
    suspend fun getSeriesList(
        @Query("random") random: Int,
        @Header("Authorization") authToken: String,
    ): Response<SeriesListResponse>

    @POST("user/v1/series/favourite-episode")
    suspend fun setFavourite(
        @Body favouriteRequest: FavouriteRequest,
        @Header("Authorization") authToken: String,
    ): Response<CommonsResponse>


    @GET("user/v1/coin-data")
    suspend fun getCoinData(
        @Header("Authorization") authToken: String,
    ): Response<CoinDataResponse>


    @POST("user/v1/update-reward")
    suspend fun setUpdateReward(
        @Body updateRewardRequest: UpdateRewardRequest,
        @Header("Authorization") authToken: String,
    ): Response<UpdateRewardResponse>

    @GET("user/v1/reward-history-list")
    suspend fun getRewardHistory(
        @Header("Authorization") authToken: String,
    ): Response<RewardHistoryResponse>


    @POST("user/v1/bind-email")
    suspend fun setBindEmail(
        @Body bindEmailRequest: BindEmailRequest,
        @Header("Authorization") authToken: String,
    ): Response<CommonsResponse>

    @POST("user/v1/series/watch-episode")
    suspend fun setWatchEpisode(
        @Body likeRequest: WatchEpisodeRequest,
        @Header("Authorization") authToken: String,
    ): Response<WatchEpisodeResponse>


    @POST("user/v1/series/unlock-episode")
    suspend fun setUnlockEpisode(
        @Body unlockEpisodeRequest: UnlockEpisodeRequest,
        @Header("Authorization") authToken: String,
    ): Response<UnlockEpisodeResponse>


    @POST("user/v1/series/all-episode-list")
    suspend fun getAllEpisodeList(
        @Body request: EpisodeRequest,
        @Header("Authorization") authToken: String,
    ): Response<EpisodeListResponse>

    @POST("user/v1/daily-coin")
    suspend fun setDailyCheckedIn(
        @Body request: DailyCheckedInRequest,
        @Header("Authorization") authToken: String,
    ): Response<CommonsResponse>


    @GET("user/v1/faq-list")
    suspend fun getFaq(
        @Header("Authorization") authToken: String,
    ): Response<FqaResponse>


    @POST("user/v1/auto-unlock-setting")
    suspend fun setAutoUnlockSetting(
        @Body request: AutoUnlockSettingRequest,
        @Header("Authorization") authToken: String,
    ): Response<AutoUnlockSettingResponse>


    @POST("user/v1/home/tag-wise")
    suspend fun getTagWiseList(
        @Body request: IdRequest,
        @Header("Authorization") authToken: String,
    ): Response<TagWiseResponse>


    @GET("user/v1/payment-getways-list")
    suspend fun getPaymentOption(
        @Header("Authorization") authToken: String,
    ): Response<PaymentOptionResponse>


    @POST("user/v1/payments/create-payment-intent")
    suspend fun createStripePaymentIntent(
        @Body request: StripePaymentIntentRequest,
        @Header("Authorization") authToken: String,
    ): Response<StripePaymentIntentResponse>


    @POST("user/v1/payments/create-order")
    suspend fun createRazorPayCreateOrder(
        @Body request: CreateRazorPayCreateOrderRequest,
        @Header("Authorization") authToken: String,
    ): Response<CreateRazorPayCreateOrderResponse>


    @POST("user/v1/payments/create")
    suspend fun createPayment(
        @Body request: CreatePaymentRequest,
        @Header("Authorization") authToken: String,
    ): Response<createPaymentResponse>


    @POST("user/v1/payments/update")
    suspend fun setPaymentUpdate(
        @Body request: PaymentUpdateRequest,
        @Header("Authorization") authToken: String,
    ): Response<CommonsResponse>

    @GET("user/v1/payments/transaction-list")
    suspend fun getTransactionList(
        @Header("Authorization") authToken: String,
    ): Response<TransactionListResponse>


    @GET("user/v1/unlocked-episode-list")
    suspend fun getUnlockedEpisodeList(
        @Header("Authorization") authToken: String,
    ): Response<UnlockedEpisodeListResponse>


    @GET("user/v1/user-series-list")
    suspend fun getUserWatchedSeriesList(
        @Header("Authorization") authToken: String,
    ): Response<UserWatchedSeriesListResponse>

    @GET("user/v1/social-links")
    suspend fun getSocialLinks(
        @Header("Authorization") authToken: String,
    ): Response<SocialLinksResponse>


    @POST("user/v1/delete-watch-history")
    suspend fun deleteWatchHistory(
        @Body request: DeleteRequest,
        @Header("Authorization") authToken: String,
    ): Response<CommonsResponse>

    @POST("user/v1/delete-favourite-series")
    suspend fun deleteFavouriteSeries(
        @Body request: DeleteRequest,
        @Header("Authorization") authToken: String,
    ): Response<CommonsResponse>

    @POST("user/v1/ads-timer")
    suspend fun getAdTimer(
        @Body request: EpisodeRequest,
        @Header("Authorization") authToken: String,
    ): Response<AdTimerResponse>

    @GET("user/v1/ads")
    suspend fun getAdsIdData(
        @Header("Authorization") authToken: String,
    ): Response<AdResponse>


    @POST("user/v1/get-ticket")
    suspend fun getTicket(
        @Body request: BindEmailRequest,
        @Header("Authorization") authToken: String,
    ): Response<TicketResponse>

    @POST("user/v1/create-ticket")
    suspend fun createTicket(
        @Body request: CreateTicketRequest,
        @Header("Authorization") authToken: String,
    ): Response<CreateTicketResponse>


    @GET("user/v1/reasons")
    suspend fun reasonsList(
        @Header("Authorization") authToken: String,
    ): Response<ReasonsListResponse>

    @GET("details")
    suspend fun getAppDetails(
        @Header("Authorization") authToken: String,
    ): Response<AppDetailsResponse>


    @POST("user/v1/delete-account")
    suspend fun deleteAccount(
        @Header("Authorization") authToken: String,
    ): Response<DeleteAccountResponse>

    @POST("user/v1/notification-allowed")
    suspend fun notificationAllowed(
        @Header("Authorization") authToken: String,
    ): Response<DeleteAccountResponse>


}
