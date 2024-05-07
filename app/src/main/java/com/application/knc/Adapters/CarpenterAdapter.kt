package com.application.knc.Adapters
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.knc.Dailogs.ImageZoomer
import com.application.knc.R
import com.application.knc.Utils.UtilsFunction
import com.application.knc.databinding.LayoutCarpenterItemBinding
import com.application.knc.databinding.LayoutDealersItemBinding
import com.application.knc.model.Carpenter
import com.application.knc.model.Dealer

class CarpenterAdapter: RecyclerView.Adapter<CarpenterAdapter.CarpenterViewHolder>()
{
    private var carpenter = ArrayList<Carpenter>()

  class CarpenterViewHolder(val binding:LayoutCarpenterItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarpenterViewHolder{
        return  CarpenterViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.layout_carpenter_item,parent,false))
    }

    override fun getItemCount(): Int {
        if (carpenter.size==0)
        {
            return 5
        }
       return carpenter.size
    }

    override fun onBindViewHolder(holder: CarpenterViewHolder, position: Int)
    {
        if(carpenter.size==0)
        {
            holder.binding.showShimmer=true
        }else
        {
            holder.binding.showShimmer=false
            holder.binding.data = carpenter[position]
            holder.binding.textMobile.setOnClickListener{
                UtilsFunction.contactDialer(carpenter[position].mobile.toString())
            }
            holder.binding.cvProfile.setOnClickListener{
                carpenter[position].profile_image_url.let { ImageZoomer(holder.itemView.context, it).show() }
            }

        }
    }

    public fun submitList(carpenter: ArrayList<Carpenter>)
    {
        this.carpenter = carpenter
        notifyDataSetChanged()
    }
    public fun addList(carpenter: ArrayList<Carpenter>)
    {
        this.carpenter.addAll(carpenter)
        notifyItemRangeChanged(this.carpenter.size,carpenter.size)
    }
}