package com.trudeals.ui.isolated.customeruser.fragment

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.R
import com.trudeals.databinding.ChatFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.isolated.chat.*
import com.trudeals.ui.isolated.customeruser.bottomsheet.ChatMoreOptionBottomsheet
import com.trudeals.utils.hideView
import com.trudeals.utils.showView
import com.trudeals.utils.toolbar.MenuItem
import com.trudeals.utils.toolbar.MenuItemTag
import com.trudeals.utils.toolbar.MenuItemType
import com.trudeals.utils.toolbar.ShowAsAction

class ChatFragment : BaseFragment<ChatFragmentBinding>(), MessageAdapter.QuoteClickListener,
    View.OnClickListener {

    private val chatMoreOptionDialog by lazy { ChatMoreOptionBottomsheet() }

    private lateinit var mainActivityViewModel: MainActivityViewModel
    private var messageList = ArrayList<Message>()
    private var quotedMessagePos = -1

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): ChatFragmentBinding {
        return ChatFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        init()
        clickListener()
        setRecyclerView()
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true)
            .showBackButton(true)
            .setToolbarTitle(arguments?.getString(NAME) ?: "")
            .addMenuItems(
                MenuItem(
                    menuItemType = MenuItemType.ICON,
                    tag = MenuItemTag.Call,
                    icon = R.drawable.ic_call,
                    showAsAction = ShowAsAction.ALWAYS
                ),
                MenuItem(
                    menuItemType = MenuItemType.ICON,
                    tag = MenuItemTag.VideoCall,
                    icon = R.drawable.ic_video_call,
                    showAsAction = ShowAsAction.ALWAYS
                ),
                MenuItem(
                    menuItemType = MenuItemType.ICON,
                    tag = MenuItemTag.More,
                    icon = R.drawable.ic_more,
                    showAsAction = ShowAsAction.ALWAYS
                )
            )
            .setToolbarElevation(R.dimen._1sdp)
            .build()

        setOnMenuItemClickListener { menuItem ->
            when (menuItem.tag) {
                MenuItemTag.More -> {
                    chatMoreOptionDialog.show(
                        childFragmentManager,
                        ChatMoreOptionBottomsheet::class.java.simpleName
                    )
                }
                MenuItemTag.Call -> {
                    showMessage("CALL")
                }
                MenuItemTag.VideoCall -> {
                    showMessage("VIDEO CALL")
                }
                else -> {}
            }
        }
    }

    private fun init() = with(binding) {
        when {
            session.isCustomerUser || session.isGuestUser -> showView(cardViewPropertyDetails)
            session.isBusinessOwnerUser -> {
                showView(cardViewPropertyDetails)
                hideView(layoutStatus.root)
                imageViewProfileImage.setImageResource(R.drawable.dummy_image_automotive)
                textViewTitle.text = getString(R.string.dummy_car_washing)
                textViewLocation.text = getString(R.string.dummy_address)
            }
            session.isRealEstateUser -> hideView(cardViewPropertyDetails)
        }
    }

    private fun setRecyclerView() = with(binding.recyclerView) {
        mainActivityViewModel =
            ViewModelProviders.of(this@ChatFragment)[MainActivityViewModel::class.java]

        val adapterIs = MessageAdapter(requireContext())
        adapterIs.setQuoteClickListener(this@ChatFragment)
        adapter = adapterIs
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        linearLayoutManager.stackFromEnd = true
        layoutManager = linearLayoutManager

        val messageSwipeController =
            MessageSwipeController(requireContext(), object : SwipeControllerActions {
                override fun showReplyUI(position: Int) {
                    quotedMessagePos = position
                    showQuotedMessage(messageList[position])
                    binding.editorLayout.linearLayout.setBackgroundResource(R.drawable.bg_chat_wo_quoted_msg_reply)
                }
            })

        val itemTouchHelper = ItemTouchHelper(messageSwipeController)
        itemTouchHelper.attachToRecyclerView(this)


        binding.editorLayout.apply {
            sendButton.setOnClickListener {
                if (editMessage.text?.trim()?.isNotEmpty() == true) {

                    if (replyLayout.visibility == View.VISIBLE) {
                        hideReplyLayout()
                        mainActivityViewModel.addQuotedMessage(
                            editMessage.text!!.trim().toString(),
                            textQuotedMessage.text.toString(),
                            quotedMessagePos
                        )

                    } else {
                        mainActivityViewModel.addMessage(editMessage.text!!.trim().toString())
                    }
                    editMessage.text!!.clear()
                    binding.editorLayout.linearLayout.setBackgroundResource(R.drawable.bg_chat_reply)
                }
            }
        }

        mainActivityViewModel.getDisplayMessage()
            .observe(this@ChatFragment) { messages ->
                messageList.clear()
                messageList.addAll(messages)
                adapterIs.setMessages(messages)
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.recyclerView.smoothScrollToPosition(messageList.size - 1)
                }, 100)
            }


        binding.editorLayout.cancelButton.setOnClickListener {
            hideReplyLayout()
            binding.editorLayout.linearLayout.setBackgroundResource(R.drawable.bg_chat_reply)
        }

        setListeners()
    }

    private fun setListeners() = with(binding) {
        recyclerView.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            if (bottom < oldBottom) {
                if (messageList.isEmpty()) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        binding.recyclerView.scrollToPosition(messageList.size)
                    }, 300)
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        binding.recyclerView.scrollToPosition(messageList.size - 1)
                    }, 300)
                }
            }
        }
    }

    private fun hideReplyLayout() = with(binding.editorLayout) {
        replyLayout.requestLayout()
        replyLayout.forceLayout()
        replyLayout.visibility = View.GONE

    }

    private fun showQuotedMessage(message: Message) = with(binding.editorLayout) {
        editMessage.requestFocus()
        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(editMessage, InputMethodManager.SHOW_IMPLICIT)

        textQuotedMessage.text = message.body

        replyLayout.visibility = View.VISIBLE
    }

    companion object {
        private const val NAME = "NAME"
        fun createBundle(name: String) = bundleOf(NAME to name)
    }

    override fun onQuoteClick(position: Int) = with(binding) {
        recyclerView.smoothScrollToPosition(position - 1)
        (recyclerView.adapter as MessageAdapter).blinkItem(position)
    }

    private fun clickListener() = with(binding) {
        imageViewAlert.setOnClickListener(this@ChatFragment)
        chatMoreOptionDialog.setClickOnItem { chatMoreOption ->
            chatMoreOptionDialog.dismiss()
            showMessage(chatMoreOption.option)
        }
    }


    override fun onClick(v: View) = with(binding) {
        when (v) {
            imageViewAlert -> {
                showMessage("Product notification")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        with(binding) {
            // Scroll to the last position in the NestedScrollView
            nestedScrollview.post {
                nestedScrollview.fullScroll(View.FOCUS_DOWN)
            }
        }
    }
}