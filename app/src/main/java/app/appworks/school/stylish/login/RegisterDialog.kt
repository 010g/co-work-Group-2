package app.appworks.school.stylish.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.appworks.school.stylish.MainViewModel
import app.appworks.school.stylish.R
import app.appworks.school.stylish.databinding.DialogLoginBinding
import app.appworks.school.stylish.databinding.DialogRegisterBinding
import app.appworks.school.stylish.ext.getVmFactory
import app.appworks.school.stylish.ext.setTouchDelegate
import com.facebook.login.LoginManager


class RegisterDialog: AppCompatDialogFragment() {

    /**
     * Lazily initialize our [LoginViewModel].
     */
    private val viewModel by viewModels<RegisterViewModel> { getVmFactory() }
    private lateinit var binding: DialogRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.LoginDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DialogRegisterBinding.inflate(inflater, container, false)
        binding.layoutRegister.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_slide_up))

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.buttonRegisterClose.setTouchDelegate()



        binding.editRegisterName.doAfterTextChanged {
            viewModel.nameInput = it.toString() != ""
            viewModel.renewRegisterButtonClickable()
        }

        binding.editRegisterEmail.doAfterTextChanged {
            viewModel.emailInput = it.toString() != ""
            viewModel.renewRegisterButtonClickable()
        }

        binding.editRegisterPassword.doAfterTextChanged {
            viewModel.passwordInput = it.toString() != ""
            viewModel.renewRegisterButtonClickable()
        }

        binding.buttonRegisterApply.setOnClickListener {
            if (viewModel.canBeRegister.value!!.all { it }){
                viewModel.postRegisterInfoToServer()

                dismiss()
            } else {
                binding.textRegisterDescription.text = getString(R.string.register_warning)
            }
        }



        viewModel.leave.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    dismiss()
                    viewModel.onLeaveCompleted()
                }
            }
        )

        return binding.root
    }

    override fun dismiss() {
        binding.layoutRegister.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_slide_down))
        Handler().postDelayed({ super.dismiss() }, 200)
    }
}