package com.trudeals.ui.isolated.customeruser.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.trudeals.databinding.ViewImageVideoFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment
import com.trudeals.utils.hideView
import com.trudeals.utils.imagepicker.load
import com.trudeals.utils.showView

class ViewImageVideoFragment : BaseFragment<ViewImageVideoFragmentBinding>() {

    private lateinit var exoPlayer: ExoPlayer

    private val isVideo by lazy {
        arguments?.getBoolean(IS_VIDEO) ?: false
    }

    private val isDrawable by lazy {
        arguments?.getBoolean(IS_DRAWABLE) ?: false
    }

    private val link by lazy {
        arguments?.getString(ITEM_MEDIA_PATH) ?: ""
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): ViewImageVideoFragmentBinding {
        return ViewImageVideoFragmentBinding.inflate(layoutInflater)
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true)
            .showBackButton(true)
            .build()
    }


    override fun bindData() {
        when {
            isVideo -> {
                showVideo()
            }
            else -> showPhoto()
        }
    }

    private fun showPhoto() {
        with(binding)
        {
            hideView(playerView)
            showView(imageViewImage)
            if (isDrawable) {
                imageViewImage.setImageResource(link.toInt())
            } else {
                imageViewImage.load(link)
            }
        }
    }


    private fun showVideo() {
        with(binding)
        {
            hideView(imageViewImage)
            showView(playerView)
            exoPlayer = ExoPlayer.Builder(requireContext()).build()
            val mediaItem =
                MediaItem.fromUri(link)
            exoPlayer.setMediaItem(mediaItem)
            playerView.player = exoPlayer
            exoPlayer.prepare()
            exoPlayer.play()
        }
    }


    override fun onPause() {
        super.onPause()
        if (this::exoPlayer.isInitialized) {
            exoPlayer.stop()
        }
    }

    companion object {
        const val IS_VIDEO = "IS_VIDEO"
        const val ITEM_MEDIA_PATH = "ITEM_MEDIA_PATH"
        const val IS_DRAWABLE = "IS_DRAWABLE"

        fun createBundle(isVideo: Boolean, itemMediaPath: String, isDrawable: Boolean) = bundleOf(
            IS_VIDEO to isVideo, ITEM_MEDIA_PATH to itemMediaPath, IS_DRAWABLE to isDrawable)
    }
}