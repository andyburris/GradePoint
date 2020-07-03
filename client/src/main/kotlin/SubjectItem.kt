
import com.andb.apps.aspen.models.Subject
import com.ccfraser.muirwik.components.MTypographyVariant
import com.ccfraser.muirwik.components.mTypography
import kotlinx.css.*
import react.RBuilder
import react.RComponent
import react.RState
import react.dom.div
import styled.css
import styled.styledDiv

class SubjectItem : RComponent<SubjectProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                display = Display.flex
                flexDirection = FlexDirection.row
                justifyContent = JustifyContent.spaceBetween
            }

            styledDiv {
                css {
                    display = Display.flex
                    flexDirection = FlexDirection.row
                }
                styledDiv {
                    css {
                        borderRadius = 50.pct
                        backgroundColor = Color(props.subject.config.color.toString(16))
                    }
                }
                div {
                    mTypography(text = props.subject.name, variant = MTypographyVariant.subtitle1)
                    mTypography(text = props.subject.teacher)
                }
            }

            mTypography(text = props.subject.termGrades(props.term).grade.toString())
        }
    }
}

interface SubjectProps : TermProps{
    var subject: Subject
}

fun RBuilder.subjectItem(subject: Subject, term: Int){
    child(SubjectItem::class){
        attrs {
            this.subject = subject
            this.term = term
        }
    }
}