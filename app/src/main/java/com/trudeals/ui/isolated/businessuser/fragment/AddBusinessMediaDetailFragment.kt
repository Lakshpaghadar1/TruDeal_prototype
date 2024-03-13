package com.trudeals.ui.isolated.businessuser.fragment

import android.graphics.Bitmap
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shockwave.pdfium.PdfDocument
import com.shockwave.pdfium.PdfiumCore
import com.trudeals.R
import com.trudeals.databinding.AddBusinessMediaDetailFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.exception.ApplicationException
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.home.businessuser.HomeActivityBU
import com.trudeals.ui.home.businessuser.fragment.HomeFragmentBU
import com.trudeals.ui.isolated.dummydata.AddPropertyImages
import com.trudeals.ui.isolated.dummydata.BusinessMediaDetail
import com.trudeals.ui.isolated.dummydata.BusinessProfileDetailMain
import com.trudeals.ui.isolated.realestateuser.adapter.AddPropertyDocAdapter
import com.trudeals.ui.isolated.realestateuser.adapter.AddPropertyImagesAdapter
import com.trudeals.utils.OnClick
import com.trudeals.utils.StepsCount
import com.trudeals.utils.dialog.DialogUtils
import com.trudeals.utils.extension.isVisible
import com.trudeals.utils.extension.loadImageFromServerAny
import com.trudeals.utils.extension.trimmedText
import com.trudeals.utils.hideView
import com.trudeals.utils.imagepicker.MediaSelector
import com.trudeals.utils.imagepicker.OutPutFileAny
import com.trudeals.utils.textdecorator.TextDecorator
import java.io.File
import java.io.FileOutputStream
import java.util.*

