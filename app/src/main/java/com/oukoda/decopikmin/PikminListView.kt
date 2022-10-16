package com.oukoda.decopikmin

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.oukoda.decopikmin.databinding.ViewPikminListBinding
import com.oukoda.decopikmin.dataclass.Pikmin
import com.oukoda.decopikmin.enum.Costume
import com.oukoda.decopikmin.enum.PikminStatus

@SuppressLint("ViewConstructor")
class PikminListView(
    context: Context,
    private val costume: Costume,
    pikminList: List<Pikmin>,
    private var pikminStatusListener: PikminStatusListener?
) : ConstraintLayout(context) {

    companion object {
        interface PikminStatusListener {
            fun onUpdateStatus(pikmin: Pikmin, oldStatus: PikminStatus)
        }
    }

    private val binding: ViewPikminListBinding
    private var pikminView7: List<FrameLayout>
    private var pikminView4: List<FrameLayout>
    private var pikminView3: List<FrameLayout>
    private var pikminView1: List<FrameLayout>
    private val pikminViewList: List<PikminView>
    private val pikminCount: Int
    private var collectedPikminCount = 0

    private val statusListener = object : PikminView.Companion.PikminStatusListener {
        override fun onUpdateStatus(pikmin: Pikmin, oldStatus: PikminStatus) {
            if (oldStatus == PikminStatus.NotHave) {
                collectedPikminCount += 1
            } else if (pikmin.pikminStatus == PikminStatus.NotHave) {
                collectedPikminCount -= 1
            }
            setCostumeTextView()
            pikminStatusListener?.onUpdateStatus(pikmin, oldStatus)
        }
    }

    init {
        binding = ViewPikminListBinding.inflate(LayoutInflater.from(context), this, true)
        pikminView7 = listOf(
            binding.pikminView71, binding.pikminView72, binding.pikminView73,
            binding.pikminView74, binding.pikminView75, binding.pikminView76, binding.pikminView77
        )
        pikminView4 = listOf(
            binding.pikminView41, binding.pikminView42, binding.pikminView43,
            binding.pikminView44
        )
        pikminView3 = listOf(binding.pikminView31, binding.pikminView32, binding.pikminView33)
        pikminView1 = listOf(binding.pikminView11)

        val viewList: List<FrameLayout>
        when (pikminList.size) {
            pikminView7.size -> {
                viewList = pikminView7
                enableView(binding.clPikmin7)
            }
            pikminView4.size -> {
                viewList = pikminView4
                enableView(binding.clPikmin4)
            }
            pikminView3.size -> {
                viewList = pikminView3
                enableView(binding.clPikmin3)
            }
            pikminView1.size -> {
                viewList = pikminView1
                enableView(binding.clPikmin1)
            }
            else -> throw IllegalArgumentException("invalid pikmin list = $pikminList")
        }
        collectedPikminCount = pikminList.filter { it.pikminStatus != PikminStatus.NotHave }.size
        pikminCount = pikminList.size
        setCostumeTextView()

        var viewIndex = 0
        val mutablePikminViewList = mutableListOf<PikminView>()
        for (i in pikminList.indices) {
            val pikmin = pikminList[i]
            val pikminView = createPikminView(pikmin)
            mutablePikminViewList.add(pikminView)
            viewList[viewIndex].addView(pikminView)
            viewIndex += 1
        }
        pikminViewList = mutablePikminViewList.toList()
    }

    fun setListener(pikminStatusListener: PikminStatusListener) {
        this.pikminStatusListener = pikminStatusListener
        pikminViewList.forEach { view ->
            view.setListener(statusListener)
        }
    }

    fun removeListener() {
        pikminStatusListener = null
        pikminViewList.forEach { view ->
            view.removeListener()
        }
    }

    fun getPikminCount() = pikminCount

    fun getCollectedPikminCount() = collectedPikminCount

    private fun setCostumeTextView() {
        val costumeText = resources.getText(Costume.getCostumeTextId(costume))
        binding.tvCostume.text =
            resources.getText(R.string.pikmin_list_view_progress).toString()
                .format(costumeText, collectedPikminCount, pikminCount)
    }

    private fun createPikminView(pikmin: Pikmin): PikminView {
        return PikminView(context, pikmin, statusListener)
    }

    private fun enableView(view: View) {
        view.visibility = VISIBLE
        view.isClickable = true
        view.focusable = FOCUSABLE
    }

}
