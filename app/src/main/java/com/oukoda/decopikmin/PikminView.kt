package com.oukoda.decopikmin

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import com.oukoda.decopikmin.databinding.ViewPikminBinding
import com.oukoda.decopikmin.dataclass.Pikmin
import com.oukoda.decopikmin.enum.Costume
import com.oukoda.decopikmin.enum.PikminStatus
import com.oukoda.decopikmin.enum.PikminType

@SuppressLint("ViewConstructor")
class PikminView(
    context: Context,
    private val pikmin: Pikmin,
    private val listener: PikminViewListener
) :
    LinearLayout(context) {
    companion object {
        val TAG: String? = PikminView::class.simpleName

        interface PikminViewListener {
            fun onStatusChanged(
                updatePikmin: Pikmin
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
            pikmin.statusUpdate()
            setStatusText()
            listener.onStatusChanged(pikmin)
        }
    }

    private fun setPikminTypeText() {
        Log.d(TAG, "setPikminTypeText: $pikmin")
        binding.tvPikminType.text =
            resources.getText(PikminType.getPikminStringId(pikmin.pikminType))
        binding.tvPikminType.setTextColor(Color.WHITE)
    }

    private fun setStatusText() {
        when (pikmin.pikminStatus) {
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
            (ResourcesCompat.getDrawable(resources, R.drawable.react, null)
                ?: return) as GradientDrawable
        drawable.setColor(
            when (pikmin.pikminType) {
                PikminType.Red -> resources.getColor(R.color.pikmin_red, null)
                PikminType.Blue -> resources.getColor(R.color.pikmin_blue, null)
                PikminType.Yellow -> resources.getColor(R.color.pikmin_yellow, null)
                PikminType.White -> resources.getColor(R.color.pikmin_white, null)
                PikminType.Purple -> resources.getColor(R.color.pikmin_purple, null)
                PikminType.Rock -> resources.getColor(R.color.pikmin_rock, null)
                PikminType.Wing -> resources.getColor(R.color.pikmin_wing, null)
            }
        )
        drawable.setStroke(3, Color.BLACK)
        binding.imageView.background = drawable
    }
}
