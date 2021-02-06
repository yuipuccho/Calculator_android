package com.example.calculator

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Path
import android.graphics.RectF
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

/**
 * 角丸ボタン
 *
 * @property btnBackgroundColor ボタン背景色
 * @property btnDisableBackgroundColor ボタン背景色（無効時）
 * @property borderColor 枠色
 * @property borderWidth 枠太さ
 * @property cornerRadius 角Radius
 */
class RoundRectButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.attr.roundRectButtonStyle)
    : AppCompatButton(context, attrs, defStyleAttr) {

    var btnBackgroundColor = Color.TRANSPARENT
        set(value) {
            field = value
            reDraw()
        }

    var btnDisableBackgroundColor = Color.TRANSPARENT
        set(value) {
            field = value
            reDraw()
        }

    var btnTextColor = Color.WHITE
        set(value) {
            field = value
            reDraw()
        }

    var btnDisableTextColor = Color.WHITE
        set(value) {
            field = value
            reDraw()
        }

    var borderColor = Color.TRANSPARENT
        set(value) {
            field = value
            reDraw()
        }

    var borderDisableColor = Color.TRANSPARENT
        set(value) {
            field = value
            reDraw()
        }

    var borderWidth = 0f
        set(value) {
            field = value
            reDraw()
        }

    var cornerRadius = 0f
        set(value) {
            field = value
            reDraw()
        }

    var likeRate: Int? = null
        set(value) {
            field = value
            reDraw()
        }

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundRectButton)
            btnBackgroundColor = typedArray.getColor(R.styleable.RoundRectButton_btn_background_color, btnBackgroundColor)
            btnDisableBackgroundColor = typedArray.getColor(R.styleable.RoundRectButton_btn_disable_background_color, btnDisableBackgroundColor)
            btnTextColor = typedArray.getColor(R.styleable.RoundRectButton_btn_text_color, textColors.defaultColor)
            btnDisableTextColor = typedArray.getColor(R.styleable.RoundRectButton_btn_disable_text_color, btnDisableTextColor)
            borderColor = typedArray.getColor(R.styleable.RoundRectButton_border_color, borderColor)
            borderDisableColor = typedArray.getColor(R.styleable.RoundRectButton_border_disable_color, borderColor)
            borderWidth = typedArray.getDimension(R.styleable.RoundRectButton_border_width, 0f)
            cornerRadius = typedArray.getDimension(R.styleable.RoundRectButton_corner_radius, 0f)
            typedArray.recycle()
        }
        reDraw()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        reDraw()
    }

    override fun draw(canvas: Canvas?) {
        val rect = RectF(0f, 0f, width.toFloat(), height.toFloat())
        val path = Path()
        path.addRoundRect(rect, cornerRadius, cornerRadius, Path.Direction.CW)
        path.close()
        canvas?.clipPath(path)
        super.draw(canvas)
    }

    private fun reDraw() {
        val drawable = GradientDrawable()
        drawable.cornerRadius = cornerRadius
        drawable.setColor(if (isEnabled) btnBackgroundColor else btnDisableBackgroundColor)
        drawable.setStroke(borderWidth.toInt(), if (isEnabled) borderColor else borderDisableColor)
        background = drawable
        setTextColor(if (isEnabled) btnTextColor else btnDisableTextColor)
    }
}