package com.andb.apps.aspen.state

import com.andb.apps.aspen.models.HomeTab
import com.andb.apps.aspen.models.OfflineAt
import com.andb.apps.aspen.models.Screen
import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.response.RecentAssignmentResponse

sealed class Action

sealed class UserAction : Action(){
    object Back : UserAction()
    data class OpenScreen(val screen: Screen) : UserAction()
    data class SwitchTerm(val term: Int) : UserAction()
    data class SwitchTab(val tab: HomeTab) : UserAction()
    data class Login(val username: String, val password: String) : UserAction()
    object Logout : UserAction()
    data class EditConfig(val config: Subject.Config) : UserAction()
    object Reload : UserAction()
    data class HideSubject(val subject: Subject) : UserAction()
    data class UnhideSubject(val subject: Subject) : UserAction()
}

sealed class DataAction : Action(){
    data class TermLoaded(val term: Int, val subjects: List<Subject>) : DataAction()
    data class RecentsLoaded(val recents: List<RecentAssignmentResponse>) : DataAction()
    object LoginFailed : DataAction()
    data class Offline(val at: OfflineAt) : DataAction()
    data class AspenError(val message: String) : DataAction()
}