package com.example.ulkelerinbayraklarmvvmcoroutineretrofit.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ulkelerinbayraklarmvvmcoroutineretrofit.R
import com.example.ulkelerinbayraklarmvvmcoroutineretrofit.databinding.FragmentCountryBinding
import com.example.ulkelerinbayraklarmvvmcoroutineretrofit.util.gorselIndır
import com.example.ulkelerinbayraklarmvvmcoroutineretrofit.util.placeHolderYap
import com.example.ulkelerinbayraklarmvvmcoroutineretrofit.viewmodel.CountryViewModel
import kotlinx.android.synthetic.main.fragment_country.*


class CountryFragment : Fragment() {
    private var countryId=0
    private lateinit var viewModel:CountryViewModel
    private lateinit var dataBinding:FragmentCountryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_country, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            countryId=CountryFragmentArgs.fromBundle(it).countryUuid
        }

        viewModel=ViewModelProvider(this).get(CountryViewModel::class.java)
        viewModel.getDataRoom(countryId)
        observerLiveData()
    }
    private fun observerLiveData() {
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer {country->
            country?.let {
                dataBinding.selectedCountry= country
               /*
                countryName.text=it.countryName
                countryCapital.text=it.countryCapital
                countryCurrency.text=it.countryCurrency
                countryLanguage.text=it.countryLanguage
                countryRegion.text=it.countryRegion
                context?.let {
                    countryImage.gorselIndır(country.imageUrl!!, placeHolderYap(it))
                }
                */
            }
        })
    }
}