package com.trudeals.ui.isolated.customeruser.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.trudeals.R
import com.trudeals.databinding.SearchFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.isolated.customeruser.adapter.PopularSearchAdapter
import com.trudeals.utils.DataUtils
import com.trudeals.utils.constants.Constants
import com.trudeals.utils.extension.trimmedText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : BaseFragment<SearchFragmentBinding>() {

    private val popularSearchAdapter by lazy {
        PopularSearchAdapter()
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): SearchFragmentBinding {
        return SearchFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        setRecyclerView()
        clickListeners()
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true)
            .showBackButton(true)
            .setToolbarTitle(getString(R.string.label_search))
            .setToolbarElevation(R.dimen._1sdp).build()
    }

    private fun setRecyclerView() = with(binding.recyclerViewPopularSearch) {
        popularSearchAdapter.setItems(DataUtils.popularSearches(), 1)
        adapter = popularSearchAdapter
    }

    private fun clickListeners() = with(binding) {
        popularSearchAdapter.setOnItemClickPositionListener { item, _ ->
            textInputEditTextSearch.setText(item)
            lifecycleScope.launch {
                showLoader()
                delay(300)
                hideLoader()
                requireActivity().finish()
            }
        }

        actionSearch()
    }

    private fun actionSearch() = with(binding) {
        textInputEditTextSearch.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (textInputEditTextSearch.trimmedText.isEmpty()) {
                    showMessage(getString(R.string.validation_enter_search_keyword))
                } else {
                    lifecycleScope.launch {
                        showLoader()
                        delay(300)
                        hideLoader()
                        val bundle = Bundle()
                        bundle.putString(Constants.SEARCH_RESULT, textInputEditTextSearch.trimmedText)
                        activity?.apply {
                            val intent = Intent()
                            intent.putExtras(bundle)
                            setResult(Activity.RESULT_OK, intent)
                        }
                        requireActivity().finish()
                    }
                    return@OnEditorActionListener true
                }
            }
            false
        })
    }
}