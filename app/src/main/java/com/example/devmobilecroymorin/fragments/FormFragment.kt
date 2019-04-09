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
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.devmobilecroymorin.R
import com.example.devmobilecroymorin.extendedFormView.ExtEditText
import com.example.devmobilecroymorin.extendedFormView.ExtRadioGroup
import com.example.devmobilecroymorin.extendedFormView.ExtSwitch
import com.example.devmobilecroymorin.parser.Element
import com.example.devmobilecroymorin.parser.JsonData
import com.example.devmobilecroymorin.parser.Result
import com.example.devmobilecroymorin.parser.Service
import com.example.devmobilecroymorin.viewModel.SharedViewModel
import kotlinx.android.synthetic.main.form_fragment.*
import kotlinx.android.synthetic.main.form_fragment.view.*
import kotlinx.android.synthetic.main.list_item.*
import kotlinx.serialization.json.Json
import org.json.JSONStringer


class FormFragment : Fragment() {

    val KEY_POSITION : String = "position"
    var jsonFile : String = ""
    var mContext : Context? = null
    var servicesList : List<Service> = emptyList()
    var elementsList : List<Element> = emptyList()
    var currentServiceIndex : Int = 0
    lateinit var myJsonData : JsonData


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
        return inflater.inflate(R.layout.form_fragment, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (mContext != null) {
            jsonFile = mContext!!.assets.open("service.json").bufferedReader().readText()
            myJsonData = Json.parse(JsonData.serializer(), jsonFile)
            servicesList = myJsonData.services
        }

        activity?.let {
            val sharedViewModel = ViewModelProviders.of(it).get(SharedViewModel::class.java)

            sharedViewModel.inputNumber.observe(this, Observer {
                it?.let {
                    currentServiceIndex = it
                    updateForm()
                }
            })
        }
    }

    fun addTextEdit(id : Int,value : String){
        var textEdit : ExtEditText = ExtEditText(value, context)
        textEdit.hint = value
        textEdit.id = id
        scrollViewLayout.addView(textEdit)
    }

    fun addRadioGroup(id : Int, values : List<String>){
        var radioGroup : ExtRadioGroup = ExtRadioGroup("RadioGroup",context)

        values.forEach { v ->
            var radioButton : RadioButton = RadioButton(context)
            radioButton.text = v
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
        var switch : ExtSwitch = ExtSwitch(value,context)
        switch.text = value
        scrollViewLayout.addView(switch)
    }

    fun addButton(id : Int, value: String){
        var button : Button = Button(context)
        button.text = value
        scrollViewLayout.addView(button)
    }

    fun addElement(id : Int, e : Element){
        when (e.type){
            "edit" -> addTextEdit(id, e.value[0])
            "radioGroup" -> addRadioGroup(id, e.value)
            "label" -> addLabel(e.value[0])
            "switch" -> addSwitch(id, e.section)
            "button" -> addButton(id, e.value[0])
        }
    }

    fun updateForm(){
        scrollViewLayout.removeAllViews()
        elementsList = myJsonData.services[currentServiceIndex].elements

        elementsList.forEach {e ->
            addElement(100, e)
        }
        addSubmitButton()
    }

    fun addSubmitButton(){
        val button : Button = Button(context)
        button.text ="S'inscrire"
        button.setOnClickListener {
            Log.i("SUBMIT", "COUCOU")
            saveUserData()
        }
        scrollViewLayout.addView(button)

    }

    fun saveUserData() {
        var element: View
        var i: Int = 0
        var resultList : ArrayList<Result> = arrayListOf()


        while (i < scrollViewLayout.childCount) {
            Log.i("SUBMIT", "child $i ${scrollViewLayout.getChildAt(i)}")

            if(scrollViewLayout.getChildAt(i) is ExtEditText) {
                var editText: ExtEditText = scrollViewLayout.getChildAt(i) as ExtEditText
                resultList.add(Result(editText.title, editText.text.toString()))
            }

            if(scrollViewLayout.getChildAt(i) is ExtRadioGroup){
                var radioGroup : ExtRadioGroup = scrollViewLayout.getChildAt(i) as ExtRadioGroup

                var radioButton : RadioButton? = scrollViewLayout.findViewById(radioGroup.checkedRadioButtonId)

                if (radioButton != null) {
                    resultList.add(Result("", radioButton.text as String))
                }else{
                    resultList.add(Result("", ""))
                }

            }

            if(scrollViewLayout.getChildAt(i) is Switch){
                var switch : Switch = scrollViewLayout.getChildAt(i) as Switch
                resultList.add(Result(switch.text as String, switch.isChecked.toString()))
            }

            i++
        }

        Log.i("Results", resultList.toString())

    }


}