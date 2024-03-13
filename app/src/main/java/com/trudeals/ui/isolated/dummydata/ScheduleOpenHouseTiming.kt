package com.trudeals.ui.isolated.dummydata

data class ScheduleOpenHouseTiming(
    var timeSlot: String,
    var isDealClosed: Boolean = false,
    var isSelected: Boolean = false
)
