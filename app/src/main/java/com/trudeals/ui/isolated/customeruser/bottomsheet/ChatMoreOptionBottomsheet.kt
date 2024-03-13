package com.trudeals.ui.isolated.customeruser.bottomsheet

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.databinding.ChatMoreOptionBottomsheetBinding
import com.trudeals.di.component.BottomSheetComponent
import com.trudeals.ui.base.BaseBottomSheet
import com.trudeals.ui.isolated.customeruser.adapter.ChatMoreOptionAdapter
import com.trudeals.ui.isolated.dummydata.ChatMoreOption
import com.trudeals.utils.DataUtils

class ChatMoreOptionBottomsheet : BaseBottomSheet<ChatMoreOptionBottomsheetBinding>(),
    View.OnClickListener {

    private var onClickOfItem: ((data: ChatMoreOption) -> Unit)? = null

    private val chatMoreOptionAdapter by lazy {
        ChatMoreOptionAdapter()
    }

    override fun createViewBinding(inflater: LayoutInflater): ChatMoreOptionBottomsheetBinding {
        return ChatMoreOptionBottomsheetBinding.inflate(inflater)
    }

    override fun inject(bottomSheetComponent: BottomSheetComponent) {
        bottomSheetComponent.inject(this)
    }

    override fun bindData() {
        setRecyclerView()
        clickListeners()
    }

    override fun destroyViewBinding() {}

    private fun setRecyclerView() = with(binding.recyclerViewOptions) {
        chatMoreOptionAdapter.setItems(DataUtils.chatMoreOption(), 1)
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter = chatMoreOptionAdapter
    }

    private fun clickListeners() = with(binding) {
        buttonCancel.setOnClickListener(this@ChatMoreOptionBottomsheet)

        chatMoreOptionAdapter.setOnItemClickPositionListener { item, _ ->
            onClickOfItem?.invoke(item)
        }
    }

    override fun onClick(v: View): Unit = with(binding) {
        when (v) {
            buttonCancel -> {
                this@ChatMoreOptionBottomsheet.dismiss()
            }
        }
    }

    fun setClickOnItem(onClickOfItem: (data: ChatMoreOption) -> Unit) {
        this.onClickOfItem = onClickOfItem
    }
}