package com.trudeals.ui.tutorial

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.trudeals.R
import com.trudeals.databinding.TutorialActivityBinding
import com.trudeals.di.component.ActivityComponent
import com.trudeals.ui.auth.AuthActivity
import com.trudeals.ui.base.BaseActivity
import com.trudeals.ui.common.adapter.ViewPagerActivityAdapter
import com.trudeals.ui.tutorial.fragment.TutorialOneFragment
import com.trudeals.ui.tutorial.fragment.TutorialThreeFragment
import com.trudeals.ui.tutorial.fragment.TutorialTwoFragment
import com.trudeals.utils.extension.isVisible

class TutorialActivity : BaseActivity(), View.OnClickListener {

    private lateinit var tutorialActivityBinding: TutorialActivityBinding
    private var count = 0
    private val viewPagerAdapter by lazy {
        ViewPagerActivityAdapter(this,
            ArrayList<Fragment>().apply {
                add(TutorialOneFragment())
                add(TutorialTwoFragment())
                add(TutorialThreeFragment())
            }
        )
    }

    override fun findFragmentPlaceHolder(): Int {
        return R.id.placeHolder
    }

    override fun createViewBinding(): View {
        tutorialActivityBinding = TutorialActivityBinding.inflate(layoutInflater)
        return tutorialActivityBinding.root
    }

    override fun inject(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViewPager()
        clickListeners()
        tutorialActivityBinding.apply {
            buttonNext.isVisible(true)
            buttonContinue.isVisible(false)
        }
    }

    private fun setViewPager() = with(tutorialActivityBinding) {
        viewPager.apply {
            adapter = viewPagerAdapter
            isUserInputEnabled = true
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    count = position
                    if (count == 2) buttonNext.text = getString(R.string.btn_continue)
                    else buttonNext.text = getString(R.string.btn_next)
                }
            })
        }
        dotIndicator.attachTo(viewPager)
    }

    private fun clickListeners() = with(tutorialActivityBinding) {
        buttonNext.setOnClickListener(this@TutorialActivity)
        buttonContinue.setOnClickListener(this@TutorialActivity)
        textViewSkip.setOnClickListener(this@TutorialActivity)
    }

    override fun onClick(v: View) = with(tutorialActivityBinding) {
        when (v) {
            buttonNext -> {
                if (count == 2) {
                    navigateToAuthActivity()
                } else if (count <= 1) {
                    viewPager.currentItem = ++count
                }
            }
            textViewSkip -> {
                navigateToAuthActivity()
            }
        }
    }

    private fun navigateToAuthActivity() {
        session.isInitial = false
        loadActivity(AuthActivity::class.java).byFinishingCurrent().start()
    }
}