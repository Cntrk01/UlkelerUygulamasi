package com.example.ulkelerinbayraklarmvvmcoroutineretrofit.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ulkelerinbayraklarmvvmcoroutineretrofit.R
import com.example.ulkelerinbayraklarmvvmcoroutineretrofit.adapter.CountryAdapter
import com.example.ulkelerinbayraklarmvvmcoroutineretrofit.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.fragment_feed.*


class FeedFragment : Fragment() {
    private lateinit var viewModel:FeedViewModel
    private val countryAdapter=CountryAdapter(arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vieww=inflater.inflate(R.layout.fragment_feed, container, false)
        // Inflate the layout for this fragment
        return vieww
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(this).get(FeedViewModel::class.java)
        viewModel.refreshData()

        countryList.layoutManager=LinearLayoutManager(context)
        countryList.adapter=countryAdapter

        swipeRefreshLayout.setOnRefreshListener {
            countryList.visibility=View.GONE
            countryError.visibility=View.GONE
            countryLoading.visibility=View.VISIBLE
            viewModel.getDataFromApi()
            swipeRefreshLayout.isRefreshing=false
        }

        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.country.observe(requireActivity(), Observer {
            it?.let {
                countryList.visibility=View.VISIBLE
                countryAdapter.updateCountryList(it)
            }
        })
        viewModel.countryError.observe(requireActivity(), Observer {
            it?.let {
                if(it){
                    countryError.visibility=View.VISIBLE
                }else{
                    countryError.visibility=View.GONE
                }
            }
        })
        viewModel.countryLoading.observe(requireActivity(), Observer {
            it?.let {
                if(it){
                    countryLoading.visibility=View.VISIBLE
                    countryError.visibility=View.GONE
                    countryList.visibility=View.GONE
                }else{
                    countryLoading.visibility=View.GONE
                }
            }
        })
    }

}