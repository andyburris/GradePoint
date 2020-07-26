package com.andb.apps.aspen

import org.w3c.dom.get
import org.w3c.dom.set
import kotlin.browser.sessionStorage

private object SessionStorage {
    var username = ""
    var password = ""
}

class StorageJS : Storage {
    override val loggedIn: Boolean
        get() = SessionStorage.username.isNotEmpty() && SessionStorage.password.isNotEmpty()
    override var username: String
        get() = SessionStorage.username ?: ""
        set(value) {
            SessionStorage.username = value
        }
    override var password: String
        get() = SessionStorage.password ?: ""
        set(value) {
            SessionStorage.password = value
        }

    override fun clear() {
        sessionStorage.clear()
    }
}