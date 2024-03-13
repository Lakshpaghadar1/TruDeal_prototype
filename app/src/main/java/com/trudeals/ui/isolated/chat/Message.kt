package com.trudeals.ui.isolated.chat

data class Message(var body: String, var timerString: String, var time : Long, var type: Int, var isDateVisible: Boolean = false) {

    var quote: String = ""
    var quotePos: Int = -1

    constructor(
        body: String,
        timeString: String,
        time:Long,
        type: Int,
        quote: String,
        quotePos: Int
    ) : this(body, timeString, time, type) {
        this.quote = quote
        this.quotePos = quotePos
    }

}

object MessageType {
    const val SEND = 1
    const val RECEIVED = 2
    const val CENTER = 3
}