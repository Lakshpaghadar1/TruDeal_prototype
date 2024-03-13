package com.trudeals.ui.isolated.chat

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object SampleMessages {

    fun getSampleMessages():List<Message>{
        val messages = ArrayList<Message>()
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        val centerFormat = SimpleDateFormat("EEE, dd/MM", Locale.ENGLISH)

        val cal = Calendar.getInstance()
       // val currentTime = cal.get(Calendar.HOUR) + cal.get(Calendar.MINUTE)

        messages.add(
            Message("1", format.format(cal.timeInMillis),  cal.timeInMillis,
                MessageType.SEND
            )
        )
        messages.add(
            Message("2", format.format(cal.timeInMillis),  cal.timeInMillis,
                MessageType.RECEIVED
            )
        )
        messages.add(
            Message("3", format.format(cal.timeInMillis),  cal.timeInMillis,
                MessageType.SEND
            )
        )
        messages.add(
            Message("4", format.format(cal.timeInMillis),  cal.timeInMillis,
                MessageType.SEND
            )
        )

        cal.add(Calendar.DAY_OF_MONTH, 1)
        messages.add(
            Message("5", format.format(cal.timeInMillis), cal.timeInMillis,
                MessageType.RECEIVED
            )
        )
        messages.add(
            Message("6", format.format(cal.timeInMillis),  cal.timeInMillis,
                MessageType.RECEIVED
            )
        )
        messages.add(Message("7", format.format(cal.timeInMillis), cal.timeInMillis, MessageType.RECEIVED))
        messages.add(Message("8", format.format(cal.timeInMillis), cal.timeInMillis, MessageType.SEND))
        messages.add(Message("9", format.format(cal.timeInMillis), cal.timeInMillis, MessageType.SEND))

        cal.add(Calendar.DAY_OF_MONTH, 1)
        messages.add(Message("7", format.format(cal.timeInMillis), cal.timeInMillis, MessageType.RECEIVED))
        messages.add(Message("8", format.format(cal.timeInMillis), cal.timeInMillis, MessageType.SEND))

        //Final list received
        val modifiedMessages = kotlin.collections.ArrayList<Message>()
        messages.forEachIndexed { index, message ->
            if(index > messages.size - 2){
                modifiedMessages.add(message)
                return modifiedMessages
            }
            if(message.timerString.substring(0,2).toInt() != messages[index+1].timerString.substring(0,2).toInt()){
                modifiedMessages.add(message)
                modifiedMessages.add(Message("", centerFormat.format(messages[index+1].time), messages[index+1].time, MessageType.CENTER))
            }else{
                modifiedMessages.add(message)
            }
        }
        return modifiedMessages
    }
}