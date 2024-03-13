package com.trudeals.utils.dotsindicator

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.trudeals.R
import com.trudeals.utils.marginitemdecorator.LayoutType
import com.trudeals.utils.marginitemdecorator.MarginItemDecorator

class DotsIndicator(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs) {

    /*private val binding: DotsIndicatorBinding =
        DotsIndicatorBinding.inflate(LayoutInflater.from(context), this, true)*/

    private val dotsIndicatorAdapter by lazy {
        DotsIndicatorAdapter()
    }

    /* --------------- Attributes --------------- */
    private val typedArray by lazy {
        getContext().obtainStyledAttributes(attrs, R.styleable.DotsIndicator)
    }

    private var dotWidth = typedArray.getDimension(
        R.styleable.DotsIndicator_dotWidth,
        resources.getDimension(R.dimen._8sdp)
    )
    private var dotHeight = typedArray.getDimension(
        R.styleable.DotsIndicator_dotHeight,
        resources.getDimension(R.dimen._4sdp)
    )

    private var selectedDotWidth = typedArray.getDimension(
        R.styleable.DotsIndicator_selectedDotWidth,
        resources.getDimension(R.dimen._17sdp)
    )
    private var selectedDotHeight = typedArray.getDimension(
        R.styleable.DotsIndicator_selectedDotHeight,
        resources.getDimension(R.dimen._4sdp)
    )

    init {
        setUpDotsRecyclerView()
    }

    fun attachTo(viewPager2: ViewPager2) {
        viewPager2.adapter?.itemCount?.let {
            for (i in 0 until it) {
                dotsIndicatorAdapter.addItem(Dot(isSelected = i == 0))
            }
        }

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                dotsIndicatorAdapter.items!!.find { it.isSelected }?.isSelected = false

                dotsIndicatorAdapter.getItem(position).isSelected = true
                dotsIndicatorAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun setUpDotsRecyclerView() {
//        recyclerViewDots.apply {
        adapter = dotsIndicatorAdapter
        dotsIndicatorAdapter.errorMessage = ""
        layoutManager = LinearLayoutManager(this.context, HORIZONTAL, false)
        addItemDecoration(
            MarginItemDecorator(
                MarginItemDecorator.getDimension(
                    this.context,
                    R.dimen._2sdp
                ), layoutType = LayoutType.LINEAR_LAYOUT_HORIZONTAL
            )
        )

        dotsIndicatorAdapter.setDotWidth(dotWidth)
        dotsIndicatorAdapter.setDotHeight(dotHeight)

        dotsIndicatorAdapter.setSelectedDotWidth(selectedDotWidth)
        dotsIndicatorAdapter.setSelectedDotHeight(selectedDotHeight)
//        }
    }
}