package com.trudeals.ui.isolated.customeruser.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.R
import com.trudeals.databinding.RequestFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.isolated.customeruser.adapter.RequestListAdapter
import com.trudeals.utils.DataUtils
import com.trudeals.utils.OnClick
import com.trudeals.utils.RequestCategoryType

class RequestFragment : BaseFragment<RequestFragmentBinding>(), View.OnClickListener {

    private var selectedCategoryType: RequestCategoryType = RequestCategoryType.REQUESTED
    private val requestListAdapter by lazy {
        RequestListAdapter()
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): RequestFragmentBinding {
        return RequestFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        setRequestedAsDefaultSelection()
        setRecyclerView()
        clickListener()
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true)
            .showBackButton(true)
            .setToolbarTitle(getString(R.string.label_request))
            .setToolbarElevation(R.dimen._1sdp)
            .build()
    }

    private fun clickListener() = with(binding) {
        buttonRequested.setOnClickListener(this@RequestFragment)
        buttonAccepted.setOnClickListener(this@RequestFragment)
        buttonCompleted.setOnClickListener(this@RequestFragment)

        requestListAdapter.setOnItemClickPositionListener { _, _ ->
            navigator.loadActivity(
                IsolatedFullActivity::class.java,
                RequestDetailFragment::class.java
            ).addBundle(
                RequestDetailFragment.createBundle(
                    null,
                    selectedCategoryType,
                    RequestFragment::class.java.simpleName
                )
            ).start()
        }

        requestListAdapter.setOnClickOfItemView { item, _, onClick ->
            when (onClick) {
                OnClick.REJECT -> {
                    showMessage("REJECTED")
                }
                OnClick.ACCEPT -> {
                    showMessage("ACCEPTED")
                }
                OnClick.CHAT -> {
                    navigator.loadActivity(
                        IsolatedFullActivity::class.java,
                        ChatFragment::class.java
                    ).addBundle(ChatFragment.createBundle(item.userName)).start()
                }
                else -> {}
            }
        }
    }

    override fun onClick(v: View): Unit = with(binding) {
        when (v) {
            buttonRequested -> {
                setSelection(RequestCategoryType.REQUESTED)
            }
            buttonAccepted -> {
                setSelection(RequestCategoryType.ACCEPTED)
            }
            buttonCompleted -> {
                setSelection(RequestCategoryType.COMPLETED)
            }
        }
    }


    private fun setSelection(selection: RequestCategoryType) {
        when (selection) {
            RequestCategoryType.REQUESTED -> {
                setRequestedAsDefaultSelection()
            }
            RequestCategoryType.ACCEPTED -> {
                setAcceptedSelection()
            }
            RequestCategoryType.COMPLETED -> {
                setCompletedSelection()
            }
        }
    }

    private fun setRequestedAsDefaultSelection() = with(binding) {
        setDefaultSelection()
        buttonRequested.isSelected = true
        selectedCategoryType = RequestCategoryType.REQUESTED
        requestListAdapter.getSelectedRequestType(selectedCategoryType)
        requestListAdapter.setItems(DataUtils.requestedList(), 1)
    }

    private fun setAcceptedSelection() = with(binding) {
        setDefaultSelection()
        buttonAccepted.isSelected = true
        selectedCategoryType = RequestCategoryType.ACCEPTED
        requestListAdapter.getSelectedRequestType(selectedCategoryType)
        requestListAdapter.setItems(DataUtils.acceptedList(), 1)
    }

    private fun setCompletedSelection() = with(binding) {
        setDefaultSelection()
        buttonCompleted.isSelected = true
        selectedCategoryType = RequestCategoryType.COMPLETED
        requestListAdapter.getSelectedRequestType(selectedCategoryType)
        requestListAdapter.setItems(DataUtils.completedList(), 1)
    }

    private fun setDefaultSelection() = with(binding) {
        buttonRequested.isSelected = false
        buttonAccepted.isSelected = false
        buttonCompleted.isSelected = false
    }

    private fun setRecyclerView() = with(binding.recyclerViewRequestList) {
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter = requestListAdapter
    }
}