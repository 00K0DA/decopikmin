package com.oukoda.decopikmin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.oukoda.decopikmin.databinding.ViewPikminListBinding
import com.oukoda.decopikmin.myclass.Costume
import com.oukoda.decopikmin.myclass.PikminStatus
import com.oukoda.decopikmin.myclass.PikminType

class PikminListView(
    context: Context,
    private val pikminTypeList: List<PikminType>,
    private val costume: Costume,
    private var pikminStatusList: List<PikminStatus>,
    private val listener: PikminView.Companion.PikminViewListener
) :
    ConstraintLayout(context) {
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
            when (pikminTypeList.size) {
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
            else -> throw IllegalArgumentException("invalid pikmin list = $pikminTypeList")
        }
        for (i in pikminTypeList.indices) {
            viewList[i].addView(createPikminView(pikminTypeList[i], pikminStatusList[i]))
        }
    }

    private fun setCostumeTextView() {
        val costumeText = when (costume) {
            Costume.Chef -> resources.getText(R.string.costume_chef)
            Costume.ShinyChef -> resources.getText(R.string.costume_shiny_chef)
            Costume.CoffeeCup -> resources.getText(R.string.costume_coffee_cup)
            Costume.Macaron -> resources.getText(R.string.costume_macaron)
            Costume.PopcornSnack -> resources.getText(R.string.costume_popcorn_snack)
            Costume.Toothbrush -> resources.getText(R.string.costume_toothbrush)
            Costume.Dandelion -> resources.getText(R.string.costume_dandelion)
            Costume.StagBeetle -> resources.getText(R.string.costume_stag_beetle)
            Costume.Acorn -> resources.getText(R.string.costume_acorn)
            Costume.FishingLure -> resources.getText(R.string.costume_fishing_lure)
            Costume.Stamp -> resources.getText(R.string.costume_stamp)
            Costume.PictureFrame -> resources.getText(R.string.costume_picture_frame)
            Costume.ToyAirPlane -> resources.getText(R.string.costume_toy_air_plane)
            Costume.PaperTrain -> resources.getText(R.string.costume_paper_train)
            Costume.Ticket -> resources.getText(R.string.costume_ticket)
            Costume.Shell -> resources.getText(R.string.costume_shell)
            Costume.Burger -> resources.getText(R.string.costume_burger)
            Costume.BottleCap -> resources.getText(R.string.costume_bottle_cap)
            Costume.Snack -> resources.getText(R.string.costume_snack)
            Costume.Mushroom -> resources.getText(R.string.costume_mushroom)
            Costume.Banana -> resources.getText(R.string.costume_banana)
            Costume.Baguette -> resources.getText(R.string.costume_baguette)
            Costume.Scissors -> resources.getText(R.string.costume_scissors)
            Costume.HairTie -> resources.getText(R.string.costume_hair_tie)
            Costume.Clover -> resources.getText(R.string.costume_clover)
            Costume.FourLeafClover -> resources.getText(R.string.costume_four_leaf_clover)
            Costume.TinyBook -> resources.getText(R.string.costume_tiny_book)
            Costume.Sushi -> resources.getText(R.string.costume_sushi)
            Costume.MountainPinBadge -> resources.getText(R.string.costume_mountain_pin_badge)
            Costume.LeafHat -> resources.getText(R.string.costume_leaf_hat)
            Costume.Sticker -> resources.getText(R.string.costume_sticker)
            Costume.Mario -> resources.getText(R.string.costume_mario)
            Costume.NewYear -> resources.getText(R.string.costume_new_year)
            Costume.Chess -> resources.getText(R.string.costume_chess)
            Costume.ThemeParkTicket -> resources.getText(R.string.costume_theme_park_ticket)
            Costume.FingerBoard -> resources.getText(R.string.costume_theme_finger_board)
            Costume.BusPaperCraft -> resources.getText(R.string.costume_theme_bus_parer_craft)
        }
        val haveCount = pikminStatusList.filter { it == PikminStatus.AlreadyExists }.size
        val pikminCount = pikminTypeList.size
        if (haveCount == pikminCount) {
            binding.tvCostume.text = "$costumeText(Complete!!)"
        } else {
            binding.tvCostume.text = "$costumeText($haveCount/$pikminCount)"
        }
    }

    fun getCostume() = costume

    private fun createPikminView(pikminType: PikminType, pikminStatus: PikminStatus): PikminView {
        return PikminView(context, pikminType, costume, pikminStatus, listener)
    }

    private fun enableView(view: View) {
        view.visibility = VISIBLE
        view.isClickable = true
        view.focusable = FOCUSABLE
    }
}
