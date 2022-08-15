package com.example.ulkelerinbayraklarmvvmcoroutineretrofit.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.ulkelerinbayraklarmvvmcoroutineretrofit.R
import com.example.ulkelerinbayraklarmvvmcoroutineretrofit.databinding.ItemCountryBinding
import com.example.ulkelerinbayraklarmvvmcoroutineretrofit.model.Country
import com.example.ulkelerinbayraklarmvvmcoroutineretrofit.util.gorselIndır
import com.example.ulkelerinbayraklarmvvmcoroutineretrofit.util.placeHolderYap
import com.example.ulkelerinbayraklarmvvmcoroutineretrofit.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.fragment_country.view.*
import kotlinx.android.synthetic.main.item_country.view.*

class CountryAdapter(val countryList : ArrayList<Country>) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(),CountryClickListener {
    class CountryViewHolder(val view:ItemCountryBinding) : RecyclerView.ViewHolder(view.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        //val view=inflater.inflate(R.layout.item_country,parent,false)
        val view=DataBindingUtil.inflate<ItemCountryBinding>(inflater,R.layout.item_country,parent,false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.view.country=countryList.get(position)
        holder.view.listener=this
      /*
        holder.itemView.name.text=countryList.get(position).countryName
        holder.itemView.region.text=countryList.get(position).countryRegion
        holder.itemView.imageView.gorselIndır(countryList.get(position).imageUrl!!, placeHolderYap(holder.itemView.context))
        holder.itemView.setOnClickListener {
            val navigate=FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList.get(position).uuid)
            Navigation.findNavController(it).navigate(navigate)

        }

       */
    }

    override fun getItemCount(): Int {
       return countryList.size
    }

    fun updateCountryList(newCount:List<Country>){
        countryList.clear()
        countryList.addAll(newCount)
        notifyDataSetChanged()
    }

    override fun onCountryClicked(v: View) {
        val navigate=FeedFragmentDirections.actionFeedFragmentToCountryFragment(v.countryUUidText.text.toString().toInt())
        Navigation.findNavController(v).navigate(navigate)
    }
}