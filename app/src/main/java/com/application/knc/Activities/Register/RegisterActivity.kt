package com.application.knc.Activities.Register

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.application.discount_medica.LocalMemory.MyLocalMemory
import com.application.knc.Activities.Login.LoginActivity
import com.application.knc.Activities.Login.LoginActivity.Companion.fieldEmptyValidator
import com.application.knc.Activities.Login.LoginActivity.Companion.passwordValidator
import com.application.knc.R
import com.application.knc.Utils.MyApp
import com.application.knc.Utils.UtilsFunction
import com.application.knc.databinding.ActivityRegisterBinding
import com.application.knc.model.UserData
import com.github.drjacky.imagepicker.ImagePicker
import com.github.drjacky.imagepicker.constant.ImageProvider
import com.google.android.material.tabs.TabLayoutMediator
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private val binding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[RegisterViewModel::class.java]
    }

    private var profileImage: MultipartBody.Part? = null

    private val govtId:ArrayList<MultipartBody.Part> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initialize()
    }

    private fun initialize() {
        binding.isBusinessDealer = intent.getBooleanExtra("isBusinessDealer", false)
        binding.inclTitleBar.imgBack.setOnClickListener(this)
        binding.inclRegister.btn.setOnClickListener(this)
        binding.imgProfilePic.setOnClickListener(this)
        binding.inclGovtId.edtField.showSoftInputOnFocus = false
        binding.inclGovtId.edtField.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                mediaMultipleLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        }
        setObserver()
    }

    private fun setObserver() {
        viewModel.registerData.observe(this) {
            binding.isProgress = false
            if (it.status) {
                binding.inclBusinessName.edtField.setText("")
                binding.inclName.edtField.setText("")
                binding.inclEmail.edtField.setText("")
                binding.inclNumber.edtNumber.setText("")
                binding.inclPassword.edtField.setText("")
                profileImage=null
                binding.imgProfilePic.setImageResource(R.drawable.img_camera)
                govtId.clear()
                binding.inclGovtId.edtField.setText("")
                UtilsFunction.showToast(this, it.message)
            } else {
                UtilsFunction.showError(this, it.message)
            }
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.imgBack-> finish()
            R.id.btn -> {
                if (binding.isBusinessDealer == true && fieldEmptyValidator(
                        binding.inclBusinessName.edtField,
                        getString(R.string.please_enter_business_name)
                    )
                ) {
                    register("dealer".toRequestBody("text/plain".toMediaTypeOrNull()))
                } else {
                    register("customer".toRequestBody("text/plain".toMediaTypeOrNull()))
                }
            }

            R.id.imgProfilePic -> {
                ImagePicker.with(this)
                    .provider(ImageProvider.BOTH)
                    .crop()
                    .cropOval()
                    .maxResultSize(512, 512, true)
                    .createIntentFromDialog { profileLauncher.launch(it) }
            }
        }
    }
    val user = MyLocalMemory.getObject(MyApp.MySingleton.USER, UserData())

    private fun register(role: RequestBody) {
        if (fieldEmptyValidator(
                binding.inclName.edtField,
                getString(R.string.please_enter_your_name)
            ) && emailValidator(binding.inclEmail.edtField) && LoginActivity.mobileValidator(
                binding.inclNumber.edtNumber
            ) && passwordValidator(binding.inclPassword.edtField)
        ) {
            if(govtId.size!=0 || binding.isBusinessDealer == true)
            {
                binding.isProgress = true
                if (profileImage == null)
                {
                    profileImage = MultipartBody.Part.createFormData("profile_pic", "")
                }

                val user = MyLocalMemory.getObject(MyApp.MySingleton.USER,UserData())

                viewModel.register(
                    businessName =  binding.inclBusinessName.edtField.text.toString()
                        .toRequestBody("text/plain".toMediaTypeOrNull()),
                    name =  binding.inclName.edtField.text.toString()
                        .toRequestBody("text/plain".toMediaTypeOrNull()),
                    mobile = binding.inclNumber.edtNumber.text.toString()
                        .toRequestBody("text/plain".toMediaTypeOrNull()),
                    email = binding.inclEmail.edtField.text.toString()
                        .toRequestBody("text/plain".toMediaTypeOrNull()),
                    role = role,
                    password = binding.inclPassword.edtField.text.toString()
                        .toRequestBody("text/plain".toMediaTypeOrNull()),
                    createdById = user.id.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
                    createdBy = user.name.toRequestBody("text/plain".toMediaTypeOrNull()),
                    profile_pic = profileImage!!,govtId,context = this
                )
            }

                else
            {
                UtilsFunction.showError(this,
                    getString(R.string.please_upload_any_government_id_like_aadhar_pancard_voterid))
            }
        }
    }

    private val profileLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!

                val imageFile = File(uri.path.toString())
                profileImage = MultipartBody.Part.createFormData(
                    "profile_image",
                    imageFile.name,
                    imageFile.asRequestBody("image/*".toMediaTypeOrNull())
                )
                binding.imgProfilePic.setImageURI(uri)
            } else {
                parseError(it)
            }
        }

    private val mediaMultipleLauncher =
        registerForActivityResult(
            ActivityResultContracts.PickMultipleVisualMedia(
                2
            )
        ) { uris ->
            if (uris.isNotEmpty())
            {
                binding.inclGovtId.edtField.setText("")
                govtId.clear()
                val fileNames = uris.map { uri ->
                    val inputStream = contentResolver.openInputStream(uri)
                    // Create a temporary file
                    val imageFile = File.createTempFile("image", null, cacheDir)

                    // Copy the content from inputStream to the temporary file
                    inputStream?.use { input ->
                        imageFile.outputStream().use { output ->
                            input.copyTo(output)
                        }
                    }
                    govtId.add( MultipartBody.Part.createFormData(
                        "government_id_files[]",
                        imageFile.name,
                        imageFile.asRequestBody("image/*".toMediaTypeOrNull())
                    ))
                    imageFile.name ?: ""
                }
                binding.inclGovtId.edtField.setText(fileNames.joinToString(separator = ","))
            }
        }

    private fun parseError(activityResult: ActivityResult) {
        if (activityResult.resultCode == ImagePicker.RESULT_ERROR) {
            UtilsFunction.showError(this, ImagePicker.getError(activityResult.data))
        }
    }

    companion object {
        fun emailValidator(editText: EditText): Boolean {
            var isValid = true
            if (!editText.text.toString()
                    .equals("") && !Patterns.EMAIL_ADDRESS.matcher(editText.text.trim().toString())
                    .matches()
            ) {
                editText.error =
                    MyApp.MySingleton.getAppContext().getString(R.string.invalid_email_address)
                isValid = false
            }
            if (!isValid) UtilsFunction.vibration(MyApp.MySingleton.getAppContext())
            return isValid
        }

    }
}