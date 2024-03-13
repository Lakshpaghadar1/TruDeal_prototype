package com.trudeals.utils.toolbar

import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes

data class MenuItem(
    var title: String? = null,
    @DrawableRes var icon: Int? = null,
    /** Define all your tags in [MenuItemTag] */
    val tag: Tag,
    var menuItemType: MenuItemType = MenuItemType.ICON,
    var showAsAction: ShowAsAction = ShowAsAction.IF_ROOM,
    @ColorRes var iconColor: Int? = null,
    @ColorRes var titleColor: Int? = null,
    @FontRes var titleTypeface: Int? = null,
    @DimenRes var titleFontSize: Int? = null,
    var isVisible: Boolean = false,
    var showBadge: Boolean = false,
    var badgeCount: Int = 0
)

sealed class DefaultMenuItemTag : Tag {
    object MoreVert : DefaultMenuItemTag()
}

enum class MenuItemType {
    ICON,
    TITLE,
    BOTH
}

enum class ShowAsAction {
    NEVER,
    ALWAYS,
    IF_ROOM
}