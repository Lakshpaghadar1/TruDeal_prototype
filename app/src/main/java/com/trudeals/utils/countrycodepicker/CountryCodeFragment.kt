package com.trudeals.utils.countrycodepicker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.trudeals.R
import com.trudeals.databinding.CountrycodeFragmentSelectCountryBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment

open class CountryCodeFragment : BaseFragment<CountrycodeFragmentSelectCountryBinding>() {

    var list = ArrayList<CountryWithFlag>()
    var listFull = ArrayList<CountryWithFlag>()

    var countryAdapter: CountryAdapter? = null

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun bindData() {
        hideKeyBoard()
        binding.toolBarTitle.text = getString(R.string.label_country_code)

        list.addAll(CountryCodeUtil.parseCountryCode(requireActivity()))
        listFull.addAll(CountryCodeUtil.parseCountryCode(requireActivity()))

        countryAdapter = CountryAdapter(requireContext(), list, object : CountryAdapter.CallBack {
            override fun onItemClick(position: Int) {
                val bundle = Bundle()
                bundle.putString(CountryCodeConstants.COUNTRY_CODE, list[position].dialCode)
                if (arguments?.containsKey("other") == true) {
                    bundle.putString("other", requireArguments().getString("other"))
                }
                activity?.apply {
                    val intent = Intent()
                    intent.putExtras(bundle)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            }
        })

        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                list.clear()
                list.addAll(listFull.filter { country ->
                    country.name.toLowerCase()
                        .contains(binding.editTextSearch.text.toString().toLowerCase())
                })
                if (countryAdapter != null) {
                    countryAdapter!!.notifyDataSetChanged()
                }
            }
        })

        binding.recyclerViewMyActivitySession.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        binding.recyclerViewMyActivitySession.adapter = countryAdapter

        binding.toolBarBack.visibility = View.VISIBLE
        binding.toolBarBack.setOnClickListener { onViewClick(it) }
        binding.imageViewSearch.setOnClickListener { onViewClick(it) }
    }

    override fun onViewClick(view: View) {
        super.onViewClick(view)
        when (view) {
            binding.toolBarBack -> {
                activity?.onBackPressed()
            }
            binding.imageViewSearch -> {
                binding.imageViewSearch.isSelected = !binding.imageViewSearch.isSelected
                TransitionManager.beginDelayedTransition(binding.linearToolbar)
                if (binding.imageViewSearch.isSelected) {
                    binding.toolBarTitle.visibility = View.GONE
                    binding.editTextSearch.visibility = View.VISIBLE
                    binding.editTextSearch.requestFocus()
                    showKeyBoard()
                } else {
                    hideKeyBoard()
                    binding.editTextSearch.setText("")
                    binding.editTextSearch.visibility = View.GONE
                    binding.toolBarTitle.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): CountrycodeFragmentSelectCountryBinding {
        return CountrycodeFragmentSelectCountryBinding.inflate(layoutInflater)
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(false).build()
    }
}
