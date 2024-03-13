package com.trudeals.ui.isolated.dummydata

import com.trudeals.R

data class ChatData(
    var profileImage: Int,
    var userName: String,
    var lastMsg: String,
    var unReadMsgCount: String? = null,
    var lastMsgTextColor: Int = R.color.C_ED1D26,
    var time: String
)
