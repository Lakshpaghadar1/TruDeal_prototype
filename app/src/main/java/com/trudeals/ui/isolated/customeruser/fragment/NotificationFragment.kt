package com.trudeals.ui.isolated.customeruser.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.R
import com.trudeals.databinding.NotificationFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.isolated.customeruser.adapter.NotificationAdapter
import com.trudeals.utils.DataUtils
import com.trudeals.utils.extension.isVisible
import com.trudeals.utils.hideView
import com.trudeals.utils.showView
import com.trudeals.utils.toolbar.MenuItem
import com.trudeals.utils.toolbar.MenuItemTag
import com.trudeals.utils.toolbar.MenuItemType
import com.trudeals.utils.toolbar.ShowAsAction

class NotificationFragment : BaseFragment<NotificationFragmentBinding>() {

    private val notificationAdapter by lazy {
        NotificationAdapter()
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): NotificationFragmentBinding {
        return NotificationFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        setRecyclerView()
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true)
            .showBackButton(true)
            .setToolbarTitle(getString(R.string.label_notifictaions))
            .addMenuItems(
                MenuItem(
                    menuItemType = MenuItemType.TITLE,
                    title = getString(R.string.btn_clear),
                    tag = MenuItemTag.Clear,
                    showAsAction = ShowAsAction.ALWAYS
                )
            )
            .setToolbarElevation(R.dimen._1sdp)
            .build()

        setOnMenuItemClickListener { menuItem ->
            when (menuItem.tag) {
                MenuItemTag.Clear -> {
                    binding.apply {
                        showView(constraintLayoutNoNotification)
                        hideView(recyclerViewNotification)
                        updateMenuItem(
                            predicate = { it.tag == MenuItemTag.Clear },
                            menuItemToUpdate = {
                                it.titleColor = R.color.C_4D000000
                                //pending:  disable click of clear text
                            })
                    }
                }
                else -> {}
            }
        }
    }

    private fun setRecyclerView() = with(binding.recyclerViewNotification) {
        notificationAdapter.setItems(DataUtils.notificationList(), 1)
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter = notificationAdapter
    }
}