package com.trudeals.ui.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.trudeals.R
import com.trudeals.core.AppSession
import com.trudeals.core.Session
import com.trudeals.di.HasComponent
import com.trudeals.di.component.ActivityComponent
import com.trudeals.di.component.FragmentComponent
import com.trudeals.di.module.FragmentModule
import com.trudeals.exception.ApplicationException
import com.trudeals.exception.AuthenticationException
import com.trudeals.exception.ServerException
import com.trudeals.ui.base.loader.Loader
import com.trudeals.ui.manager.Navigator
import com.trudeals.utils.imagepicker.ImageAndVideoPicker
import com.trudeals.utils.imagepicker.MediaSelectHelper
import com.trudeals.utils.imagepicker.MediaType
import com.trudeals.utils.validator.Validator
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

/**
 * Created by hlink21 on 25/4/16.
 */

abstract class BaseFragment<T : ViewBinding> : Fragment(), HasComponent<FragmentComponent> {

    protected lateinit var toolbar: HasToolbar
    private lateinit var loader: Loader

    @Inject
    lateinit var appSession: AppSession

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var validator: Validator

    @Inject
    lateinit var session: Session


    @Inject
    lateinit var mediaSelectHelper: MediaSelectHelper

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val component: FragmentComponent
        get() {
            return getComponent(ActivityComponent::class.java).plus(FragmentModule(this))
        }

    private var _binding: T? = null

