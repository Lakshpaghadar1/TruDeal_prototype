package com.trudeals.utils.countrycodepicker

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.databinding.CountryCodeItemSelectCountryBinding
import com.trudeals.utils.extension.isVisible

class CountryAdapter(
    internal var context: Context,
    internal var countries: List<CountryWithFlag>,
    private val callBack: CallBack
) : RecyclerView.Adapter<CountryAdapter.DataHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        return DataHolder(
            CountryCodeItemSelectCountryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
//        Glide.with(context).load(countries[position].flag).into(holder.imageCountry)
        holder.binding.imageCountry.text = countries[position].flag
        holder.binding.textViewCountryCode.text = countries[position].dialCode
        holder.binding.textViewCountryName.text = countries[position].name

        holder.binding.root.setOnClickListener { callBack.onItemClick(position) }
        holder.binding.view.isVisible(position != itemCount - 1)
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    interface CallBack {
        fun onItemClick(position: Int)

    }

    inner class DataHolder(val binding: CountryCodeItemSelectCountryBinding) :
        RecyclerView.ViewHolder(binding.root)
}