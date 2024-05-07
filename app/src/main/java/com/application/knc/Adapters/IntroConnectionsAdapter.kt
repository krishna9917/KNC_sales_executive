package com.application.knc.Adapters
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.application.knc.R
import com.application.knc.databinding.LayoutConnectionBottomItemBinding
import com.application.knc.databinding.LayoutConnectionUpperItemBinding

class IntroConnectionsAdapter: RecyclerView.Adapter<ViewHolder>()
{

    class UpperCornerConnectionLayoutViewHolder(val binding: LayoutConnectionUpperItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    class BottomCornerConnectLayoutViewHolder(val binding: LayoutConnectionBottomItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if(viewType==1)
        {
           return UpperCornerConnectionLayoutViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.layout_connection_upper_item,parent,false))

        }
        return  BottomCornerConnectLayoutViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.layout_connection_bottom_item,parent,false))
    }

    override fun getItemViewType(position: Int): Int {
        if(position%2==0)
        {
            return 1
        }
        return 0
    }

    override fun getItemCount(): Int {
        return  4
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }


}