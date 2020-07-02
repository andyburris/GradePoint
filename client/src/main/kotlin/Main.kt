
import com.andb.apps.aspen.initKoin
import com.andb.apps.aspen.state.Action
import react.RProps
import react.dom.render
import kotlin.browser.document

fun main() {
    console.log("Hello, Kotlin/JS!")
    initKoin()
    render(document.getElementById("root")) {
        child(App::class){}
    }
}

external interface ActionHandlerProps : RProps {
    var handler: (Action) -> Unit
}