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
import ui.dp

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
                    margin(left = 64.dp, top = 64.dp)
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
                        maxWidth = 500.dp
                        margin(horizontal = 64.dp)
                    }

                    Text("Log in with Aspen", TextVarient.H3)

                    LoginTextBox(
                        placeholder = "Username",
                        onChange = { setState { username = it } }
                    ) {
                        marginTop = 64.dp
                    }

                    LoginTextBox(
                        onChange = { setState { password = it } },
                        placeholder = "Password",
                        password = true
                    ) {
                        marginTop = 32.dp
                    }

                    OutlinedButton(
                        text = "LOG IN",
                        onClick = {
                            console.log("username = ${state.username}, password = ${state.password}")
                            props.onLoginClicked(state.username, state.password)
                        }
                    ){
                        margin(top = 64.dp)
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
                    this += BoxShadow(false, 0.dp, 3.dp, 1.5.dp, 0.dp, Color("#00000022"))
                }
                width = 64.dp
                height = 64.dp
                margin(right = 32.dp)
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