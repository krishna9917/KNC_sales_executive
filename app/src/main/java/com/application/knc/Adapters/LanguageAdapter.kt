package com.application.knc.Adapters
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.discount_medica.LocalMemory.MyLocalMemory
import com.application.knc.AppInterface.DialogClickListener
import com.application.knc.R
import com.application.knc.Utils.MyApp
import com.application.knc.databinding.LayoutLanguageItemBinding
import com.application.knc.model.NameIcon

class LanguageAdapter(private val languages:ArrayList<NameIcon>, private val dialogClickListener: DialogClickListener): RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder>()
{
    class  LanguageViewHolder(val binding :LayoutLanguageItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        return  LanguageViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.layout_language_item,parent,false))
    }

    override fun getItemCount(): Int {
      return  languages.size
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int)
    {
          holder.binding.isActive = MyLocalMemory.getString(MyApp.MySingleton.LANGUAGE)==languages[position].subName
          holder.binding.data = languages[position]
          holder.itemView.setOnClickListener {
              if(MyLocalMemory.getString(MyApp.MySingleton.LANGUAGE)!=languages[position].subName)
              {
                  MyLocalMemory.putString(MyApp.MySingleton.LANGUAGE, languages[position].subName!!)
                  dialogClickListener.onClick(101,data =languages[position].subName)
              }
          }
    }


}