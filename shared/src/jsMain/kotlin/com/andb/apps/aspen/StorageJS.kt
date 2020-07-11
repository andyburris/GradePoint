package com.andb.apps.aspen

import org.w3c.dom.get
import org.w3c.dom.set
import kotlin.browser.sessionStorage

class StorageJS : Storage {
    override val loggedIn: Boolean
        get() = sessionStorage["username"] != null && sessionStorage["password"] != null
    override var username: String
        get() = sessionStorage["username"] ?: ""
        set(value) {
            sessionStorage["username"] = value
        }
    override var password: String
        get() = sessionStorage["password"] ?: ""
        set(value) {
            sessionStorage["password"] = value
        }

    override fun clear() {
        sessionStorage.clear()
    }
}