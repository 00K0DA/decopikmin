package com.oukoda.decopikmin

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import com.oukoda.decopikmin.databinding.ViewPikminBinding
import com.oukoda.decopikmin.myclass.Costume
import com.oukoda.decopikmin.myclass.PikminStatus
import com.oukoda.decopikmin.myclass.PikminType

class PikminView(
    context: Context,
    private val pikminType: PikminType,
    private val costume: Costume,
    private var pikminStatus: PikminStatus,
    private val listener: PikminViewListener
) :
    LinearLayout(context) {
    companion object {
        val TAG: String? = PikminView::class.simpleName
        interface PikminViewListener {
            fun onStatusChanged(
                pikminType: PikminType,
                costume: Costume,
                pikminStatus: PikminStatus
            )
        }
    }

    private val binding: ViewPikminBinding
    init {
        binding = ViewPikminBinding.inflate(LayoutInflater.from(context), this, true)
        orientation = HORIZONTAL
        setPikminTypeText()
        setStatusText()
        setDrawableColor()

        binding.cl.setOnClickListener {
            toggleStatus()
            setStatusText()
            listener.onStatusChanged(pikminType, costume, pikminStatus)
        }
    }

    private fun toggleStatus() {
        pikminStatus = when (pikminStatus) {
            PikminStatus.AlreadyExists -> PikminStatus.Growing
            PikminStatus.Growing -> PikminStatus.NotHave
            PikminStatus.NotHave -> PikminStatus.AlreadyExists
        }
    }

    private fun setPikminTypeText() {
        Log.d(TAG, "setPikminTypeText: $costume $pikminType $pikminStatus")
        Log.d(TAG, "setPikminTypeText: ${binding.tvPikminType.text}")
        binding.tvPikminType.text = when (pikminType) {
            PikminType.Red -> "赤"
            PikminType.Blue -> "青"
            PikminType.Yellow -> "黄"
            PikminType.White -> "白"
            PikminType.Purple -> "紫"
            PikminType.Rock -> "岩"
            PikminType.Wing -> "羽"
        }
        binding.tvPikminType.setTextColor(Color.WHITE)
    }

    private fun setStatusText() {
        when (pikminStatus) {
            PikminStatus.AlreadyExists -> {
                binding.tvStatus.text = "保持"
                binding.tvStatus.setTextColor(Color.parseColor("#4DB56A"))
            }
            PikminStatus.Growing -> {
                binding.tvStatus.text = "育成中"
                binding.tvStatus.setTextColor(Color.parseColor("#FF7E00"))
            }
            PikminStatus.NotHave -> {
                binding.tvStatus.text = "未保持"
                binding.tvStatus.setTextColor(Color.GRAY)
            }
        }
    }

    private fun setDrawableColor() {
        val drawable: GradientDrawable =
            (ResourcesCompat.getDrawable(resources, R.drawable.react, null) ?: return) as GradientDrawable
        drawable.setColor(when (pikminType) {
            PikminType.Red -> resources.getColor(R.color.pikmin_red, null)
            PikminType.Blue -> resources.getColor(R.color.pikmin_blue, null)
            PikminType.Yellow -> resources.getColor(R.color.pikmin_yellow, null)
            PikminType.White -> resources.getColor(R.color.pikmin_white, null)
            PikminType.Purple -> resources.getColor(R.color.pikmin_purple, null)
            PikminType.Rock -> resources.getColor(R.color.pikmin_rock, null)
            PikminType.Wing -> resources.getColor(R.color.pikmin_wing, null)
        })
        drawable.setStroke(3, Color.BLACK)
        binding.imageView.background = drawable
    }
}
