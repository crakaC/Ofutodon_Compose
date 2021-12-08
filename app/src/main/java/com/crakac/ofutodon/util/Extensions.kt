package com.crakac.ofutodon.util

import com.crakac.ofutodon.R
import com.crakac.ofutodon.mastodon.entity.Status


fun Status.Visibility.iconResource(): Int {
    return when (this) {
        Status.Visibility.Public -> R.drawable.ic_public
        Status.Visibility.UnListed -> R.drawable.ic_lock_open
        Status.Visibility.Private -> R.drawable.ic_lock
        Status.Visibility.Direct -> R.drawable.ic_direct
    }
}

fun Status.Visibility.stringResource(): Int {
    return when (this) {
        Status.Visibility.Public -> R.string.visibility_public
        Status.Visibility.UnListed -> R.string.visibility_unlisted
        Status.Visibility.Private -> R.string.visibility_private
        Status.Visibility.Direct -> R.string.visibility_direct
    }
}
