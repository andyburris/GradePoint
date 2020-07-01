package com.andb.apps.aspen.state

import com.andb.apps.aspen.models.HomeTab
import com.andb.apps.aspen.models.Screen
import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.response.RecentAssignmentResponse

sealed class Action

sealed class UserAction : Action(){
    object Back : UserAction()
    class OpenScreen(val screen: Screen) : UserAction()
    class SwitchTerm(val term: Int) : UserAction()
    class SwitchTab(val tab: HomeTab) : UserAction()
    class Login(val username: String, val password: String) : UserAction()
    object Logout : UserAction()
    class EditConfig(val config: Subject.Config) : UserAction()
}

sealed class DataAction : Action(){
    class TermLoaded(val subjects: List<Subject>) : DataAction()
    class RecentsLoaded(val recents: List<RecentAssignmentResponse>) : DataAction()
}