package com.app.reelshort.UI.Activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.reelshort.R
import com.app.reelshort.UI.Fragment.MyListFragment
import com.app.reelshort.ViewModel.UserViewModel
import com.app.reelshort.databinding.ActivityInvitationCodeBinding
import com.app.reelshort.databinding.ActivityMyListBinding
import dagger.hilt.android.AndroidEntryPoint
import test.app.gallery.UI1.Base.BaseActivity

@AndroidEntryPoint
class MyListActivity : BaseActivity() {
    lateinit var binding: ActivityMyListBinding
    val viewModel: UserViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyListBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}