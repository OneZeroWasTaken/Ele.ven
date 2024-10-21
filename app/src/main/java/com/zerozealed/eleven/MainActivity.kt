package com.zerozealed.eleven

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.zerozealed.eleven.databinding.ActivityMainBinding
import com.zerozealed.eleven.model.Time

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var arrivalTime = Time(8, 0)
    private var lunchTime = Time(0, 30)
    private var endTime = Time(16, 30)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initButtons()
    }

    private fun initButtons() {
        binding.buttonSelectArrival.setOnClickListener {
            openTimePicker { hour, minute ->
                arrivalTime = Time(hour, minute)
                binding.textArrival.text = getString(R.string.time_template, "Arrival", hour, minute)
                updateTimeTexts()
            }
        }
        binding.buttonSelectLunch.setOnClickListener {
            openTimePicker { hour, minute ->
                lunchTime = Time(hour, minute)
                binding.textLunch.text = getString(R.string.time_template, "Lunch", hour, minute)
                updateTimeTexts()
            }
        }
        binding.buttonSelectEnd.setOnClickListener {
            openTimePicker { hour, minute ->
                endTime = Time(hour, minute)
                binding.textEnd.text = getString(R.string.time_template, "End", hour, minute)
                updateTimeTexts()
            }
        }
    }

    private fun updateTimeTexts() {
        val workTime = endTime - lunchTime - arrivalTime
        binding.textWorkTime.text = workTime.toClockString()
    }

    private fun openTimePicker(selectedTime: (hour: Int, minute: Int) -> Unit) {
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
            .setHour(8)
            .setMinute(0)
            .setTitleText("Select Time")
            .build()

        picker.show(supportFragmentManager, "TimePicker")
        picker.addOnPositiveButtonClickListener {
            selectedTime(picker.hour, picker.minute)
        }
    }

    companion object {
        const val TAG = "MainActivity"
    }
}