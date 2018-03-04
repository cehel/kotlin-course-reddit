@file:JvmName("DateUtils")

package ch.zuehlke.reddit.common

import java.util.*

/**
 * Created by chsc on 04.03.18.
 */


fun Long.friendlyTime(): String {
    val sec = Math.floor((Date().time / 1000 - this).toDouble())
    var interval = Math.floor(sec / 31536000)

    if (interval >= 1) {
        return if (interval == 1.0)
            interval.toString() + " year ago"
        else
            interval.toString() + " years ago"
    }
    interval = Math.floor(sec / 2592000)
    if (interval >= 1) {
        return if (interval == 1.0)
            interval.toString() + " month ago"
        else
            interval.toString() + " months ago"
    }
    interval = Math.floor(sec / 86400)
    if (interval >= 1) {
        return if (interval == 1.0)
            interval.toString() + " day ago"
        else
            interval.toString() + " days ago"
    }
    interval = Math.floor(sec / 3600)
    if (interval >= 1) {
        return if (interval == 1.0)
            interval.toString() + " hour ago"
        else
            interval.toString() + " hours ago"
    }
    interval = Math.floor(sec / 60)
    if (interval >= 1) {
        return if (interval == 1.0)
            interval.toString() + " minute ago"
        else
            interval.toString() + " minutes ago"
    }
    return Math.floor(sec).toString() + " seconds ago"
}