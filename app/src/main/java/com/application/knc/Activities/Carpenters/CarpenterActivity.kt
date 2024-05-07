package com.application.knc.Activities.Carpenters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.application.knc.Activities.Dealers.DealersViewModel
import com.application.knc.Adapters.CarpenterAdapter
import com.application.knc.Adapters.DealersAdapter
import com.application.knc.R
import com.application.knc.databinding.ActivityCarpenterBinding

class CarpenterActivity : AppCompatActivity(),View.OnClickListener {

    private  val binding by lazy {
        ActivityCarpenterBinding.inflate(layoutInflater)
    }

    private  val adapter by lazy {
        CarpenterAdapter()
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[CarpenterViewModel::class.java]
    }

    private var page = 1
    private var per_page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        intializer()
    }

    private fun intializer() {

        binding.inclTitleBar.imgBack.setOnClickListener(this)
        binding.rvCarpenters.adapter = adapter
        binding.llNestedScroll.viewTreeObserver.addOnScrollChangedListener {
            val view =
                binding.llNestedScroll.getChildAt(binding.llNestedScroll.childCount - 1) as View
            val diff: Int =
                view.bottom - (binding.llNestedScroll.height + binding.llNestedScroll.scrollY)
            if (diff == 0 && adapter.itemCount == per_page * page && !binding.isPageProgress!!)
            {
                binding.isPageProgress = true
                viewModel.getCarpenters(true, page, dealerId = intent.getStringExtra("dealerId").toString())
                binding.llNestedScroll.post { binding.llNestedScroll.fullScroll(View.FOCUS_DOWN) }
            }
        }
        setObserver()
    }

    private fun setObserver()
    {
        viewModel.carpenterData.observe(this){
            binding.isPageProgress=false
            per_page = it.data.per_page
            page = it.data.current_page
            if (it.status)
            {
                if (it.data.current_page > 1) {
                    adapter.addList(it.data.data)
                } else {
                    adapter.submitList(it.data.data)
                }
            } else
            {
                if (it.data.current_page == 1)
                    adapter.submitList(it.data.data)
            }
            if (0 < it.data.data.size)
            {
                binding.clNoItemFound.clLayout.visibility = View.GONE

            } else if (page == 1 && 0 == it.data.data.size)
            {
                binding.clNoItemFound.clLayout.visibility = View.VISIBLE
            }
        }
        viewModel.getCarpenters(dealerId = intent.getStringExtra("dealerId").toString())
    }

    override fun onClick(p0: View?) {
        when(p0?.id)
        {
            R.id.imgBack -> finish()
        }
    }
}