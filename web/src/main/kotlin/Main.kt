
import com.andb.apps.aspen.initKoin
import com.andb.apps.aspen.state.Action
import kotlinx.css.*
import react.RProps
import react.dom.render
import styled.injectGlobal
import ui.dp
import ui.header
import kotlinx.browser.document

fun main() {
    console.log("Hello, Kotlin/JS!")
    initKoin()
    injectGlobal(styles.toString())
    render(document.getElementById("root")) {
        app()
    }
}

typealias ActionHandler = (Action) -> Unit
external interface ActionHandlerProps : RProps {
    var handler: ActionHandler
}

private val styles = CSSBuilder().apply {
    body {
        margin(0.dp)
        padding(0.dp)
    }
    p {
        margin(top = 0.dp, bottom = 0.dp)
    }
    "*"{
        fontFamily = "Montserrat, Helvetica, sans-serif"
    }
    h1 {
        margin(top = 0.dp, bottom = 0.dp)
        header()
    }
}