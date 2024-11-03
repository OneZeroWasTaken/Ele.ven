package com.zerozealed.eleven

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zerozealed.eleven.model.Time

class MainViewModel : ViewModel() {

    private val _arrivalTime: MutableLiveData<Time> = MutableLiveData(defaultArrival)
    val arrivalTime: LiveData<Time> = _arrivalTime

    private val _lunchTime: MutableLiveData<Time> = MutableLiveData(defaultLunch)
    val lunchTime: LiveData<Time> = _lunchTime

    private val _leaveTime: MutableLiveData<Time> = MutableLiveData(defaultLeave)
    val leaveTime: LiveData<Time> = _leaveTime

    fun getTotalWorkTime(): Time =
        leaveTime.requireValue() - lunchTime.requireValue() - arrivalTime.requireValue()

    fun setArrivalTime(time: Time) {
        _arrivalTime.value = time
    }

    fun setLunchTime(time: Time) {
        _lunchTime.value = time
    }

    fun setLeaveTime(time: Time) {
        _leaveTime.value = time
    }

    fun getTodaysFlex(): Time = getTotalWorkTime() - Time(8, 0)

    companion object {
        private val defaultArrival = Time(8, 0)
        private val defaultLunch = Time(0, 30)
        private val defaultLeave = Time(16, 30)
    }
}