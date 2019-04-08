package com.example.devmobilecroymorin.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.devmobilecroymorin.R

class ResultFragment : Fragment() {

    val KEY_POSITION : String = "position"
    var jsonFile : String = ""
    var mContext : Context? = null

    fun newInstance(position : Int): ResultFragment{

        var sf = ResultFragment()

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
        return inflater.inflate(R.layout.result_fragment, null)
    }
}