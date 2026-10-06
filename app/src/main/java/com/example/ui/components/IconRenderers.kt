package com.example.ui.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas as AndroidCanvas
import android.graphics.Paint as AndroidPaint
import android.graphics.Path as AndroidPath
import android.graphics.RectF as AndroidRectF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.BackgroundPreset
import com.example.ui.IconShapeMask
import com.example.ui.theme.AmoledBlack
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.NeonCyan

fun createShapePath(size: Size, shapeMask: IconShapeMask): Path {
    val path = Path()
    val w = size.width
    val h = size.height

    when (shapeMask) {
        IconShapeMask.CIRCLE -> {
            path.addOval(Rect(0f, 0f, w, h))
        }
        IconShapeMask.SQUIRCLE -> {
            // Super-ellipse squircle
            val r = w * 0.28f
            path.moveTo(r, 0f)
            path.lineTo(w - r, 0f)
            path.cubicTo(w - r * 0.2f, 0f, w, r * 0.2f, w, r)
            path.lineTo(w, h - r)
            path.cubicTo(w, h - r * 0.2f, w - r * 0.2f, h, w - r, h)
            path.lineTo(r, h)
            path.cubicTo(r * 0.2f, h, 0f, h - r * 0.2f, 0f, h - r)
            path.lineTo(0f, r)
            path.cubicTo(0f, r * 0.2f, r * 0.2f, 0f, r, 0f)
            path.close()
        }
        IconShapeMask.ROUNDED_SQUARE -> {
            val r = w * 0.22f
            path.moveTo(r, 0f)
            path.lineTo(w - r, 0f)
            path.quadraticBezierTo(w, 0f, w, r)
            path.lineTo(w, h - r)
            path.quadraticBezierTo(w, h, w - r, h)
            path.lineTo(r, h)
            path.quadraticBezierTo(0f, h, 0f, h - r)
            path.lineTo(0f, r)
            path.quadraticBezierTo(0f, 0f, r, 0f)
            path.close()
        }
        IconShapeMask.HEXAGON -> {
            val r = w / 2f
            path.moveTo(r, 0f)
            path.lineTo(w, h * 0.25f)
            path.lineTo(w, h * 0.75f)
            path.lineTo(r, h)
            path.lineTo(0f, h * 0.75f)
            path.lineTo(0f, h * 0.25f)
            path.close()
        }
        IconShapeMask.TEARDROP -> {
            path.moveTo(w * 0.5f, 0f)
            path.quadraticBezierTo(w, h * 0.2f, w, h * 0.6f)
            path.cubicTo(w, h * 0.82f, w * 0.82f, h, w * 0.5f, h)
            path.cubicTo(w * 0.18f, h, 0f, h * 0.82f, 0f, h * 0.6f)
            path.quadraticBezierTo(0f, h * 0.2f, w * 0.5f, 0f)
            path.close()
        }
        IconShapeMask.PEBBLE -> {
            path.moveTo(w * 0.35f, 0f)
            path.cubicTo(w * 0.8f, 0f, w, h * 0.25f, w, h * 0.55f)
            path.cubicTo(w, h * 0.85f, w * 0.75f, h, w * 0.45f, h)
            path.cubicTo(w * 0.15f, h, 0f, h * 0.75f, 0f, h * 0.4f)
            path.cubicTo(0f, h * 0.1f, w * 0.1f, 0f, w * 0.35f, 0f)
            path.close()
        }
        IconShapeMask.DIAMOND -> {
            val cx = w / 2f
            val cy = h / 2f
            val corner = w * 0.08f
            path.moveTo(cx, corner)
            path.lineTo(w - corner, cy)
            path.lineTo(cx, h - corner)
            path.lineTo(corner, cy)
            path.close()
        }
    }
    return path
}

