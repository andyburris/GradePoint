
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.h1
import styled.styledInput

class LoginPage : RComponent<LoginProps, LoginState>() {
    override fun RBuilder.render() {
        h1 {
            +"GradePoint"
        }
        styledInput(InputType.text){
            key = "usernameInput"
            attrs {
                placeholder = "Username"
                onChangeFunction = {
                    val newUsername = (it.target as HTMLInputElement).value
                    console.log("newUsername = $newUsername, state.username = ${state.username}")
                    setState {
                        username = newUsername
                    }
                }
            }
        }
        styledInput(InputType.text){
            key = "passwordInput"
            attrs {
                placeholder = "Password"
                onChangeFunction = {
                    val newPassword = (it.target as HTMLInputElement).value
                    setState {
                        password = newPassword
                    }
                }
            }
        }
        styledInput(InputType.button) {
            attrs {
                value = "Login"
                onClickFunction = {
                    console.log("username = ${state.username}, password = ${state.password}")
                    props.onLogin(state.username, state.password)
                }
            }
        }
    }
}

external interface LoginProps : RProps{
    var onLogin: (username: String, password: String) -> Unit
}
external interface LoginState : RState {
    var username: String
    var password: String
}