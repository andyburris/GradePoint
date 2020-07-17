package ui.common.fliptoolkit

import react.RBuilder

fun RBuilder.animated(flipID: String, flippedProps: FlippedProps.() -> Unit = {}, block: RBuilder.() -> Unit) {
    Flipped {
        attrs {
            this.flipId = flipID
            flippedProps.invoke(this)
        }
        block.invoke(this)
    }
}

fun RBuilder.deAnimate(flipID: String, block: RBuilder.() -> Unit){
    Flipped {
        attrs {
            this.inverseFlipId = flipID
            this.scale = "false"
        }
        block.invoke(this)
    }
}

fun <T> RBuilder.animatorBase(key: T, block: RBuilder.() -> Unit) {
    Flipper {
        attrs {
            this.flipKey = key.hashCode().toString()
        }
        block.invoke(this)
    }
}