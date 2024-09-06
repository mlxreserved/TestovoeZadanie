package com.example.login

import android.content.Context
import android.os.Bundle
import android.telecom.Call
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.math.log

class CodeFragment: Fragment() {

    interface Callbacks{
        fun onApplyClick()
    }

    private lateinit var sentMessage: TextView
    private lateinit var codeField1: EditText
    private lateinit var codeField2: EditText
    private lateinit var codeField3: EditText
    private lateinit var codeField4: EditText
    private lateinit var optEditText: List<EditText>
    private lateinit var applyBtn: Button
    private lateinit var codeState: StateFlow<CodeState>
    private var callbacks: Callbacks? = null

    private val loginViewModel: LoginViewModel by activityViewModels()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        codeState = loginViewModel.codeState
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_code, container, false)

        sentMessage = view.findViewById(R.id.sent_message) as TextView
        codeField1 = view.findViewById(R.id.codeField1) as EditText
        codeField2 = view.findViewById(R.id.codeField2) as EditText
        codeField3 = view.findViewById(R.id.codeField3) as EditText
        codeField4 = view.findViewById(R.id.codeField4) as EditText
        optEditText = listOf(codeField1,codeField2,codeField3,codeField4)
        applyBtn = view.findViewById(R.id.apply_button) as Button

        applyBtn.setOnClickListener {
            callbacks?.onApplyClick()
        }

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                codeState.collect{ state->
                    applyBtn.isEnabled = state.activeButton
                    when(state.currentPos){
                        0 -> {
                            codeField1.requestFocus()
                            codeField1.hint=""
                        }
                        1 -> {
                            codeField2.requestFocus()
                            codeField2.hint=""
                        }
                        2 -> {
                            codeField3.requestFocus()
                            codeField3.hint=""
                        }
                        3 -> {
                            codeField4.requestFocus()
                            codeField4.hint=""
                        }
                    }

                }
            }
        }

        when(codeState.value.currentPos){
            0->codeField1.requestFocus()
            1->{
                codeField1.setText(codeState.value.code[0])
                codeField2.requestFocus()
            }
            2->{
                codeField1.setText(codeState.value.code[0])
                codeField2.setText(codeState.value.code[1])
                codeField3.requestFocus()
            }
            3->{
                codeField1.setText(codeState.value.code[0])
                codeField2.setText(codeState.value.code[1])
                codeField3.setText(codeState.value.code[2])
                codeField4.requestFocus()
            }
            4->{
                codeField1.setText(codeState.value.code[0])
                codeField2.setText(codeState.value.code[1])
                codeField3.setText(codeState.value.code[2])
                codeField4.setText(codeState.value.code[3])
            }
        }

        codeField1.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                (v as EditText).hint = ""
            } else if(!hasFocus && (v as EditText).text.toString() == ""){
                v.hint="*"
            }
        }
        codeField2.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                (v as EditText).hint = ""
            } else if(!hasFocus && (v as EditText).text.toString() == ""){
                v.hint="*"
            }
        }
        codeField3.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                (v as EditText).hint = ""
            } else if(!hasFocus && (v as EditText).text.toString() == ""){
                v.hint="*"
            }
        }
        codeField4.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                (v as EditText).hint = ""
            } else if(!hasFocus && (v as EditText).text.toString() == ""){
                v.hint="*"
            }
        }


        sentMessage.text = getString(R.string.sent_message,loginViewModel.getEmail())
    }

    override fun onStart() {
        super.onStart()

        for (j in 0..optEditText.size-1) {
            optEditText[j].addTextChangedListener(
                object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {

                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if(s.toString().length>0) {
                            loginViewModel.addCode(s.toString())
                            if(j == 3){
                                applyBtn.isEnabled=codeState.value.activeButton
                            }
                            nextEditText().requestFocus()
                        } else{
                            loginViewModel.delCode()
                            prevEditText().requestFocus()
                            if(j == 3){
                                applyBtn.isEnabled=codeState.value.activeButton
                            }
                        }
                    }

                    override fun afterTextChanged(s: Editable?) {

                    }

                    fun nextEditText(): EditText {
                        for(i in 0..optEditText.size-2){
                            if(optEditText[i] == optEditText[j]) {
                                optEditText[i+1].hint=""
                                return optEditText[i + 1]
                            }
                        }
                        return optEditText[optEditText.size-1]
                    }
                    fun prevEditText(): EditText{
                        for(i in 1..optEditText.size-1){
                            if(optEditText[i]==optEditText[j]){
                                optEditText[i].hint=getString(R.string.code_hint)
                                return optEditText[i-1]
                            }
                        }
                        return optEditText[0]
                    }
                }
            )
        }
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

}