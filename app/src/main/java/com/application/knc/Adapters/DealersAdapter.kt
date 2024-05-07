package com.application.knc.Adapters
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.knc.Activities.Carpenters.CarpenterActivity
import com.application.knc.Dailogs.ImageZoomer
import com.application.knc.R
import com.application.knc.Utils.UtilsFunction
import com.application.knc.databinding.LayoutDealersItemBinding
import com.application.knc.model.Dealer

class DealersAdapter: RecyclerView.Adapter<DealersAdapter.DealerViewHolder>()
{
    private var dealers = ArrayList<Dealer>()

  class DealerViewHolder(val binding:LayoutDealersItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealerViewHolder {
        return  DealerViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.layout_dealers_item,parent,false))
    }

    override fun getItemCount(): Int {
        if (dealers.size==0)
        {
            return 5
        }
       return dealers.size
    }

    override fun onBindViewHolder(holder: DealerViewHolder, position: Int)
    {
        if(dealers.size==0)
        {
            holder.binding.showShimmer=true
        }else
        {
            holder.binding.showShimmer=false
            holder.binding.data = dealers[position]
            holder.binding.cvProfile.setOnClickListener{
                dealers[position].profile_image_url?.let { ImageZoomer(holder.itemView.context, it).show() }
            }
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context,CarpenterActivity::class.java)
                intent.putExtra("dealerId",dealers[position].id.toString())
                holder.itemView.context.startActivity(intent)
            }
            holder.binding.textMobile.setOnClickListener{
                UtilsFunction.contactDialer(dealers[position].mobile.toString())
            }
        }
    }

    public fun submitList(dealers: ArrayList<Dealer>)
    {
        this.dealers = dealers
        notifyDataSetChanged()
    }
    public fun addList(dealers: ArrayList<Dealer>)
    {
        this.dealers.addAll(dealers)
        notifyItemRangeChanged(this.dealers.size,dealers.size)
    }
}