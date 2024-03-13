package com.trudeals.ui.isolated.customeruser.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Rect
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.databinding.DialogSelectUserBinding
import com.trudeals.ui.base.basedialogfragment.BaseDialogComponent
import com.trudeals.ui.base.basedialogfragment.BaseDialogFragment
import com.trudeals.ui.isolated.customeruser.adapter.UserSelectionAdapter
import com.trudeals.ui.isolated.dummydata.UserSelection
import com.trudeals.utils.DataUtils

class DialogSelectUser : BaseDialogFragment<DialogSelectUserBinding>() {

    private var setUserType: String? = null
    private val userSelectionAdapter by lazy { UserSelectionAdapter() }
    private var onCLickOfOption: ((selectedUser: UserSelection) -> Unit)? = null

    override fun inject(baseDialogComponent: BaseDialogComponent) {
        baseDialogComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): DialogSelectUserBinding {
        return DialogSelectUserBinding.inflate(layoutInflater)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        return dialog
    }

    override fun bindData() {
        setRecyclerView()
        clickListeners()
    }
    private fun setRecyclerView() = with(binding.recyclerViewUserType) {
        userSelectionAdapter.setItems(DataUtils.userSelection(), 1)
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter = userSelectionAdapter

        if (setUserType == null) {
            DataUtils.userSelection().firstOrNull()?.isSelected = true
        }
        userSelectionAdapter.updateItem(predicate = {it.user == setUserType}) {
            it.isSelected = true
        }
    }

    private fun clickListeners() {
        userSelectionAdapter.setOnItemClickPositionListener { item, position ->
            userSelectionAdapter.changeSelection(position, true)
            onCLickOfOption?.invoke(item)
        }
    }

    fun setOnOptionClick(onCLickOfOption: (selectedUser: UserSelection) -> Unit) {
        this.onCLickOfOption = onCLickOfOption
    }

    fun setUserSelected(setUserType: String) {
        this.setUserType = setUserType
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onResume() {
        super.onResume()
        // Override the fragment's onResume method to set the dialog's onTouchListener to dismiss the dialog when touched outside
        dialog?.window?.decorView?.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val outRect = Rect()
                dialog?.window?.decorView?.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    dismiss()
                }
            }
            true
        }
    }

    companion object {
        private const val WIDTH = "WIDTH"
        private const val HEIGHT = "HEIGHT"

        fun createBundle(width: Int, height: Int) = bundleOf(WIDTH to width, HEIGHT to height)
    }
}