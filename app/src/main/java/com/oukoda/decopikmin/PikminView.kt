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
import com.oukoda.decopikmin.enum.PikminStatus
import com.oukoda.decopikmin.enum.PikminType
import java.util.concurrent.atomic.AtomicInteger

@SuppressLint("ViewConstructor")
class PikminView(
    context: Context,
    private val pikmin: Pikmin,
    private var pikminStatusListener: PikminStatusListener?
) :
    LinearLayout(context) {
    companion object {
        val TAG: String? = PikminView::class.simpleName

        interface PikminStatusListener {
            fun onUpdateStatus(pikmin: Pikmin, oldStatus: PikminStatus)
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
            val oldStatus = pikmin.pikminStatus
            pikmin.statusUpdate()
            setStatusText()
            pikminStatusListener?.onUpdateStatus(pikmin, oldStatus)
        }
    }

    fun setListener(pikminStatusListener: PikminStatusListener) {
        this.pikminStatusListener = pikminStatusListener
    }

    fun removeListener() {
        pikminStatusListener = null
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
                binding.tvStatus.text = resources.getText(R.string.pikmin_status_already)
                binding.tvStatus.setTextColor(
                    resources.getColor(
                        R.color.pikmin_status_already,
                        null
                    )
                )
            }
            PikminStatus.Growing -> {
                binding.tvStatus.text = resources.getText(R.string.pikmin_status_growing)
                binding.tvStatus.setTextColor(
                    resources.getColor(
                        R.color.pikmin_status_growing,
                        null
                    )
                )
            }
            PikminStatus.NotHave -> {
                binding.tvStatus.text = resources.getText(R.string.pikmin_status_not_have)
                binding.tvStatus.setTextColor(
                    resources.getColor(
                        R.color.pikmin_status_not_have,
                        null
                    )
                )
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
