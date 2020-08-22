package com.andb.apps.aspen.ui.common.color

import androidx.compose.ui.Alignment
import androidx.compose.ui.drawBehind
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Box
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.stateFor
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawShadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RRect
import androidx.compose.ui.geometry.Radius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.andb.apps.aspen.ui.common.AlternativeSlider

@Composable
fun OpacityPicker(color: Color, alpha: Float, modifier: Modifier = Modifier, onSelect: (alpha: Float) -> Unit) {
    Row {
        OpacitySlider(color = color, alpha = alpha, modifier = Modifier.fillMaxWidth().weight(1f), onSelect = onSelect)
        //OpacityTextField(alpha = alpha, onSelect = onSelect, modifier = Modifier.width(56.dp))
    }
}

@Composable
private fun OpacitySlider(color: Color, alpha: Float, modifier: Modifier = Modifier, onSelect: (alpha: Float) -> Unit){
    AlternativeSlider(
        position = alpha,
        track = {
            Box(modifier = Modifier
                .height(32.dp)
                .fillMaxWidth()
                .gravity(Alignment.Center)
                .drawBehind {
                    val gradient = HorizontalGradient(
                        colors = listOf(Color.Transparent, color),
                        startX = 0f,
                        endX = size.width
                    )
                    drawTiles(4, 16.dp)
                    drawRoundRect(gradient, radius = Radius(16.dp.toPx()))
                }
            )
        },
        thumb = {
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .size(28.dp)
                    .drawShadow(2.dp, shape = CircleShape)
                    .drawBehind {
                        drawTiles(2, 14.dp)
                    },
                shape = CircleShape,
                border = BorderStroke(3.dp, MaterialTheme.colors.background),
                backgroundColor = color.copy(alpha = alpha)
            )
        },
        modifier = modifier,
        onChange = onSelect
    )
}

@Composable
private fun OpacityTextField(alpha: Float, modifier: Modifier = Modifier, onSelect: (alpha: Float) -> Unit) {
    val text = stateFor(alpha) { (alpha * 100).toInt().toString() }
/*    Box(modifier){
        FilledTextField(value = text.value, onValueChange = { text.value = it }, label = {}, modifier = Modifier.height(32.dp))
    }*/
}

private fun DrawScope.drawTiles(rows: Int, radius: Dp, color1: Color = Color.LightGray, color2: Color = Color.White){
    val rrect = RRect(0f, 0f, size.width, size.height, Radius(radius.toPx()))
    clipPath(Path().apply { addRRect(rrect) }) {
        for (row in 0 until rows){
            val tileSize = size.height / rows
            val tilesPerRow = (size.width/tileSize).toInt() + 1
            for (col in 0 until tilesPerRow){
                val grey = (row + col) % 2 == 0
                drawRect(if (grey) color1 else color2, topLeft = Offset(col * tileSize, row * tileSize), size = Size(tileSize, tileSize))
            }
        }
    }
}