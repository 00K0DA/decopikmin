package com.oukoda.decopikmin

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.oukoda.decopikmin.databinding.ViewPikminListBinding
import com.oukoda.decopikmin.enum.Costume
import com.oukoda.decopikmin.enum.PikminStatus
import com.oukoda.decopikmin.enum.PikminType

@SuppressLint("ViewConstructor")
class PikminListView(
    context: Context,
    private val costume: Costume,
    private var pikminStatusMap: Map<PikminType, PikminStatus>,
    private val listener: PikminListViewListener
) : ConstraintLayout(context) {
    companion object {
        interface PikminListViewListener{
            fun onStatusChanged(
                pikminType: PikminType,
                costume: Costume,
                pikminStatus: PikminStatus
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
        pikminView7 = listOf(binding.pikminView71, binding.pikminView72, binding.pikminView73,
            binding.pikminView74, binding.pikminView75, binding.pikminView76, binding.pikminView77)
        pikminView4 = listOf(binding.pikminView41, binding.pikminView42, binding.pikminView43,
            binding.pikminView44)
        pikminView3 = listOf(binding.pikminView31, binding.pikminView32, binding.pikminView33)
        pikminView1 = listOf(binding.pikminView11)

        setCostumeTextView()
        val viewList: List<FrameLayout>
            when (pikminStatusMap.size) {
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
            else -> throw IllegalArgumentException("invalid pikmin list = $pikminStatusMap")
        }
        var viewIndex = 0
        for ((pikminType, pikminStatus) in pikminStatusMap) {
            viewList[viewIndex].addView(createPikminView(pikminType, pikminStatus))
            viewIndex += 1
        }
    }

    private fun setCostumeTextView() {
        val costumeText = resources.getText(Costume.getCostumeTextId(costume))
        val haveCount = pikminStatusMap.values.filter { it == PikminStatus.AlreadyExists }.size
        val pikminCount = pikminStatusMap.keys.size
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

    private fun createPikminView(pikminType: PikminType, pikminStatus: PikminStatus): PikminView {
        val pikminViewListener = object: PikminView.Companion.PikminViewListener{
            override fun onStatusChanged(
                pikminType: PikminType,
                costume: Costume,
                pikminStatus: PikminStatus
            ) {
                val mutableMap = pikminStatusMap.toMutableMap()
                mutableMap[pikminType] = pikminStatus
                pikminStatusMap = mutableMap.toMap()
                setCostumeTextView()
                listener.onStatusChanged(pikminType, costume, pikminStatus)
            }

        }
        return PikminView(context, pikminType, costume, pikminStatus, pikminViewListener)
    }

    private fun enableView(view: View) {
        view.visibility = VISIBLE
        view.isClickable = true
        view.focusable = FOCUSABLE
    }
}
