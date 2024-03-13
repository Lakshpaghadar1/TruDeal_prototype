package com.trudeals.ui.isolated.optionsbottomsheet

import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.databinding.OptionsBottomSheetBinding
import com.trudeals.di.component.BottomSheetComponent
import com.trudeals.ui.base.BaseBottomSheet

class OptionsBottomSheet<E : Option> : BaseBottomSheet<OptionsBottomSheetBinding>() {

    private val optionsAdapter by lazy {
        OptionsAdapter<E>()
    }
    private var optionsList: List<E> = emptyList()

    private var title: String? = null

    private var positiveButtonText: String? = null

    //private var description: String? = null

    private var selectedOption: String? = null

    private var allowSelection = true

    private var onPositiveButtonClick: ((selectedOption: E) -> Unit)? = null

    override fun inject(bottomSheetComponent: BottomSheetComponent) {
    }

    override fun createViewBinding(inflater: LayoutInflater): OptionsBottomSheetBinding {
        return OptionsBottomSheetBinding.inflate(inflater)
    }

    override fun bindData() {
        init()
        setUpOptionsRecyclerView()
        setUpClickListener()
    }

    private fun init() = with(binding) {
        textViewTitle.text = title
        //textViewSubTitle.text = description
        positiveButtonText?.let { buttonSelect.text = positiveButtonText }
    }

    private fun setUpOptionsRecyclerView() = with(binding) {
        recyclerViewOptions.apply {
            /*val flexManager = FlexboxLayoutManager(requireContext())
            flexManager.flexDirection = FlexDirection.ROW
            flexManager.justifyContent = JustifyContent.FLEX_START
            layoutManager = flexManager*/
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = optionsAdapter

            if (selectedOption == null) {
                optionsList.firstOrNull()?.isSelected = true
            }
            optionsAdapter.setItems(optionsList as ArrayList, 1)
            optionsAdapter.allowSelection(allowSelection)

            optionsAdapter.updateItem(predicate = { it.option == selectedOption }) {
                it.isSelected = true
            }
        }
    }

    private fun setUpClickListener() = with(binding) {
        optionsAdapter.setOnItemClickPositionListener { item, position ->
            optionsAdapter.changeSelection(position, true)
        }

        buttonSelect.setOnClickListener {
            optionsAdapter.items!!.find { it.isSelected }?.apply {
                onPositiveButtonClick?.invoke(this)
            }
            selectedOption?.let {
                dismiss()
            }
        }

        textViewCancel.setOnClickListener {
            dismiss()
        }
    }

    fun setTitle(title: String) = apply {
        this.title = title
    }

    /*fun setDescription(description: String) = apply {
        this.description = description
    }*/

    fun allowSelection(allowSelection: Boolean) = apply {
        this.allowSelection = allowSelection
    }

    fun setPositiveButtonText(positiveButtonText: String) = apply {
        this.positiveButtonText = positiveButtonText
    }

    fun setOptionsList(optionList: List<E>) = apply {
        this.optionsList = optionList
    }

    fun setSelectedOption(selectedOption: String?) = apply {
        this.selectedOption = selectedOption
    }

    fun setOnPositiveButtonClickListener(onPositiveButtonClick: (selectedOption: E) -> Unit) =
        apply {
            this.onPositiveButtonClick = onPositiveButtonClick
        }

    override fun destroyViewBinding() {}
}