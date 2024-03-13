package com.trudeals.ui.isolated.businessuser.fragment

import android.app.Activity
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import com.trudeals.R
import com.trudeals.databinding.AddBusinessProfileDetailsFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.exception.ApplicationException
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.home.businessuser.HomeActivityBU
import com.trudeals.ui.home.businessuser.fragment.HomeFragmentBU
import com.trudeals.ui.isolated.customeruser.fragment.AllCategoryChpFragment
import com.trudeals.ui.isolated.dummydata.BusinessProfileDetail
import com.trudeals.ui.isolated.dummydata.BusinessProfileDetailMain
import com.trudeals.utils.StepsCount
import com.trudeals.utils.extension.*

class AddBusinessProfileDetailsFragment : BaseFragment<AddBusinessProfileDetailsFragmentBinding>(),
    View.OnClickListener {
    private val sourceScreen by lazy { arguments?.getString(SOURCE_SCREEN) }
    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): AddBusinessProfileDetailsFragmentBinding {
        return AddBusinessProfileDetailsFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        setFilters()
        clickListener()
        setData()
    }

    override fun setUpToolbar() {}

    private fun setFilters() = with(binding) {
        editTextBusinessName.setTextConstraint()
        editTextBusinessName.filters = arrayOf(
            InputFilter.LengthFilter(getInteger(R.integer.int_name_max_length)),
            editTextBusinessName.applyFilter(applyEmojiFilter = true)
        )
        editTextBusinessDescription.filters = arrayOf(
            InputFilter.LengthFilter(getInteger(R.integer.int_description_max_length)),
            editTextBusinessDescription.applyFilter()
        )
        editTextBusinessAddress.filters =
            arrayOf(editTextBusinessAddress.applyFilter(applyEmojiFilter = true))
        editTextBusinessCity.setTextConstraint()
        editTextBusinessCity.filters =
            arrayOf(editTextBusinessAddress.applyFilter(applyEmojiFilter = true))
        editTextBusinessState.setTextConstraint()
        editTextBusinessState.filters =
            arrayOf(editTextBusinessAddress.applyFilter(applyEmojiFilter = true))
        //editTextBusinessZipCode.setZipCodeFormat(editTextBusinessZipCode)
        editTextBusinessZipCode.filters = arrayOf(
            editTextBusinessZipCode.applyFilter()/*,
            InputFilter.LengthFilter(getInteger(R.integer.int_zip_code_max_length))*/
        )
        editTextBusinessLandmarks.filters =
            arrayOf(editTextBusinessAddress.applyFilter(applyEmojiFilter = true))
    }

    private fun setData() {
        when (sourceScreen) {
            HomeFragmentBU::class.java.simpleName, HomeActivityBU::class.java.simpleName -> {
                setDefaultData()
            }
        }
    }

    private fun setDefaultData() = with(binding) {
        editTextBusinessName.setText(getString(R.string.label_x, "Salon"))
        editTextBusinessDescription.setText(getString(R.string.label_x, "xyz"))
        editTextSelectBusinessCategory.setText(getString(R.string.label_x, "Automotive"))
        editTextBusinessAddress.setText(getString(R.string.label_x, "xyz"))
        editTextBusinessCity.setText(getString(R.string.label_x, "xyz"))
        editTextBusinessState.setText(getString(R.string.label_x, "xyz"))
        editTextBusinessZipCode.setText(getString(R.string.label_x, "xyz"))
        editTextBusinessLandmarks.setText(getString(R.string.label_x, "xyz"))
    }

    private fun clickListener() = with(binding) {
        editTextBusinessDescription.scrollableText()
        setCounterTextWatcher(editTextBusinessDescription, descriptionCounter)
        editTextSelectBusinessCategory.setOnClickListener(this@AddBusinessProfileDetailsFragment)
        buttonNext.setOnClickListener(this@AddBusinessProfileDetailsFragment)
    }

    private val selectedCategory =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.extras?.getString("SELECTED_CATEGORY").let { selectedCategory ->
                    binding.editTextSelectBusinessCategory.setText(selectedCategory)
                }
            }
        }

    override fun onClick(v: View): Unit = with(binding) {
        when (v) {
            editTextSelectBusinessCategory -> {
                navigator.loadActivity(
                    IsolatedFullActivity::class.java,
                    AllCategoryChpFragment::class.java
                ).onResultActivity(selectedCategory).addBundle(
                    AllCategoryChpFragment
                        .createBundle(AddBusinessProfileDetailsFragment::class.java.simpleName)
                ).start()
            }
            buttonNext -> {
                if (validation()) {
                    (parentFragment as CreateBusinessProfileMainFragment).navigateToNextStep(
                        getData(), StepsCount.STEP_TWO
                    )
                }
            }
            else -> {}
        }
    }

    private fun validation(): Boolean = with(binding) {
        return try {
            with(validator) {
                submit(editTextBusinessName).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_business_name)).check()

                submit(editTextBusinessDescription).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_business_description)).check()

                submit(editTextSelectBusinessCategory).checkEmpty()
                    .errorMessage(getString(R.string.validation_select_business_category)).check()

                submit(editTextBusinessAddress).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_address))
                    .check()

                submit(editTextBusinessCity).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_city))
                    .check()

                submit(editTextBusinessState).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_state))
                    .check()

                submit(editTextBusinessZipCode).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_zip_code))
                    /*.matchPatter(RegexConstant.ZIP_CODE_REGEX)
                    .errorMessage(getString(R.string.validation_enter_valid_zip_code))*/
                    .check()

                /*submit(editTextBusinessLandmarks).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_landmarks))
                    .check()*/
                true
            }
        } catch (e: ApplicationException) {
            showMessage(e)
            false
        }
    }

    private fun getData(): BusinessProfileDetailMain = with(binding) {
        val data = BusinessProfileDetailMain()
        data.businessProfileDetail =
            BusinessProfileDetail() // Initialize businessProfileDetail if it is null
        data.apply {
            businessProfileDetail?.apply {
                name = editTextBusinessName.trimmedText
                description = editTextBusinessDescription.trimmedText
                category = editTextSelectBusinessCategory.trimmedText
                address = editTextBusinessAddress.trimmedText
                city = editTextBusinessCity.trimmedText
                state = editTextBusinessState.trimmedText
                zipCode = editTextBusinessZipCode.trimmedText
                landmarks = editTextBusinessLandmarks.trimmedText
            }
        }
        return@with data
    }

    companion object {
        private const val SOURCE_SCREEN = "SOURCE_SCREEN"

        fun createBundle(sourceScreen: String) = bundleOf(SOURCE_SCREEN to sourceScreen)
    }

}