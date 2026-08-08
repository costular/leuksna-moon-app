package com.costular.leuksna_moon_phases.presentation.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.SystemClock
import android.util.AttributeSet
import android.view.View
import kotlin.math.max
import kotlin.random.Random

class AnimatedStarsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val stars = List(STAR_COUNT) { createStar() }
    private var running = false
    private var animationTimeMs = 0L
    private var lastFrameTimeMs = 0L

    private val frameCallback = object : Runnable {
        override fun run() {
            if (!running) return
            if (!ValueAnimator.areAnimatorsEnabled()) {
                onStop()
                return
            }
            val frameTimeMs = SystemClock.uptimeMillis()
            animationTimeMs += frameTimeMs - lastFrameTimeMs
            lastFrameTimeMs = frameTimeMs
            invalidate()
            postOnAnimation(this)
        }
    }

    fun onStart() {
        if (!running && ValueAnimator.areAnimatorsEnabled()) {
            running = true
            lastFrameTimeMs = SystemClock.uptimeMillis()
            postOnAnimation(frameCallback)
        }
    }

    fun onStop() {
        running = false
        removeCallbacks(frameCallback)
    }

    override fun onDetachedFromWindow() {
        onStop()
        super.onDetachedFromWindow()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.TRANSPARENT)
        val size = max(1f, width.coerceAtMost(height).toFloat())
        stars.forEach { star ->
            if (animationTimeMs - star.cycleStartMs >= star.cycleDurationMs) {
                star.reset(animationTimeMs)
            }

            val progress = (animationTimeMs - star.cycleStartMs).toFloat() / star.cycleDurationMs
            val alpha = (star.maxAlpha * triangleWave(progress)).toInt()
            paint.color = Color.argb(alpha, 255, 255, 255)
            canvas.drawCircle(star.x * width, star.y * height, star.radius * size / 350f, paint)
        }
    }

    private fun createStar() = Star().also { star ->
        star.reset(cycleStartMs = 0L)
        star.cycleStartMs -= Random.nextLong(star.cycleDurationMs)
    }

    private fun triangleWave(progress: Float): Float =
        if (progress < HALF_CYCLE) progress / HALF_CYCLE else (1f - progress) / HALF_CYCLE

    private class Star {
        var x = 0f
        var y = 0f
        var radius = 0f
        var maxAlpha = 0
        var cycleDurationMs = 0L
        var cycleStartMs = 0L

        fun reset(cycleStartMs: Long) {
            x = Random.nextFloat()
            y = Random.nextFloat()
            radius = Random.nextFloat() * MAX_RADIUS_MULTIPLIER + MIN_RADIUS_MULTIPLIER
            maxAlpha = Random.nextInt(MIN_ALPHA, MAX_ALPHA + 1)
            cycleDurationMs = Random.nextLong(MIN_CYCLE_DURATION_MS, MAX_CYCLE_DURATION_MS + 1)
            this.cycleStartMs = cycleStartMs
        }
    }

    private companion object {
        const val STAR_COUNT = 60
        const val HALF_CYCLE = 0.5f
        const val MIN_RADIUS_MULTIPLIER = 0.3f
        const val MAX_RADIUS_MULTIPLIER = 0.7f
        const val MIN_ALPHA = 120
        const val MAX_ALPHA = 255
        const val MIN_CYCLE_DURATION_MS = 3_000L
        const val MAX_CYCLE_DURATION_MS = 8_000L
    }
}
