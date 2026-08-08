package com.costular.leuksna_moon_phases.presentation.view

import android.app.Dialog
import android.os.Bundle
import androidx.annotation.LayoutRes
import com.costular.leuksna_moon_phases.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class RoundedBottomSheetFragment(@LayoutRes contentLayoutId: Int) :
    BottomSheetDialogFragment(contentLayoutId) {

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        BottomSheetDialog(requireContext(), theme)
}
