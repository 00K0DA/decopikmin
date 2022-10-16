package com.oukoda.decopikmin

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.oukoda.decopikmin.databinding.ActivityMainBinding
import com.oukoda.decopikmin.dataclass.Pikmin
import com.oukoda.decopikmin.enum.Costume
import com.oukoda.decopikmin.enum.DecorType
import com.oukoda.decopikmin.enum.PikminStatus
import com.oukoda.decopikmin.enum.PikminType
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

        val pikminViewListener = object : PikminListView.Companion.PikminListViewListener {
            override fun onStatusChanged(
                updatePikmin: Pikmin
            ) {
                val key = updatePikmin.createStatusKey()
                sharedPreferences.edit().putInt(key, updatePikmin.pikminStatus.value).apply()
                if (updatePikmin.pikminStatus == PikminStatus.AlreadyExists) {
                    haveCount += 1
                } else if (updatePikmin.pikminStatus == PikminStatus.NotHave) {
                    haveCount -= 1
                }
                setCompleteText()
            }
        }

        DecorType.values().forEach { decorType ->
            val decorTextView = DecorTextView(
                applicationContext,
                resources.getText(DecorType.getDecorText(decorType)).toString()
            )
            DecorType.getCostumes(decorType).forEach { costume ->
                val mutablePikminList = mutableListOf<Pikmin>()
                val pikminTypeList = Costume.getPikminList(costume)
                for (i in pikminTypeList.indices) {
                    val pikminType = pikminTypeList[i]
                    val number = mutablePikminList.count { it.pikminType == pikminType }
                    val pikmin = Pikmin.newInstance(decorType, costume, pikminType, number)
                    val key = pikmin.createStatusKey()
                    pikmin.pikminStatus =
                        PikminStatus.create(
                            sharedPreferences.getInt(key, PikminStatus.NotHave.value)
                        )
                    mutablePikminList.add(pikmin)
                }
                val pikminListView = PikminListView(
                    applicationContext,
                    costume,
                    mutablePikminList.toList(),
                    pikminViewListener
                )
                decorTextView.addPikminListView(pikminListView)
            }
            binding.cl.addView(decorTextView)
        }
        setCompleteText()
    }

    private fun setCompleteText() {
        val pikminCount = Costume.getAllPikminCount()
        val percent = haveCount.toFloat() * 100 / pikminCount
        CoroutineScope(Dispatchers.Main).launch {
            binding.textView.text = getString(R.string.activity_main_complete_title).format(percent)
        }
    }

}
