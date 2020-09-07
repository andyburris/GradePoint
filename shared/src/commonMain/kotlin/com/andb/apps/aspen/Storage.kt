package com.andb.apps.aspen

interface Storage {
    val loggedIn: Boolean
    var username: String
    var password: String
    fun clearLogin()

    var showHidden: Boolean
}