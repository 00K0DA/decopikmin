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
                val mutablePikminStatusMap = mutableMapOf<PikminType, PikminStatus>()
                for (i in pikminTypeList.indices){
                    mutablePikminStatusMap[pikminTypeList[i]] = statusList[i]
                }
                haveCount += statusList.filter { it == PikminStatus.AlreadyExists }.size
                val pikminListView = PikminListView(
                    applicationContext, costume, mutablePikminStatusMap.toMap(), pikminViewListener)
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
        textView.text = resources.getText(DecorType.getDecorText(decorType))
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
