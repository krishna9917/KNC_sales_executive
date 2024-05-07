package com.application.knc.Adapters
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.knc.AppInterface.AdapterViewClickListener
import com.application.knc.model.NameIcon
import com.application.knc.R
import com.application.knc.databinding.LayoutFunctionalityTabsItemBinding
import com.application.knc.databinding.LayoutHomeFunctionalityOptionsItemBinding

class HomeFunctionalityOptionsAdapter(
    private val functionalityButtons: ArrayList<NameIcon>,
    val clickListener: AdapterViewClickListener,
    val layout: Int = 0
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class FunctionalityOptionsViewHolder(val binding: LayoutHomeFunctionalityOptionsItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    class DrawerFunctionalityOptionsViewHolder(val binding: LayoutFunctionalityTabsItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (layout == 1) {
            val holder = viewHolder as DrawerFunctionalityOptionsViewHolder
            holder.binding.data = functionalityButtons[position]
        } else {
            val holder = viewHolder as FunctionalityOptionsViewHolder
            holder.binding.data = functionalityButtons[position]
        }
        viewHolder.itemView.setOnClickListener {
            clickListener.onClick(functionalityButtons[position].id, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (layout == 1) return DrawerFunctionalityOptionsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_functionality_tabs_item,
                parent,
                false
            )
        )
        return FunctionalityOptionsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_home_functionality_options_item, parent, false
            )
        )
    }


    override fun getItemCount(): Int {
        return functionalityButtons.size
    }

    override fun getItemViewType(position: Int): Int {
        return layout
    }


}