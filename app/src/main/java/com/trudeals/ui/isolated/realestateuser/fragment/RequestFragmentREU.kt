package com.trudeals.ui.isolated.realestateuser.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.R
import com.trudeals.databinding.RealEstateRequestFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.isolated.dummydata.RealEstateRequestList
import com.trudeals.ui.isolated.realestateuser.adapter.RequestListAdapterREU
import com.trudeals.utils.ButtonStatus
import com.trudeals.utils.DataUtils
import com.trudeals.utils.OnClick
import com.trudeals.utils.dialog.DialogUtils

class RequestFragmentREU : BaseFragment<RealEstateRequestFragmentBinding>() {

    private val requestListAdapter by lazy {
        RequestListAdapterREU()
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): RealEstateRequestFragmentBinding {
        return RealEstateRequestFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() {
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
        requestListAdapter.setOnItemClickPositionListener { item, position ->
            /* navigator.loadActivity(
                 IsolatedFullActivity::class.java,
                 RequestDetailFragment::class.java
             ).addBundle(
                 RequestDetailFragment.createBundle(
                     null
                 )
             ).start()*/
        }

        requestListAdapter.setOnClickOfItemView { item, _, onClick ->
            when (onClick) {
                OnClick.REJECT -> {
                    showDialog(
                        R.string.label_are_you_sure_you_want_to_reject_a_request,
                        item,
                        ButtonStatus.REJECTED
                    )
                }
                OnClick.ACCEPT -> {
                    showDialog(
                        R.string.label_are_you_sure_you_want_to_accept_a_request,
                        item,
                        ButtonStatus.ACCEPTED
                    )
                }
                else -> {}
            }
        }
    }

    private fun showDialog(msg: Int, item: RealEstateRequestList, setButtonStatus: ButtonStatus) {
            DialogUtils.showAlertDialog(
                requireContext(),
                message = msg,
                onPositiveButtonClick = {
                    item.buttonStatus = setButtonStatus
                    requestListAdapter.updateItem(item)
                }
            )
        }

    private fun setRecyclerView() = with(binding.recyclerViewRequestList) {
        requestListAdapter.setItems(DataUtils.realEstateRequestList(), 1)
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter = requestListAdapter
    }
}