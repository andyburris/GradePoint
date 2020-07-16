package ui.subject

import ActionHandler
import ActionHandlerProps
import TermProps
import com.andb.apps.aspen.models.Category
import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.models.SubjectGrade
import com.andb.apps.aspen.state.UserAction
import kotlinx.css.*
import kotlinx.css.properties.border
import react.RBuilder
import react.RComponent
import react.RState
import styled.css
import styled.styledDiv
import ui.Text
import ui.TextVarient
import ui.Theme
import ui.common.MaterialIcon
import ui.common.TermSwitcher
import ui.dp
import util.displayFlex
import util.flexbox
import util.iconName

class SubjectPage : RComponent<SubjectPageProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                width = 100.vw
                padding(horizontal = 64.dp)
                boxSizing = BoxSizing.borderBox
            }

            styledDiv {
                css {
                    width = 100.pct
                    displayFlex(justifyContent = JustifyContent.spaceBetween, alignItems = Align.center, wrap = FlexWrap.wrap)
                    margin(vertical = 64.dp)
                }
                Text(props.subject.name, TextVarient.Header)
                TermSwitcher(props.term) { props.handler(UserAction.SwitchTerm(it)) }
            }

            flexbox(
                wrap = FlexWrap.wrapReverse,
                otherCSS = {
                    margin(all = (-32).dp)
                }
            ) {
                styledDiv {
                    css {
                        margin(left = 32.dp, top = 32.dp, bottom = 32.dp, right = (-32).dp)
                        flex(flexGrow = 3.0)
                    }
                    AssignmentTable(props.subject.termGrades(props.term).assignments)
                }
                styledDiv {
                    css {
                        margin(all = 32.dp)
                        flex(flexGrow = 1.0)
                    }
                    CategoryTable(props.subject.termGrades(props.term).categories, props.subject.termGrades(props.term).grade)
                }
            }
        }
    }
}

private fun RBuilder.CategoryTable(categories: List<Category>, termGrade: SubjectGrade){
    styledDiv {
        css {
            border(2.dp, BorderStyle.solid, rgba(0, 0, 0, 0.5))
            padding(all = 16.dp)
        }

        Text("CATEGORIES", TextVarient.Bold){
            color = Theme.Primary
            margin(bottom = 16.dp)
        }

        for (category in categories){
            CategoryItem(category) {
                margin(bottom = 16.dp)
            }
        }
        TotalItem(termGrade)
    }
}

private fun RBuilder.CategoryItem(category: Category, css: CSSBuilder.() -> Unit = {}){
    flexbox(justifyContent = JustifyContent.spaceBetween, alignItems = Align.center, otherCSS = css){
        flexbox(alignItems = Align.center){
            MaterialIcon(category.iconName())
            Text(category.name, TextVarient.Bold){
                margin(left = 8.dp)
            }
            Text("(${category.weight}%)", TextVarient.Secondary){
                margin(left = 8.dp)
            }
        }
        Text("${category.average} ${category.letter}".trim(), TextVarient.Bold)
    }
}

private fun RBuilder.TotalItem(grade: SubjectGrade){
    flexbox(justifyContent = JustifyContent.spaceBetween, alignItems = Align.center){
        Text("Total", TextVarient.Bold){
            color = rgba(0, 0, 0, 0.5)
        }
        Text(grade.toString(), TextVarient.Bold)
    }
}

interface SubjectPageProps : TermProps, ActionHandlerProps{
    var subject: Subject
}

fun RBuilder.SubjectPage(subject: Subject, term: Int, handler: ActionHandler) {
    child(SubjectPage::class) {
        attrs {
            this.subject = subject
            this.term = term
            this.handler = handler
        }
    }
}