package com.example.login

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.lang.Thread.State

internal data class LoginState(
    val email: String = ""
)

internal data class CodeState(
    val code: MutableList<String> = mutableListOf(),
    val activeButton: Boolean = false,
    val currentPos: Int = 0,
    var prevSelected: Int = 0
)

internal class LoginViewModel: ViewModel() {

    private val _codeState = MutableStateFlow(CodeState())
    val codeState: StateFlow<CodeState> = _codeState.asStateFlow()

    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun addCode(input: String){

        _codeState.update {
            val code = it.code.apply { add(input) }
            it.copy(code = code,
                currentPos = code.size,
                activeButton = code.size+1==5) }
    }

    fun changePrevSelected(prev: Int){
        _codeState.update {
            it.copy(prevSelected = prev)
        }
    }

    fun delCode(){
        _codeState.update {
            val code = it.code.apply { removeLast() }
            it.copy(code = code,
                currentPos = code.size,
                activeButton = code.size+1==5)
        }
    }

    fun changePos(cur: Int){
        _codeState.update {
            it.copy(currentPos = cur)
        }
    }


    fun changeEmail(input: String) {
        _loginState.update { it.copy(email = input) }
    }

    fun checkEmail(): Boolean {
        return !TextUtils.isEmpty(loginState.value.email) && android.util.Patterns.EMAIL_ADDRESS.matcher(loginState.value.email).matches()
    }

    fun getEmail(): String{
        return loginState.value.email
    }
}