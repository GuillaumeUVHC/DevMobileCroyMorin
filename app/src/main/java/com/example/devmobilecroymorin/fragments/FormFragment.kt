package com.example.devmobilecroymorin.fragments

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.devmobilecroymorin.R
import com.example.devmobilecroymorin.parser.Element
import com.example.devmobilecroymorin.parser.JsonData
import com.example.devmobilecroymorin.parser.Service
import com.example.devmobilecroymorin.viewModel.SharedViewModel
import kotlinx.android.synthetic.main.form_fragment.*
import kotlinx.android.synthetic.main.form_fragment.view.*
import kotlinx.android.synthetic.main.list_item.*
import kotlinx.serialization.json.Json


class FormFragment : Fragment() {

    val KEY_POSITION : String = "position"
    var jsonFile : String = ""
    var mContext : Context? = null
    var servicesList : List<Service> = emptyList()
    var elementsList : List<Element> = emptyList()
    var currentServiceIndex : Int = 0
    lateinit var formLayout : ConstraintLayout


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

        if (mContext != null){
            jsonFile = mContext!!.assets.open("service.json").bufferedReader().readText()
            val myJsonData : JsonData = Json.parse(JsonData.serializer(), jsonFile)
            servicesList = myJsonData.services
            elementsList = myJsonData.services[currentServiceIndex].elements
        }


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.let {
            val sharedViewModel = ViewModelProviders.of(it).get(SharedViewModel::class.java)

            sharedViewModel.inputNumber.observe(this, Observer {
                it?.let {
                    currentServiceIndex = it
                    textView.text = currentServiceIndex.toString()
                }
            })
        }

        return inflater.inflate(R.layout.form_fragment, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        addTextEdit(700,"TEST")
        addTextEdit(701,"zieojfiojzefoijeziof")

        addRadioGroup(702, listOf("HOMME", "FEMME"))
        addLabel("TEST")
        addSwitch(703, "SWITCH")



    }

    fun addTextEdit(id : Int,value : String){
        var textEdit : EditText = EditText(context)
        textEdit.hint = value
        textEdit.id = id
        scrollViewLayout.addView(textEdit)
    }

    fun addRadioGroup(id : Int, values : List<String>){
        var radioGroup : RadioGroup = RadioGroup(context)

        values.forEach { v ->
            var radioButton : RadioButton = RadioButton(context)
            radioButton.setText(v)
            radioGroup.addView(radioButton)
        }
        radioGroup.orientation = LinearLayout.HORIZONTAL
        radioGroup.id = id
        scrollViewLayout.addView(radioGroup)
    }

    fun addLabel(value : String){
        var label : TextView = TextView(context)
        label.text = "$value :"
        label.setTypeface(null, Typeface.BOLD)
        scrollViewLayout.addView(label)
    }

    fun addSwitch(id : Int, value: String){
        var switch : Switch = Switch(context)
        switch.text = value
        scrollViewLayout.addView(switch)
    }

    fun addSection(sectionName : String){

    }








}