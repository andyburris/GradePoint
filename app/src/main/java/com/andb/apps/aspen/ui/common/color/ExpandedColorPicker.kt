package com.andb.apps.aspen.ui.common.color

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.onPositioned
import androidx.compose.ui.platform.DensityAmbient
import androidx.compose.ui.unit.dp

private val hues: List<Color> = (0..360).map { HSB(it / 360f, 1f, 1f).toColor() }

@Composable
fun ExpandedColorPicker(selected: Color, modifier: Modifier = Modifier, onSelect: (color: Color) -> Unit) {

    val (oldHSB, setOldHSB) = remember { mutableStateOf<HSB?>(null) }
    val selectedHSB = remember(selected) {
        val oldColor = oldHSB?.toColor()
        val newHSB = selected.toHSB()
        when {
            newHSB.brightness == 0f -> newHSB.copy(hue = oldHSB?.hue ?: newHSB.hue, saturation = oldHSB?.saturation ?: newHSB.saturation)
            newHSB.saturation == 0f -> newHSB.copy(hue = oldHSB?.hue ?: newHSB.hue)
            oldColor == selected -> oldHSB
            else -> newHSB
        }
    }
//    val hue = remember(selected) { selectedHSB.hue }
//    val saturation = remember(selected) { selectedHSB.saturation }
//    val brightness = remember(selected) { selectedHSB.brightness }
//    val alpha = remember(selected) { selected.alpha }

//    val (hue, setHue) = remember { mutableStateOf(selectedHSB.hue) }
//    val (saturation, setSaturation) = remember { mutableStateOf(selectedHSB.saturation) }
//    val (brightness, setBrightness) = remember { mutableStateOf(selectedHSB.brightness) }
//    val (alpha, setAlpha) = remember { mutableStateOf(1f) }
//    val hsb = remember(hue, saturation, brightness) { HSB(hue, saturation, brightness) }

    println("selectedHSB changed - hsb = $selectedHSB")

    val update: (newHSB: HSB) -> Unit = { newHSB ->
        println("update invoked - updated = $selectedHSB")
        val updated = newHSB.toColor()
        setOldHSB(newHSB)
        onSelect.invoke(updated)
    }

    Column(modifier) {
        Text(text = "Pick Color".toUpperCase(), style = MaterialTheme.typography.subtitle1, modifier = Modifier.padding(bottom = 32.dp))
        Row {
            val rowHeight = remember { mutableStateOf(0) } // track height of SaturationLightnessPicker and give it to HuePicker since fillMaxHeight doesn't work as row has Constraints.Infinite so fillMax doesn't work
            SaturationBrightnessPicker(
                selectedHSB.hue, selectedHSB.saturation, selectedHSB.brightness,
                modifier = Modifier.weight(1f).aspectRatio(1f).padding(end = 32.dp).onPositioned { rowHeight.value = it.size.height }
            ) { saturation, brightness ->
                println("changing saturation and brightness, current hsb = $selectedHSB")
                update.invoke(selectedHSB.copy(saturation = saturation, brightness = brightness))
            }
            HuePicker(colors = hues, hue = selectedHSB.hue, modifier = Modifier.height(with(DensityAmbient.current) { rowHeight.value.toDp() })) { hue ->
                //setHue.invoke(hue)
                println("changing hue, current hsb = $selectedHSB")
                update.invoke(selectedHSB.copy(hue = hue))
            }
        }
        Text(text = "Opacity".toUpperCase(), style = MaterialTheme.typography.subtitle1, modifier = Modifier.padding(vertical = 32.dp))
        OpacityPicker(color = selectedHSB) {
//            setAlpha.invoke(it)
            println("changing alpha, current hsb = $selectedHSB")
            update.invoke(selectedHSB.copy(alpha = it))
        }
    }
}


