package com.trudeals.ui.isolated.chat

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.text.SimpleDateFormat
import java.util.*

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private var messagesList: MutableList<Message> = ArrayList()
    private val messagesListLiveData = MutableLiveData<List<Message>>()
    var currentMessageHeight = 0
    val currentCalendar = Calendar.getInstance()
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
    val centerFormat = SimpleDateFormat("EEE, dd/MM", Locale.ENGLISH)

    init {
        messagesList.addAll(SampleMessages.getSampleMessages())
        messagesListLiveData.value = messagesList
    }

    fun addMessage(body: String) {
        messagesList.add(
            Message(
                body,
                format.format(currentCalendar.timeInMillis),
                currentCalendar.timeInMillis,
                MessageType.SEND
            )
        )
        messagesList.add(
            Message(
                body,
                format.format(currentCalendar.timeInMillis),
                currentCalendar.timeInMillis,
                MessageType.RECEIVED
            )
        )
        messagesListLiveData.value = messagesList
    }


    fun addQuotedMessage(body: String, quote: String, quotePos: Int) {
        messagesList.add(
            Message(
                body,
                format.format(currentCalendar.timeInMillis),
                currentCalendar.timeInMillis,
                MessageType.SEND,
                quote,
                quotePos
            )
        )
        messagesList.add(
            Message(
                body,
                format.format(currentCalendar.timeInMillis),
                currentCalendar.timeInMillis,
                MessageType.RECEIVED,
                quote,
                quotePos
            )
        )
        messagesListLiveData.value = messagesList
    }


    fun getDisplayMessage(): LiveData<List<Message>> {
        return messagesListLiveData
    }
}