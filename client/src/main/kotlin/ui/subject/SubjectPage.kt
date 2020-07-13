package ui.subject

import TermProps
import com.andb.apps.aspen.models.Subject
import kotlinx.css.margin
import kotlinx.css.marginTop
import kotlinx.css.px
import react.RBuilder
import react.RComponent
import react.RState
import styled.css
import styled.styledDiv
import styled.styledH1

class SubjectPage : RComponent<SubjectPageProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                margin(horizontal = 64.px)
            }
            styledH1 {
                css {
                    marginTop = 64.px
                }
                +props.subject.name
            }
        }
    }
}

interface SubjectPageProps : TermProps{
    var subject: Subject
}

fun RBuilder.SubjectPage(subject: Subject, term: Int){
    child(SubjectPage::class) {
        attrs {
            this.subject = subject
            this.term = term
        }
    }
}