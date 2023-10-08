package com.polytechtest.arch

import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

abstract class BaseComposeFragment : Fragment() {
    @Composable
    protected open fun FragmentScreen() {
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                FragmentScreen()
            }
        }
    }
}

abstract class BaseComposeFragmentWithVM :
    BaseComposeFragment() {
    protected abstract val viewModel: BaseViewModel
}

abstract class BaseComposeDialogFragment(
    private val heightPercent: Float,
    private val ratio: Float,
    @StyleRes private val styleRes: Int,
    @DrawableRes private val backgroundRes: Int,
) : DialogFragment() {

    @Composable
    protected open fun DialogFragmentScreen() {
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        requireDialog().requestWindowFeature(Window.FEATURE_NO_TITLE)
        setStyle(STYLE_NO_FRAME, styleRes)
        requireDialog().window?.setBackgroundDrawableResource(backgroundRes)
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                DialogFragmentScreen()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDialogSize(heightPercent, ratio)
    }

    companion object {
        fun DialogFragment.setDialogSize(percentageHeight: Float, ratio: Float) {
            val dm = Resources.getSystem().displayMetrics
            val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
            val percentHeight = rect.height() * percentageHeight
            val percentWidth = percentHeight * ratio
            dialog?.window?.setLayout(percentWidth.toInt(), percentHeight.toInt())
        }
    }
}