package com.mobile.test.utils

import android.os.Bundle
import androidx.navigation.NavDirections

sealed class NavigationCommand {
    data class To(val directions: NavDirections): NavigationCommand()
    object Back: NavigationCommand()
    data class BackTo(val destinationId: Int, val inclusive: Boolean = false): NavigationCommand()
    data class ToRoot(val inclusive: Boolean = false): NavigationCommand()
    data class StartActivity<T>(val activityName: Class<T>, val bundle: Bundle?, val isFinishedCurrentActivity: Boolean = true): NavigationCommand()
    data class ShowLoadingIndicator(val isShowing: Boolean): NavigationCommand()
    // TODO: handle other navigation command such as navigate, pop back stack, pop to root, ...
}