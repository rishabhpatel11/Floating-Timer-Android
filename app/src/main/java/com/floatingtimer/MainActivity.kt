package com.floatingtimer

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.NumberPicker
import android.widget.NumberPicker.OnValueChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class MainActivity : AppCompatActivity() {

    private var START_TIME_IN_MS: Long = 60000

    private lateinit var mTextViewCountDown: TextView
    private lateinit var mButtonStartPause: Button
    private lateinit var mButtonReset: Button
    private lateinit var mButtonSetTimer: Button

    private lateinit var mCountDownTimer: CountDownTimer
    private var mTimerRunning = false
    private var mTimeLeftInMS = START_TIME_IN_MS

    private lateinit var mTextViewSetCount: TextView
    private var setCount = 0
    private lateinit var mButtonResetSetCount: ImageButton

    private lateinit var picker: NumberPicker


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startFloatingService()

        setContentView(R.layout.activity_main)

        mTextViewCountDown = findViewById(R.id.text_view_countdown)
        mButtonStartPause = findViewById(R.id.button_start_pause)
        mButtonReset = findViewById(R.id.button_reset)
        mButtonSetTimer = findViewById(R.id.button_set_timer)

        mTextViewSetCount = findViewById(R.id.text_view_set_count_number)
        mButtonResetSetCount = findViewById(R.id.button_reset_set_count)

        picker = findViewById(R.id.number_picker_set_timer)
        picker.maxValue = 3;
        picker.minValue = 0;
        //var pickerVals: Array<String>
        var pickerValues = arrayOf("30", "60", "90", "120")
        picker.displayedValues = pickerValues

        picker.setOnValueChangedListener(OnValueChangeListener { numberPicker, i, i1 ->
            val valuePicker1: Int = picker.value
            Log.d("picker value", pickerValues[valuePicker1]);
        })

        mButtonStartPause.setOnClickListener(View.OnClickListener {
            if (mTimerRunning) {
                pauseTimer()
            } else {
                startTimer()
                setCount += 1
                mTextViewSetCount.setText(Integer.toString(setCount))
            }
        })

        mButtonReset.setOnClickListener(View.OnClickListener { resetTimer() })

        mButtonSetTimer.setOnClickListener(View.OnClickListener { setTimer() })

        mButtonResetSetCount.setOnClickListener(View.OnClickListener {
            setCount = 0
            mTextViewSetCount.setText(Integer.toString(0))
        })

        updateCountDownText()

    }

    private fun resetTimer() {
        if (mTimerRunning) {
            mTimerRunning = false
            mCountDownTimer!!.cancel()
            mButtonStartPause!!.text = "start"
        }
        mTimeLeftInMS = START_TIME_IN_MS
        updateCountDownText()
        mButtonReset!!.visibility = View.INVISIBLE
        mButtonStartPause!!.visibility = View.VISIBLE
    }

    private fun startTimer() {
        mCountDownTimer = object : CountDownTimer(mTimeLeftInMS, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                mTimeLeftInMS = millisUntilFinished
                updateCountDownText()
            }

            override fun onFinish() {
                mTimerRunning = false
                mButtonStartPause!!.text = "RESET"
                //mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset!!.visibility = View.INVISIBLE
            }
        }.start()
        mTimerRunning = true
        mButtonStartPause!!.text = "pause"
        mButtonReset!!.visibility = View.VISIBLE
    }

    private fun updateCountDownText() {
        val seconds = (mTimeLeftInMS / 1000).toInt()
        val timeLeftFormatted = String.format(Locale.getDefault(), "%02d", seconds)
        mTextViewCountDown!!.text = timeLeftFormatted
    }

    private fun pauseTimer() {
        mCountDownTimer!!.cancel()
        mTimerRunning = false
        mButtonStartPause!!.text = "Start"
        //mButtonReset.setVisibility(View.VISIBLE);
    }

    private fun setTimer() {
        Log.d("openDialog", "Before")
        //val exampleDialog = ExampleDialog()
        //exampleDialog.show(supportFragmentManager, "example dialog")
    }

    fun applyTime(time: Int) {
        START_TIME_IN_MS = (time * 1000).toLong()
        resetTimer()
        Log.d("applyTime", START_TIME_IN_MS.toString())
    }
}