package com.trudeals.ui.isolated.chat

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.trudeals.R
import java.util.*
import kotlin.collections.ArrayList


class MessageAdapter(private val context: Context) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private var messageList = ArrayList<Message>()
    private var blinkItem = NO_POSITION

    interface QuoteClickListener {
        fun onQuoteClick(position: Int)
    }

    private var mQuoteClickListener: QuoteClickListener? = null

    fun setQuoteClickListener(listener: QuoteClickListener) {
        mQuoteClickListener = listener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MessageViewHolder {
        val messageViewHolder =
            MessageViewHolder(LayoutInflater.from(context).inflate(viewType, viewGroup, false))
        messageViewHolder.quoteLayout?.setOnClickListener {
            mQuoteClickListener?.onQuoteClick(messageList[messageViewHolder.adapterPosition].quotePos)
        }
        return messageViewHolder
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messageList[position]

        holder.txtSendMsg?.text = message.body
        holder.txtQuotedMsg?.text = message.quote
        //holder.textViewTime?.text =  message.time
        holder.dateText?.text = message.timerString

        if (blinkItem == position) {
            val anim: Animation = AlphaAnimation(0.0f, 0.5f)
            android.os.Handler().postDelayed({
                anim.duration = 200
                holder.itemView.startAnimation(anim)
                anim.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation?) {
                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        blinkItem = NO_POSITION
                    }

                    override fun onAnimationRepeat(animation: Animation?) {
                    }
                })
            }, 100)

        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItemViewType(messageList[position])
    }

    private fun getItemViewType(message: Message): Int {
        return if (message.type == MessageType.SEND) {
            if (message.quotePos == -1) R.layout.item_send_message_row
            else R.layout.item_send_message_quoted_row
        }
        else if(message.type == MessageType.RECEIVED) {
            if (message.quotePos == -1) R.layout.item_received_message_row
            else R.layout.item_received_message_quoted_row
        }else{
            R.layout.item_chat_divider
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setMessages(albumList: List<Message>) {
        this.messageList = albumList as ArrayList<Message>
        notifyDataSetChanged()
    }

    class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtSendMsg: AppCompatTextView? = view.findViewById(R.id.txtBody)
        val txtQuotedMsg: AppCompatTextView? = view.findViewById(R.id.textQuote)
        val quoteLayout: CardView? = view.findViewById(R.id.reply)
        val dateText: AppCompatTextView? = view.findViewById(R.id.textViewDate)
        val textViewTime: AppCompatTextView? = view.findViewById(R.id.txtTime)
        val startViewDivider: View? = view.findViewById(R.id.viewStart)
        val endViewDivider: View? = view.findViewById(R.id.viewEnd)
    }

    fun getList() : ArrayList<Message> = messageList

    fun blinkItem(position: Int) {
        blinkItem = position
        notifyItemChanged(position)
    }
}