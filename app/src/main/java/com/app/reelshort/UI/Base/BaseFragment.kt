package test.app.gallery.UI1.Base

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import com.app.reelshort.App.BaseApplication
import com.app.reelshort.Utils.DPreferences
import kotlin.math.abs

abstract class BaseFragment : Fragment() {
    lateinit var pref: DPreferences
//    var reelsAdapter2: SeriesReelsAdapter? = null

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)
        pref = DPreferences(context)
    }

    fun setCenterLayoutManager(recyclerView: RecyclerView) {
        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)

                val itemPosition = parent.getChildAdapterPosition(view)

                val itemWidth = view.layoutParams.width
                val offset = (parent.width - itemWidth) / 2

                if (itemPosition == 0) {
                    outRect.left = offset
                } else if (itemPosition == state.itemCount - 1) {
                    outRect.right = offset
                }
            }
        })
        val centerLayoutManager = CenterLayoutManager(BaseApplication.getInstance(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setLayoutManager(centerLayoutManager)
    }
}

open class CenterLayoutManager : LinearLayoutManager {
    private val mShrinkAmount = 0.20f
    private val mShrinkDistance = 0.9f

    constructor(context: Context?) : super(context)

    constructor(context: Context?, orientation: Int, reverseLayout: Boolean) : super(
        context, orientation, reverseLayout
    )

    constructor(
        context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun checkLayoutParams(lp: RecyclerView.LayoutParams): Boolean {
        lp.width = width / 2
        lp.height = width / 2
        return true
    }

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView, state: RecyclerView.State, position: Int
    ) {
        val smoothScroller: SmoothScroller = CenterSmoothScroller(recyclerView.context)
        smoothScroller.targetPosition = position
        startSmoothScroll(smoothScroller)
    }

    private class CenterSmoothScroller(context: Context?) : LinearSmoothScroller(context) {
        override fun calculateDtToFit(
            viewStart: Int, viewEnd: Int, boxStart: Int, boxEnd: Int, snapPreference: Int
        ): Int {
            return (boxStart + (boxEnd - boxStart) / 2) - (viewStart + (viewEnd - viewStart) / 2)
        }
    }

    override fun canScrollHorizontally(): Boolean {
        return true
    }

    override fun scrollHorizontallyBy(
        dx: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?
    ): Int {
        val orientation = orientation
        if (orientation == HORIZONTAL) {
            val scrolled = super.scrollHorizontallyBy(dx, recycler, state)

            val midpoint = width / 2f
            val d0 = 0f
            val d1 = mShrinkDistance * midpoint
            val s0 = 1f
            val s1 = 1f - mShrinkAmount
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                val childMidpoint = (getDecoratedRight(child!!) + getDecoratedLeft(child)) / 2f
                val d = d1.coerceAtMost(abs(midpoint - childMidpoint))
                val scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0)
                child.scaleX = scale
                child.scaleY = scale

            }
            return scrolled
        } else {
            return 0
        }

    }
}