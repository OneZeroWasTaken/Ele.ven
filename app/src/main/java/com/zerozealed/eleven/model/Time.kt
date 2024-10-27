package com.zerozealed.eleven.model

import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

class Time(
    private val duration: Duration
) {
    constructor(hour: Int, minute: Int) : this(hour.hours + minute.minutes) {
        require(hour >= 0) {
            "Hour must be >= 0"
        }
        require(minute in 0..<60) {
            "Minute must be in 0..<60"
        }
    }

    val hours: Int = duration.inWholeHours.toInt()
    val minutes: Int = duration.inWholeMinutes.toInt() % 60

    operator fun plus(other: Time): Time = Time(this.duration + other.duration)

    operator fun minus(other: Time): Time = Time(this.duration - other.duration)

    fun toClockString(): String {
        val h = duration.inWholeHours
        val m = duration.inWholeMinutes % 60
        return "$h:${m.toString().padStart(2, '0')}"
    }
}