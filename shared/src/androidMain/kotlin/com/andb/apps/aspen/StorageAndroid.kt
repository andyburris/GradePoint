package com.andb.apps.aspen

import com.netguru.kissme.Kissme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

open class StorageAndroid(val kissme: Kissme) : Storage{
    override val loggedIn: Boolean
        get() = kissme.contains("username") && kissme.contains("password")
    override var username: String
        get() = kissme.getString("username", "")!!
        set(value) {
            kissme.putString("username", value)
        }
    override var password: String
        get() = kissme.getString("password", "")!!
        set(value) {
            kissme.putString("password", value)
        }

    override fun clearLogin() {
        kissme.remove("username")
        kissme.remove("password")
    }

    override var showHidden: Boolean
        get() = kissme.getBoolean("showHidden", true)
        set(value) {
            kissme.putBoolean("showHidden", value)
            showHiddenFlow.value = value
        }

    val showHiddenFlow = MutableStateFlow(showHidden)
}