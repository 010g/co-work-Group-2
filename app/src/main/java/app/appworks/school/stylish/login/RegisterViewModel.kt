package app.appworks.school.stylish.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.appworks.school.stylish.NativeLoginResult
import app.appworks.school.stylish.data.NativeSignUpBody
import app.appworks.school.stylish.data.source.StylishRepository
import app.appworks.school.stylish.util.Logger
import kotlinx.coroutines.launch

class RegisterViewModel(private val stylishRepository: StylishRepository) : ViewModel() {

    // Handle leave register
    private val _leave = MutableLiveData<Boolean>()

    val leave: LiveData<Boolean>
        get() = _leave

    val name = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    var nameInput = false
    var emailInput = false
    var passwordInput = false


    private val _canBeRegister = MutableLiveData<List<Boolean>>()

    val canBeRegister: LiveData<List<Boolean>>
        get() = _canBeRegister



    init {
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]$this")
        Logger.i("------------------------------------")

        renewRegisterButtonClickable()
    }



    fun postRegisterInfoToServer() {
            viewModelScope.launch {
//                val userSignUpBody = NativeSignUpBody(name.value!!,email.value!!,password.value!!)

                stylishRepository.userSignUp(name.value!!, email.value!!,password.value!!)


            }
    }

    fun renewRegisterButtonClickable(){
        val forRegisterButtonClickable = listOf(
            nameInput,
            emailInput,
            passwordInput
        )
        _canBeRegister.value = forRegisterButtonClickable
    }


    fun leave() {
        _leave.value = true
    }

    fun onLeaveCompleted() {
        _leave.value = null
    }

    fun nothing() {}
}