
import com.andb.apps.aspen.models.Screen
import com.andb.apps.aspen.state.Screens
import com.andb.apps.aspen.state.UserAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import react.RBuilder
import react.RComponent
import react.RState
import react.dom.h1
import react.setState

class App : RComponent<AppPageProps, AppState>() {

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
            else -> {
                h1 {
                    +"Not implemented yet"
                }
            }
        }
    }
}

external interface AppPageProps : ActionHandlerProps {
    var screen: Screen
}

external interface AppState : RState {
    var screens: Screens
    var currentScreen: Screen
}

fun RBuilder.app(){
    child(App::class){}
}