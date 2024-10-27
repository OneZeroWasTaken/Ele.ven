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
            binding.textArrival.text = getString(
                R.string.time_template,
                getString(R.string.arrival),
                time.toClockString()
            )
            updateTimeTexts()
        }
        viewModel.lunchTime.observe(this) { time ->
            binding.textLunch.text = getString(
                R.string.time_template,
                getString(R.string.lunch),
                time.toClockString()
            )
            updateTimeTexts()
        }
        viewModel.leaveTime.observe(this) { time ->
            binding.textLeave.text = getString(
                R.string.time_template,
                getString(R.string.leave),
                time.toClockString()
            )
            updateTimeTexts()
        }
    }

    private fun initButtons() {
        binding.buttonSelectArrival.setOnClickListener {
            openTimePicker(viewModel.arrivalTime.requireValue()) { time ->
                viewModel.setArrivalTime(time)
            }
        }
        binding.buttonSelectLunch.setOnClickListener {
            openTimePicker(viewModel.lunchTime.requireValue()) { time ->
                viewModel.setLunchTime(time)
            }
        }
        binding.buttonSelectLeave.setOnClickListener {
            openTimePicker(viewModel.leaveTime.requireValue()) { time ->
                viewModel.setLeaveTime(time)
            }
        }
    }

    private fun updateTimeTexts() {
        val workTime = viewModel.getTotalWorkTime()
        binding.textWorkTime.text = workTime.toClockString()
    }

    private fun openTimePicker(startTime: Time, selectedTime: (time: Time) -> Unit) {
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
            .setHour(startTime.hours)
            .setMinute(startTime.minutes)
            .setTitleText(getString(R.string.select_time))
            .build()

        picker.addOnPositiveButtonClickListener {
            selectedTime(Time(picker.hour, picker.minute))
        }
        picker.show(supportFragmentManager, TAG_TIME_PICKER)
    }

    companion object {
        const val TAG = "MainActivity"
        const val TAG_TIME_PICKER = "TimePicker"
    }
}