fun getBrushForPreset(preset: BackgroundPreset, size: Size): Brush {
    return when (preset) {
        BackgroundPreset.SOLID_AMOLED -> Brush.linearGradient(
            colors = listOf(Color(0xFF0C101A), Color(0xFF05070B))
        )
        BackgroundPreset.GRADIENT_CYAN_VIOLET -> Brush.linearGradient(
            colors = listOf(Color(0xFF00E5FF), Color(0xFF7C4DFF)),
            start = Offset(0f, 0f),
            end = Offset(size.width, size.height)
        )
        BackgroundPreset.GRADIENT_SUNSET -> Brush.linearGradient(
            colors = listOf(Color(0xFFFF3366), Color(0xFFFF9900)),
            start = Offset(0f, 0f),
            end = Offset(size.width, size.height)
        )
        BackgroundPreset.GRADIENT_EMERALD -> Brush.linearGradient(
            colors = listOf(Color(0xFF00E676), Color(0xFF00B0FF)),
            start = Offset(0f, 0f),
            end = Offset(size.width, size.height)
        )
        BackgroundPreset.GLASS_DARK -> Brush.linearGradient(
            colors = listOf(Color(0xCC1A2333), Color(0x990C101A))
        )
        BackgroundPreset.TRANSPARENT -> Brush.linearGradient(
            colors = listOf(Color.Transparent, Color.Transparent)
        )
    }
}

@Composable
fun StyledIconCanvas(
    modifier: Modifier = Modifier,
    shapeMask: IconShapeMask,
    bgPreset: BackgroundPreset,
    borderColor: Color,
    borderWidthDp: Float,
    scalePercent: Float,
    rotationDeg: Float,
    bitmap: Bitmap? = null,
    badgeSymbol: String? = null
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val size = this.size
            val path = createShapePath(size, shapeMask)

            // Draw Background
            drawPath(
                path = path,
                brush = getBrushForPreset(bgPreset, size)
            )

            // Draw Image inside clipping path
            if (bitmap != null && !bitmap.isRecycled) {
                val imgBitmap = bitmap.asImageBitmap()
                drawContext.canvas.save()
                drawContext.canvas.clipPath(path)

                rotate(degrees = rotationDeg, pivot = Offset(size.width / 2, size.height / 2)) {
                    val scaleFactor = (scalePercent / 100f) * 0.75f
                    scale(scale = scaleFactor, pivot = Offset(size.width / 2, size.height / 2)) {
                        val left = (size.width - bitmap.width) / 2
                        val top = (size.height - bitmap.height) / 2
                        drawImage(
                            image = imgBitmap,
                            topLeft = Offset(left, top)
                        )
                    }
                }
                drawContext.canvas.restore()
            }

            // Draw border
            if (borderWidthDp > 0f) {
                drawPath(
                    path = path,
                    color = borderColor,
                    style = Stroke(width = borderWidthDp * density)
                )
            }
        }

        // Overlay Badge
        if (!badgeSymbol.isNullOrEmpty()) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(borderColor)
                    .border(1.dp, Color.Black, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = badgeSymbol,
                    fontSize = 11.sp
                )
            }
        }
    }
}

