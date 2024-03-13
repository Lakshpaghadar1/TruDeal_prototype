package com.trudeals.ui.isolated.businessuser.fragment

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.R
import com.trudeals.databinding.ContactOptionsBottomsheetBinding
import com.trudeals.di.component.BottomSheetComponent
import com.trudeals.ui.base.BaseBottomSheet
import com.trudeals.ui.isolated.businessuser.adapter.ContactOptionsAdapter
import com.trudeals.utils.DataUtils
import com.trudeals.utils.OnClick

class ContactOptionsBottomsheet : BaseBottomSheet<ContactOptionsBottomsheetBinding>() {

    private val contactOptionsAdapter by lazy { ContactOptionsAdapter() }

    override fun createViewBinding(inflater: LayoutInflater): ContactOptionsBottomsheetBinding {
        return ContactOptionsBottomsheetBinding.inflate(inflater)
    }

    override fun inject(bottomSheetComponent: BottomSheetComponent) {
        bottomSheetComponent.inject(this)
    }

    override fun bindData() {
        setRecyclerView()
        clickListener()
    }

    override fun destroyViewBinding() {}

    private fun setRecyclerView() = with(binding.recyclerViewContactOption) {
        contactOptionsAdapter.setItems(DataUtils.contactOptions(), 1)
        layoutManager = GridLayoutManager(
            requireContext(),
            resources.getInteger(R.integer.int_5),
            RecyclerView.VERTICAL,
            false
        )
        adapter = contactOptionsAdapter
    }

    private fun clickListener() {
        contactOptionsAdapter.setOnClickOfView { _, _, onClick ->
            when(onClick) {
                OnClick.PHONE -> {
                    showMessage("PHONE")
                    dialPhoneNumber()
                    dismiss()
                }
                OnClick.SMS -> {
                    showMessage("SMS")
                    sendSMS()
                    dismiss()
                }
                OnClick.EMAIL -> {
                    sendEmail()
                    dismiss()
                }
                else -> {}
            }
        }
    }

    private fun sendEmail() {
        val emailIntent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "email_to"))
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("recipient@example.com")) // Specify recipient email address
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email Subject") // Specify email subject
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email Body") // Specify email body
        startActivity(emailIntent)
    }

    private fun sendSMS() {
        val sendIntent = Intent(Intent.ACTION_VIEW)
        sendIntent.data = Uri.parse("smsto:") // Specify "sms to:" to open the SMS app
        sendIntent.putExtra("address", "1234567890") // Specify recipient phone number
        sendIntent.putExtra("sms_body", "SMS Body") // Specify SMS body
        startActivity(sendIntent)
    }

    private fun dialPhoneNumber() {
        val dialIntent = Intent(Intent.ACTION_DIAL)
        dialIntent.data = Uri.parse("tel:0123456789")
        startActivity(dialIntent)
    }
}