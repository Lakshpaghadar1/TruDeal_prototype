package com.trudeals.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class StatusType : Parcelable {
    //NEED DISCUSS POSSIBILITIES WITH SIR/BACKEND
    FEATURED, FOR_SALE, FOR_RENT, DISCOUNT, FEATURED_AND_FORSALE, NONE, FOR_VACATION_RENT
}

@Parcelize
enum class OnClick : Parcelable {
    HEART,
    CONTACT,
    ADDRESS,
    WEBSITE,
    EMAIL_ID,
    DELETE,
    BLOCK,
    CHANGE_PASSWORD,
    MY_FAV_REAL_ESTATE,
    MY_FAV_DEALS, NEWS,
    DELETE_ACCOUNT,
    RATE_APP,
    SHARE_APP,
    HELP_AND_FAQ,
    ADVERTISE_WITH_US,
    TERMS_AND_PRIVACY_POLICY,
    ABOUT_US,
    CONTACT_US,
    REJECT,
    ACCEPT,
    CHAT,
    MY_LISTING,
    ADD_NEW,
    EDIT,
    RE_UPLOAD,
    DRAG_HELPER,
    CANCEL,
    MY_BUSINESS_PROFILE,
    ADD_SLOT,
    START_TIME,
    END_TIME,
    ACTIVE,
    PLAY,
    PAUSE,
    PHONE,
    SMS,
    EMAIL,
    MODIFY_REQUEST,
    SCHEDULE,
    EDIT_SCHEDULE
}

enum class ButtonStatus {
    REJECTED,
    ACCEPTED
}

enum class MoreOption {
    SEE_PROFILE, BLOCK, DELETE_CHAT
}

@Parcelize
enum class TabType : Parcelable {
    ALL,
    HOME_FOR_SALE,
    RENTALS,
    VACATION_RENTALS,
    AUTOMOTIVE,
    SALON,
    BEAUTY_AND_SPA,
    FOOD_AND_DRINK,
    HEALTH_AND_FITNESS,
    HOME_AND_GARDEN,
    MEDICAL_SERVICE,
    SERVICES,
    THINGS_TO_DO,
    TRAVEL,
    NONE
}

@Parcelize
enum class MainCategoryType : Parcelable {
    REAL_ESTATE,
    LOCAL_DEALS,
    BOTH
}

@Parcelize
enum class SubCategoryType : Parcelable {
    NONE,
    REAL_ESTATE,
    DEALS,
    DEALS_AND_REAL_ESTATE
}

@Parcelize
enum class RequestCategoryType : Parcelable {
    REQUESTED,
    ACCEPTED,
    COMPLETED
}

@Parcelize
enum class UserType : Parcelable {
    CUSTOMER_USER,
    REAL_ESTATE_USER,
    BUSINESS_USER
}

@Parcelize
enum class RealEstateOrBusinessChipType : Parcelable {
    MY_LISTING,
    SCHEDULE_AN_OPEN_HOUSE,
    BOOKING_REQUEST,
    BUSINESS_PROFILE,
    ADD_OR_EDIT_DEALS,
    CHAT
}

@Parcelize
enum class PropertyDetailsTag : Parcelable {
    FEATURES,
    PROPERTY_AREA_AND_SPECIFICATIONS,
    PROPERTY_IMAGES,
    PROPERTY_VIDEO,
    NONE
}

@Parcelize
enum class PropertyListTag : Parcelable {
    HOME_FOR_SALE,
    HOME_FOR_RENT,
    VACATION_RENTAL
}

@Parcelize
enum class StepsCount : Parcelable {
    STEP_ONE,
    STEP_TWO,
    STEP_THREE,
    STEP_FOUR
}

enum class ClickTypeTag {
    SHORT_CLICK, LONG_CLICK
}

enum class TypeOfDeals {
    LINK_DEAL_TO_MY_SHOPPING_CART, PAY_AT_THE_POINT_OF_SALE
}

enum class TimeType {
    START_TIME, END_TIME
}

enum class AmPm {
    AM, PM
}