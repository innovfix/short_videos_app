package com.app.reelshort.UI.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import com.app.reelshort.UI.Adapter.SearchAdapter
import com.app.reelshort.Utils.CommonsKt
import com.app.reelshort.Utils.HistoryPrefs
import com.app.reelshort.Utils.gone
import com.app.reelshort.Utils.showToast
import com.app.reelshort.Utils.visible
import com.app.reelshort.ViewModel.ApiResult
import com.app.reelshort.ViewModel.UserViewModel
import com.app.reelshort.databinding.ActivitySearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import test.app.gallery.UI1.Base.BaseActivity
import kotlin.getValue


@AndroidEntryPoint
class SearchActivity : BaseActivity() {

    private val viewModel: UserViewModel by viewModels()


    lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }


        loadHistrory()
        binding.searchInput.setOnKeyListener { v, keyCode, event ->

            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {

                val inputText = binding.searchInput.text.toString()
                if (inputText.isEmpty()) {
                    binding.searchInput.error = "Please Enter text"
                    return@setOnKeyListener false
                }
                if (inputText.isNotEmpty()) {
                    callApi(inputText)
                }

                true
            } else {
                false
            }
        }


        binding.searchInput.requestFocus()

        binding.searchInput.postDelayed({
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.searchInput, InputMethodManager.SHOW_IMPLICIT)
        }, 100)


    }

    private fun loadHistrory() {
        val suggestions = HistoryPrefs.getClickHistory(this)
        if (suggestions.isNotEmpty()) {
            binding.searchHistoryRv.adapter = SearchAdapter(suggestions) { item ->
                HistoryPrefs.saveClick(this@SearchActivity, item)
                startActivity(Intent(this@SearchActivity, ReelsActivity::class.java).apply {
                    putExtra(CommonsKt.SERIES_ID_EXTRA, item.id.toString())
                })
            }
            binding.llHistory.visible()
        } else {
            binding.llHistory.gone()
        }
    }

    private fun callApi(inputText: String) {
        showProgress()
        viewModel.viewModelScope.launch(Dispatchers.Main) {
            val result = viewModel.repository.getSearch(inputText, pref.authToken)
            if (result is ApiResult.Success) {
                result.data.responseDetails?.let { responseDetails ->
                    val adapter =
                        SearchAdapter(CommonsKt.getCommonInfo(result.data.responseDetails.searchlist!!)) { item ->

                            HistoryPrefs.saveClick(this@SearchActivity, item)
                            startActivity(
                                Intent(
                                    this@SearchActivity,
                                    ReelsActivity::class.java
                                ).apply {
                                    putExtra(CommonsKt.SERIES_ID_EXTRA, item.id.toString())
                                })
                        }
                    binding.peopleSearchingRv.adapter = adapter
                    if (responseDetails.searchlist!!.isEmpty()) {
                        showEmpty()
                    } else {
                        binding.progressLayout.mainLayout.visibility = View.GONE
                    }
                }
            } else if (result is ApiResult.Error) {
                showErrorEmpty(result.message)
            }
        }

    }

    private fun showEmpty() = with(binding) {
        progressLayout.mainLayout.visible()
        progressLayout.progressAnimation.gone()
        progressLayout.emptyLayout.visible()
    }

    private fun showProgress() = with(binding) {
        progressLayout.mainLayout.visible()
        progressLayout.progressAnimation.visible()
        progressLayout.emptyLayout.gone()
    }

    private fun showErrorEmpty(message: String) = with(binding) {
        binding.progressLayout.progressAnimation.gone()
        binding.progressLayout.emptyLayout.visible()
        binding.progressLayout.mainLayout.visible()
        binding.progressLayout.emptyTitle.text = message
    }
}