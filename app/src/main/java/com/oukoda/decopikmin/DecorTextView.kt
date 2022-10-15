package com.oukoda.decopikmin

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.LinearLayout
import com.oukoda.decopikmin.databinding.ViewDecorTextBinding

class DecorTextView(
    context: Context,
    private val decorText: String
) :
    LinearLayout(context) {

    var isOpen = false

    companion object {
        val TAG: String? = DecorTextView::class.simpleName
    }

    private val binding: ViewDecorTextBinding
    private val openAnimation = RotateAnimation(
        0.0f, 180.0f,
        Animation.RELATIVE_TO_SELF, 0.5f,
        Animation.RELATIVE_TO_SELF, 0.5f
    )
    private val closeAnimation = RotateAnimation(
        180.0f, 360.0f,
        Animation.RELATIVE_TO_SELF, 0.5f,
        Animation.RELATIVE_TO_SELF, 0.5f
    )

    init {
        binding = ViewDecorTextBinding.inflate(LayoutInflater.from(context), this, true)
        setAnimationSetting(openAnimation)
        setAnimationSetting(closeAnimation)
        binding.tvDecorText.text = decorText

        binding.clText.setOnClickListener {
            binding.tvOpen.isClickable = false
            isOpen = !isOpen
            if (isOpen) {
                binding.tvOpen.startAnimation(openAnimation)
                binding.motionLayout.transitionToEnd()
            } else {
                binding.tvOpen.startAnimation(closeAnimation)
                binding.motionLayout.transitionToStart()
            }
            Log.d(TAG, ": ${openAnimation.repeatCount}")
        }
        Log.d(TAG, ": ${openAnimation.repeatCount}")
        binding.motionLayout.transitionToStart()
    }

    fun addPikminListView(view: PikminListView) {
        binding.linearLayout.addView(view)
    }

    private fun setAnimationSetting(animation: RotateAnimation) {
        animation.duration = 275
        animation.repeatCount = 0
        animation.fillAfter = true
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                // Do Nothing
            }

            override fun onAnimationEnd(animation: Animation?) {
                binding.tvOpen.isClickable = true
            }

            override fun onAnimationRepeat(animation: Animation?) {
                // Do Nothing
            }
        })
    }
}
