package com.andb.apps.aspen.ui.common.color

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.state
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.onPositioned
import androidx.compose.ui.platform.DensityAmbient
import androidx.compose.ui.unit.dp
import com.andb.apps.aspen.ui.common.AlternativeSlider
import com.andb.apps.aspen.util.HSB
import com.andb.apps.aspen.util.toColor
import com.andb.apps.aspen.util.toHSB

private val hues: List<Color> = (0..360).map { HSB(it / 360f, 1f, 1f).toColor() }

@Composable
fun ExpandedColorPicker(_selected: Color, modifier: Modifier = Modifier, onSelect: (color: Color) -> Unit) {
    val (hue, setHue) = remember { mutableStateOf(_selected.toHSB().hue) }
    val (saturation, setSaturation) = remember { mutableStateOf(_selected.toHSB().saturation) }
    val (brightness, setBrightness) = remember { mutableStateOf(_selected.toHSB().brightness) }
    val (alpha, setAlpha) = remember { mutableStateOf(1f) }
    val hsb = HSB(hue, saturation, brightness)

    fun update(newHue: Float = hue, newSaturation: Float = saturation, newBrightness: Float = brightness, newAlpha: Float = alpha) {
        onSelect.invoke(HSB(newHue, newSaturation, newBrightness).toColor().copy(alpha = newAlpha))
    }

    println("hsb updated - hsb = $hsb")
    Column(modifier) {
        Text(text = "Pick Color".toUpperCase(), style = MaterialTheme.typography.subtitle1, modifier = Modifier.padding(bottom = 32.dp))
        Row {
            val rowHeight = remember { mutableStateOf(0) } // track height of SaturationLightnessPicker and give it to HuePicker since fillMaxHeight doesn't work as row has Constraints.Infinite so fillMax doesn't work
            SaturationBrightnessPicker(
                hue, saturation, brightness,
                modifier = Modifier.weight(1f).aspectRatio(1f).padding(end = 32.dp).onPositioned { rowHeight.value = it.size.height }
            ) { saturation, brightness ->
                println("changing saturation and brightness, current hsb = $hsb")
                setSaturation.invoke(saturation)
                setBrightness.invoke(brightness)
                update(newSaturation = saturation, newBrightness = brightness)
            }
            HuePicker(colors = hues, hue = hsb.hue, modifier = Modifier.height(with(DensityAmbient.current) { rowHeight.value.toDp() })) { hue ->
                setHue.invoke(hue)
                update(newHue = hue)
            }
        }
        Text(text = "Opacity".toUpperCase(), style = MaterialTheme.typography.subtitle1, modifier = Modifier.padding(vertical = 32.dp))
        OpacityPicker(color = hsb.toColor().copy(alpha = 1f), alpha = alpha) {
            setAlpha.invoke(it)
            update(newAlpha = it)
        }

        ColorPickerTextField(selected = hsb.toColor().copy(alpha = alpha), modifier = Modifier.padding(top = 32.dp)) {

        }
    }
}

