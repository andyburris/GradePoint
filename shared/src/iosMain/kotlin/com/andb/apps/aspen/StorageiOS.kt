package com.andb.apps.aspen

import com.netguru.kissme.Kissme

class StorageiOS(val kissme: Kissme) : Storage{
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

    override fun clear() {
        kissme.remove("username")
        kissme.remove("password")
    }
}