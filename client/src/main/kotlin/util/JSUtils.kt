package util

import com.andb.apps.aspen.models.Category
import com.andb.apps.aspen.models.Subject
import kotlinx.css.*
import kotlinx.css.properties.IterationCount
import kotlinx.css.properties.s
import kotlinx.html.DIV
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLTextAreaElement
import org.w3c.dom.events.Event
import react.RBuilder
import styled.StyledDOMBuilder
import styled.animation
import styled.css
import styled.styledDiv

val Event.targetInputValue: String
    get() = (target as? HTMLInputElement)?.value ?: (target as? HTMLTextAreaElement)?.value ?: ""

fun clamp(min: LinearDimension, between: LinearDimension, max: LinearDimension): LinearDimension{
    return LinearDimension("clamp($min, $between, $max)")
}

private operator fun Number.times(linearDimension: LinearDimension): LinearDimension = linearDimension.times(this)
private val LinearDimension.number: Double get() = value.takeWhile { it in '0'..'9' }.toDouble()

fun CSSBuilder.shimmer(){
    background = "linear-gradient(to right, #eff1f3 4%, #e2e2e2 25%, #eff1f3 36%);"
    backgroundSize = "1000px, 100%"
/*    animation(duration = 2.s, iterationCount = IterationCount.infinite){
        0 { backgroundPosition = "-1000px 0" }
        100 { backgroundPosition = "1000px 0"}
    }*/
}

fun CSSBuilder.displayFlex(direction: FlexDirection = FlexDirection.inherit, justifyContent: JustifyContent = JustifyContent.inherit, alignItems: Align = Align.center, wrap: FlexWrap = FlexWrap.inherit){
    display = Display.flex
    flexDirection = direction
    this.justifyContent = justifyContent
    this.alignItems = alignItems
    this.flexWrap = wrap
}

fun RBuilder.flexbox(direction: FlexDirection = FlexDirection.inherit, justifyContent: JustifyContent = JustifyContent.inherit, alignItems: Align = Align.center, wrap: FlexWrap = FlexWrap.inherit, children: StyledDOMBuilder<DIV>.() -> Unit){
    styledDiv {
        css {
            displayFlex(direction, justifyContent, alignItems, wrap)
        }
        children.invoke(this)
    }
}

fun Subject.Icon.iconName(): String{
    return when(this){
        Subject.Icon.ART -> "palette"
        Subject.Icon.ATOM -> "school"
        Subject.Icon.BOOK -> "book"
        Subject.Icon.CALCULUS -> "school"
        Subject.Icon.COMPASS -> "school"
        Subject.Icon.COMPUTER -> "laptop"
        Subject.Icon.FLASK -> "school"
        Subject.Icon.LANGUAGE -> "language"
        Subject.Icon.MUSIC -> "music_note"
        Subject.Icon.PE -> "directions_run"
        Subject.Icon.SCHOOL -> "school"
        Subject.Icon.BIOLOGY -> "school"
        Subject.Icon.CAMERA -> "camera_alt"
        Subject.Icon.DICE -> "casino"
        Subject.Icon.ECONOMICS -> "trending_up"
        Subject.Icon.ENGINEERING -> "build"
        Subject.Icon.FILM -> "movie"
        Subject.Icon.FINANCE -> "attach_money"
        Subject.Icon.FRENCH -> "outlined_flag"
        Subject.Icon.GLOBE -> "public"
        Subject.Icon.GOVERNMENT -> "account_balance"
        Subject.Icon.HEALTH -> "local_hospital"
        Subject.Icon.HISTORY -> "school"
        Subject.Icon.LAW -> "gavel"
        Subject.Icon.NEWS -> "school"
        Subject.Icon.PSYCHOLOGY -> "school"
        Subject.Icon.SOCIOLOGY -> "people"
        Subject.Icon.SPEAKING -> "school"
        Subject.Icon.STATISTICS -> "bar_chart"
        Subject.Icon.THEATER -> "school"
        Subject.Icon.TRANSLATE -> "translate"
        Subject.Icon.WRITING -> "edit"
    }
}

fun Category.iconName(): String {
    return when {
        "assessment" in name.toLowerCase() -> "assignment_turned_in"
        "participation" in name.toLowerCase() -> "assignment_ind"
        else -> "assignment"
    }
}