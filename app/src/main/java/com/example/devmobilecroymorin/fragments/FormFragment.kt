package com.example.devmobilecroymorin.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.devmobilecroymorin.R
import com.example.devmobilecroymorin.viewModel.SharedViewModel
import kotlinx.android.synthetic.main.form_fragment.*
import kotlinx.android.synthetic.main.list_item.*


class FormFragment : Fragment() {

    val KEY_POSITION : String = "position"
    var jsonFile : String = ""
    var mContext : Context? = null


    fun newInstance(position : Int): FormFragment{

        var sf = FormFragment()

        val args : Bundle = Bundle()
        args.putInt(KEY_POSITION, position)
        sf.arguments = args

        return sf
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.let {
            val sharedViewModel = ViewModelProviders.of(it).get(SharedViewModel::class.java)

            sharedViewModel.inputNumber.observe(this, Observer {
                it?.let {
                    textView.text = it.toString()
                }
            })
        }


        return inflater.inflate(R.layout.form_fragment, null)
    }






}