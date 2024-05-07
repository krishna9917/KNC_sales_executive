package com.application.knc.Dailogs

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import com.application.knc.Adapters.LanguageAdapter
import com.application.knc.AppInterface.DialogClickListener
import com.application.knc.R
import com.application.knc.databinding.LayoutLangageChooserBinding
import com.application.knc.model.NameIcon

class LanguageChooser(context: Context, private val clickListener: DialogClickListener) : AlertDialog(context), View.OnClickListener,DialogClickListener {
    private val binding by lazy {
        LayoutLangageChooserBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initializer()
    }

    private fun initializer() {
        binding.imgClose.setOnClickListener(this)
        val languages = ArrayList<NameIcon>()
        languages.add(NameIcon(name = "English", subName = "en"))
        languages.add(NameIcon(name = "हिंदी", subName = "hi"))
        binding.rvLanguage.adapter = LanguageAdapter(languages,this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.imgClose -> dismiss()
        }
    }

    override fun onClick(clickCode: Int, data: Any?, callBack: DialogClickListener?) {
        dismiss()
        clickListener.onClick(clickCode,data)
    }

}