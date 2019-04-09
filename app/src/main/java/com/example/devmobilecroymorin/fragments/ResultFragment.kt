package com.example.devmobilecroymorin.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.devmobilecroymorin.R
import com.example.devmobilecroymorin.adapters.ServiceAdapter
import com.example.devmobilecroymorin.adapters.userDataAdapter
import com.example.devmobilecroymorin.parser.Parser
import com.example.devmobilecroymorin.parser.Service
import com.example.devmobilecroymorin.parser.UserData
import com.example.devmobilecroymorin.parser.UserList
import kotlinx.android.synthetic.main.service_fragment.*

class ResultFragment : Fragment() {

    val KEY_POSITION : String = "position"
    var jsonFile : String = ""
    var mContext : Context? = null
    var resultList : UserList = UserList(arrayListOf())

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        resultList = Parser().readUserList(context!!.externalCacheDir?.path + "myfile.txt")
        Log.i("CREATED", "RESULT")

        var uAdapter = userDataAdapter(this.context!!, 0 , resultList.userList )
        list.adapter = uAdapter
    }
}