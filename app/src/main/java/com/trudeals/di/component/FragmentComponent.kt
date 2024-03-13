package com.trudeals.di.component

import com.trudeals.di.PerFragment
import com.trudeals.di.module.FragmentModule
import com.trudeals.ui.auth.signin.SignInFragment
import com.trudeals.ui.auth.forgotpassword.ForgotPasswordFragment
import com.trudeals.ui.auth.otpverification.VerificationFragment
import com.trudeals.ui.auth.setnewpassword.SetNewPasswordFragment
import com.trudeals.ui.auth.signup.SignUpFragment
import com.trudeals.ui.auth.usertype.UserTypeFragment
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.webview.WebViewFragment
import com.trudeals.ui.home.businessuser.fragment.HomeFragmentBU
import com.trudeals.ui.home.businessuser.fragment.MyFavOrViewAllListFragmentBU
import com.trudeals.ui.home.customeruser.fragment.HomeRealEstateAndLocalDealsTabFragments
import com.trudeals.ui.home.customeruser.fragment.HomeFragmentCU
import com.trudeals.ui.home.realestateuser.fragment.HomeFragmentREU
import com.trudeals.ui.home.realestateuser.fragment.HomeRealEstateTabFragments
import com.trudeals.ui.home.realestateuser.fragment.SubscriptionFragment
import com.trudeals.ui.isolated.businessuser.fragment.*
import com.trudeals.ui.isolated.customeruser.fragment.*
import com.trudeals.ui.isolated.realestateuser.fragment.*
import com.trudeals.ui.tutorial.fragment.TutorialOneFragment
import com.trudeals.ui.tutorial.fragment.TutorialThreeFragment
import com.trudeals.ui.tutorial.fragment.TutorialTwoFragment
import com.trudeals.utils.countrycodepicker.CountryCodeFragment
import dagger.Subcomponent

/**
 * Created by hlink21 on 31/5/16.
 */

@PerFragment
@Subcomponent(modules = [(FragmentModule::class)])
interface FragmentComponent {
    fun baseFragment(): BaseFragment<*>
    fun inject(webViewFragment: WebViewFragment)
    fun inject(loginFragment: SignInFragment)
    fun inject(countryCodeFragment: CountryCodeFragment)
    fun inject(homeFragment: HomeFragmentCU)
    fun inject(tutorialOneFragment: TutorialOneFragment)
    fun inject(tutorialThreeFragment: TutorialThreeFragment)
    fun inject(tutorialTwoFragment: TutorialTwoFragment)
    fun inject(selectUserFragment: UserTypeFragment)
    fun inject(signUpFragment: SignUpFragment)
    fun inject(forgotPasswordFragment: ForgotPasswordFragment)
    fun inject(otpVerificationFragment: VerificationFragment)
    fun inject(setNewPasswordFragment: SetNewPasswordFragment)
    fun inject(homeRealEstateAllFragment: HomeRealEstateAndLocalDealsTabFragments)
    fun inject(realEstateDetailFragment: RealEstateDetailFragmentCU)
    fun inject(searchFragment: SearchFragment)
    fun inject(filterListFragment: FilterListFragment)
    fun inject(chatFragment: ChatListFragment)
    fun inject(notificationFragment: NotificationFragment)
    fun inject(customerLocalDealDetailFragment: LocalDealDetailFragment)
    fun inject(allCategoryChpFragment: AllCategoryChpFragment)
    fun inject(localDealsSelectedCategoryChipListFragment: LocalDealsSelectedCategoryChipListFragment)
    fun inject(chatFragment: ChatFragment)
    fun inject(customerUserProfileFragment: UserProfileFragment)
    fun inject(customerEditProfileFragment: EditProfileFragment)
    fun inject(myFavRealEstateFragment: MyFavListsFragmentCU)
    fun inject(newsFragment: NewsFragment)
    fun inject(newsDetailsFragment: NewsDetailsFragment)
    fun inject(requestFragment: RequestFragment)
    fun inject(requestDetailFragment: RequestDetailFragment)
    fun inject(contactUsFragment: ContactUsFragment)
    fun inject(homeFragmentREU: HomeFragmentREU)
    fun inject(homeRealEstateTabFragments: HomeRealEstateTabFragments)
    fun inject(realEstateDetailFragmentREU: RealEstateDetailFragmentREU)
    fun inject(viewImageVideoFragment: ViewImageVideoFragment)
    fun inject(requestFragmentREU: RequestFragmentREU)
    fun inject(mapFragment: MapFragment)
    fun inject(realEstatePropertyListFragment: RealEstatePropertyListFragment)
    fun inject(addOrEditPropertyDetailsFragment: AddOrEditPropertyDetailsFragment)
    fun inject(previewOrSavePropertyDetailFragment: PreviewOrSavePropertyDetailFragment)
    fun inject(addOrEditPropertyDetailsMainFragment: AddOrEditPropertyDetailsMainFragment)
    fun inject(selectRentDurationFragment: SelectRentDurationFragment)
    fun inject(bookingRequestFragment: BookingRequestFragment)
    fun inject(scheduleOpenHouseTimingFragment: ScheduleOpenHouseTimingFragment)
    fun inject(myFavListFragmentREU: MyFavListFragmentREU)
    fun inject(subscriptionFragment: SubscriptionFragment)
    fun inject(homeFragmentBU: HomeFragmentBU)
    fun inject(myFavListFragmentBU: MyFavOrViewAllListFragmentBU)
    fun inject(businessDealDetailFragment: BusinessDealDetailFragment)
    fun inject(createBusinessProfileMainFragment: CreateBusinessProfileMainFragment)
    fun inject(addBusinessProfileDetailsFragment: AddBusinessProfileDetailsFragment)
    fun inject(addBusinessMediaDetailFragment: AddBusinessMediaDetailFragment)
    fun inject(addBusinessSocialMediaDetailFragment: AddBusinessSocialMediaDetailFragment)
    fun inject(addBusinessAvailabilityDetailsFragment: AddBusinessAvailabilityDetailsFragment)
    fun inject(addOrEditDealFragment: AddOrEditDealFragment)
    fun inject(addOrEditDealDetailsFragment: AddOrEditDealDetailsFragment)
    fun inject(imageLibraryFragment: ImageLibraryMainFragment)
    fun inject(imageLibraryTabFragments: ImageLibraryTabFragments)
}
