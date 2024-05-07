package com.application.knc.Adapters
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.knc.R
import com.application.knc.databinding.LayoutIntroProductItemBinding

class IntroProductsAdapter: RecyclerView.Adapter<IntroProductsAdapter.IntroProductViewHolder>()
{

    class IntroProductViewHolder(private val binding:LayoutIntroProductItemBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroProductViewHolder {
        return  IntroProductViewHolder(
            DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_intro_product_item,
            parent,
            false
        ))
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: IntroProductViewHolder, position: Int) {

    }


}