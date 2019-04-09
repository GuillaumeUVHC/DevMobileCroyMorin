package com.example.devmobilecroymorin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.devmobilecroymorin.R
import com.example.devmobilecroymorin.parser.Service
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class ServiceAdapter(context: Context, var resource: Int, var services: ArrayList<Service>) :
    ArrayAdapter<Service>(context, resource, services) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var listService : View? = convertView

        if(listService == null) {
            listService = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }

        val currentService : Service = services[position]

        var textView = listService!!.findViewById<TextView>(R.id.textView_name)
        textView.text = currentService.title

        var imageView : ImageView = listService!!.findViewById<ImageView>(R.id.imageView_icon)


        val picasso = Picasso.Builder(context)
            .listener { _, _, e -> e.printStackTrace() }
            .build()
        picasso.setIndicatorsEnabled(true)

        var imageUrl : String = currentService.elements[0].value[0]

        picasso.load(imageUrl).fit().into(imageView)

        return listService
    }


}