package com.trudeals.ui.base.basedialogfragment
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.trudeals.R
import com.trudeals.core.AppPreferences
import com.trudeals.core.Session
import com.trudeals.di.HasComponent
import com.trudeals.di.component.ActivityComponent
import com.trudeals.exception.ApplicationException
import com.trudeals.exception.AuthenticationException
import com.trudeals.exception.ServerException
import com.trudeals.ui.auth.AuthActivity
import com.trudeals.ui.base.BaseActivity
import com.trudeals.ui.manager.Navigator
import com.trudeals.utils.validator.Validator
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

abstract class BaseDialogFragment<T : ViewBinding> : DialogFragment(),
    HasComponent<BaseDialogComponent> {
    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var appPreferences: AppPreferences

    @Inject
    lateinit var session: Session

    @Inject
    lateinit var validator: Validator

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var _binding: T? = null

    protected val binding: T
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = createViewBinding(inflater,container,false)
        return binding.root
    }

    override val component: BaseDialogComponent
        get() {
            return getComponent(ActivityComponent::class.java).plus(BaseDialogModule(this))
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(false)
        bindData()
    }

    protected fun <C> getComponent(componentType: Class<C>): C {
        return componentType.cast((activity as HasComponent<C>).component)
    }

    open fun onShow() {
    }

    fun showMessage(message: String) {
        showSnackBar(message)
    }

    fun showMessage(@StringRes stringId: Int) {
        showSnackBar(getString(stringId))
    }

    fun showMessage(applicationException: ApplicationException) {
        showSnackBar(applicationException.message)
    }

    private fun showSnackBar(message: String) {
        hideKeyBoard()
        if (view != null) {
            val snackbar = Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG)
            snackbar.duration = 3000
            snackbar.setActionTextColor(Color.WHITE)
            snackbar.setAction("OK", View.OnClickListener { snackbar.dismiss() })
            val snackView = snackbar.getView()
            val textView =
                snackView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
            textView.maxLines = 4

            // Used to show snackbar on top
            val params = snackView.layoutParams as FrameLayout.LayoutParams
            params.gravity = Gravity.TOP
            snackView.layoutParams = params

            snackView.setBackgroundColor(requireActivity().getResources().getColor(R.color.colorPrimary))
            snackbar.show()
        }
    }

    private fun showSnackBar(message: String, viewSet: View) {
        hideKeyBoard()
        if (viewSet != null) {
            val snackbar = Snackbar.make(viewSet, message, Snackbar.LENGTH_LONG)
            snackbar.duration = 3000
            snackbar.setActionTextColor(Color.WHITE)
            snackbar.setAction("OK", View.OnClickListener { snackbar.dismiss() })
            val snackView = snackbar.getView()
            val textView =
                snackView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
            textView.maxLines = 4

            // Used to show snackbar on top
            val params = snackView.layoutParams as FrameLayout.LayoutParams
            params.gravity = Gravity.TOP
            snackView.layoutParams = params

            snackView.setBackgroundColor(requireActivity().getResources().getColor(R.color.colorPrimary))
            snackbar.show()
        }
    }

    private fun DialogFragment.setWidthPercent(percentage: Int) {
        val percent = percentage.toFloat() / 100
        val dm = Resources.getSystem().displayMetrics
        val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
        val percentWidth = rect.width() * percent
        dialog?.window?.setLayout(percentWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
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

    public fun onError(throwable: Throwable) {
        try {
            when (throwable) {
                is ServerException -> showMessage(throwable.message.toString())
                is ConnectException -> showMessage(R.string.connection_exception)
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


    override fun onAttach(context: Context) {
        inject(component)
        super.onAttach(context)
    }

    override fun onStart() {
        super.onStart()
        setWidthPercent(90)
    }

    fun logout() {
        session.clearSession()
        navigator.loadActivity(AuthActivity::class.java).byFinishingAll().start()
    }

    protected abstract fun inject(baseDialogComponent: BaseDialogComponent)
    protected abstract fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?,attachToRoot: Boolean): T
    protected abstract fun bindData()
}