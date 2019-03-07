/**
 * Copyright © 2019 Microsoft Corporation. All rights reserved.
 */

package com.microsoft.officeuifabric.util

import android.content.Context
import android.graphics.Point
import android.graphics.Rect
import android.support.v4.view.ViewCompat
import android.view.Surface
import android.view.View
import android.view.WindowManager

private const val TABLET_SIZE_THRESHOLD = 600
private const val NAV_BAR_HEIGHT_STRING = "navigation_bar_height"
private const val STATUS_BAR_HEIGHT_STRING = "status_bar_height"
private const val DIMEN_STRING = "dimen"
private const val BOOL_STRING = "bool"
private const val ANDROID_STRING = "android"
private const val CONFIG_SHOW_NAVIGATION_BAR = "config_showNavigationBar"

val Context.isTablet: Boolean
    get() {
        return resources.configuration.smallestScreenWidthDp >= TABLET_SIZE_THRESHOLD
    }

val Context.statusBarHeight: Int
    get() {
        return getSystemDimension(STATUS_BAR_HEIGHT_STRING)
    }

val Context.navigationBarHeight: Int
    get() {
        return getSystemDimension(NAV_BAR_HEIGHT_STRING)
    }

val Context.softNavBarOffsetX: Int
    get() {
        val rotation = (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.rotation
        return if (rotation == Surface.ROTATION_270 && hasSoftNavigationBar && !isTablet)
            navigationBarHeight
        else
            0
    }

fun Context.getSystemDimension(dimensionId: String): Int {
    val resourceId = resources.getIdentifier(dimensionId, DIMEN_STRING, ANDROID_STRING)
    return if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else 0
}

// This check returns false in emulators
val Context.hasSoftNavigationBar: Boolean
    get() {
        val id = resources.getIdentifier(CONFIG_SHOW_NAVIGATION_BAR, BOOL_STRING, ANDROID_STRING)
        return id > 0 && resources.getBoolean(id)
    }

val Context.displaySize: Point
    get(){
        val displaySize = Point()
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getSize(displaySize)
        return displaySize
    }

val View.layoutIsRtl: Boolean
    get() {
        return ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL
    }

val View.isVisibleOnScreen: Boolean
    get() {
        val viewBounds = Rect()
        getHitRect(viewBounds)
        return getLocalVisibleRect(viewBounds)
    }