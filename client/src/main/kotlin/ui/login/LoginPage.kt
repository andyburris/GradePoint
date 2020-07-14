package ui.login

import kotlinx.css.*
import kotlinx.css.properties.BoxShadow
import kotlinx.css.properties.BoxShadows
import react.*
import styled.css
import styled.styledDiv
import styled.styledImg
import ui.Text
import ui.TextVarient
import ui.common.OutlinedButton

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
                    margin(left = 64.px, top = 64.px)
                }

                ProductHeader()
            }

            styledDiv {
                css {
                    flexGrow = 1.0
                    display = Display.flex
                    flexDirection = FlexDirection.column
                    justifyContent = JustifyContent.center
                    alignItems = Align.center
                }

                styledDiv {
                    css {
                        width = 100.vw
                        maxWidth = 500.px
                        margin(horizontal = 64.px)
                    }

                    Text("Log in with Aspen", TextVarient.H3)

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

private fun RBuilder.ProductHeader(){
    styledDiv {

        css {
            display = Display.flex
            flexDirection = FlexDirection.row
            alignItems = Align.center
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

        Text("GradePoint", TextVarient.H3)
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