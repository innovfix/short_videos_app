package test.app.gallery.UI1.Base

import androidx.fragment.app.Fragment
import com.app.reelshort.UI.Adapter.SeriesReelsAdapter
import com.app.reelshort.Utils.DPreferences

abstract class BaseFragment : Fragment() {
    lateinit var pref: DPreferences
    var reelsAdapter2: SeriesReelsAdapter? = null

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)
        pref = DPreferences(context)
    }
}