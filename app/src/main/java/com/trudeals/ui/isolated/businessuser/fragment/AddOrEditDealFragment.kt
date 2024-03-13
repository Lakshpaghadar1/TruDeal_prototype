package com.trudeals.ui.isolated.businessuser.fragment

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.R
import com.trudeals.databinding.AddOrEditDealFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.home.dummydata.BusinessHomeListItem
import com.trudeals.ui.isolated.businessuser.adapter.BusinessDealListAdapter
import com.trudeals.ui.isolated.dummydata.BusinessDeal
import com.trudeals.utils.DataUtils
import com.trudeals.utils.OnClick
import com.trudeals.utils.dialog.DialogUtils

class AddOrEditDealFragment : BaseFragment<AddOrEditDealFragmentBinding>(), View.OnClickListener {

    private var isEdit: Boolean = false
    private var position: Int = -1
    private val businessDealListAdapter by lazy { BusinessDealListAdapter() }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): AddOrEditDealFragmentBinding {
        return AddOrEditDealFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        setRecyclerView()
        clickListener()
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true)
            .showBackButton(true)
            .setToolbarTitle(getString(R.string.label_add_or_edit_deals))
            .setToolbarElevation(R.dimen._1sdp)
            .build()
    }

    private fun setRecyclerView() = with(binding.recyclerViewDeals) {
        businessDealListAdapter.setItems(DataUtils.setDealList(), 1)
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter = businessDealListAdapter
    }

    private val dataAsResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.extras?.getParcelable<BusinessDeal>(AddOrEditDealDetailsFragment.DATA)?.let { data ->
                    if (!isEdit) businessDealListAdapter.addItem(data)
                    else businessDealListAdapter.updateItem(position, data)
                }
            }
        }


    private fun clickListener() = with(binding) {
        buttonAddNewDeal.setOnClickListener(this@AddOrEditDealFragment)
        businessDealListAdapter.setOnItemClickPositionListener { _, _ ->
            navigator.loadActivity(
                IsolatedFullActivity::class.java,
                BusinessDealDetailFragment::class.java
            ).addBundle(
                BusinessDealDetailFragment.createBundle(
                    BusinessHomeListItem(),  //temporary managed 2 data class that is why - passing 2 data class here - will manage at API time
                    BusinessDeal(),
                    AddOrEditDealFragment::class.java.simpleName
                )
            ).start()
        }

        businessDealListAdapter.setOnClickOfView { _, parentItem, _, parentPosition, onClick ->
            when (onClick) {
                OnClick.ACTIVE -> {
                    showMessage("ACTIVE")
                }
                OnClick.PAUSE -> {
                    showMessage("PAUSE")
                }
                OnClick.PLAY -> {
                    showMessage("PLAY")
                }
                OnClick.EDIT -> {
                    isEdit = true
                    position = parentPosition
                    navigator.loadActivity(
                        IsolatedFullActivity::class.java,
                        AddOrEditDealDetailsFragment::class.java
                    ).onResultActivity(dataAsResult).addBundle(
                        AddOrEditDealDetailsFragment.createBundle(
                            parentItem,
                            OnClick.EDIT,
                            getString(R.string.label_edit_deal)
                        )
                    ).start() //currently passing empty detail data for next screen in bundle
                }
                OnClick.DELETE -> {
                    showDialogOnDeleteImage(parentItem)
                }
                else -> {}
            }
        }
    }

    override fun onClick(v: View) = with(binding) {
        when (v) {
            buttonAddNewDeal -> {
                isEdit = false
                navigator.loadActivity(
                    IsolatedFullActivity::class.java,
                    AddOrEditDealDetailsFragment::class.java
                ).onResultActivity(dataAsResult).addBundle(
                    AddOrEditDealDetailsFragment.createBundle(
                        BusinessDeal(),
                        OnClick.ADD_NEW,
                        getString(R.string.label_add_deal)
                    )
                )
                    .start() //passing empty data in bundle as its compulsory to pass, no requirement on add deal
            }
        }
    }

    private fun showDialogOnDeleteImage(parentItem: BusinessDeal) {
        DialogUtils.showAlertDialog(
            requireContext(),
            message = R.string.label_are_you_sure_you_want_to_delete_a_deal,
            onPositiveButtonClick = {
                businessDealListAdapter.removeItem(parentItem)
            }
        )
    }
}