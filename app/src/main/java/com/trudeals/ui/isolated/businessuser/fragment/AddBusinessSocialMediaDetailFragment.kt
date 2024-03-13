package com.trudeals.ui.isolated.businessuser.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import com.trudeals.R
import com.trudeals.databinding.AddBusinessSocialMediaDetailsFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.exception.ApplicationException
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.home.businessuser.HomeActivityBU
import com.trudeals.ui.home.businessuser.fragment.HomeFragmentBU
import com.trudeals.ui.isolated.dummydata.BusinessProfileDetailMain
import com.trudeals.utils.StepsCount
import com.trudeals.utils.constants.RegexConstant
import com.trudeals.utils.countrycodepicker.CountryCodeConstants
import com.trudeals.utils.countrycodepicker.CountryCodeFragment
import com.trudeals.utils.extension.applyFilter
import com.trudeals.utils.extension.trimmedText

class AddBusinessSocialMediaDetailFragment :
    BaseFragment<AddBusinessSocialMediaDetailsFragmentBinding>(), View.OnClickListener {

    //For country code
    private lateinit var countryStartForResult: ActivityResultLauncher<Intent>

    private val previousScreenData by lazy {
        arguments?.getParcelable<BusinessProfileDetailMain>(DATA)
    }


    private val sourceScreen by lazy { arguments?.getString(SOURCE_SCREEN) }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): AddBusinessSocialMediaDetailsFragmentBinding {
        return AddBusinessSocialMediaDetailsFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        countryStartForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val bundle = result.data?.extras

                    bundle?.let {
                        bundle.getString(CountryCodeConstants.COUNTRY_CODE)?.let {
                            binding.layoutPhoneNumber.textViewCountryCode.text = it
                        }
                    }
                }
            }
    }

    override fun bindData() {
        setData()
        setFilters()
        clickListener()
    }

    override fun setUpToolbar() {}

    private fun setData() {
        when (sourceScreen) {
            HomeFragmentBU::class.java.simpleName, HomeActivityBU::class.java.simpleName -> {
                setDefaultData()
            }
        }
    }

    private fun setDefaultData() = with(binding) {
        editTextEmailAddress.setText(getString(R.string.label_x, "xyz@xyz.com"))
        layoutPhoneNumber.textViewCountryCode.text = getString(R.string.label_x, "+91")
        layoutPhoneNumber.editTextPhoneNumber.setText(getString(R.string.label_x, "5623698523"))
        editTextFacebookLink.setText(getString(R.string.label_x, "xyz.com"))
        editTextTwitterLink.setText(getString(R.string.label_x, "xyz.com"))
        editTextGoogleProfile.setText(getString(R.string.label_x, "xyz.com"))
        editTextInstagramLink.setText(getString(R.string.label_x, "xyz.com"))
        editTextYelpLink.setText(getString(R.string.label_x, "xyz.com"))
        editTextMetaKeyword.setText(getString(R.string.label_x, "xyz"))
    }


    private fun setFilters() = with(binding) {
        editTextEmailAddress.filters = arrayOf(
            editTextEmailAddress.applyFilter(blockSpecialChar = true)
        )
        editTextFacebookLink.filters = arrayOf(editTextEmailAddress.applyFilter(applyEmojiFilter = true))
        editTextTwitterLink.filters = arrayOf(editTextEmailAddress.applyFilter(applyEmojiFilter = true))
        editTextGoogleProfile.filters = arrayOf(editTextEmailAddress.applyFilter(applyEmojiFilter = true))
        editTextInstagramLink.filters = arrayOf(editTextEmailAddress.applyFilter(applyEmojiFilter = true))
        editTextYelpLink.filters = arrayOf(editTextEmailAddress.applyFilter(applyEmojiFilter = true))
        editTextMetaKeyword.filters = arrayOf(editTextEmailAddress.applyFilter(applyEmojiFilter = true))
    }

    private fun clickListener() = with(binding) {
        layoutPhoneNumber.textViewCountryCode.setOnClickListener(this@AddBusinessSocialMediaDetailFragment)
        buttonNext.setOnClickListener(this@AddBusinessSocialMediaDetailFragment)
    }

    override fun onClick(v: View): Unit = with(binding) {
        when (v) {
            layoutPhoneNumber.textViewCountryCode -> {
                navigator.loadActivity(
                    IsolatedFullActivity::class.java,
                    CountryCodeFragment::class.java
                ).onResultActivity(countryStartForResult).start()
            }
            buttonNext -> {
                if (validation()) {
                    (parentFragment as CreateBusinessProfileMainFragment).navigateToNextStep(
                        getData(), StepsCount.STEP_FOUR
                    )
                }
            }
        }
    }

    private fun getData(): BusinessProfileDetailMain = with(binding) {
        val data = BusinessProfileDetailMain()
        data.businessProfileDetail = previousScreenData?.businessProfileDetail
        data.businessMediaDetail = previousScreenData?.businessMediaDetail
        data.apply {
            businessSocialMediaDetail?.apply {
                emailAddress = editTextEmailAddress.trimmedText
                countryCode = layoutPhoneNumber.textViewCountryCode.trimmedText
                phoneNumber = layoutPhoneNumber.editTextPhoneNumber.trimmedText
                facebookLink = editTextFacebookLink.trimmedText
                twitterLink = editTextTwitterLink.trimmedText
                googleProfile = editTextGoogleProfile.trimmedText
                instagramLink = editTextInstagramLink.trimmedText
                yelpLink = editTextYelpLink.trimmedText
                metaKeyword = editTextMetaKeyword.trimmedText
            }
        }
    }

    private fun validation(): Boolean = with(binding) {
        return try {
            with(validator) {
                submit(editTextEmailAddress).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_email))
                    .checkValidEmail().errorMessage(getString(R.string.validation_valid_email))
                    .check()

                submit(layoutPhoneNumber.editTextPhoneNumber).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_phone_number))
                    .checkMinDigits(getInteger(R.integer.int_phone_number_min_length))
                    .errorMessage(getString(R.string.validation_min_digits_phone_number))
                    .matchPatter(RegexConstant.CHECK_PHONE_NUMBER)
                    .errorMessage(getString(R.string.validation_enter_valid_phone_number))
                    .check()

                if (editTextFacebookLink.trimmedText.isNotEmpty()) {
                    submit(editTextFacebookLink).matchPatter(RegexConstant.COMMON_URL)
                        //.matchPatter(RegexConstant.FACEBOOK_LINK_REGEX) //eg: valid facebook url "https://www.facebook.com/example"
                        .errorMessage(getString(R.string.validation_enter_valid_facebook_link))
                        .check()
                }

                if (editTextTwitterLink.trimmedText.isNotEmpty()) {
                    submit(editTextTwitterLink).matchPatter(RegexConstant.COMMON_URL)
                        //.matchPatter(RegexConstant.TWITTER_LINK_REGEX) //eg: valid twitter url "https://www.twitter.com/example_user"
                        .errorMessage(getString(R.string.validation_enter_valid_twitter_link))
                        .check()
                }

                if (editTextGoogleProfile.trimmedText.isNotEmpty()) {
                    submit(editTextGoogleProfile).matchPatter(RegexConstant.COMMON_URL)
                        //.matchPatter(RegexConstant.GOOGLE_PROFILE_REGEX) //eg: valid google url "https://plus.google.com/123456789"
                        .errorMessage(getString(R.string.validation_valid_google_profile))
                        .check()
                }

                if (editTextInstagramLink.trimmedText.isNotEmpty()) {
                    submit(editTextInstagramLink).matchPatter(RegexConstant.COMMON_URL)
                        //.matchPatter(RegexConstant.INSTAGRAM_LINK_REGEX) //eg: valid instagram url "https://www.instagram.com/example_user"
                        .errorMessage(getString(R.string.validation_enter_valid_instagram_link))
                        .check()
                }

                if (editTextYelpLink.trimmedText.isNotEmpty()) {
                    submit(editTextYelpLink).matchPatter(RegexConstant.COMMON_URL)
                        //.matchPatter(RegexConstant.YELP_LINK_REGEX) //eg: valid yelp url "https://www.yelp.com/biz/example-business"
                        .errorMessage(getString(R.string.validation_enter_valid_yelp_link))
                        .check()
                }

                /*submit(editTextFacebookLink).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_facebook_link))
                    .matchPatter(RegexConstant.COMMON_URL)
                    //.matchPatter(RegexConstant.FACEBOOK_LINK_REGEX) //eg: valid facebook url "https://www.facebook.com/example"
                    .errorMessage(getString(R.string.validation_enter_valid_facebook_link))
                    .check()*/

                /*submit(editTextTwitterLink).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_twitter_link))
                    .matchPatter(RegexConstant.COMMON_URL)
                    //.matchPatter(RegexConstant.TWITTER_LINK_REGEX) //eg: valid twitter url "https://www.twitter.com/example_user"
                    .errorMessage(getString(R.string.validation_enter_valid_twitter_link))
                    .check()*/

                /*submit(editTextGoogleProfile).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_google_profile))
                    .matchPatter(RegexConstant.COMMON_URL)
                    //.matchPatter(RegexConstant.GOOGLE_PROFILE_REGEX) //eg: valid google url "https://plus.google.com/123456789"
                    .errorMessage(getString(R.string.validation_valid_google_profile))
                    .check()*/

                /*submit(editTextInstagramLink).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_instagram_link))
                    .matchPatter(RegexConstant.COMMON_URL)
                    //.matchPatter(RegexConstant.INSTAGRAM_LINK_REGEX) //eg: valid instagram url "https://www.instagram.com/example_user"
                    .errorMessage(getString(R.string.validation_enter_valid_instagram_link))
                    .check()*/

                /*submit(editTextYelpLink).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_yelp_link))
                    .matchPatter(RegexConstant.COMMON_URL)
                    //.matchPatter(RegexConstant.YELP_LINK_REGEX) //eg: valid yelp url "https://www.yelp.com/biz/example-business"
                    .errorMessage(getString(R.string.validation_enter_valid_yelp_link))
                    .check()*/

               /* submit(editTextMetaKeyword).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_meta_keyword))
                    .check()*/

                true
            }
        } catch (e: ApplicationException) {
            showMessage(e)
            false
        }
    }


    companion object {
        private const val DATA = "DATA"
        private const val SOURCE_SCREEN = "SOURCE_SCREEN"

        fun createBundle(data: BusinessProfileDetailMain) =
            bundleOf(DATA to data)

        fun createBundleSS(sourceString: String) =
            bundleOf(SOURCE_SCREEN to sourceString)
    }
}