package com.andb.apps.aspen.ui.common.color

import androidx.compose.Composable
import androidx.compose.remember
import androidx.compose.state
import androidx.ui.core.*
import androidx.ui.core.gesture.pressIndicatorGestureFilter
import androidx.ui.foundation.Border
import androidx.ui.foundation.Box
import androidx.ui.foundation.Text
import androidx.ui.foundation.gestures.DragDirection
import androidx.ui.foundation.gestures.draggable
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.geometry.Offset
import androidx.ui.geometry.RRect
import androidx.ui.geometry.Radius
import androidx.ui.geometry.Size
import androidx.ui.graphics.*
import androidx.ui.graphics.drawscope.DrawScope
import androidx.ui.graphics.drawscope.clipPath
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.unit.Dp
import androidx.ui.unit.IntSize
import androidx.ui.unit.dp
import com.andb.apps.aspen.ui.common.AlternativeSlider
import com.andb.apps.aspen.util.HSB
import com.andb.apps.aspen.util.toColor
import com.andb.apps.aspen.util.toHSB

private val hues: List<Color> = (0..360).map { HSB(it / 360f, 1f, 1f).toColor() }

@Composable
fun ExpandedColorPicker(_selected: Color, modifier: Modifier = Modifier, onSelect: (color: Color) -> Unit) {
    val (hue, setHue) = state { _selected.toHSB().hue }
    val (saturation, setSaturation) = state { _selected.toHSB().saturation }
    val (brightness, setBrightness) = state { _selected.toHSB().brightness }
    val (alpha, setAlpha) = state { 1f }
    val hsb = HSB(hue, saturation, brightness)

    fun update(newHue: Float = hue, newSaturation: Float = saturation, newBrightness: Float = brightness, newAlpha: Float = alpha) {
        onSelect.invoke(HSB(newHue, newSaturation, newBrightness).toColor().copy(alpha = newAlpha))
    }

    println("hsb updated - hsb = $hsb")
    Column(modifier) {
        Text(text = "Pick Color".toUpperCase(), style = MaterialTheme.typography.subtitle1, modifier = Modifier.padding(bottom = 32.dp))
        Row {
            val rowHeight = state { 0 } // track height of SaturationLightnessPicker and give it to HuePicker since fillMaxHeight doesn't work as row has Constraints.Infinite so fillMax doesn't work
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

