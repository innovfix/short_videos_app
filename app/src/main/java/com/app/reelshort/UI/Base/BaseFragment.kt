package test.app.gallery.UI1.Base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.reelshort.Ads.AdsGoogle
import com.app.reelshort.UI.Adapter.SeriesReelsAdapter
import com.app.reelshort.UI.Fragment.ReelsFragment
import com.app.reelshort.Utils.AdminPreference

abstract class BaseFragment : Fragment() {
    lateinit var pref: AdminPreference
    var reelsAdapter2: SeriesReelsAdapter? = null

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)
        pref = AdminPreference(context)
    }
}