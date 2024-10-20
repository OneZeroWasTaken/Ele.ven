package com.zerozealed.eleven

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.zerozealed.eleven.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var arrivalTime: Pair<Int, Int> = 8 to 0
    private var lunchTime: Pair<Int, Int> = 0 to 30
    private var endTime: Pair<Int, Int> = 16 to 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initButtons()
    }

    private fun initButtons() {
        binding.buttonSelectArrival.setOnClickListener {
            openTimePicker { hour, minute ->
                arrivalTime = hour to minute
                binding.textArrival.text = getString(R.string.time_template, "Arrival", hour, minute)
                updateTimeTexts()
            }
        }
        binding.buttonSelectLunch.setOnClickListener {
            openTimePicker { hour, minute ->
                lunchTime = hour to minute
                binding.textLunch.text = getString(R.string.time_template, "Lunch", hour, minute)
                updateTimeTexts()
            }
        }
        binding.buttonSelectEnd.setOnClickListener {
            openTimePicker { hour, minute ->
                endTime = hour to minute
                binding.textEnd.text = getString(R.string.time_template, "End", hour, minute)
                updateTimeTexts()
            }
        }
    }

    private fun updateTimeTexts() {
        val workHours = endTime.first - lunchTime.first - arrivalTime.first
        val workMinutes = endTime.second - lunchTime.second - arrivalTime.second
        binding.textWorkTime.text = getString(R.string.time_template, "Work time", workHours, workMinutes)
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