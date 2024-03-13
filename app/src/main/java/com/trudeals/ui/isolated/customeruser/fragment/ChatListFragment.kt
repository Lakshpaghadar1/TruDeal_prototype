package com.trudeals.ui.isolated.customeruser.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.R
import com.trudeals.databinding.ChatListFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.isolated.customeruser.adapter.ChatAdapter
import com.trudeals.utils.DataUtils
import com.trudeals.utils.OnClick

class ChatListFragment : BaseFragment<ChatListFragmentBinding>() {

    private val chatAdapter by lazy {
        ChatAdapter()
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): ChatListFragmentBinding {
        return ChatListFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        setRecyclerView()
        clickListeners()
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true)
            .showBackButton(true)
            .setToolbarTitle(getString(R.string.label_chat))
            .setToolbarElevation(R.dimen._1sdp)
            .build()
    }

    private fun clickListeners() = with(binding) {
        chatAdapter.setOnItemClickPositionListener { item, _ ->
            navigator.loadActivity(IsolatedFullActivity::class.java, ChatFragment::class.java)
                .addBundle(ChatFragment.createBundle(item.userName)).start()
        }

        chatAdapter.setClickOnView { item, _, onClick ->
            when (onClick) {
                OnClick.BLOCK -> {
                    showMessage("BLOCK")
                }
                OnClick.DELETE -> {
                    chatAdapter.removeItem(item)
                }
                else -> {}
            }
        }
    }

    private fun setRecyclerView() = with(binding.recyclerViewChat) {
        chatAdapter.setItems(DataUtils.chatList(), 1)
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter = chatAdapter
    }
}