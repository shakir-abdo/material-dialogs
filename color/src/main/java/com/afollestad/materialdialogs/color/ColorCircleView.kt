/*
 * Licensed under Apache-2.0
 *
 * Designed and developed by Aidan Follestad (@afollestad)
 */
package com.afollestad.materialdialogs.color

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Color.GRAY
import android.graphics.Color.TRANSPARENT
import android.graphics.Color.WHITE
import android.graphics.Paint
import android.graphics.Paint.Style.FILL
import android.graphics.Paint.Style.STROKE
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.view.View
import com.afollestad.materialdialogs.color.utilext.dimenPx
import java.lang.Math.pow
import java.lang.Math.sqrt

/** @author Aidan Follestad (afollestad) */
internal class ColorCircleView(
  context: Context,
  attrs: AttributeSet? = null
) : View(context, attrs) {

  private val strokePaint = Paint()
  private val fillPaint = Paint()

  private val borderWidth = dimenPx(R.dimen.color_circle_view_border)

  init {
    setWillNotDraw(false)
    strokePaint.style = STROKE
    strokePaint.isAntiAlias = true
    strokePaint.color = Color.BLACK
    strokePaint.strokeWidth = borderWidth.toFloat()
    fillPaint.style = FILL
    fillPaint.isAntiAlias = true
    fillPaint.color = Color.DKGRAY
  }

  @ColorInt
  var color: Int = Color.BLACK
    set(value) {
      field = value
      fillPaint.color = value
      invalidate()
    }
  @ColorInt
  var border: Int = Color.DKGRAY
    set(value) {
      field = value
      strokePaint.color = value
      invalidate()
    }

  override fun onMeasure(
    widthMeasureSpec: Int,
    heightMeasureSpec: Int
  ) = super.onMeasure(widthMeasureSpec, widthMeasureSpec)

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    if (color == TRANSPARENT) {
      // Draw transparent grid
      var color: Int = GRAY
      val largeStep: Int = measuredWidth / 12
      val smallStep: Int = measuredWidth / 6
      for (x in 0..measuredWidth step largeStep) {
        for (y in 0..measuredHeight step largeStep) {
          val left = x.toFloat()
          val right = (left + smallStep)
          val top = y.toFloat()
          val bottom = (top + smallStep)
          fillPaint.color = color
          canvas.drawRect(left, top, right, bottom, fillPaint)
        }
        color = if (color == GRAY) WHITE else GRAY
      }
    } else {
      // Draw solid color
      canvas.drawCircle(
          measuredWidth / 2f,
          measuredHeight / 2f,
          (measuredWidth / 2f) - borderWidth,
          fillPaint
      )
    }
    canvas.drawCircle(
        measuredWidth / 2f,
        measuredHeight / 2f,
        (measuredWidth / 2f) - borderWidth,
        strokePaint
    )
  }

  private fun isInCircle(
    x: Int,
    y: Int,
    r: Int
  ): Boolean {
    val x1 = x.toDouble()
    val y1 = y.toDouble()
    val x2 = (measuredWidth / 2).toDouble()
    val y2 = (measuredHeight / 2).toDouble()
    val two = 2.toDouble()
    return sqrt(pow((x1 - x2), two) + pow((y1 - y2), two)) < r
  }
}
