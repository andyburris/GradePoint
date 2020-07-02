package com.andb.apps.aspen

class StorageJS : Storage {
    override val loggedIn: Boolean
        get() = false
    override var username: String
        get() = ""
        set(value) {}
    override var password: String
        get() = ""
        set(value) {}

    override fun clear() {}
}