package com.example.devmobilecroymorin.fragments

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.example.devmobilecroymorin.R
import com.example.devmobilecroymorin.adapters.ServiceAdapter
import com.example.devmobilecroymorin.parser.JsonData
import com.example.devmobilecroymorin.parser.Service
import com.example.devmobilecroymorin.viewModel.SharedViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.service_fragment.*
import kotlinx.serialization.json.Json

class ServicesFragment : Fragment() {

    val KEY_POSITION : String = "position"
    var jsonFile : String = ""
    var mContext : Context? = null
    var selectedItem : Int = 0
    lateinit var sharedViewModel : SharedViewModel
    var servicesList : List<Service> = emptyList()

    fun newInstance(position : Int): ServicesFragment{

        var sf = ServicesFragment()

        val args : Bundle = Bundle()
        args.putInt(KEY_POSITION, position)
        sf.arguments = args

        return sf
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        activity?.let {
            sharedViewModel = ViewModelProviders.of(it).get(SharedViewModel::class.java)
        }

        val serviceList : ArrayList<Service> = ArrayList<Service>()
        if (mContext != null){
            jsonFile = mContext!!.assets.open("service.json").bufferedReader().readText()
            val myJsonData : JsonData = Json.parse(JsonData.serializer(), jsonFile)
            servicesList = myJsonData.services
        }

        var sAdapter = ServiceAdapter(this.context!!, 0 , servicesList as ArrayList<Service>)
        list.adapter = sAdapter

        Log.i("LISTENER", "HELLOOOOO")

        list.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->

                selectedItem = i
                sharedViewModel?.inputNumber?.postValue(i)
                Log.i("LISTENER", "ON POS $selectedItem")
    }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.service_fragment, null)

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }



}