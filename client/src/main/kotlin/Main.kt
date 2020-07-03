
import com.andb.apps.aspen.initKoin
import com.andb.apps.aspen.state.Action
import com.ccfraser.muirwik.components.styles.ThemeOptions
import kotlinx.css.*
import react.RProps
import react.dom.render
import kotlin.browser.document

fun main() {
    console.log("Hello, Kotlin/JS!")
    initKoin()
    //injectGlobal(styles.toString())
    render(document.getElementById("root")) {
        //mThemeProvider(theme = createMuiTheme(paletteOptions)){
            app()
        //}
    }
}

external interface ActionHandlerProps : RProps {
    var handler: (Action) -> Unit
}

private val styles = CSSBuilder().apply {
    body {
        margin(0.px)
        padding(0.px)
    }
    p {
        margin(top = 0.px, bottom = 0.px)
    }
}

private val paletteOptions: ThemeOptions = js("""
({palette: {
    primary: {
        main: '#388E3C',
    },
    secondary: {
        main: '#388E3C',
    }
}
})
""")