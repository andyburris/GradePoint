
import com.ccfraser.muirwik.components.*
import com.ccfraser.muirwik.components.button.MButtonSize
import com.ccfraser.muirwik.components.button.MButtonVariant
import com.ccfraser.muirwik.components.button.mButton
import com.ccfraser.muirwik.components.form.MFormControlVariant
import kotlinx.css.*
import kotlinx.css.properties.BoxShadow
import kotlinx.css.properties.BoxShadows
import org.w3c.dom.events.Event
import react.*
import styled.StyledHandler
import styled.css
import styled.styledDiv
import styled.styledImg

class LoginPage : RComponent<LoginProps, LoginState>() {
    private val onChangeUsername = { event: Event ->
        val value = event.targetInputValue
        setState { username = value }
    }

    private val onChangePassword = { event: Event ->
        val value = event.targetInputValue
        setState { password = value }
    }

    private val onLogin = { _: Event ->
        console.log("username = ${state.username}, password = ${state.password}")
        props.onLoginClicked(state.username, state.password)
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                display = Display.flex
                flexDirection = FlexDirection.column
                justifyContent = JustifyContent.center
                alignItems = Align.center
                height = 100.vh
            }

            styledImg(src = "assets/GradePointIcon.svg"){
                css {
                    borderRadius = 50.pct
                    boxShadow = BoxShadows().apply {
                        this += BoxShadow(false, 0.px, 3.px, 1.5.px, 0.px, Color("#00000022"))
                    }
                    width = 144.px
                    height = 144.px
                }
            }
            mTypography ("GradePoint", variant = MTypographyVariant.h4){
                css {
                    margin(top = 32.px, bottom = 32.px)
                }
            }
            mTextField(
                label = "Username",
                variant = MFormControlVariant.outlined,
                onChange = onChangeUsername,
                handler = textFieldStyle
            )
            mTextField(
                label = "Password",
                variant = MFormControlVariant.outlined,
                onChange = onChangePassword,
                handler = textFieldStyle
            )
            styledDiv {
                css {
                    margin(top = 32.px)
                }
                mButton("Login", onClick = onLogin, disabled = (state.username.isNullOrEmpty() || state.password.isNullOrEmpty()), color = MColor.primary, variant = MButtonVariant.contained, size = MButtonSize.large){
                    css {
                        margin(top = 32.px)
                        borderRadius = 32.px
                    }
                }
            }
        }
    }
}

external interface LoginProps : RProps{
    var onLoginClicked: (username: String, password: String) -> Unit
}
external interface LoginState : RState {
    var username: String
    var password: String
}
fun RBuilder.loginPage(onLogin: (username: String, password: String) -> Unit){
    child(LoginPage::class){
        attrs {
            onLoginClicked = onLogin
        }
    }
}

val textFieldStyle: StyledHandler<MTextFieldProps> = {
    css {
        width = 100.vw
        maxWidth = 500.px
        margin(horizontal = 64.px)
    }
}
