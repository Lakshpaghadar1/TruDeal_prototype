package com.trudeals.ui.isolated.customeruser.bottomsheet

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.R
import com.trudeals.databinding.AdvanceFilterBottomsheetBinding
import com.trudeals.di.component.BottomSheetComponent
import com.trudeals.ui.base.BaseBottomSheet
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.home.customeruser.fragment.HomeFragmentCU
import com.trudeals.ui.isolated.customeruser.adapter.LivingStatusAdapter
import com.trudeals.ui.isolated.customeruser.adapter.PropertyTypeAdapter
import com.trudeals.ui.isolated.customeruser.fragment.FilterListFragment
import com.trudeals.ui.isolated.dummydata.MinMaxPrice
import com.trudeals.ui.isolated.dummydata.SelectBathrooms
import com.trudeals.ui.isolated.dummydata.SelectBeds
import com.trudeals.ui.isolated.optionsbottomsheet.OptionsBottomSheet
import com.trudeals.utils.DataUtils
import com.trudeals.utils.MainCategoryType
import com.trudeals.utils.TabType
import com.trudeals.utils.extension.trimmedText

class AdvanceFilterBottomsheet : BaseBottomSheet<AdvanceFilterBottomsheetBinding>(),
    View.OnClickListener {

    var sourceScreen: String? = null
    var currentTab: TabType = TabType.ALL

    private val propertyTypeAdapter by lazy {
        PropertyTypeAdapter()
    }

    private val livingStatusAdapter by lazy {
        LivingStatusAdapter()
    }

    override fun createViewBinding(inflater: LayoutInflater): AdvanceFilterBottomsheetBinding {
        return AdvanceFilterBottomsheetBinding.inflate(inflater)
    }

    override fun inject(bottomSheetComponent: BottomSheetComponent) {
        bottomSheetComponent.inject(this)
    }

    override fun bindData() {
        init()
        setRecyclerView()
        clickListeners()
    }

    override fun destroyViewBinding() {}

    private fun init() = with(binding) {
        layoutEditTextMinPrice.editText.hint = "15K"
        layoutEditTextMaxPrice.editText.hint = "20K"
    }

    private fun clickListeners() = with(binding) {
        layoutEditTextMinPrice.editText.setOnClickListener(this@AdvanceFilterBottomsheet)
        layoutEditTextMaxPrice.editText.setOnClickListener(this@AdvanceFilterBottomsheet)
        editTextSelectBeds.setOnClickListener(this@AdvanceFilterBottomsheet)
        editTextSelectBathrooms.setOnClickListener(this@AdvanceFilterBottomsheet)
        buttonApply.setOnClickListener(this@AdvanceFilterBottomsheet)
        buttonCancel.setOnClickListener(this@AdvanceFilterBottomsheet)
        buttonClear.setOnClickListener(this@AdvanceFilterBottomsheet)

        propertyTypeAdapter.setOnItemClickPositionListener { _, position ->
            propertyTypeAdapter.changeSelection(position, true)
        }

        livingStatusAdapter.setOnItemClickPositionListener { _, position ->
            livingStatusAdapter.changeSelection(position, true)
        }
    }

    private fun setRecyclerView() = with(binding) {
        recyclerViewPropertyType.apply {
            propertyTypeAdapter.setItems(DataUtils.propertyType(), 1)
            adapter = propertyTypeAdapter
        }

        recyclerViewLivingStatus.apply {
            livingStatusAdapter.setItems(DataUtils.livingStatus(), 1)
            layoutManager = GridLayoutManager(requireContext(), resources.getInteger(R.integer.int_2), RecyclerView.VERTICAL, false)
            adapter = livingStatusAdapter
        }
    }

    override fun onClick(v: View): Unit = with(binding) {
        when (v) {
            layoutEditTextMinPrice.editText -> {
                OptionsBottomSheet<MinMaxPrice>().setTitle("Select Minimum Price")
                    .setOptionsList(DataUtils.minPrice())
                    .setOnPositiveButtonClickListener { selectedOption ->
                        layoutEditTextMinPrice.editText.setText(selectedOption.option)
                    }.setSelectedOption(layoutEditTextMinPrice.editText.trimmedText)
                    .show(childFragmentManager, "MIN_PRICE_DIALOG")
            }
            layoutEditTextMaxPrice.editText -> {
                OptionsBottomSheet<MinMaxPrice>().setTitle("Select Maximum Price")
                    .setOptionsList(DataUtils.maxPrice())
                    .setOnPositiveButtonClickListener { selectedOption ->
                        layoutEditTextMaxPrice.editText.setText(selectedOption.option)
                    }.setSelectedOption(layoutEditTextMaxPrice.editText.trimmedText)
                    .show(childFragmentManager, "MAX_PRICE_DIALOG")
            }
            editTextSelectBeds -> {
                OptionsBottomSheet<SelectBeds>().setTitle("Select Beds")
                    .setOptionsList(DataUtils.selectBeds())
                    .setOnPositiveButtonClickListener { selectedOption ->
                        editTextSelectBeds.setText(selectedOption.option)
                    }.setSelectedOption(editTextSelectBeds.trimmedText)
                    .show(childFragmentManager, "SELECT_BEDS")
            }
            editTextSelectBathrooms -> {
                OptionsBottomSheet<SelectBathrooms>().setTitle("Select Baths")
                    .setOptionsList(DataUtils.selectBathrooms())
                    .setOnPositiveButtonClickListener { selectedOption ->
                        editTextSelectBathrooms.setText(selectedOption.option)
                    }.setSelectedOption(editTextSelectBathrooms.trimmedText)
                    .show(childFragmentManager, "SELECT_BATHROOMS")
            }
            buttonApply -> {
                this@AdvanceFilterBottomsheet.dismiss()
                if (sourceScreen == HomeFragmentCU::class.java.simpleName) {
                    navigator.loadActivity(
                        IsolatedFullActivity::class.java,
                        FilterListFragment::class.java
                    ).addBundle(
                        FilterListFragment.createBundle(
                            currentTab = currentTab, mainCategoryType = MainCategoryType.REAL_ESTATE
                        )
                    ).start()
                }
            }
            buttonCancel -> {
                this@AdvanceFilterBottomsheet.dismiss()
            }
            buttonClear -> {
                clearData()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun clearData() = with(binding) {
        layoutEditTextMinPrice.editText.text?.clear()
        layoutEditTextMaxPrice.editText.text?.clear()
        editTextSelectBeds.text?.clear()
        editTextSelectBathrooms.text?.clear()

        //make first item selected as default
        propertyTypeAdapter.items?.let { items ->
            for (i in items.indices) {
                items[i].isSelected = i == 0
            }
        }
        propertyTypeAdapter.notifyDataSetChanged()

        //make each item not selected as default
        livingStatusAdapter.updateItem(predicate = { it.isSelected }) {
            it.isSelected = false
        }
    }
}