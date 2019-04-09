package com.example.devmobilecroymorin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.devmobilecroymorin.R
import com.example.devmobilecroymorin.parser.Result
import com.example.devmobilecroymorin.parser.UserData

class userDataAdapter(context: Context, var resource: Int, var userDatas: ArrayList<UserData>) :
    ArrayAdapter<UserData>(context, resource, userDatas) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var userDataList : View? = convertView

        if(userDataList == null) {
            userDataList = LayoutInflater.from(context).inflate(R.layout.user_data_layout, parent, false)
        }

        val currentUserData : UserData = userDatas[position]
        var currentUserResults : ArrayList<Result> = currentUserData.data

        var textView = userDataList!!.findViewById<TextView>(R.id.textView4)

        var text : String = ""


        for(r : Result in currentUserResults){
                text = "$text${r.entry} = ${r.value} \n"

        }

        textView.text = text

        return userDataList
    }


}