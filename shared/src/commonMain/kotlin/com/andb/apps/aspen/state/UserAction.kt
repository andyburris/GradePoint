package com.andb.apps.aspen.state

import com.andb.apps.aspen.models.HomeTab
import com.andb.apps.aspen.models.Screen
import com.andb.apps.aspen.models.Subject

sealed class UserAction{
    object Back : UserAction()
    class OpenScreen(val screen: Screen) : UserAction()
    class SwitchTerm(val term: Int) : UserAction()
    class SwitchTab(val tab: HomeTab) : UserAction()
    class Login(val username: String, val password: String) : UserAction()
    object Logout : UserAction()
    class EditConfig(val config: Subject.Config) : UserAction()
}
