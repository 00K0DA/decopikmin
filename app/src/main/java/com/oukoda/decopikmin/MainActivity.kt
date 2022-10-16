package com.oukoda.decopikmin

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.get
import com.oukoda.decopikmin.databinding.ActivityMainBinding
import com.oukoda.decopikmin.dataclass.Pikmin
import com.oukoda.decopikmin.enum.Costume
import com.oukoda.decopikmin.enum.DecorType
import com.oukoda.decopikmin.enum.PikminStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    companion object {
        val TAG: String? = MainActivity::class.simpleName

        interface PikminStatusGetter {
            fun getPikminStatus(key: String): PikminStatus
        }
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val pikminStatusGetter = object : PikminStatusGetter {
        override fun getPikminStatus(key: String): PikminStatus {
            return PikminStatus.create(sharedPreferences.getInt(key, PikminStatus.NotHave.value))
        }

    }
    private var collectedPikminCount = 0
    private lateinit var viewList: List<DecorTextView>
    private val statusListener = object : DecorTextView.Companion.PikminStatusListener {
        override fun onUpdateStatus(pikmin: Pikmin, oldStatus: PikminStatus) {
            val key = pikmin.createStatusKey()
            sharedPreferences.edit().putInt(key, pikmin.pikminStatus.value).apply()
            if (oldStatus == PikminStatus.NotHave) {
                collectedPikminCount += 1
            } else if (pikmin.pikminStatus == PikminStatus.NotHave) {
                collectedPikminCount -= 1
            }
            setCompleteText()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getPreferences(Context.MODE_PRIVATE)

        val mutableViewList = mutableListOf<DecorTextView>()
        DecorType.values().forEach { decorType ->
            val decorTextView = DecorTextView(
                applicationContext,
                decorType,
                pikminStatusGetter,
                statusListener
            )
            collectedPikminCount += decorTextView.getCollectedPikminCount()
            mutableViewList.add(decorTextView)
            binding.cl.addView(decorTextView)
        }
        viewList = mutableViewList.toList()
        setCompleteText()
    }

    private fun setCompleteText() {
        val pikminCount = Costume.getAllPikminCount()
        val percent = collectedPikminCount.toFloat() * 100 / pikminCount
        CoroutineScope(Dispatchers.Main).launch {
            binding.textView.text = getString(R.string.activity_main_complete_title).format(
                percent,
                collectedPikminCount,
                pikminCount
            )
        }
    }

    override fun onStart() {
        super.onStart()
        viewList.forEach {
            it.setListener(statusListener)
        }
    }

    override fun onStop() {
        viewList.forEach {
            it.removeListener()
        }
        super.onStop()
    }
}
