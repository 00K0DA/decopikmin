package com.oukoda.decopikmin

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.oukoda.decopikmin.databinding.ActivityMainBinding
import com.oukoda.decopikmin.myclass.Costume
import com.oukoda.decopikmin.myclass.DecorType
import com.oukoda.decopikmin.myclass.PikminStatus
import com.oukoda.decopikmin.myclass.PikminType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    companion object {
        val TAG: String? = MainActivity::class.simpleName
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var haveCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getPreferences(Context.MODE_PRIVATE)

        val pikminViewListener = object : PikminView.Companion.PikminViewListener {
            override fun onStatusChanged(
                pikminType: PikminType,
                costume: Costume,
                pikminStatus: PikminStatus
            ) {
                val key = createStatusKey(pikminType, costume)
                sharedPreferences.edit().putInt(key, pikminStatus.value).apply()
                if (pikminStatus == PikminStatus.AlreadyExists) {
                    haveCount += 1
                } else if (pikminStatus == PikminStatus.NotHave) {
                    haveCount -= 1
                }
                setCompleteText()
            }
        }

        DecorType.values().forEach { decorType ->
            val decorTextView = createDecorTextView(decorType)
            binding.cl.addView(decorTextView)
            DecorType.getCostumes(decorType).forEach { costume ->
                val pikminTypeList = Costume.getPikminList(costume)
                val statusList: List<PikminStatus> = createStatusList(pikminTypeList, costume)
                haveCount += statusList.filter { it == PikminStatus.AlreadyExists }.size
                val pikminListView =
                    PikminListView(applicationContext,
                    pikminTypeList,
                    costume,
                    statusList,
                    pikminViewListener)
                binding.cl.addView(pikminListView)
            }
        }
        setCompleteText()
    }

    private fun createStatusList(pikminTypeList: List<PikminType>, costume: Costume): List<PikminStatus> {
        val statusList: MutableList<PikminStatus> = mutableListOf()
        pikminTypeList.forEach { pikminType ->
            val key = createStatusKey(pikminType, costume)
            val status = PikminStatus.create(
                sharedPreferences.getInt(key, PikminStatus.NotHave.value)
            )
            statusList.add(status)
        }
        return statusList.toList()
    }

    private fun createDecorTextView(decorType: DecorType): TextView {
        val textView = TextView(applicationContext)
        textView.textSize = 24F
        textView.gravity = Gravity.CENTER
        textView.setBackgroundColor(Color.parseColor("#FAFAD2"))
        textView.text = when (decorType) {
            DecorType.Restaurant -> resources.getText(R.string.decor_type_restaurant)
            DecorType.Cafe -> resources.getText(R.string.decor_type_cafe)
            DecorType.Sweetshop -> resources.getText(R.string.decor_type_sweetshop)
            DecorType.MovieTheater -> resources.getText(R.string.decor_type_movie_theater)
            DecorType.Pharmacy -> resources.getText(R.string.decor_type_pharmacy)
            DecorType.Zoo -> resources.getText(R.string.decor_type_zoo)
            DecorType.Forest -> resources.getText(R.string.decor_type_forest)
            DecorType.Waterside -> resources.getText(R.string.decor_type_waterside)
            DecorType.PostOffice -> resources.getText(R.string.decor_type_post_office)
            DecorType.ArtGallery -> resources.getText(R.string.decor_type_art_gallery)
            DecorType.Airport -> resources.getText(R.string.decor_type_airport)
            DecorType.Station -> resources.getText(R.string.decor_type_station)
            DecorType.Beach -> resources.getText(R.string.decor_type_beach)
            DecorType.BurgerPlace -> resources.getText(R.string.decor_type_burger_place)
            DecorType.CornerStore -> resources.getText(R.string.decor_type_corner_store)
            DecorType.Supermarket -> resources.getText(R.string.decor_type_supermarket)
            DecorType.Bakery -> resources.getText(R.string.decor_type_bakery)
            DecorType.HairSalon -> resources.getText(R.string.decor_type_hair_salon)
            DecorType.ClothesStore -> resources.getText(R.string.decor_type_clothes_store)
            DecorType.Park -> resources.getText(R.string.decor_type_park)
            DecorType.LibraryAndBookstore -> resources.getText(R.string.decor_type_library_and_bookstore)
            DecorType.Special -> resources.getText(R.string.decor_type_special)
            DecorType.LoadSide -> resources.getText(R.string.decor_type_load_side)
            DecorType.SushiRestaurant -> resources.getText(R.string.decor_type_sushi_restaurant)
            DecorType.Mountain -> resources.getText(R.string.decor_type_mountain)
            DecorType.Weather -> resources.getText(R.string.decor_type_weather)
            DecorType.ThemePark -> resources.getText(R.string.decor_type_theme_park)
        }
        return textView
    }

    private fun createStatusKey(pikminType: PikminType, costume: Costume): String {
        return "${pikminType.value}-${costume.value}"
    }

    private fun setCompleteText() {
        val pikminCount = Costume.getAllPikminCount()
        val percent = haveCount.toFloat() * 100 / pikminCount
        CoroutineScope(Dispatchers.Main).launch {
            binding.textView.text = getString(R.string.activity_main_complete_title).format(percent)
        }
    }

    override fun onStart() {
        super.onStart()
    }
    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
