package com.trudeals.ui.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.trudeals.R
import com.trudeals.core.AppPreferences
import com.trudeals.core.Session
import com.trudeals.di.HasComponent
import com.trudeals.di.component.ActivityComponent
import com.trudeals.di.component.BottomSheetComponent
import com.trudeals.di.module.BottomSheetModule
import com.trudeals.exception.ApplicationException
import com.trudeals.exception.AuthenticationException
import com.trudeals.exception.ServerException
import com.trudeals.ui.auth.AuthActivity
import com.trudeals.ui.base.loader.Loader
import com.trudeals.ui.base.loader.LoadingDialog
import com.trudeals.ui.manager.Navigator
import com.trudeals.utils.imagepicker.ImageAndVideoPicker
import com.trudeals.utils.validator.Validator
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException
import javax.inject.Inject


abstract class BaseBottomSheet<T : ViewBinding> : BottomSheetDialogFragment(),
    HasComponent<BottomSheetComponent>, Loader/*, HasBottomSheetToolbar*/ {

    @Inject
    lateinit var validator: Validator

    @Inject
    lateinit var appPreferences: AppPreferences

    @Inject
    lateinit var session: Session

    @Inject
    lateinit var navigator: Navigator

    protected lateinit var toolbar: HasToolbar

    private var _binding: T? = null

    protected val binding: T
        get() = _binding!!


    override val component: BottomSheetComponent
        get() {
            return getComponent(ActivityComponent::class.java).plus(BottomSheetModule(this))
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
        _binding = createViewBinding(inflater)

        binding.root.viewTreeObserver.addOnGlobalLayoutListener(viewTreeObserver)
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            // window?.setDimAmount(0.2f) // Set dim amount here
            setOnShowListener {
                val bottomSheet =
                    findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
                bottomSheet.setBackgroundResource(android.R.color.transparent)
                // BottomSheetBehavior.from(bottomSheet!!).state = BottomSheetBehavior.STATE_EXPANDED
                /* BottomSheetBehavior.from(bottomSheet).skipCollapsed = true
                 BottomSheetBehavior.from(bottomSheet).isHideable = true*/
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetStyle)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        BottomSheetBehavior.from<View>(
            (dialog as BottomSheetDialog)
                .findViewById(com.google.android.material.R.id.design_bottom_sheet)!!
        )
            .addBottomSheetCallback(addBottomSheetCallback)
        bindData()
    }

    protected fun <C> getComponent(componentType: Class<C>): C {
        return componentType.cast((activity as HasComponent<C>).component)
    }

    override fun onAttach(context: Context) {
        inject(component)
        super.onAttach(context)

        if (activity is HasToolbar)
            toolbar = activity as HasToolbar


    }


    fun hideKeyBoard() {
        if (activity is BaseActivity) {
            (activity as BaseActivity).hideKeyboard()
        }
    }

    fun showKeyBoard() {
        if (activity is BaseActivity) {
            (activity as BaseActivity).showKeyboard()
        }
    }


    fun getParentFragment(targetFragment: Class<T>): T? {
        if (parentFragment == null) return null
        try {
            return targetFragment.cast(parentFragment)
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }

        return null
    }

    open fun onShow() {

    }

    open fun onBackActionPerform(): Boolean {
        return true
    }

    open fun onViewClick(view: View) {

    }

    override fun onDestroyView() {
        destroyViewBinding()
        _binding = null
        requireView().viewTreeObserver.removeOnGlobalLayoutListener(viewTreeObserver)
        super.onDestroyView()
    }

    fun onError(throwable: Throwable) {
        try {
            when (throwable) {
                is ServerException -> showMessage(throwable.message.toString())
                is ConnectException -> showMessage(R.string.connection_exception)
                is TimeoutException -> showMessage(R.string.connection_exception)
                is AuthenticationException -> {
                    logout()
                }
                is ApplicationException -> {
                    showMessage(throwable.toString())
                }
                is SocketTimeoutException -> showMessage(R.string.socket_time_out_exception)
                else -> showMessage(getString(R.string.other_exception) + throwable.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun logout() {
        var userType: String? = ""
        appPreferences.clearAll()
       // session.isLoggedOut = true
        //manage user type selection on welcome screen
        navigator.loadActivity(AuthActivity::class.java).byFinishingAll().start()
    }

    fun hideSystemUI() {
        //(activity as BaseActivity).hideSystemUI()
    }

    fun showSystemUI() {
        //(activity as BaseActivity).showSystemUI()
    }


  /*  override fun showToolbar(b: Boolean) {
        toolbar.showToolbar(b)
    }*/

    fun showMessageOnActivity(m: String) {
        (activity as BaseActivity).showMessage(m)
    }

    fun showMessageOnActivity(@StringRes stringId: Int) {
        (activity as BaseActivity).showMessage(stringId)
    }

    fun showToast(m: String) {
        (activity as BaseActivity).showToast(m)
    }

    fun showSnackBarMessage(message: String) {
        showSnackBar(message)
    }

    fun showSnackBarMessage(@StringRes stringId: Int) {
        showSnackBar(getString(stringId))
    }

    fun showMessage(applicationException: ApplicationException) {
        showSnackBar(applicationException.message)
    }

    fun showMessage(message: String) {
        showSnackBar(message)
    }

    fun showMessage(@StringRes stringId: Int) {
        showSnackBar(getString(stringId))
    }

    private fun showSnackBar(message: String) {
        hideKeyBoard()
        if (view != null) {
            val snackbar =
                dialog?.window?.decorView?.let { Snackbar.make(it, message, Snackbar.LENGTH_LONG) }
            snackbar?.duration = 3000
            snackbar?.setActionTextColor(Color.WHITE)
            // snackbar?.setAction("OK", View.OnClickListener { snackbar.dismiss() })
            val snackView = snackbar?.view

            // Used to show snackbar on top
            val params = snackView?.layoutParams as FrameLayout.LayoutParams
            params.gravity = Gravity.TOP
            params.topMargin = 150
            params.marginEnd = 10
            params.marginStart = 10
            snackView.layoutParams = params

            val textView =
                snackView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
            textView.maxLines = 4

            snackView.setBackgroundColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.colorPrimary,
                    null
                )
            )
            snackbar.show()
        }
    }

    //documentPicker function
    protected fun selectSinglePdfFromFiles(onDocumentSelected: (String) -> Unit) {
        val documentPicker =
            ImageAndVideoPicker.newInstance().pickDocument(true)
        documentPicker.isDocument = true
        documentPicker.setImageCallBack(object : ImageAndVideoPicker.ImageVideoPickerResult() {
            override fun onFail(message: String) {
                //showMessage(message)
            }

            override fun onDocumentSelected(list: java.util.ArrayList<String>) {
                super.onDocumentSelected(list)
                onDocumentSelected(list.first())
            }
        })

        documentPicker.show(
            childFragmentManager,
            ImageAndVideoPicker::class.java.simpleName
        )
    }

    /**
     * This method is used for binding view with your binding
     */
    protected abstract fun createViewBinding(
        inflater: LayoutInflater
    ): T

    protected abstract fun inject(bottomSheetComponent: BottomSheetComponent)
    protected abstract fun bindData()
    protected abstract fun destroyViewBinding()


    //
    fun show(childFragmentManager: FragmentManager) {
        show(childFragmentManager, this.tag)
    }


    // for bottom sheet state callback
    private val addBottomSheetCallback by lazy {
        object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED)
                    this@BaseBottomSheet.dismiss()
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        }
    }

    private var isLoaderShowing: Boolean = false
    private val loader by lazy {
        LoadingDialog {
            dismiss()
        }
    }

    override fun showLoader() {
        if (!isLoaderShowing) {
            isLoaderShowing = true
            loader.show(childFragmentManager, "")
        }
    }

    override fun hideLoader() {
        if (isLoaderShowing) {
            isLoaderShowing = false
            loader.dismiss()
        }
    }


    protected var bottomSheetBehavior = BottomSheetBehavior.STATE_EXPANDED

    // for full wrap content
    private val viewTreeObserver by lazy {
        object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                BottomSheetBehavior.from<View>(
                    (dialog as BottomSheetDialog)
                        .findViewById(com.google.android.material.R.id.design_bottom_sheet)!!
                ).apply {
                    state = bottomSheetBehavior
                    peekHeight = 0
                }
                view!!.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        }
    }

    protected fun getInteger(@IntegerRes id: Int) = resources.getInteger(id)
    protected fun getColor(@ColorRes id: Int) = resources.getColor(id, null)
}

