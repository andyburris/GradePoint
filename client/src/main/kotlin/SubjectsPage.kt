
import com.andb.apps.aspen.models.Subject
import kotlinx.css.*
import react.RBuilder
import react.RComponent
import react.RState
import react.dom.h1
import styled.css
import styled.styledButton
import styled.styledDiv

class SubjectsPage : RComponent<SubjectsProps, SubjectsState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                width = 100.vw
                padding(horizontal = 64.px)
                display = Display.flex
                justifyContent = JustifyContent.spaceBetween
            }
            h1 { +"Classes" }
            styledButton {
                +"Term ${props.term}"
            }
        }

        for (subject in props.subjects){
            subjectItem(subject, props.term)
        }
    }

}

interface SubjectsProps : TermProps {
    var subjects: List<Subject>
}

interface SubjectsState : RState {
    var termExpanded : Boolean
}

fun RBuilder.subjectsPage(subjects: List<Subject>, term: Int, handler: ActionHandler){
    child(SubjectsPage::class){
        attrs {
            this.subjects = subjects
            this.term = term
            this.handler = handler
        }
    }
}