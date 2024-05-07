package com.application.knc.Activities.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.application.discount_medica.LocalMemory.MyLocalMemory
import com.application.knc.Activities.HomeActivity
import com.application.knc.R
import com.application.knc.Utils.MyApp
import com.application.knc.Utils.UtilsFunction
import com.application.knc.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private val viewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initializer()
    }

    private fun initializer()
    {
        binding.inclTitleBar.imgBack.setOnClickListener(this)
        binding.inclLogin.btn.setOnClickListener(this)
        setObservers()
    }

    private fun setObservers() {
        viewModel.loginLiveData.observe(this){
            binding.isProgress=false
            if (it.status)
            {
                MyLocalMemory.putObject(MyApp.MySingleton.USER,it.data)
                MyLocalMemory.putString(MyApp.MySingleton.USER_TOKEN,it.access_token)
                MyLocalMemory.putBoolean(MyApp.MySingleton.IS_LOGIN,true)
                startActivity(Intent(this,HomeActivity::class.java))
                finishAffinity()
            }else
            {
                UtilsFunction.showError(this,it.message)
            }
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id)
        {
            R.id.imgBack -> finish()
            R.id.btn-> {
                if (mobileValidator(binding.inclNumber.edtNumber) && passwordValidator(binding.inclPassword.edtField))
                {
                    binding.isProgress=true
                    viewModel.login(binding.inclNumber.edtNumber.text.toString(),binding.inclPassword.edtField.text.toString())
                }
            }
        }
    }


    companion object {
        fun mobileValidator(editField: EditText): Boolean {
            var isValid = true
            val mobileNumber = editField.text.toString()
            if (!fieldEmptyValidator(
                    editField,
                    MyApp.MySingleton.getAppContext().getString(R.string.please_enter_mobile_number)
                )
            ) {
                isValid = false
            } else if (mobileNumber.length < 10 || !(mobileNumber[0].equals('9') || mobileNumber[0].equals(
                    '8'
                ) || mobileNumber[0].equals('7') || mobileNumber[0].equals('6'))
            ) {
                editField.error =
                    MyApp.MySingleton.getAppContext().getString(R.string.invalid_mobile_number)
                isValid = false
            }
            if (!isValid) UtilsFunction.vibration(MyApp.MySingleton.getAppContext())
            return isValid
        }

        fun passwordValidator(editField: EditText): Boolean {
            var isValid = true
            if (!fieldEmptyValidator(
                    editField,
                    MyApp.MySingleton.getAppContext().getString(R.string.please_enter_password)
                )
            ) {
                isValid = false
            } else if (editField.text.toString().length < 4) {
                editField.error = MyApp.MySingleton.getAppContext()
                    .getString(R.string.invalid_password_min_4_char)
                isValid = false
            }
            if (!isValid) UtilsFunction.vibration(MyApp.MySingleton.getAppContext())
            return isValid
        }


        fun fieldEmptyValidator(editText: EditText, message: String): Boolean {
            var isValid = true
            if (editText.text.toString().equals("")) {
                editText.error = message
                isValid = false
            }
            if (!isValid) UtilsFunction.vibration(MyApp.MySingleton.getAppContext())
            return isValid
        }
    }
}