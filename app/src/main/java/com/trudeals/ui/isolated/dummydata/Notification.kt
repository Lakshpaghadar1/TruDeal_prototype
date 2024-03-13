package com.trudeals.ui.isolated.dummydata

data class Notification(var date: String, var subData: ArrayList<SubNotification>)

data class SubNotification(var time: String, var title: String, var description: String,)