    protected val binding: T
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = createViewBinding(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
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

        if (activity is Loader)
            loader = activity as Loader
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

    fun <T : BaseFragment<*>> getParentFragment(targetFragment: Class<T>): T? {
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

    fun showMessage(message: String) {
        (activity as BaseActivity).showMessage(message)
    }

    fun showMessage(@StringRes stringId: Int) {
        (activity as BaseActivity).showMessage(getString(stringId))
    }

    fun showMessage(applicationException: ApplicationException) {
        (activity as BaseActivity).showMessage(applicationException.message)
    }

    open fun onBackActionPerform(): Boolean {
        return true
    }

    open fun onViewClick(view: View) {

    }

    fun onError(throwable: Throwable) {
        //(activity as BaseActivity).onError(throwable)
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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun logout() {
        if (activity is BaseActivity) {
            (activity as BaseActivity).logout()
        }
    }

    protected abstract fun inject(fragmentComponent: FragmentComponent)

    /**
     * This method is used for binding view with your binding
     */
    protected abstract fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): T

    protected abstract fun bindData()

    /**
     * Note: You should call at least [HasToolbar.showToolbar] method.
     * */
    protected abstract fun setUpToolbar()

    // Loader
    fun showLoader() {
        loader.showLoader()
    }

    fun hideLoader() {
        loader.hideLoader()
    }

    protected fun getInteger(@IntegerRes id: Int) = resources.getInteger(id)
    protected fun getColor(@ColorRes id: Int) = resources.getColor(id, null)

    // Image picker
    protected fun selectSingleMedia(
        mediaType: MediaType = MediaType.IMAGE,
        onImagesSelected: (mediaPath: String, mediaType: String) -> Unit
    ) {
        val imageAndVideoPicker = ImageAndVideoPicker()

        when (mediaType) {
            MediaType.IMAGE -> imageAndVideoPicker.pickImage(true)
            MediaType.VIDEO -> imageAndVideoPicker.pickVideo(true)
            MediaType.IMAGE_VIDEO -> imageAndVideoPicker.pickImage(true).pickVideo(true)
        }

        imageAndVideoPicker.setImageCallBack(object : ImageAndVideoPicker.ImageVideoPickerResult() {
            override fun onFail(message: String) {
                showMessage(message)
            }

            override fun onImagesSelected(list: ArrayList<String>) {
                super.onImagesSelected(list)
                list.firstOrNull()?.let { mediaPath ->
                    onImagesSelected(mediaPath, MediaType.IMAGE.identifier)
                }
            }

            override fun onVideoSelected(list: ArrayList<String>) {
                super.onVideoSelected(list)
                list.firstOrNull()?.let { mediaPath ->
                    onImagesSelected(mediaPath, MediaType.VIDEO.identifier)
                }
            }
        })

        imageAndVideoPicker.show(childFragmentManager, ImageAndVideoPicker::class.simpleName)
    }

    protected fun selectMultipleMedia(
        mediaType: MediaType = MediaType.IMAGE,
        onImagesSelected: (ArrayList<String>, mediaType: String) -> Unit
    ) {
        val imageAndVideoPicker = ImageAndVideoPicker().allowMultiple()

        when (mediaType) {
            MediaType.IMAGE -> imageAndVideoPicker.pickImage(true)
            MediaType.VIDEO -> imageAndVideoPicker.pickVideo(true)
            MediaType.IMAGE_VIDEO -> imageAndVideoPicker.pickImage(true).pickVideo(true)
        }

        imageAndVideoPicker.setImageCallBack(object : ImageAndVideoPicker.ImageVideoPickerResult() {
            override fun onFail(message: String) {
                showMessage(message)
            }

            override fun onImagesSelected(list: ArrayList<String>) {
                super.onImagesSelected(list)

                onImagesSelected(list, MediaType.IMAGE.identifier)
            }

            override fun onVideoSelected(list: ArrayList<String>) {
                super.onVideoSelected(list)

                Log.d("Video", "onVideoSelected: $list")
                onImagesSelected(list, MediaType.VIDEO.identifier)
            }
        })

        imageAndVideoPicker.show(childFragmentManager, ImageAndVideoPicker::class.simpleName)
    }

    fun loadFragment(
        @IdRes containerViewId: Int,
        fragmentClass: Class<out BaseFragment<out ViewBinding>>,
        isAddToBackStack: Boolean,
        bundle: Bundle?
    ) {
        childFragmentManager.commit {
            setReorderingAllowed(true)
            replace(containerViewId, fragmentClass, bundle, fragmentClass.name)
            if (isAddToBackStack) addToBackStack(fragmentClass.name)
        }
    }

    /*
    protected fun uploadSingleFile(
        directoryName: String,
        Url: String,
        onCompleted: (String) -> Unit,
        onError: ((id: Int, exception: Exception?) -> Unit)? = null
    ) {
        val file = File(Url)
        s3BucketManager.uploadFile(
            file = file, // File need to upload
            filename = file.getRandomName()!!, //if not pass argument then it has by default random name
            directory = directoryName, // full path of upload directory
            s3SingleTransferListener = object :
                S3SingleTransferListener { //file uploading status listener

                override fun onCompleted(fileName: String) {
                    onCompleted(fileName)
                }

                override fun onError(id: Int, exception: Exception?) {
                    // Do something on got any error file uploading
                    if (onError != null) {
                        onError(id, exception)
                    } else {
                        exception?.message?.let {
                            showMessage(it)
                        }
                    }
                }

                override fun onProgress(progress: Int) {
                    // Do something on file uploading progress
                }
            }
        )
    }

    protected fun uploadMultipleFiles(
        directoryName: String,
        Url: List<String>,
        onCompleted: (ArrayList<String>) -> Unit,
        onError: ((id: Int, exception: Exception?) -> Unit)? = null
    ) {
//        try {
            val file = ArrayList<File>()
            Url.forEach {
                file.add(File(it))
            }
            s3BucketManager.uploadFiles(
                files = file, // File need to upload
                directory = directoryName, // full path of upload directory invention_media
                s3MultipleTransferListener = object :
                    S3MultipleTransferListener { //file uploading status listener
                    override fun filesUploadedCount(totalFiles: Int, pendingFile: Int) {
                    }

                    override fun onCompleted(listOfName: ArrayList<String>) {
                        onCompleted(listOfName)
                    }

                    override fun onError(id: Int, exception: Exception?) {
                        exception?.message?.let {
                            showMessage(it)
                        }
                        /*if (onError != null) {
                            onError(id, exception)
                        } else {
                            exception?.message?.let {
                                showMessage(it)
                            }
                        }*/
                    }

                    override fun onProgress(progress: Int) {
                    }
                }
            )
//        } catch (e: IllegalArgumentException) {
//            hideLoader()
//            e.message?.let { showMessage(getString(R.string.message_please_select_file_again)) }
//        }
    }

    * */
}