class AddBusinessMediaDetailFragment : BaseFragment<AddBusinessMediaDetailFragmentBinding>(),
    View.OnClickListener {

    private var isReUploadMedia = false
    private var isUploadMenuMedia = false
    private var isUploadSliderMedia = false
    private lateinit var mediaPath: String
    private var position: Int = -1
    private val addPropertyDocAdapter by lazy { AddPropertyDocAdapter() }
    private val addMenuImagesAdapter by lazy { AddPropertyImagesAdapter() }
    private val addSliderImagesAdapter by lazy { AddPropertyImagesAdapter() }
    private val previousScreenData by lazy {
        arguments?.getParcelable<BusinessProfileDetailMain>(DATA)
    }
    private val sourceScreen by lazy { arguments?.getString(SOURCE_SCREEN) }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): AddBusinessMediaDetailFragmentBinding {
        return AddBusinessMediaDetailFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        setData()
        clickListener()
        setDocRecyclerView()
        setMenuRecyclerView()
        setSliderRecyclerView()
        setMediaPicker()
        manageAddBrochuresBtn()
        manageAddMenuImagesBtn()
        manageAddSliderImagesBtn()
        setSpannable()
    }

    override fun setUpToolbar() {}

    private fun setData() {
        when (sourceScreen) {
            HomeFragmentBU::class.java.simpleName, HomeActivityBU::class.java.simpleName -> {
                setDefaultData()
            }
        }
    }

    private fun setDefaultData() {
        //set at api time
    }


    private fun clickListener() = with(binding) {
        buttonUploadBusinessLogo.setOnClickListener(this@AddBusinessMediaDetailFragment)
        imageViewReUploadLogo.setOnClickListener(this@AddBusinessMediaDetailFragment)
        imageViewDeleteLogo.setOnClickListener(this@AddBusinessMediaDetailFragment)
        buttonUploadBrochure.setOnClickListener(this@AddBusinessMediaDetailFragment)
        buttonUploadMenu.setOnClickListener(this@AddBusinessMediaDetailFragment)
        buttonUploadBusinessSlider.setOnClickListener(this@AddBusinessMediaDetailFragment)
        buttonNext.setOnClickListener(this@AddBusinessMediaDetailFragment)
    }

    override fun onClick(v: View): Unit = with(binding) {
        when (v) {
            buttonUploadBusinessLogo -> {
                isUploadMenuMedia = false
                isUploadSliderMedia = false
                if (!constraintLayoutUploadLogo.isVisible)
                    mediaSelectHelper.selectOptionsForImagePicker(true)
            }
            imageViewReUploadLogo -> {
                mediaSelectHelper.selectOptionsForImagePicker(true)
            }
            imageViewDeleteLogo -> {
                showDialogOnDeleteLogo()
            }
            buttonUploadBrochure -> {
                isReUploadMedia = false
                mediaSelectHelper.openPdfIntent()
            }
            buttonUploadMenu -> {
                isReUploadMedia = false
                isUploadMenuMedia = true
                isUploadSliderMedia = false
                mediaSelectHelper.selectOptionsForImagePicker(true)
            }
            buttonUploadBusinessSlider -> {
                isReUploadMedia = false
                isUploadSliderMedia = true
                isUploadMenuMedia = false
                mediaSelectHelper.selectOptionsForImagePicker(true)
            }
            buttonNext -> {
                //if (validation()) {
                    (parentFragment as CreateBusinessProfileMainFragment).navigateToNextStep(
                        getData(), steps = StepsCount.STEP_THREE
                    )
                //}
            }
        }
    }

    private fun getData(): BusinessProfileDetailMain = with(binding) {
        val data = BusinessProfileDetailMain()
        data.businessProfileDetail = previousScreenData?.businessProfileDetail
        data.businessMediaDetail = BusinessMediaDetail()
        data.apply {
            businessMediaDetail?.apply {
                businessLogo = if (::mediaPath.isInitialized) mediaPath else ""
                listOfBrochures = addPropertyDocAdapter.items as ArrayList<OutPutFileAny>
                listOfMenu = addMenuImagesAdapter.items as ArrayList<AddPropertyImages>
                listOfBusinessSlider = addSliderImagesAdapter.items as ArrayList<AddPropertyImages>
            }
        }
    }

    private fun setMenuRecyclerView() = with(binding.recyclerViewMenu) {
        itemTouchHelperMenu.attachToRecyclerView(this)
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter = addMenuImagesAdapter

        //set image recycler click listeners
        addMenuImagesAdapter.setOnClickOfView { item, position, onClick ->
            when (onClick) {
                OnClick.DELETE -> {
                    showDialogOnDeleteMenuImage(item)
                }
                OnClick.RE_UPLOAD -> {
                    isReUploadMedia = true
                    this@AddBusinessMediaDetailFragment.position = position
                    mediaSelectHelper.selectOptionsForImagePicker(true)
                }
                else -> {}
            }
        }
    }


    private fun setSliderRecyclerView() = with(binding.recyclerViewBusinessSlider) {
        itemTouchHelperSlider.attachToRecyclerView(this)
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter = addSliderImagesAdapter

        //set image recycler click listeners
        addSliderImagesAdapter.setOnClickOfView { item, position, onClick ->
            when (onClick) {
                OnClick.DELETE -> {
                    showDialogOnDeleteSliderImage(item)
                }
                OnClick.RE_UPLOAD -> {
                    isReUploadMedia = true
                    this@AddBusinessMediaDetailFragment.position = position
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
                    this@AddBusinessMediaDetailFragment.position = position
                    mediaSelectHelper.openPdfIntent()
                }
                else -> {}
            }
        }
    }

    /*Media Selection with callback*/
    private fun setMediaPicker() = with(binding) {
        mediaSelectHelper.canSelectMultipleImages(false)

        mediaSelectHelper.registerCallback(object : MediaSelector {
            override fun onImageUri(uri: Uri) {
                uri.path?.let { it ->
                    if (isUploadMenuMedia) {  //upload menu images
                        if (isReUploadMedia) {
                            addMenuImagesAdapter.updateItem(position,
                                itemToUpdate = { it.imageUri = uri })
                        } else {
                            addMenuImagesAdapter.addItem(AddPropertyImages(uri))
                        }
                        recyclerViewMenu.isVisible(addMenuImagesAdapter.items?.isNotEmpty()!!)
                        manageAddMenuImagesBtn()
                    } else if (isUploadSliderMedia) { //upload slider images
                        if (isReUploadMedia) {
                            addSliderImagesAdapter.updateItem(position,
                                itemToUpdate = { it.imageUri = uri })
                        } else {
                            addSliderImagesAdapter.addItem(AddPropertyImages(uri))
                        }
                        recyclerViewBusinessSlider.isVisible(addSliderImagesAdapter.items?.isNotEmpty()!!)
                        manageAddSliderImagesBtn()
                    } else {
                        //upload business logo image
                        mediaPath = it
                        imageViewLogo.loadImageFromServerAny(uri)
                        constraintLayoutUploadLogo.isVisible(it.isNotEmpty())
                    }
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

    private fun manageAddBrochuresBtn() = with(binding) {
        if (recyclerViewDocuments.isVisible) buttonUploadBrochure.text =
            getString(R.string.btn_add_brochures)
        else buttonUploadBrochure.text = getString(R.string.btn_upload)
    }

    private fun manageAddMenuImagesBtn() = with(binding) {
        if (recyclerViewMenu.isVisible) buttonUploadMenu.text =
            getString(R.string.btn_add_menu)
        else buttonUploadMenu.text = getString(R.string.btn_upload)
    }

    private fun manageAddSliderImagesBtn() = with(binding) {
        if (recyclerViewBusinessSlider.isVisible) buttonUploadBusinessSlider.text =
            getString(R.string.btn_add_business_slider)
        else buttonUploadBusinessSlider.text = getString(R.string.btn_upload)
    }

    private fun showDialogOnDeleteLogo() = with(binding) {
        DialogUtils.showAlertDialog(
            requireContext(),
            message = R.string.label_are_you_sure_you_want_to_delete_a_logo,
            onPositiveButtonClick = {
                hideView(constraintLayoutUploadLogo)
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

    private fun showDialogOnDeleteMenuImage(item: AddPropertyImages) = with(binding) {
        DialogUtils.showAlertDialog(
            requireContext(),
            message = R.string.label_are_you_sure_you_want_to_delete_an_image,
            onPositiveButtonClick = {
                addMenuImagesAdapter.removeItem(item)
                recyclerViewMenu.isVisible(addMenuImagesAdapter.items?.isNotEmpty()!!)
                manageAddMenuImagesBtn()
            }
        )
    }

    private fun showDialogOnDeleteSliderImage(item: AddPropertyImages) = with(binding) {
        DialogUtils.showAlertDialog(
            requireContext(),
            message = R.string.label_are_you_sure_you_want_to_delete_an_image,
            onPositiveButtonClick = {
                addSliderImagesAdapter.removeItem(item)
                recyclerViewBusinessSlider.isVisible(addSliderImagesAdapter.items?.isNotEmpty()!!)
                manageAddSliderImagesBtn()
            }
        )
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

    private val itemTouchHelperMenu by lazy {
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
                        addMenuImagesAdapter.items ?: arrayListOf<AddPropertyImages>(),
                        fromPosition, toPosition
                    )
                    addMenuImagesAdapter.notifyItemMoved(fromPosition, toPosition)
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
            }
        ItemTouchHelper(itemTouchCallback)
    }

    private val itemTouchHelperSlider by lazy {
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
                        addSliderImagesAdapter.items ?: arrayListOf<AddPropertyImages>(),
                        fromPosition, toPosition
                    )
                    addSliderImagesAdapter.notifyItemMoved(fromPosition, toPosition)
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
            }
        ItemTouchHelper(itemTouchCallback)
    }


    private fun validation(): Boolean = with(binding) {
        return try {
            with(validator) {
                if (!constraintLayoutUploadLogo.isVisible) {
                    showMessage(getString(R.string.validation_upload_business_logo))
                    return false
                }
                if (addPropertyDocAdapter.items.isNullOrEmpty()) {
                    showMessage(getString(R.string.validation_upload_brochure))
                    return false
                }
                if (addMenuImagesAdapter.items.isNullOrEmpty()) {
                    showMessage(getString(R.string.validation_upload_menu))
                    return false
                }
                if (addSliderImagesAdapter.items.isNullOrEmpty()) {
                    showMessage(getString(R.string.validation_upload_business_slider))
                    return false
                }
                true
            }
        } catch (e: ApplicationException) {
            showMessage(e)
            false
        }
    }

    private fun setSpannable() = with(binding) {
        TextDecorator.decorate(textViewUploadBusinessLogo, textViewUploadBusinessLogo.trimmedText)
            .setTextColor(R.color.C_9C9C9C, getString(R.string.label_jpg_png_only))
            .setTypeface(
                ResourcesCompat.getFont(
                    textViewUploadBusinessLogo.context,
                    R.font.cerebri_sans_regular
                ),
                getString(R.string.label_jpg_png_only)
            )
            //.setTextColor(R.color.C_ED1D26, getString(R.string.label_mandatory_field_icon))
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

        TextDecorator.decorate(textViewUploadMenu, textViewUploadMenu.trimmedText)
            .setTextColor(R.color.C_9C9C9C, getString(R.string.label_jpg_png_only))
            .setTypeface(
                ResourcesCompat.getFont(textViewUploadMenu.context, R.font.cerebri_sans_regular),
                getString(R.string.label_jpg_png_only)
            )
            //.setTextColor(R.color.C_ED1D26, getString(R.string.label_mandatory_field_icon))
            .build()

        TextDecorator.decorate(
            textViewUploadBusinessSlider,
            textViewUploadBusinessSlider.trimmedText
        )
            .setTextColor(R.color.C_9C9C9C, getString(R.string.label_jpg_png_only))
            .setTypeface(
                ResourcesCompat.getFont(
                    textViewUploadBusinessSlider.context,
                    R.font.cerebri_sans_regular
                ),
                getString(R.string.label_jpg_png_only)
            )
            //.setTextColor(R.color.C_ED1D26, getString(R.string.label_mandatory_field_icon))
            .build()
    }

    companion object {
        private const val DATA = "DATA"
        private const val SOURCE_SCREEN = "SOURCE_SCREEN"

        fun createBundle(data: BusinessProfileDetailMain) =
            bundleOf(DATA to data)

        fun createBundleSS(sourceString: String) =
            bundleOf(SOURCE_SCREEN to sourceString)
    }
}