package com.trudeals.ui.isolated.realestateuser.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.os.Parcelable
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.DOWN
import androidx.recyclerview.widget.ItemTouchHelper.UP
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shockwave.pdfium.PdfDocument
import com.shockwave.pdfium.PdfiumCore
import com.trudeals.R
import com.trudeals.databinding.AddOrEditPropertyDetailsBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.exception.ApplicationException
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.isolated.dummydata.*
import com.trudeals.ui.isolated.optionsbottomsheet.OptionsBottomSheet
import com.trudeals.ui.isolated.realestateuser.adapter.AddPropertyDocAdapter
import com.trudeals.ui.isolated.realestateuser.adapter.AddPropertyImagesAdapter
import com.trudeals.ui.isolated.realestateuser.dialog.YearPickerDialog
import com.trudeals.utils.*
import com.trudeals.utils.dialog.DialogUtils
import com.trudeals.utils.extension.*
import com.trudeals.utils.imagepicker.MediaSelector
import com.trudeals.utils.imagepicker.OutPutFileAny
import com.trudeals.utils.textdecorator.TextDecorator
import java.io.File
import java.io.FileOutputStream
import java.util.*

class AddOrEditPropertyDetailsFragment : BaseFragment<AddOrEditPropertyDetailsBinding>(),
    View.OnClickListener {

    private var isReUploadMedia = false
    private var position: Int = -1
    private lateinit var videoPath: String

    private val addPropertyImagesAdapter by lazy { AddPropertyImagesAdapter() }
    private val addPropertyDocAdapter by lazy { AddPropertyDocAdapter() }
    private val yearPickerDialog by lazy { YearPickerDialog() }
    private var selectedYr: Int = Calendar.getInstance().get(Calendar.YEAR)

    private val currentTab by lazy { arguments?.getParcelable<PropertyListTag>(CURRENT_TAB) }
    private val onClick by lazy { arguments?.getParcelable<OnClick>(ON_CLICK) }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): AddOrEditPropertyDetailsBinding {
        return AddOrEditPropertyDetailsBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        init()
        setImageRecyclerView()
        setDocRecyclerView()
        clickListener()
    }

    override fun setUpToolbar() {}

    private fun init() {
        when (onClick) {
            OnClick.ADD_NEW -> {}
            OnClick.EDIT -> {
                setDefaultData()
            }
            else -> {}
        }
        manageAddImagesBtn()
        manageAddBrochuresBtn()
        setButtonVisibility()
        setFilters()
        setMediaPicker()
        setSpannable()
    }

    private fun setButtonVisibility() = with(binding) {
        when (currentTab) {
            PropertyListTag.HOME_FOR_SALE -> {
                showView(buttonPreview, buttonSave)
                hideView(buttonNext)
            }
            else -> {
                showView(buttonNext)
                hideView(buttonPreview, buttonSave)
            }
        }
    }

    private fun setFilters() = with(binding) {
        editTextTitle.setTextConstraint()
        editTextTitle.filters = arrayOf(
            InputFilter.LengthFilter(getInteger(R.integer.int_title_max_length)),
            editTextTitle.applyFilter(applyEmojiFilter = true)
        )
        editTextArea.filters = arrayOf(
            InputFilter.LengthFilter(getInteger(R.integer.int_area_max_length)),
            editTextArea.applyFilter(applyEmojiFilter = true)
        )
        editTextBuiltYear.filters = arrayOf(
            InputFilter.LengthFilter(getInteger(R.integer.int_built_in_year_min_max_length)),
            editTextBuiltYear.applyFilter(applyEmojiFilter = true)
        )
        editTextAddress.filters = arrayOf(
            editTextDescription.applyFilter(applyEmojiFilter = true)
        )
        editTextDescription.filters = arrayOf(
            InputFilter.LengthFilter(getInteger(R.integer.int_description_max_length)),
            editTextDescription.applyFilter()
        )
    }


    private fun setImageRecyclerView() = with(binding.recyclerViewImages) {
        itemTouchHelper.attachToRecyclerView(this)
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter = addPropertyImagesAdapter

        //set image recycler click listeners
        addPropertyImagesAdapter.setOnClickOfView { item, position, onClick ->
            when (onClick) {
                OnClick.DELETE -> {
                    showDialogOnDeleteImage(item)
                }
                OnClick.RE_UPLOAD -> {
                    isReUploadMedia = true
                    this@AddOrEditPropertyDetailsFragment.position = position
                    mediaSelectHelper.selectOptionsForImagePicker(true)
                }
                else -> {}
            }
        }
    }

    private fun setDocRecyclerView() = with(binding.recyclerViewDocuments) {
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter = addPropertyDocAdapter
        //set doc recycler click listeners
        addPropertyDocAdapter.setOnClickOfView { item, position, onClick ->
            when (onClick) {
                OnClick.DELETE -> {
                    showDialogOnDeleteDoc(item)
                }
                OnClick.RE_UPLOAD -> {
                    isReUploadMedia = true
                    this@AddOrEditPropertyDetailsFragment.position = position
                    mediaSelectHelper.openPdfIntent()
                }
                else -> {}
            }
        }
    }

    private fun clickListener() = with(binding) {
        editTextDescription.scrollableText()
        setCounterTextWatcher(editTextDescription, descriptionCounter)
        buttonUploadImages.setOnClickListener(this@AddOrEditPropertyDetailsFragment)
        buttonUploadVideo.setOnClickListener(this@AddOrEditPropertyDetailsFragment)
        imageViewReUploadVideo.setOnClickListener(this@AddOrEditPropertyDetailsFragment)
        imageViewDeleteVideo.setOnClickListener(this@AddOrEditPropertyDetailsFragment)
        buttonUploadBrochure.setOnClickListener(this@AddOrEditPropertyDetailsFragment)
        editTextSelectBeds.setOnClickListener(this@AddOrEditPropertyDetailsFragment)
        editTextSelectBaths.setOnClickListener(this@AddOrEditPropertyDetailsFragment)
        editTextSelectGarageSize.setOnClickListener(this@AddOrEditPropertyDetailsFragment)
        editTextSelectAreaUnit.setOnClickListener(this@AddOrEditPropertyDetailsFragment)
        editTextBuiltYear.setOnClickListener(this@AddOrEditPropertyDetailsFragment)
        editTextSelectPropertyType.setOnClickListener(this@AddOrEditPropertyDetailsFragment)
        buttonPreview.setOnClickListener(this@AddOrEditPropertyDetailsFragment)
        buttonSave.setOnClickListener(this@AddOrEditPropertyDetailsFragment)
        buttonNext.setOnClickListener(this@AddOrEditPropertyDetailsFragment)
        checkboxMakeDealFeatured.setOnClickListener(this@AddOrEditPropertyDetailsFragment)
    }

    /*Media Selection with callback*/
    private fun setMediaPicker() = with(binding) {
        mediaSelectHelper.canSelectMultipleImages(false)
        mediaSelectHelper.canSelectMultipleVideo(false)

        mediaSelectHelper.registerCallback(object : MediaSelector {
            override fun onImageUri(uri: Uri) {
                uri.path?.let {
                    if (isReUploadMedia) {
                        addPropertyImagesAdapter.updateItem(position,
                            itemToUpdate = { it.imageUri = uri })
                    } else {
                        addPropertyImagesAdapter.addItem(AddPropertyImages(uri))
                    }
                    recyclerViewImages.isVisible(addPropertyImagesAdapter.items?.isNotEmpty()!!)
                    manageAddImagesBtn()
                }
            }

            override fun onVideoUri(uri: Uri) {
                uri.path?.let {
                    videoPath = it
                    imageViewVideo.loadImageFromServerAny(uri)
                    constraintLayoutUploadVideo.isVisible(it.isNotEmpty())
                }
            }

            override fun onAnyFileSelected(outPutFileAny: OutPutFileAny) {
                mediaSelectHelper.createImageFile().apply {
                    generateImageFromPdf(outPutFileAny.uri, this)
                    outPutFileAny.apply {
                        thumbImage = path
                    }
                    if (isReUploadMedia) {
                        addPropertyDocAdapter.updateItem(position, outPutFileAny)
                    } else {
                        addPropertyDocAdapter.addItem(outPutFileAny)
                    }
                    recyclerViewDocuments.isVisible(addPropertyDocAdapter.items?.isNotEmpty()!!)
                    manageAddBrochuresBtn()
                }
            }
        })
    }

    private val dataAsResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.extras?.getParcelable<RealEstatePropertyList>("DATA").let { data ->
                    result.data?.extras?.getParcelable<PropertyListTag>("CURRENT_TAB")
                        .let { currentTab ->
                            val intent = Intent()
                            intent.putExtra("DATA", data)
                            intent.putExtra("CURRENT_TAB", currentTab as Parcelable)
                            requireActivity().setResult(Activity.RESULT_OK, intent)
                            navigator.goBack()
                        }
                }
            }
        }

    override fun onClick(v: View) {
        binding.apply {
            when (v) {
                buttonUploadImages -> {
                    isReUploadMedia = false
                    mediaSelectHelper.selectOptionsForImagePicker(true)
                }
                buttonUploadVideo -> {
                    if (!constraintLayoutUploadVideo.isVisible)
                        mediaSelectHelper.selectOptionsForVideoPicker()
                }
                imageViewDeleteVideo -> {
                    showDialogOnDeleteVideo()
                }
                imageViewReUploadVideo -> {
                    mediaSelectHelper.selectOptionsForVideoPicker()
                }
                buttonUploadBrochure -> {
                    isReUploadMedia = false
                    mediaSelectHelper.openPdfIntent()
                }
                editTextSelectBeds -> {
                    hideKeyBoard()
                    OptionsBottomSheet<SelectBeds>().setTitle("Select Beds")
                        .setOptionsList(DataUtils.selectBeds())
                        .setOnPositiveButtonClickListener { selectedOption ->
                            editTextSelectBeds.setText(selectedOption.option)
                        }.setSelectedOption(editTextSelectBeds.trimmedText)
                        .show(childFragmentManager, "SELECT_BEDS")
                }
                editTextSelectBaths -> {
                    hideKeyBoard()
                    OptionsBottomSheet<SelectBathrooms>().setTitle("Select Baths")
                        .setOptionsList(DataUtils.selectBathrooms())
                        .setOnPositiveButtonClickListener { selectedOption ->
                            editTextSelectBaths.setText(selectedOption.option)
                        }.setSelectedOption(editTextSelectBaths.trimmedText)
                        .show(childFragmentManager, "SELECT_BATHS")
                }
                editTextSelectGarageSize -> {
                    hideKeyBoard()
                    OptionsBottomSheet<SelectGarageSize>().setTitle("Select Garage Size")
                        .setOptionsList(DataUtils.selectGarageSize())
                        .setOnPositiveButtonClickListener { selectedOption ->
                            editTextSelectGarageSize.setText(selectedOption.option)
                        }.setSelectedOption(editTextSelectGarageSize.trimmedText)
                        .show(childFragmentManager, "SELECT_GARAGE_SIZE")
                }
                editTextSelectAreaUnit -> {
                    hideKeyBoard()
                    OptionsBottomSheet<SelectAreaUnit>().setTitle("Select Area Unit")
                        .setOptionsList(DataUtils.selectAreaUnit())
                        .setOnPositiveButtonClickListener { selectedOption ->
                            editTextSelectAreaUnit.setText(selectedOption.option)
                        }.setSelectedOption(editTextSelectAreaUnit.trimmedText)
                        .show(childFragmentManager, "SELECT_ARA_UNIT")
                }
                editTextBuiltYear -> {
                    openBuiltYearDialog()
                }
                editTextSelectPropertyType -> {
                    hideKeyBoard()
                    OptionsBottomSheet<SelectPropertyType>().setTitle("Select Property Type")
                        .setOptionsList(DataUtils.propertyTypeOptions())
                        .setOnPositiveButtonClickListener { selectedOption ->
                            editTextSelectPropertyType.setText(selectedOption.option)
                        }.setSelectedOption(editTextSelectPropertyType.trimmedText)
                        .show(childFragmentManager, "SELECT_PROPERTY_TYPE")
                }
                buttonPreview -> {
                    if (validation())
                        navigator.loadActivity(
                            IsolatedFullActivity::class.java,
                            PreviewOrSavePropertyDetailFragment::class.java
                        ).onResultActivity(dataAsResult).addBundle(
                            PreviewOrSavePropertyDetailFragment.createBundle(
                                AddOrEditPropertyDetailsFragment::class.java.simpleName,
                                getData(),//set data
                                currentTab
                            )
                        ).start()
                }
                buttonSave -> {
                    if (validation()) {
                        val intent = Intent()
                        intent.putExtra("DATA", getData()) //set data
                        intent.putExtra("CURRENT_TAB", currentTab as Parcelable)
                        requireActivity().setResult(Activity.RESULT_OK, intent)
                        navigator.goBack()
                    }
                }
                buttonNext -> {
                    //if (validation())
                    (parentFragment as AddOrEditPropertyDetailsMainFragment).navigateToNext(
                        getData(), steps = StepsCount.STEP_TWO
                    )
                }
            }
        }
    }

    private fun getData(): RealEstatePropertyList = with(binding) {
        val data = RealEstatePropertyList()
        data.apply {
            title = editTextTitle.trimmedText
            image = R.drawable.dummy_image_listing
            //listOfImages = addPropertyImagesAdapter.items as ArrayList<AddPropertyImages>
            video = if (::videoPath.isInitialized) videoPath else ""
            listOfBrochures = arrayListOf()
            amenities.apply {
                this.numberOfBeds = editTextSelectBeds.trimmedText
                this.numberOfBath = editTextSelectBaths.trimmedText
                this.numberOfGarage = editTextSelectGarageSize.trimmedText
                this.numberOfSqFt = editTextArea.trimmedText
                builtYear = editTextBuiltYear.trimmedText
            }
            propertyType = editTextSelectPropertyType.trimmedText

            address = editTextAddress.trimmedText
            description = editTextDescription.trimmedText
            price = editTextPrice.trimmedText
            status = when (currentTab) {
                PropertyListTag.HOME_FOR_SALE -> StatusType.FOR_SALE
                PropertyListTag.HOME_FOR_RENT -> StatusType.FOR_RENT
                PropertyListTag.VACATION_RENTAL -> StatusType.FOR_VACATION_RENT
                else -> {
                    StatusType.FOR_SALE
                }
            }
        }
        return data
    }

    private fun setDefaultData() = with(binding) {
        editTextTitle.setText(getString(R.string.label_x, "New Apartment Nice View"))
        editTextSelectBeds.setText(getString(R.string.label_x, "1"))
        editTextSelectBaths.setText(getString(R.string.label_x, "1"))
        editTextSelectGarageSize.setText(getString(R.string.label_x, "1"))
        editTextArea.setText(getString(R.string.label_x, "2200"))
        editTextBuiltYear.setText(getString(R.string.label_x, "2003"))
        editTextSelectPropertyType.setText(getString(R.string.label_x, "All"))
        editTextAddress.setText(getString(R.string.label_x, "Quincy St, Brooklyn, NY, USA"))
        editTextDescription.setText(
            getString(
                R.string.label_x,
                "Description - Lorem ipsum dolor sit amet, conquer ad elite."
            )
        )
        editTextPrice.setText(getString(R.string.label_x, "55000"))
    }

    private fun validation(): Boolean = with(binding) {
        return try {
            with(validator) {
                submit(editTextTitle).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_title)).check()

                if (addPropertyImagesAdapter.items.isNullOrEmpty()) {
                    showMessage(getString(R.string.validation_upload_images))
                    return false
                }

                if (!constraintLayoutUploadVideo.isVisible) {
                    showMessage(getString(R.string.validation_upload_video))
                    return false
                }

                /*if (addPropertyDocAdapter.items.isNullOrEmpty()) {
                    showMessage(getString(R.string.validation_upload_brochure))
                    return false
                }*/

                submit(editTextSelectBeds).checkEmpty()
                    .errorMessage(getString(R.string.validation_select_beds)).check()

                submit(editTextSelectBaths).checkEmpty()
                    .errorMessage(getString(R.string.validation_select_baths)).check()

                submit(editTextSelectGarageSize).checkEmpty()
                    .errorMessage(getString(R.string.validation_select_garage_size)).check()

                submit(editTextArea).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_total_area)).check()

                submit(editTextSelectPropertyType).checkEmpty()
                    .errorMessage(getString(R.string.validation_select_property_type)).check()

                submit(editTextAddress).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_address)).check()

                submit(editTextDescription).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_description)).check()

                submit(editTextPrice).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_price)).check()

                /*submit(editTextMetaKeyword).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_meta_keyword)).check()*/
                true
            }
        } catch (e: ApplicationException) {
            showMessage(e)
            false
        }
    }

    private fun openBuiltYearDialog() = with(binding) {
        yearPickerDialog.show(childFragmentManager, YearPickerDialog::class.java.simpleName)
        yearPickerDialog.getSelectedTime(selectedYr)
        callbackFromYearDialog()
    }

    private fun callbackFromYearDialog() = with(binding) {
        yearPickerDialog.setOnSelectClick { selectedYear ->
            selectedYr = selectedYear
            yearPickerDialog.dismiss()
            editTextBuiltYear.setText(selectedYr.toString())
        }
    }

    private val itemTouchHelper by lazy {
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(UP or DOWN, 0) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPosition = viewHolder.bindingAdapterPosition
                val toPosition = target.bindingAdapterPosition
                Collections.swap(
                    addPropertyImagesAdapter.items ?: arrayListOf<AddPropertyImages>(),
                    fromPosition, toPosition
                )
                addPropertyImagesAdapter.notifyItemMoved(fromPosition, toPosition)
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

    private fun manageAddBrochuresBtn() = with(binding) {
        if (recyclerViewDocuments.isVisible) buttonUploadBrochure.text =
            getString(R.string.btn_add_brochures)
        else buttonUploadBrochure.text = getString(R.string.btn_upload)
    }

    private fun showDialogOnDeleteImage(item: AddPropertyImages) = with(binding) {
        DialogUtils.showAlertDialog(
            requireContext(),
            message = R.string.label_are_you_sure_you_want_to_delete_an_image,
            onPositiveButtonClick = {
                addPropertyImagesAdapter.removeItem(item)
                recyclerViewImages.isVisible(addPropertyImagesAdapter.items?.isNotEmpty()!!)
                manageAddImagesBtn()
            }
        )
    }

    private fun showDialogOnDeleteVideo() = with(binding) {
        DialogUtils.showAlertDialog(
            requireContext(),
            message = R.string.label_are_you_sure_you_want_to_delete_a_video,
            onPositiveButtonClick = {
                hideView(constraintLayoutUploadVideo)
            }
        )
    }

    private fun showDialogOnDeleteDoc(item: OutPutFileAny) = with(binding) {
        DialogUtils.showAlertDialog(
            requireContext(),
            message = R.string.label_are_you_sure_you_want_to_delete_a_brochure,
            onPositiveButtonClick = {
                addPropertyDocAdapter.removeItem(item)
                recyclerViewDocuments.isVisible(addPropertyDocAdapter.items?.isNotEmpty()!!)
                manageAddBrochuresBtn()
            }
        )
    }

    private fun setSpannable() = with(binding) {
        TextDecorator.decorate(textViewUploadVideo, textViewUploadVideo.trimmedText)
            .setTextColor(R.color.C_9C9C9C, getString(R.string.label_mp4_mov))
            .setTypeface(
                ResourcesCompat.getFont(textViewUploadVideo.context, R.font.cerebri_sans_regular),
                getString(R.string.label_mp4_mov)
            )
            .setTextColor(R.color.C_ED1D26, getString(R.string.label_mandatory_field_icon))
            .build()

        TextDecorator.decorate(textViewUploadBrochure, textViewUploadBrochure.trimmedText)
            .setTextColor(R.color.C_9C9C9C, getString(R.string.label_pdf_only))
            .setTypeface(
                ResourcesCompat.getFont(
                    textViewUploadBrochure.context,
                    R.font.cerebri_sans_regular
                ),
                getString(R.string.label_pdf_only)
            )
            //.setTextColor(R.color.C_ED1D26, getString(R.string.label_mandatory_field_icon))
            .build()

    }


    fun generateImageFromPdf(pdfUri: Uri?, createImageFile: File) {
        val pageNumber = 0
        val pdfiumCore = PdfiumCore(requireContext())
        try {
            val fd: ParcelFileDescriptor =
                pdfUri?.let { requireActivity().contentResolver.openFileDescriptor(it, "r") }!!
            val pdfDocument: PdfDocument? = pdfiumCore.newDocument(fd)
            pdfiumCore.openPage(pdfDocument, pageNumber)
            val width: Int = pdfiumCore.getPageWidthPoint(pdfDocument, pageNumber)
            val height: Int = pdfiumCore.getPageHeightPoint(pdfDocument, pageNumber)
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            pdfiumCore.renderPageBitmap(pdfDocument, bmp, pageNumber, 0, 0, width, height)
            saveImage(bmp, createImageFile)
            pdfiumCore.closeDocument(pdfDocument)
        } catch (_: Exception) {

        }
    }

    private fun saveImage(bmp: Bitmap, createImageFile: File) {
        var out: FileOutputStream? = null
        try {
            out = FileOutputStream(createImageFile)
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out) // bmp is your Bitmap instance
        } catch (_: Exception) {
        } finally {
            try {
                out?.close()
            } catch (_: Exception) {
            }
        }
    }

    companion object {
        private const val ON_CLICK = "ON_CLICK"
        private const val CURRENT_TAB = "CURRENT_TAB"

        fun createBundle(onClick: OnClick, currentTab: PropertyListTag) =
            bundleOf(ON_CLICK to onClick, CURRENT_TAB to currentTab)
    }

}