// Generate a high resolution (512x512) Software ARGB_8888 Bitmap for saving & Home Screen pinning
fun renderStyledIconBitmap(
    bitmap: Bitmap?,
    shapeMask: IconShapeMask,
    bgPreset: BackgroundPreset,
    borderColorInt: Int,
    borderWidthPx: Float,
    scalePercent: Float,
    rotationDeg: Float,
    badgeSymbol: String?,
    sizePx: Int = 512
): Bitmap {
    val output = Bitmap.createBitmap(sizePx, sizePx, Bitmap.Config.ARGB_8888)
    val canvas = AndroidCanvas(output)
    val rect = AndroidRectF(0f, 0f, sizePx.toFloat(), sizePx.toFloat())

    val path = AndroidPath()
    val w = sizePx.toFloat()
    val h = sizePx.toFloat()

    when (shapeMask) {
        IconShapeMask.CIRCLE -> {
            path.addOval(rect, AndroidPath.Direction.CW)
        }
        IconShapeMask.SQUIRCLE -> {
            val r = w * 0.28f
            path.moveTo(r, 0f)
            path.lineTo(w - r, 0f)
            path.cubicTo(w - r * 0.2f, 0f, w, r * 0.2f, w, r)
            path.lineTo(w, h - r)
            path.cubicTo(w, h - r * 0.2f, w - r * 0.2f, h, w - r, h)
            path.lineTo(r, h)
            path.cubicTo(r * 0.2f, h, 0f, h - r * 0.2f, 0f, h - r)
            path.lineTo(0f, r)
            path.cubicTo(0f, r * 0.2f, r * 0.2f, 0f, r, 0f)
            path.close()
        }
        IconShapeMask.ROUNDED_SQUARE -> {
            val r = w * 0.22f
            path.addRoundRect(rect, r, r, AndroidPath.Direction.CW)
        }
        IconShapeMask.HEXAGON -> {
            val r = w / 2f
            path.moveTo(r, 0f)
            path.lineTo(w, h * 0.25f)
            path.lineTo(w, h * 0.75f)
            path.lineTo(r, h)
            path.lineTo(0f, h * 0.75f)
            path.lineTo(0f, h * 0.25f)
            path.close()
        }
        IconShapeMask.TEARDROP -> {
            path.moveTo(w * 0.5f, 0f)
            path.quadTo(w, h * 0.2f, w, h * 0.6f)
            path.cubicTo(w, h * 0.82f, w * 0.82f, h, w * 0.5f, h)
            path.cubicTo(w * 0.18f, h, 0f, h * 0.82f, 0f, h * 0.6f)
            path.quadTo(0f, h * 0.2f, w * 0.5f, 0f)
            path.close()
        }
        IconShapeMask.PEBBLE -> {
            path.moveTo(w * 0.35f, 0f)
            path.cubicTo(w * 0.8f, 0f, w, h * 0.25f, w, h * 0.55f)
            path.cubicTo(w, h * 0.85f, w * 0.75f, h, w * 0.45f, h)
            path.cubicTo(w * 0.15f, h, 0f, h * 0.75f, 0f, h * 0.4f)
            path.cubicTo(0f, h * 0.1f, w * 0.1f, 0f, w * 0.35f, 0f)
            path.close()
        }
        IconShapeMask.DIAMOND -> {
            val cx = w / 2f
            val cy = h / 2f
            val corner = w * 0.08f
            path.moveTo(cx, corner)
            path.lineTo(w - corner, cy)
            path.lineTo(cx, h - corner)
            path.lineTo(corner, cy)
            path.close()
        }
    }

    // Paint Background
    val bgPaint = AndroidPaint(AndroidPaint.ANTI_ALIAS_FLAG).apply {
        style = AndroidPaint.Style.FILL
        when (bgPreset) {
            BackgroundPreset.SOLID_AMOLED -> {
                color = android.graphics.Color.parseColor("#0C101A")
            }
            BackgroundPreset.GRADIENT_CYAN_VIOLET -> {
                shader = android.graphics.LinearGradient(
                    0f, 0f, w, h,
                    android.graphics.Color.parseColor("#00E5FF"),
                    android.graphics.Color.parseColor("#7C4DFF"),
                    android.graphics.Shader.TileMode.CLAMP
                )
            }
            BackgroundPreset.GRADIENT_SUNSET -> {
                shader = android.graphics.LinearGradient(
                    0f, 0f, w, h,
                    android.graphics.Color.parseColor("#FF3366"),
                    android.graphics.Color.parseColor("#FF9900"),
                    android.graphics.Shader.TileMode.CLAMP
                )
            }
            BackgroundPreset.GRADIENT_EMERALD -> {
                shader = android.graphics.LinearGradient(
                    0f, 0f, w, h,
                    android.graphics.Color.parseColor("#00E676"),
                    android.graphics.Color.parseColor("#00B0FF"),
                    android.graphics.Shader.TileMode.CLAMP
                )
            }
            BackgroundPreset.GLASS_DARK -> {
                color = android.graphics.Color.parseColor("#1A2333")
            }
            BackgroundPreset.TRANSPARENT -> {
                color = android.graphics.Color.TRANSPARENT
            }
        }
    }
    canvas.drawPath(path, bgPaint)

    // Clip & Draw User Bitmap
    if (bitmap != null && !bitmap.isRecycled) {
        canvas.save()
        canvas.clipPath(path)
        canvas.rotate(rotationDeg, w / 2, h / 2)
        val s = (scalePercent / 100f) * 0.8f
        canvas.scale(s, s, w / 2, h / 2)

        val destRect = AndroidRectF(
            (w - bitmap.width) / 2,
            (h - bitmap.height) / 2,
            (w + bitmap.width) / 2,
            (h + bitmap.height) / 2
        )
        canvas.drawBitmap(bitmap, null, destRect, AndroidPaint(AndroidPaint.ANTI_ALIAS_FLAG or AndroidPaint.FILTER_BITMAP_FLAG))
        canvas.restore()
    }

    // Draw Border
    if (borderWidthPx > 0f) {
        val strokePaint = AndroidPaint(AndroidPaint.ANTI_ALIAS_FLAG).apply {
            style = AndroidPaint.Style.STROKE
            color = borderColorInt
            strokeWidth = borderWidthPx
        }
        canvas.drawPath(path, strokePaint)
    }

    return output
}
