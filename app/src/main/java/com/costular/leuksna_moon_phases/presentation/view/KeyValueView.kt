package com.costular.leuksna_moon_phases.presentation.view

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.costular.leuksna_moon_phases.R
import com.costular.leuksna_moon_phases.databinding.ViewKeyValueBinding
import com.costular.leuksna_moon_phases.util.readRecycling
import java.lang.IllegalStateException
import kotlin.properties.Delegates.observable

class KeyValueView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr) {

    private val binding = ViewKeyValueBinding.inflate(android.view.LayoutInflater.from(context), this)

    var key: String by observable("") { _, _, newValue ->
        binding.textKey.text = newValue
    }

    var value: String by observable("") { _, _, newValue ->
        binding.textValue.text = newValue
    }

    init {
        readAttrs(attributeSet)
    }

    private fun readAttrs(attrs: AttributeSet?) {
        attrs ?: return

        val attrValues = context.obtainStyledAttributes(attrs, R.styleable.KeyValueView)
        attrValues.readRecycling {
            val key = getString(R.styleable.KeyValueView_kv_key)
                ?: throw IllegalStateException("You must set the key")

            val value = getString(R.styleable.KeyValueView_kv_value) ?: ""

            this@KeyValueView.key = key
            this@KeyValueView.value = value
        }
    }
}
