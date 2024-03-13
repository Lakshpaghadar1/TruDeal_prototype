package com.trudeals.ui.isolated.chat

import android.os.Handler
import android.os.Message
import android.view.View

class ViewHandler(view: View) : Handler() {

    var myView: View = view

    override fun handleMessage(msg: Message) {
        myView.visibility = View.GONE
    }
}
