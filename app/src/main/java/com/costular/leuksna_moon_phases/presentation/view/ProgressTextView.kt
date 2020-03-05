package com.costular.leuksna_moon_phases.presentation.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.ColorUtils
import com.costular.leuksna_moon_phases.R
import com.costular.leuksna_moon_phases.util.dp
import com.costular.leuksna_moon_phases.util.readAttrColor
import com.costular.leuksna_moon_phases.util.readRecycling

class ProgressTextView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {

    private val progressPaint = Paint()
    private val backgroundPaint = Paint()
    private val textPaint = Paint()

    init {
        readAttrs(attributeSet)
    }

    private var content: String = ""

    private var textSize: Float = DEFAULT_TEXT_SIZE
        set(value) {
            field = value
            textPaint.textSize = value
        }

    private var progress: Int = DEFAULT_PROGRESS

    private var progressColor: Int = DEFAULT_PROGRESS_COLOR

    private var progressBackgroundColor: Int = DEFAULT_BACKGROUND_COLOR

    private var textColor: Int = readAttrColor(R.attr.colorOnSurface)

    private var progressWidth: Int = DEFAULT_WIDTH

    private var center = 0f
    private var radius = 0f
    private var textWidth = 0f
    private var textHeight = 0f

    private fun readAttrs(attributeSet: AttributeSet?) {
        attributeSet ?: return

        val attrs = context.obtainStyledAttributes(attributeSet, R.styleable.ProgressTextView)
        attrs.readRecycling {
            textSize =
                getDimension(R.styleable.ProgressTextView_pt_textSize, DEFAULT_TEXT_SIZE)

            progress = getInt(R.styleable.ProgressTextView_pt_progress, DEFAULT_PROGRESS)

            progressColor =
                getColor(R.styleable.ProgressTextView_pt_progressColor, DEFAULT_PROGRESS_COLOR)

            progressWidth =
                getDimensionPixelSize(R.styleable.ProgressTextView_pt_progressWidth, DEFAULT_WIDTH)

            progressBackgroundColor = getDimensionPixelSize(
                R.styleable.ProgressTextView_pt_backgroundColor,
                DEFAULT_BACKGROUND_COLOR
            )

            textColor = getColor(
                R.styleable.ProgressTextView_pt_textColor,
                readAttrColor(R.attr.colorOnSurface)
            )
        }

        with(progressPaint) {
            color = progressColor
            isAntiAlias = true
            strokeWidth = progressWidth.toFloat()
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
        }
        with(backgroundPaint) {
            color = progressBackgroundColor
            isAntiAlias = true
            strokeWidth = progressWidth.toFloat()
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
        }

        with(textPaint) {
            color = textColor
            strokeWidth = 0f
            textSize = this@ProgressTextView.textSize
            typeface = ResourcesCompat.getFont(context, R.font.lato)
        }
    }

    private fun calculate() {
        center = width / 2f
        radius = (center - (progressWidth / 2f))

        textWidth = textPaint.measureText(content)

        val bounds = Rect()
        textPaint.getTextBounds(content, 0, content.length, bounds)
        textHeight = bounds.height().toFloat()
    }

    fun updateContent(content: String) {
        this.content = content
        invalidate()
    }

    fun updateProgress(progress: Int) {
        this.progress = progress
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        calculate()

        if (isEnabled) {
            with (textPaint) {
                color = textColor
            }
            with (progressPaint) {
                color = progressColor
            }
        } else {
            with (textPaint) {
                val disabledColor = ColorUtils.setAlphaComponent(textColor, DISABLED_ALPHA)
                color = disabledColor
            }
            with (progressPaint) {
                val disabledColor = ColorUtils.setAlphaComponent(progressColor, DISABLED_ALPHA)
                color = disabledColor
            }
        }

        drawText(canvas)
        if (isEnabled) drawProgressBackground(canvas)
        drawProgress(canvas)
    }

    private fun drawProgressBackground(canvas: Canvas?) {
        val newRadius = radius / 1.3f
        val oval = RectF(
            center - newRadius, center - newRadius, center + newRadius, center + newRadius
        )
        canvas?.drawArc(oval, -90f, 360f, false, backgroundPaint)
    }

    private fun drawProgress(canvas: Canvas?) {
        val newRadius = radius / 1.3f
        val oval = RectF(
            center - newRadius, center - newRadius, center + newRadius, center + newRadius
        )
        canvas?.drawArc(oval, -90f, 360 * (progress / 100f), false, progressPaint)
    }

    private fun drawText(canvas: Canvas?) {
        canvas?.drawText(content, center - (textWidth / 2f), center + (textHeight / 2f), textPaint)
    }

    private fun measureDimension(desiredSize: Int, measureSpec: Int): Int {
        var result: Int
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            result = desiredSize
            if (specMode == MeasureSpec.AT_MOST) {
                result = result.coerceAtMost(specSize)
            }
        }
        return result
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = suggestedMinimumWidth + paddingLeft + paddingRight
        val desiredHeight = suggestedMinimumHeight + paddingTop + paddingBottom
        setMeasuredDimension(
            measureDimension(desiredWidth, widthMeasureSpec),
            measureDimension(desiredHeight, heightMeasureSpec)
        )
    }

    companion object {
        private val DEFAULT_PROGRESS_COLOR = Color.parseColor("#FFFFFF")
        private val DEFAULT_BACKGROUND_COLOR = Color.parseColor("#61FFFFFF")
        private val DEFAULT_TEXT_SIZE = 16.dp.toFloat()
        private const val DEFAULT_PROGRESS = 0
        private val DEFAULT_WIDTH = 2.dp
        private const val DISABLED_ALPHA = 38
    }

}