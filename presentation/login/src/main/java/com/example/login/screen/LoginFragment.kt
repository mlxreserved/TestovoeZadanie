package com.example.login.screen

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.login.R
import com.google.android.material.textfield.TextInputLayout

class LoginFragment: Fragment() {

    interface Callbacks{
        fun onNextClick()
    }

    private var callbacks: Callbacks? = null

    private lateinit var textField: EditText
    private lateinit var textField2: TextInputLayout
    private lateinit var nextButton: Button
    private lateinit var enterWithPass: TextView


    private val loginViewModel: LoginViewModel by activityViewModels()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login,container, false)

        textField = view.findViewById(R.id.textField) as EditText
        textField2 = view.findViewById(R.id.textInputLayout) as TextInputLayout
        nextButton = view.findViewById(R.id.next_button) as Button
        enterWithPass = view.findViewById(R.id.enter_with_password) as TextView

        textField.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                tryEnterEmail()
                true
            }
            false
        }

        if(loginViewModel.getEmail().isBlank()) {
            nextButton.isEnabled = false
        }

        enterWithPass.setOnClickListener {}

        nextButton.setOnClickListener {
            tryEnterEmail()
        }

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun onStart() {
        super.onStart()
        val titleWatcher = object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loginViewModel.changeEmail(s.toString())
                textField2.error=null
                textField.setBackgroundResource(R.drawable.custom_input)
                if(s.toString()!=""){
                    nextButton.isEnabled=true
                } else {
                    nextButton.isEnabled=false
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        }
        textField.addTextChangedListener(titleWatcher)
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    fun tryEnterEmail(){
        if(loginViewModel.checkEmail()){
            callbacks?.onNextClick()
        } else {
            textField2.error = getString(R.string.wrong_enter)
            textField.setBackgroundResource(R.drawable.error_custom_input)
        }
    }

}