package com.oukoda.decopikmin

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.LinearLayout
import com.oukoda.decopikmin.databinding.ViewDecorTextBinding
import com.oukoda.decopikmin.dataclass.Pikmin
import com.oukoda.decopikmin.enum.Costume
import com.oukoda.decopikmin.enum.DecorType
import com.oukoda.decopikmin.enum.PikminStatus

@SuppressLint("ViewConstructor")
class DecorTextView(
    context: Context,
    private val decorType: DecorType,
    statusGetter: MainActivity.Companion.PikminStatusGetter,
    private var pikminStatusListener: PikminStatusListener?
) :
    LinearLayout(context) {

    companion object {
        val TAG: String? = DecorTextView::class.simpleName

        interface PikminStatusListener {
            fun onUpdateStatus(pikmin: Pikmin, oldStatus: PikminStatus)
        }

        private const val ANIMATION_DURATION = 275L

        private val closeAnimation = RotateAnimation(
            180.0f, 360.0f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )

        private val openAnimation = RotateAnimation(
            0.0f, 180.0f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
    }

    private val binding: ViewDecorTextBinding
    private val viewList: List<PikminListView>
    private val statusListener = object : PikminListView.Companion.PikminStatusListener {
        override fun onUpdateStatus(pikmin: Pikmin, oldStatus: PikminStatus) {
            if (oldStatus == PikminStatus.NotHave) {
                collectedPikminCount += 1
            } else if (pikmin.pikminStatus == PikminStatus.NotHave) {
                collectedPikminCount -= 1
            }
            setPikminCount()
            pikminStatusListener?.onUpdateStatus(pikmin, oldStatus)
        }
    }
    var isOpen = false
    private val pikminCount: Int
    private var collectedPikminCount = 0

    init {
        binding = ViewDecorTextBinding.inflate(LayoutInflater.from(context), this, true)
        setAnimationSetting(openAnimation)
        setAnimationSetting(closeAnimation)
        binding.tvDecorText.text = resources.getText(DecorType.getDecorText(decorType))

        val mutableViewList = mutableListOf<PikminListView>()
        var tempPikminCount = 0
        var tempCollectedPikminCount = 0
        DecorType.getCostumes(decorType).forEach { costume ->
            val mutablePikminList = mutableListOf<Pikmin>()
            val pikminTypeList = Costume.getPikminList(costume)
            for (i in pikminTypeList.indices) {
                val pikminType = pikminTypeList[i]
                val number = mutablePikminList.count { it.pikminType == pikminType }
                val pikmin = Pikmin.newInstance(decorType, costume, pikminType, number)
                val key = pikmin.createStatusKey()
                pikmin.pikminStatus = statusGetter.getPikminStatus(key)
                mutablePikminList.add(pikmin)
            }
            val pikminListView = PikminListView(
                context,
                costume,
                mutablePikminList.toList(),
                statusListener
            )
            tempPikminCount += pikminListView.getPikminCount()
            tempCollectedPikminCount += pikminListView.getCollectedPikminCount()
            mutableViewList.add(pikminListView)
            binding.linearLayout.addView(pikminListView)
        }

        viewList = mutableViewList.toList()
        pikminCount = tempPikminCount
        collectedPikminCount = tempCollectedPikminCount
        setPikminCount()

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

    fun setListener(pikminStatusListener: PikminStatusListener) {
        this.pikminStatusListener = pikminStatusListener
        viewList.forEach { view ->
            view.setListener(statusListener)
        }
    }

    fun removeListener() {
        pikminStatusListener = null
        viewList.forEach { view ->
            view.removeListener()
        }
    }

    fun setPikminCount() {
        binding.tvCount.text = "%d/%d".format(collectedPikminCount, pikminCount)
    }

    fun getCollectedPikminCount() = collectedPikminCount

    private fun setAnimationSetting(animation: RotateAnimation) {
        animation.duration = ANIMATION_DURATION
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
