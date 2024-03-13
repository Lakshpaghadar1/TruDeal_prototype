package com.trudeals.utils.constants

object RegexConstant {
    const val USER_NAME = "/^[a-zA-Z0-9._]+\$/"
    const val PROMO_CODE = "^[A-Z0-9]\$"
    const val CHECK_UPPERCASE_LOWERCASE = "^(?=.*[a-z])(?=.*[A-Z]).{4,}\$"
    const val CHECK_NUMBER_LETTERS = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{4,}\$"
    const val CHECK_ONE_SPECIAL_CHAR = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%^&+=]).{4,}\$"
    const val BLOCK_CHARSET = "~#^|$%&*!=<>\""   //Special characters to block
    const val BLOCK_CHARSET_WITH_AT_SIGN = "@~#^|\$%&*!=<>'\""   //@ characters to block
    const val BLOCK_NUMBER_SET = "0123456789"   //Numbers to block
    const val ZIP_CODE_REGEX = "^[0-9]{5}(?:-[0-9]{4})?\$"   //Numbers to block
    const val CHECK_PHONE_NUMBER =
        "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{2,5})(?: *x(\\d+))?\\s*\$"
    const val FACEBOOK_LINK_REGEX = "^(https?:\\/\\/)?(www\\.)?facebook\\.com\\/[a-zA-Z0-9.]+\\/?\$"
    const val TWITTER_LINK_REGEX = "^(https?:\\/\\/)?(www\\.)?twitter\\.com\\/[a-zA-Z0-9_]{1,15}\\/?\$"
    const val GOOGLE_PROFILE_REGEX = "^(https?:\\/\\/)?(www\\.)?plus\\.google\\.com\\/([0-9]+|[a-zA-Z0-9._]+\\/?)\$"
    const val INSTAGRAM_LINK_REGEX = "^(https?:\\/\\/)?(www\\.)?instagram\\.com\\/[a-zA-Z0-9_.]+\\/?\$"
    const val YELP_LINK_REGEX = "^(https?:\\/\\/)?(www\\.)?yelp\\.com\\/biz\\/[a-zA-Z0-9-]+\\/?\$"
    const val SHOPPING_CART_URL_REGEX = "^https?://(?:www\\.)?example\\.com/shopping-cart/[A-Za-z0-9_-]+/?$"
    const val COMMON_URL = "^(https?://)?(www\\.)?([-a-z0-9]{1,63}\\.)*?[a-z0-9][-a-z0-9]{0,61}[a-z0-9]\\.[a-z]{2,6}(/[-\\w@\\+\\.~#\\?&/=%]*)?$"
    const val PERCENTAGE_REGEX = "^\\d{1,2}$|^100$"
}