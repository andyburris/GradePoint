package ui.home
import TermProps
import com.andb.apps.aspen.models.Subject
import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.RBuilder
import react.RComponent
import react.RState
import react.dom.div
import styled.css
import styled.styledDiv
import ui.Text
import ui.TextVarient
import ui.common.MaterialIcon
import ui.dp
import util.iconName

class SubjectItem : RComponent<SubjectItemProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            attrs {
                onClickFunction = { props.onClick.invoke() }
            }

            css {
                display = Display.flex
                flexDirection = FlexDirection.row
                justifyContent = JustifyContent.spaceBetween
                alignItems = Align.center
                cursor = Cursor.pointer
            }

            styledDiv {
                css {
                    display = Display.flex
                    flexDirection = FlexDirection.row
                    alignItems = Align.center
                }

                SubjectIcon(props.subject.config.color, props.subject.config.icon.iconName()){

                }

                div {
                    Text(props.subject.name, TextVarient.Bold)
                    Text(props.subject.teacher, TextVarient.Secondary)
                }
            }

            Text(props.subject.termGrades(props.term).grade.toString(), TextVarient.Bold)
        }
    }
}

private fun RBuilder.SubjectIcon(color: Int, iconName: String, onClick: (Event) -> Unit){
    styledDiv {
        css {
            borderRadius = 50.pct
            backgroundColor = Color("#" + color.toUInt().toString(16).substring(2))
            width = 64.dp
            height = 64.dp
            margin(right = 24.dp)
            display = Display.flex
            justifyContent = JustifyContent.center
            alignItems = Align.center
        }

        attrs {
            this.onClickFunction = onClick
        }

        MaterialIcon(iconName, size = 32.dp)
    }
}

interface SubjectItemProps : TermProps {
    var subject: Subject
    var onClick: () -> Unit
}

fun RBuilder.SubjectItem(subject: Subject, term: Int, onClick: () -> Unit){
    child(SubjectItem::class){
        attrs {
            this.subject = subject
            this.term = term
            this.onClick = onClick
        }
    }
}