package com.trudeals.ui.isolated.customeruser.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.R
import com.trudeals.core.Session
import com.trudeals.databinding.DialogSelectUserBinding
import com.trudeals.ui.isolated.customeruser.adapter.UserSelectionAdapter
import com.trudeals.ui.isolated.dummydata.UserSelection
import com.trudeals.utils.DataUtils
import javax.inject.Inject

class UserDialog(
    context: Context,
    @StyleRes themeResId: Int,
    width: Int,
    x: Float,
    y: Float,
    selectedUser: String? = null,
    isREUSubscribed: Boolean = false,
    isBUSubscribed: Boolean = false
) :
    AlertDialog.Builder(context, themeResId) {
    @SuppressLint("InflateParams")
    var binding = DialogSelectUserBinding.inflate(LayoutInflater.from(context))

    @Inject
    lateinit var session: Session

    private var alertDialog: AlertDialog
    private var onCLickOfOption: ((selectedUser: UserSelection) -> Unit)? = null
    private val userSelectionAdapter by lazy { UserSelectionAdapter() }

    init {
        setView(binding.root)
        alertDialog = create()
        alertDialog.setCancelable(true)
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.show()

        binding.clInternalRoot.setOnClickListener { alertDialog.dismiss() }
        binding.clInternalRoot.layoutParams.width = context.resources.displayMetrics.widthPixels
        binding.clInternalRoot.layoutParams.height = context.resources.displayMetrics.heightPixels
        binding.recyclerViewUserType.layoutParams.width = width
        binding.recyclerViewUserType.x = x
        binding.recyclerViewUserType.y = y + 38f

        userSelectionAdapter.getSubscribedFlags(isREUSubscribed, isBUSubscribed)
        setRecyclerView(selectedUser)
        clickListeners(selectedUser)
        adjustDialog()
    }

    private fun setRecyclerView(selectedUser: String?) = with(binding.recyclerViewUserType) {
        layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        adapter = userSelectionAdapter

        val alList = DataUtils.userSelection()
        if (selectedUser == null) {
            alList.firstOrNull()?.isSelected = true
        }
        userSelectionAdapter.setItems(alList, 1)
        userSelectionAdapter.updateItem(predicate = { it.user == selectedUser }) {
            it.isSelected = true
        }

    }

    private fun clickListeners(selectedUser: String?) {
        userSelectionAdapter.setOnItemClickPositionListener { item, position ->
            userSelectionAdapter.changeSelection(position, true)
            onCLickOfOption?.invoke(item)
            selectedUser?.let {
                alertDialog.dismiss()
            }
        }
    }

    fun setOnOptionClick(onCLickOfOption: (selectedUser: UserSelection) -> Unit) {
        this.onCLickOfOption = onCLickOfOption
    }

    private fun adjustDialog() {
        val lp: WindowManager.LayoutParams = WindowManager.LayoutParams()
        lp.copyFrom(alertDialog.window?.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        alertDialog.window?.attributes = lp
        alertDialog.window?.setBackgroundDrawableResource(R.color.C_4D000000)
    }
}