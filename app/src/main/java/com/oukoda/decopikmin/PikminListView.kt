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
    private val pikminList: List<Pikmin>,
    private val listener: PikminListViewListener
) : ConstraintLayout(context) {
    companion object {
        interface PikminListViewListener {
            fun onStatusChanged(
                updatePikmin: Pikmin
            )
        }
    }

    private val binding: ViewPikminListBinding
    private var pikminView7: List<FrameLayout>
    private var pikminView4: List<FrameLayout>
    private var pikminView3: List<FrameLayout>
    private var pikminView1: List<FrameLayout>

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

        setCostumeTextView()
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
        var viewIndex = 0
        for (i in pikminList.indices) {
            val pikmin = pikminList[i]
            viewList[viewIndex].addView(createPikminView(pikmin))
            viewIndex += 1
        }
    }

    private fun setCostumeTextView() {
        val costumeText = resources.getText(Costume.getCostumeTextId(costume))
        val haveCount = pikminList.filter { it.pikminStatus == PikminStatus.AlreadyExists }.size
        val pikminCount = pikminList.size
        if (haveCount == pikminCount) {
            binding.tvCostume.text =
                resources.getText(R.string.pikmin_list_view_complete).toString()
                    .format(costumeText)
        } else {
            binding.tvCostume.text =
                resources.getText(R.string.pikmin_list_view_progress).toString()
                    .format(costumeText, haveCount, pikminCount)
        }
    }

    private fun createPikminView(pikmin: Pikmin): PikminView {
        val pikminViewListener = object : PikminView.Companion.PikminViewListener {
            override fun onStatusChanged(
                updatePikmin: Pikmin
            ) {
                var index = -1
                for (i in pikminList.indices) {
                    val tempPikmin = pikminList[i]
                    if (tempPikmin == updatePikmin) {
                        index = i
                    }
                }
                if (index != -1) {
                    val mutableList = pikminList.toMutableList()
                    mutableList[index] = updatePikmin

                    setCostumeTextView()
                    listener.onStatusChanged(updatePikmin)
                }
            }
        }
        return PikminView(context, pikmin, pikminViewListener)
    }

    private fun enableView(view: View) {
        view.visibility = VISIBLE
        view.isClickable = true
        view.focusable = FOCUSABLE
    }
}
