
import com.andb.apps.aspen.models.Screen
import com.andb.apps.aspen.state.Screens
import com.andb.apps.aspen.state.UserAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import react.*
import react.dom.h1
import ui.home.SubjectListPage
import ui.subject.SubjectPage

class App : RComponent<RProps, AppState>() {

    override fun AppState.init(){
        screens = Screens().apply {
            this += UserAction.Logout
        }
        currentScreen = Screen.Login
        CoroutineScope(Dispatchers.Default).launch {
            screens.currentScreen.collect {
                setState {
                    currentScreen = it
                }
            }
        }
    }

    override fun RBuilder.render() {
        when(state.currentScreen){
            is Screen.Login -> loginPage { username, password ->
                setState {
                    state.screens += UserAction.Login(username, password)
                }
            }
            is Screen.Home -> SubjectListPage((state.currentScreen as Screen.Home).subjects, 4){
                state.screens += it
            }
            is Screen.Subject -> SubjectPage((state.currentScreen as Screen.Subject).subject, (state.currentScreen as Screen.Subject).term)
            else -> {
                h1 {
                    +"Not implemented yet"
                }
            }
        }
    }
}


external interface TermProps : ActionHandlerProps {
    var term: Int
}

external interface AppState : RState {
    var screens: Screens
    var currentScreen: Screen
}

fun RBuilder.app(){
    child(App::class){}
}