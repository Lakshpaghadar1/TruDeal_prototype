package com.trudeals.utils.toolbar

sealed class MenuItemTag : Tag {
    object Request : MenuItemTag()
    object Chat : MenuItemTag()
    object Notification : MenuItemTag()
    object Clear : MenuItemTag()
    object Call : MenuItemTag()
    object VideoCall : MenuItemTag()
    object More : MenuItemTag()
    object AddNew : MenuItemTag()
}
