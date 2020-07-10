import kotlinx.css.*
import kotlinx.css.properties.BoxShadow
import kotlinx.css.properties.BoxShadows
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.*
import react.dom.h5
import styled.*
import ui.common.OutlinedButton
import ui.login.LoginTextBox
import util.targetInputValue

class LoginPage : RComponent<LoginProps, LoginState>() {

    override fun RBuilder.render() {
        styledDiv {

            css {
                display = Display.flex
                flexDirection = FlexDirection.column
                height = 100.vh
            }

            styledDiv {
                css {
                    flexGrow = 0.0
                    display = Display.flex
                    flexDirection = FlexDirection.row
                    alignItems = Align.center
                    margin(left = 64.px, top = 64.px)
                }

                styledImg(src = "assets/GradePointIcon.svg") {
                    css {
                        borderRadius = 50.pct
                        boxShadow = BoxShadows().apply {
                            this += BoxShadow(false, 0.px, 3.px, 1.5.px, 0.px, Color("#00000022"))
                        }
                        width = 64.px
                        height = 64.px
                        margin(right = 32.px)
                    }
                }

                styledP {
                    css {
                        fontSize = 2.rem
                        fontWeight = FontWeight.w600
                        fontFamily = "Montserrat"
                    }
                    +"GradePoint"
                }
            }
            styledDiv {
                css {
                    flexGrow = 1.0
                    display = Display.flex
                    flexDirection = FlexDirection.column
                    justifyContent = JustifyContent.center
                    alignItems = Align.center
                    //height = 100.vh
                }

                styledDiv {

                    css {
                        width = 100.vw
                        maxWidth = 500.px
                        margin(horizontal = 64.px)
                    }

                    styledP {
                        css {
                            fontSize = 2.25.rem
                            fontWeight = FontWeight.w600
                        }
                        +"Log in with Aspen"
                    }

                    LoginTextBox(
                        placeholder = "Username",
                        onChange = { setState { username = it } }
                    ) {
                        marginTop = 64.px
                    }

                    LoginTextBox(
                        onChange = { setState { password = it } },
                        placeholder = "Password",
                        password = true
                    ) {
                        marginTop = 32.px
                    }

                    OutlinedButton(
                        text = "LOG IN",
                        onClick = {
                            console.log("username = ${state.username}, password = ${state.password}")
                            props.onLoginClicked(state.username, state.password)
                        }
                    ){
                        margin(top = 64.px)
                        width = 100.pct
                    }
                }
            }
        }
    }
}

external interface LoginProps : RProps {
    var onLoginClicked: (username: String, password: String) -> Unit
}

external interface LoginState : RState {
    var username: String
    var password: String
}

fun RBuilder.loginPage(onLogin: (username: String, password: String) -> Unit) {
    child(LoginPage::class) {
        attrs {
            onLoginClicked = onLogin
        }
    }
}