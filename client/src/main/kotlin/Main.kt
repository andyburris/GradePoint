
import com.andb.apps.aspen.initKoin
import com.andb.apps.aspen.state.Action
import kotlinx.css.*
import react.RProps
import react.dom.render
import styled.injectGlobal
import kotlin.browser.document

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
        margin(0.px)
        padding(0.px)
    }
    p {
        margin(top = 0.px, bottom = 0.px)
    }
    "*"{
        fontFamily = "Montserrat, Helvetica, sans-serif"
    }
}