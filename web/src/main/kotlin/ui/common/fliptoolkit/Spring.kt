package ui.common.fliptoolkit

public fun spring(config: SpringConfig = SpringConfig.NO_WOBBLE, delay: Number = 0, values: Map<String, Pair<Number, Number>> = emptyMap(), onComplete: () -> Unit = {}, onUpdate: (SpringSnapshot) -> Unit){
    val options = object : SpringOptions{
        override var config: String = config.value
        override var values: dynamic = null
        override var onUpdate: (Number) -> Unit = {
            println("onUpdate: number = ${JSON.stringify(it)}")
            val snapshot = SpringSnapshot(currentProgress = it.toDouble(), values = emptyMap())
            onUpdate.invoke(snapshot)
        }
        override var delay: Number = delay
        override var onComplete: () -> Unit = onComplete
    }
    //println("options.values = ${options.values}")
    spring.invoke(options)
}

data class SpringSnapshot(val currentProgress: Double, val values: Map<String, Double>)

enum class SpringConfig(val value: String) {
    NO_WOBBLE("noWobble"),
    GENTLE("gentle"),
    VERY_GENTLE("veryGentle"),
    WOBBLY("wobbly"),
    STIFF("stiff")
}