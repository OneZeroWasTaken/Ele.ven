package com.zerozealed.eleven

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.zerozealed.eleven.databinding.ActivityMainBinding
import com.zerozealed.eleven.model.Time

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setContentView(binding.root)

        initButtons()
        initObservers()
    }

    private fun initObservers() {
        viewModel.arrivalTime.observe(this) { time ->
            binding.textArrival.text = getString(R.string.time_template, "Arrival", time.toClockString())
            updateTimeTexts()
        }
        viewModel.lunchTime.observe(this) { time ->
            binding.textLunch.text = getString(R.string.time_template, "Lunch", time.toClockString())
            updateTimeTexts()
        }
        viewModel.leaveTime.observe(this) { time ->
            binding.textEnd.text = getString(R.string.time_template, "End", time.toClockString())
            updateTimeTexts()
        }
    }

    private fun initButtons() {
        binding.buttonSelectArrival.setOnClickListener {
            openTimePicker { time ->
                viewModel.setArrivalTime(time)
            }
        }
        binding.buttonSelectLunch.setOnClickListener {
            openTimePicker { time ->
                viewModel.setLunchTime(time)
            }
        }
        binding.buttonSelectEnd.setOnClickListener {
            openTimePicker { time ->
                viewModel.setLeaveTime(time)
            }
        }
    }

    private fun updateTimeTexts() {
        val workTime = viewModel.getTotalWorkTime()
        binding.textWorkTime.text = workTime.toClockString()
    }

    private fun openTimePicker(selectedTime: (time: Time) -> Unit) {
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
            .setHour(8)
            .setMinute(0)
            .setTitleText("Select Time")
            .build()

        picker.show(supportFragmentManager, "TimePicker")
        picker.addOnPositiveButtonClickListener {
            selectedTime(Time(picker.hour, picker.minute))
        }
    }

    companion object {
        const val TAG = "MainActivity"
    }
}