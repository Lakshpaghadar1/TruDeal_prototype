package com.trudeals.ui.isolated.businessuser.fragment

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.R
import com.trudeals.databinding.AddOrEditDealDetailsFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.exception.ApplicationException
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.home.dummydata.BusinessHomeListItem
import com.trudeals.ui.isolated.dummydata.*
import com.trudeals.ui.isolated.optionsbottomsheet.OptionsBottomSheet
import com.trudeals.ui.isolated.realestateuser.adapter.AddDealImagesAdapter
import com.trudeals.ui.isolated.realestateuser.fragment.ImageLibraryMainFragment
import com.trudeals.utils.*
import com.trudeals.utils.constants.RegexConstant
import com.trudeals.utils.dialog.DialogUtils
import com.trudeals.utils.extension.*
import java.util.*

class AddOrEditDealDetailsFragment : BaseFragment<AddOrEditDealDetailsFragmentBinding>(),
    View.OnClickListener {

    private lateinit var startDate: Calendar
    private var isReUploadMedia = false
    private var position: Int = -1

    //private val addDealImagesAdapter by lazy { AddPropertyImagesAdapter() }
    private val addDealImagesAdapter by lazy { AddDealImagesAdapter() }
    private val data by lazy { arguments?.getParcelable<BusinessDeal>(DATA) }
    private val onClick by lazy { arguments?.getParcelable(ON_CLICK) ?: OnClick.ADD_NEW }
    private val title by lazy { arguments?.getString(TITLE) }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): AddOrEditDealDetailsFragmentBinding {
        return AddOrEditDealDetailsFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        clickListener()
        when (onClick) {
            OnClick.ADD_NEW -> {}
            OnClick.EDIT -> {
                setEditDefaultData()
            }
            else -> {}
        }
        setDefaultData()
        setFilter()
        //setMediaPicker()
        setImageRecyclerView()
        manageAddImagesBtn()
        with(binding) {
            editTextRetailValue.addTextChangedListener(textWatcher)
            editTextDealPrice.addTextChangedListener(textWatcher)
        }
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true)
            .showBackButton(true)
        title?.let {
            setToolbarTitle(it)
        }
        setToolbarElevation(R.dimen._1sdp)
            .build()
    }

    private fun setEditDefaultData() = with(binding) {
        editTextDealTitle.setText(getString(R.string.label_x, "Deal Name"))
        editTextDealDescription.setText(getString(R.string.label_x, "Deal Description"))
        editTextDealTCDescription.setText(getString(R.string.label_x, "Deal Terms and Conditions"))
        editTextNoOfDeals.setText(getString(R.string.label_x, "120"))
        editTextRetailValue.setText(getString(R.string.label_x, "500"))
        editTextDealPrice.setText(getString(R.string.label_x, "250"))
        editTextPercentageOff.setText(getString(R.string.label_x, "50"))
        editTextSelectTypeOfDeal.setText(
            getString(
                R.string.label_x,
                "Link Deal to My Shopping Cart"
            )
        )
        editTextPromoCode.setText(getString(R.string.label_x, "A250"))
        editTextShoppingCartUrl.setText(
            getString(
                R.string.label_x,
                "https://www.example.com/shopping-cart/cart-123"
            )
        )
    }

    private val handler = Handler(Looper.getMainLooper())
    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            handler.removeCallbacks(calculateDiscountRunnable)
            handler.postDelayed(calculateDiscountRunnable, 500L)
        }
    }

    private val calculateDiscountRunnable = Runnable {
        val retailPrice = binding.editTextRetailValue.trimmedText.toDoubleOrNull()
        val dealPrice = binding.editTextDealPrice.trimmedText.toDoubleOrNull()

        if (retailPrice != null && dealPrice != null) {
            if (dealPrice < retailPrice) {
                val discountPercentage = ((retailPrice - dealPrice) / retailPrice) * 100
                binding.editTextPercentageOff.setText(String.format("%.2f", discountPercentage))
            } else {
                showMessage(getString(R.string.validation_valid_deal_price))
                binding.editTextPercentageOff.text =
                    null // Clear the discount percentage if deal price is not less than retail price
            }
        }
    }


    private fun setDefaultData() = with(binding) {
        editTextBusinessName.setText(R.string.label_automotive)
        editTextDealCategory.setText(R.string.label_automotive)
        editTextDealCity.setText(getString(R.string.label_x, "Naples"))
        editTextDealState.setText(getString(R.string.label_x, "Florida"))

        textViewStartDateIs.text = getCurrentDate()
        startDate = Calendar.getInstance()
        textViewEndDateIs.text = getCurrentDate()
        textViewStartTimeIs.text = getCurrentTime()
        textViewEndTimeIs.text = getCurrentTime()
    }

    private fun setFilter() = with(binding) {
        editTextDealTitle.setTextConstraint()
        editTextDealTitle.filters = arrayOf(
            editTextDealTitle.applyFilter(applyEmojiFilter = true)
        )
        editTextDealDescription.filters = arrayOf(
            InputFilter.LengthFilter(getInteger(R.integer.int_description_max_length)),
            editTextDealDescription.applyFilter()
        )
        editTextDealTCDescription.filters = arrayOf(
            InputFilter.LengthFilter(getInteger(R.integer.int_description_max_length)),
            editTextDealTCDescription.applyFilter()
        )
        editTextDealCategory.filters = arrayOf(
            editTextDealCategory.applyFilter(applyEmojiFilter = true)
        )
        editTextDealCity.filters = arrayOf(
            editTextDealCity.applyFilter(applyEmojiFilter = true)
        )
        editTextDealState.filters = arrayOf(
            editTextDealState.applyFilter(applyEmojiFilter = true)
        )
        editTextPromoCode.filters = arrayOf(editTextPromoCode.applyFilter(applyEmojiFilter = true))
        editTextShoppingCartUrl.filters =
            arrayOf(editTextShoppingCartUrl.applyFilter(applyEmojiFilter = true))
    }

    private val dataAsResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.extras?.getParcelable<BusinessDeal>("DATA")?.let { data ->
                    val intent = Intent()
                    intent.putExtra(DATA, data) //set data
                    requireActivity().setResult(Activity.RESULT_OK, intent)
                    navigator.goBack()
                }
            }
        }

    private val getImagesList =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val bundle = result.data?.extras?.getParcelableArrayList<ImageLibrary>(
                    ImageLibraryMainFragment.LIST
                )
                bundle.let { list ->
                    list?.forEach { imageLibrary ->
                        if (result.data?.extras?.getBoolean(ImageLibraryMainFragment.IS_REUPLOAD) == true) {
                            addDealImagesAdapter.updateItem(position,
                                itemToUpdate = { it.imageUri = imageLibrary.profileImage })
                        } else {
                            addDealImagesAdapter.addItem(AddDealImages(imageUri = imageLibrary.profileImage))
                        }
                    }
                    binding.recyclerViewImages.isVisible(addDealImagesAdapter.items?.isNotEmpty()!!)
                    manageAddImagesBtn()
                }
            }
        }

    private fun clickListener() = with(binding) {
        //editTextDealDescription.scrollableText()
        //editTextDealTCDescription.scrollableText()
        setCounterTextWatcher(editTextDealDescription, dealDescriptionCounter)
        setCounterTextWatcher(editTextDealTCDescription, dealTCDescriptionCounter)

        textViewStartDateIs.setOnClickListener(this@AddOrEditDealDetailsFragment)
        textViewEndDateIs.setOnClickListener(this@AddOrEditDealDetailsFragment)
        textViewStartTimeIs.setOnClickListener(this@AddOrEditDealDetailsFragment)
        textViewEndTimeIs.setOnClickListener(this@AddOrEditDealDetailsFragment)
        editTextSelectTypeOfDeal.setOnClickListener(this@AddOrEditDealDetailsFragment)
        buttonUploadImages.setOnClickListener(this@AddOrEditDealDetailsFragment)
        buttonPreview.setOnClickListener(this@AddOrEditDealDetailsFragment)
        buttonSave.setOnClickListener(this@AddOrEditDealDetailsFragment)
    }

    override fun onClick(v: View): Unit = with(binding) {
        when (v) {
            editTextSelectTypeOfDeal -> {
                hideKeyBoard()
                OptionsBottomSheet<SelectTypeOfDeal>().setTitle("Select Type Of Deal")
                    .setOptionsList(DataUtils.selectTypeOfDeal())
                    .setOnPositiveButtonClickListener { selectedOption ->
                        editTextSelectTypeOfDeal.setText(selectedOption.option)
                        setEditTextVisibility(selectedOption)
                    }.setSelectedOption(editTextSelectTypeOfDeal.trimmedText)
                    .show(childFragmentManager, "SELECT_TYPE_OF_DEAL")
            }
            textViewStartDateIs -> {
                showStartDatePicker()
            }
            textViewEndDateIs -> {
                showEndDatePicker(startDate)
            }
            textViewStartTimeIs -> {
                showStartTimePickerDialog()
            }
            textViewEndTimeIs -> {
                //if (textViewStartDateIs.trimmedText == textViewEndDateIs.trimmedText) {
                showEndTimePickerDialog()
                //} else {}
            }
            buttonUploadImages -> {
                hideKeyBoard()
                /*isReUploadMedia = false
                //if (addPropertyImagesAdapter.items?.size!! <= 3)
                mediaSelectHelper.selectOptionsForImagePicker(true)*/
                if (addDealImagesAdapter.itemCount != 3) {
                    isReUploadMedia = false
                    navigator.loadActivity(
                        IsolatedFullActivity::class.java,
                        ImageLibraryMainFragment::class.java
                    ).addBundle(
                        ImageLibraryMainFragment.createBundle(
                            isReUploadMedia,
                            addDealImagesAdapter.items?.size ?: 0
                        )
                    )
                        .onResultActivity(getImagesList).start()
                } else {
                    showMessage("Maximum item count reached")
                }
            }
            buttonPreview -> {
                if (validation()) {
                    navigator.loadActivity(
                        IsolatedFullActivity::class.java,
                        BusinessDealDetailFragment::class.java
                    ).onResultActivity(dataAsResult).addBundle(
                        BusinessDealDetailFragment.createBundle(
                            BusinessHomeListItem(), //temporary managed 2 data class that is why - passing 2 data class here - will manage at API time
                            getData(),
                            AddOrEditDealDetailsFragment::class.java.simpleName
                        )
                    ).start()
                }
            }
            buttonSave -> {
                if (validation()) {
                    val intent = Intent()
                    intent.putExtra(DATA, getData()) //set data
                    requireActivity().setResult(Activity.RESULT_OK, intent)
                    navigator.goBack()
                }
            }
        }
    }

    private fun setEditTextVisibility(selectedOption: SelectTypeOfDeal) = with(binding) {
        when (selectedOption.tag) {
            TypeOfDeals.LINK_DEAL_TO_MY_SHOPPING_CART -> {
                showView(groupShoppingCartUrl, groupPromocode)
            }
            TypeOfDeals.PAY_AT_THE_POINT_OF_SALE -> {
                hideView(groupShoppingCartUrl, groupPromocode)
            }
        }
    }

    @JvmName("getData1")
    private fun getData(): BusinessDeal = with(binding) {
        val data = BusinessDeal()
        data.apply {
            dealName = editTextDealTitle.trimmedText
            discount = editTextPercentageOff.trimmedText.plus("%")
            startSaleDate = textViewStartDateIs.trimmedText
            endSaleDate = textViewEndDateIs.trimmedText
            startSaleTime = textViewStartTimeIs.trimmedText
            endSaleTime = textViewEndTimeIs.trimmedText
            views = getString(R.string.label_x, "200")
            redeems = getString(R.string.label_x, "45")
            shares = getString(R.string.label_x, "10")
        }
        return data
    }

    private fun setImageRecyclerView() = with(binding.recyclerViewImages) {
        itemTouchHelper.attachToRecyclerView(this)
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter = addDealImagesAdapter

        //set image recycler click listeners
        addDealImagesAdapter.setOnClickOfView { item, position, onClick ->
            when (onClick) {
                OnClick.DELETE -> {
                    showDialogOnDeleteImage(item)
                }
                OnClick.RE_UPLOAD -> {
                    isReUploadMedia = true
                    this@AddOrEditDealDetailsFragment.position = position
                    navigator.loadActivity(
                        IsolatedFullActivity::class.java,
                        ImageLibraryMainFragment::class.java
                    ).addBundle(
                        ImageLibraryMainFragment.createBundle(
                            isReUploadMedia,
                            addDealImagesAdapter.items?.size ?: 0
                        )
                    )
                        .onResultActivity(getImagesList).start()
                    //mediaSelectHelper.selectOptionsForImagePicker(true)
                }
                else -> {}
            }
        }
    }

    /*Media Selection with callback*//*
    private fun setMediaPicker() = with(binding) {
        mediaSelectHelper.canSelectMultipleImages(false)

        mediaSelectHelper.registerCallback(object : MediaSelector {
            override fun onImageUri(uri: Uri) {
                uri.path?.let {
                    if (isReUploadMedia) {
                        addDealImagesAdapter.updateItem(position,
                            itemToUpdate = { it.imageUri = uri })
                    } else {
                        addDealImagesAdapter.addItem(AddPropertyImages(uri))
                    }
                    recyclerViewImages.isVisible(addDealImagesAdapter.items?.isNotEmpty()!!)
                    manageAddImagesBtn()
                }
            }
        })
    }*/

    private val itemTouchHelper by lazy {
        val itemTouchCallback =
            object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    val fromPosition = viewHolder.bindingAdapterPosition
                    val toPosition = target.bindingAdapterPosition
                    Collections.swap(
                        addDealImagesAdapter.items ?: arrayListOf<AddPropertyImages>(),
                        fromPosition, toPosition
                    )
                    addDealImagesAdapter.notifyItemMoved(fromPosition, toPosition)
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
            }
        ItemTouchHelper(itemTouchCallback)
    }

    private fun manageAddImagesBtn() = with(binding) {
        if (recyclerViewImages.isVisible) buttonUploadImages.text =
            getString(R.string.btn_add_images)
        else buttonUploadImages.text = getString(R.string.btn_upload)
    }

    /*private fun showDialogOnDeleteImage(item: AddPropertyImages) = with(binding) {
        DialogUtils.showAlertDialog(
            requireContext(),
            message = R.string.label_are_you_sure_you_want_to_delete_an_image,
            onPositiveButtonClick = {
                addDealImagesAdapter.removeItem(item)
                recyclerViewImages.isVisible(addDealImagesAdapter.items?.isNotEmpty()!!)
                manageAddImagesBtn()
            }
        )
    }*/

    private fun showDialogOnDeleteImage(item: AddDealImages) = with(binding) {
        DialogUtils.showAlertDialog(
            requireContext(),
            message = R.string.label_are_you_sure_you_want_to_delete_an_image,
            onPositiveButtonClick = {
                addDealImagesAdapter.removeItem(item)
                recyclerViewImages.isVisible(addDealImagesAdapter.items?.isNotEmpty()!!)
                manageAddImagesBtn()
            }
        )
    }

    private fun validation(): Boolean = with(binding) {
        return try {
            with(validator) {
                submit(editTextDealTitle).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_deal_title)).check()

                submit(editTextDealDescription).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_deal_description)).check()

                submit(editTextDealTCDescription).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_deal_terms_and_conditions))
                    .check()

                /*if (addDealImagesAdapter.items.isNullOrEmpty()) {
                    showMessage(getString(R.string.validation_upload_images))
                    return false
                }*/

                submit(editTextNoOfDeals).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_no_of_deals_offered))
                    .check()

                submit(editTextRetailValue).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_retail_value))
                    .check()

                submit(editTextDealPrice).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_deal_price))
                    .check()

                /*if (editTextDealPrice.trimmedText.toInt() >= editTextRetailValue.trimmedText.toInt()) {
                    showMessage(getString(R.string.validation_valid_deal_price))
                    return false
                }*/

                submit(editTextPercentageOff).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_percentage_off))
                    /*.matchPatter(RegexConstant.PERCENTAGE_REGEX)
                    .errorMessage(getString(R.string.validation_enter_valid_percentage))*/
                    .check()

                if (editTextShoppingCartUrl.isVisible) {
                    submit(editTextSelectTypeOfDeal).checkEmpty()
                        .errorMessage(getString(R.string.validation_select_type_of_deal))
                        .check()
                }

                if (editTextPromoCode.isVisible) {
                    submit(editTextPromoCode).checkEmpty()
                        .errorMessage(getString(R.string.validation_enter_promocode))
                        .check()
                }

                submit(editTextShoppingCartUrl).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_shopping_url))
                    .matchPatter(RegexConstant.COMMON_URL)
                    //.matchPatter(RegexConstant.SHOPPING_CART_URL_REGEX)// eg, https://www.example.com/shopping-cart/cart-123
                    .errorMessage(getString(R.string.validation_enter_valid_shopping_url))
                    .check()

                true
            }
        } catch (e: ApplicationException) {
            showMessage(e)
            false
        }
    }

    private fun showStartTimePickerDialog() = with(binding) {
        val currentTime = Calendar.getInstance()
        val hour = currentTime.get(Calendar.HOUR_OF_DAY)
        val minute = currentTime.get(Calendar.MINUTE)

        TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->
            val selectedTime = getTimeString(selectedHour, selectedMinute)
            textViewStartTimeIs.text = selectedTime
        }, hour, minute, false).show()
    }

    private fun showEndTimePickerDialog() = with(binding) {
        val currentTime = Calendar.getInstance()
        val hour = currentTime.get(Calendar.HOUR_OF_DAY)
        val minute = currentTime.get(Calendar.MINUTE)

        TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->
            val selectedTime = getTimeString(selectedHour, selectedMinute)
            textViewStartTimeIs.text = selectedTime
            textViewEndTimeIs.text = getString(R.string.label_x, "00:00 PM")
        }, hour, minute, false).show()
    }

    /* private fun showEndTimePickerDialog(selectedTime: Calendar) = with(binding) {
         val calendar = Calendar.getInstance()
         val hour = calendar.get(Calendar.HOUR_OF_DAY)
         val minute = calendar.get(Calendar.MINUTE)

         TimePickerDialog(
             requireContext(),
             { _, hourOfDay, minute ->
                 val selectedCalendar = Calendar.getInstance()
                 selectedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                 selectedCalendar.set(Calendar.MINUTE, minute)

                 if (selectedCalendar.timeInMillis < selectedTime.timeInMillis) {
                     val selectedFormattedTime = formatTime(selectedCalendar)
                     textViewEndTimeIs.text = selectedFormattedTime
                 } else {
                     // Show an error message or handle invalid selection
                 }
             }, hour, minute, false
         ).show()
     }*/

    private val selectedCalendar = Calendar.getInstance()
    private fun showStartDatePicker() = with(binding) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            requireContext(),
            { _, yearIs, monthOfYear, dayOfMonth ->
                selectedCalendar.set(Calendar.YEAR, yearIs)
                selectedCalendar.set(Calendar.MONTH, monthOfYear)
                selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                startDate = selectedCalendar
                val selectedDate = formatDate(selectedCalendar)
                textViewStartDateIs.text = selectedDate
                textViewEndDateIs.text = selectedDate
            }, year, month, day
        ).show()
    }

    private fun showEndDatePicker(startDate: Calendar) = with(binding) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val endDatePickerDialog = DatePickerDialog(
            requireContext(),
            { _, yearIs, monthOfYear, dayOfMonth ->
                // Date selected, handle accordingly
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(Calendar.YEAR, yearIs)
                selectedCalendar.set(Calendar.MONTH, monthOfYear)
                selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val selectedDate = formatDate(selectedCalendar)
                textViewEndDateIs.text = selectedDate
            },
            year,
            month,
            day
        )

        // Set minimum date as the start date
        endDatePickerDialog.datePicker.minDate = startDate.timeInMillis

        // Set a custom DatePickerDialog.OnDateSetListener to disable dates after the start date
        endDatePickerDialog.datePicker.init(
            startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH),
            startDate.get(Calendar.DAY_OF_MONTH)
        ) { _, yearIs, monthOfYear, dayOfMonth ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(Calendar.YEAR, yearIs)
            selectedCalendar.set(Calendar.MONTH, monthOfYear)
            selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            // Disable dates after the start date
            if (selectedCalendar.timeInMillis < startDate.timeInMillis) {
                endDatePickerDialog.datePicker.updateDate(
                    startDate.get(Calendar.YEAR),
                    startDate.get(Calendar.MONTH),
                    startDate.get(Calendar.DAY_OF_MONTH)
                )
            }
        }
        endDatePickerDialog.show()
    }

    companion object {
        const val DATA = "DATA"
        private const val ON_CLICK = "ON_CLICK"
        private const val TITLE = "TITLE"

        fun createBundle(data: BusinessDeal, onClick: OnClick, title: String) =
            bundleOf(DATA to data, ON_CLICK to onClick, TITLE to title)
    }
}