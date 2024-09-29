package com.example.vacancy.screen

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.vacancy.R
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment: BottomSheetDialogFragment() {

    private lateinit var addSopr: TextView
    private lateinit var sopr: EditText
    private lateinit var response: Button
    private lateinit var responseWithSopr: Button
    private var string: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_fragment_response, container, false)

        addSopr = view.findViewById(R.id.add_sopr)
        sopr = view.findViewById(R.id.sopr)
        response = view.findViewById(R.id.apply_button)
        responseWithSopr = view.findViewById(R.id.apply_button_with_sopr)

        addSopr.setOnClickListener {
            sopr.visibility = View.VISIBLE
            addSopr.visibility = View.GONE
            sopr.requestFocus()
            response.visibility = View.GONE
            responseWithSopr.visibility = View.VISIBLE
        }

        if(arguments?.getString("string") != null){
            string = arguments?.getString("string")
            sopr.visibility = View.VISIBLE
            addSopr.visibility = View.GONE
            sopr.setText(string)
            response.visibility = View.GONE
            responseWithSopr.visibility = View.VISIBLE
        }


        response.setOnClickListener {
            dismiss()
        }

        responseWithSopr.setOnClickListener {
            dismiss()
        }

        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)


        if(dialog is BottomSheetDialog){
            dialog.behavior.skipCollapsed = true
            dialog.behavior.state = STATE_EXPANDED
        }
        return dialog
    }
}