package com.trudeals.ui.common.webview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.trudeals.R
import com.trudeals.databinding.WebviewFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment

class WebViewFragment : BaseFragment<WebviewFragmentBinding>() {

    private val title by lazy { arguments?.getInt(TITLE) }
    private val url by lazy { arguments?.getString(URL) }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): WebviewFragmentBinding {
        return WebviewFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        url?.let {
            binding.webView.loadUrl(it)
        }
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true)
        title?.let {
            setToolbarTitle(it)
        }
        showBackButton(true)
            .setToolbarElevation(R.dimen._1sdp)
            .build()
    }

    companion object {
        private const val TITLE = "TITLE"
        private const val URL = "URL"

        fun createBundle(title: Int, url: String) = bundleOf(TITLE to title, URL to url)
    }
}