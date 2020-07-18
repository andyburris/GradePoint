package ui.common.fliptoolkit

import kotlin.math.PI
import kotlin.math.max
import kotlin.math.pow

public fun springAnimate(config: SpringConfig = springConfig(0.2), delay: Number = 0, values: Map<String, Pair<Number, Number>> = emptyMap(), onComplete: () -> Unit = {}, onUpdate: (SpringSnapshot) -> Unit){
    val options = object : SpringOptions{
        override var config: SpringConfig = config
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

/*enum class SpringConfig(val value: String) {

}*/

class SpringConfig(val stiffness: Number, val damping: Number){
    companion object{
        val NO_WOBBLE = SpringConfig(stiffness = 200, damping = 26)
        val GENTLE = SpringConfig(stiffness = 120, damping = 14)
        val VERY_GENTLE = SpringConfig(stiffness = 130, damping = 17)
        val WOBBLY = SpringConfig(stiffness = 180, damping = 12)
        val STIFF = SpringConfig(stiffness = 260, damping = 26)
    }
}

fun springConfig(speed: Double, dampingRatio: Double = 1.0): SpringConfig {
    val nonZeroSpeed = max(speed, 0.01)
    return SpringConfig(
        stiffness =  ((2 * PI) / nonZeroSpeed).pow(2),
        damping = (4 * PI * dampingRatio) / nonZeroSpeed
    )
}