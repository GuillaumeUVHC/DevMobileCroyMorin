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
import com.example.devmobilecroymorin.extendedFormView.ExtCheckBox
import com.example.devmobilecroymorin.extendedFormView.ExtEditText
import com.example.devmobilecroymorin.extendedFormView.ExtRadioGroup
import com.example.devmobilecroymorin.extendedFormView.ExtSwitch
import com.example.devmobilecroymorin.parser.*
import com.example.devmobilecroymorin.viewModel.SharedViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.form_fragment.*
import kotlinx.serialization.json.Json
import java.io.*


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

        //Recupération des des services
        if (mContext != null) {
            jsonFile = mContext!!.assets.open("service.json").bufferedReader().readText()
            myJsonData = Json.parse(JsonData.serializer(), jsonFile)
            servicesList = myJsonData.services
        }

        updateForm()

        activity?.let {
            val sharedViewModel = ViewModelProviders.of(it).get(SharedViewModel::class.java)

            //Recuperation depuis le shared viewModel de l'indice du service selectionné
            sharedViewModel.inputNumber.observe(this, Observer {
                it?.let {
                    currentServiceIndex = it
                    updateForm()
                }
            })
        }
    }

    //Ajout d'un champs de saisie texte
    fun addTextEdit(mandatory : Boolean,value : String){
        var textEdit : ExtEditText = ExtEditText(mandatory, value , context)
        textEdit.hint = value
        scrollViewLayout.addView(textEdit)
    }

    //Ajout d'un radio group et de ses radio buttons
    fun addRadioGroup(mandatory : Boolean, values : List<String>){
        var radioGroup : ExtRadioGroup = ExtRadioGroup(mandatory,"RadioGroup",context)

        values.forEach { v ->
            var radioButton : RadioButton = RadioButton(context)
            radioButton.text = v
            radioGroup.addView(radioButton)
        }
        radioGroup.orientation = LinearLayout.HORIZONTAL
        scrollViewLayout.addView(radioGroup)
    }

    //Ajout d'un Label
    fun addLabel(value : String){
        var label : TextView = TextView(context)
        label.text = "$value :"
        label.setTypeface(null, Typeface.BOLD)
        scrollViewLayout.addView(label)
    }

    //Ajout d'un switch
    fun addSwitch(mandatory : Boolean, value: String){
        var switch : ExtSwitch = ExtSwitch(mandatory, value,context)
        switch.text = value
        scrollViewLayout.addView(switch)
    }

    //ajout d'un button (checkbox)
    fun addButton(mandatory : Boolean, value: String){
        var button : ExtCheckBox = ExtCheckBox(mandatory, value, context)
        button.text = value
        scrollViewLayout.addView(button)
    }

    //ajout d'un elément en fonction de son type précisé dans le service.json
    fun addElement(mandatory : Boolean, e : Element){
        when (e.type){
            "edit" -> addTextEdit(mandatory, e.value[0])
            "radioGroup" -> addRadioGroup(mandatory, e.value)
            "label" -> addLabel(e.value[0])
            "switch" -> addSwitch(mandatory, e.section)
            "button" -> addButton(mandatory, e.value[0])
        }
    }

    //création de tous les champs du formulaire du service
    fun updateForm(){
        updateImage()
        scrollViewLayout.removeAllViews()
        elementsList = myJsonData.services[currentServiceIndex].elements

        elementsList.forEach {e ->
            addElement(e.mandatory.toBoolean(), e)
        }
        addSubmitButton()
    }

    //Ajout du bouton de validation + listener
    fun addSubmitButton(){
        val button : Button = Button(context)
        button.text ="S'inscrire"
        button.setOnClickListener {
            saveUserData()
        }
        scrollViewLayout.addView(button)
    }

    //sauvegarde d'un utilsiateur (infos de champs)
    fun saveUserData() {
        var element: View
        var i: Int = 0
        var resultList : ArrayList<Result> = arrayListOf()
        var canSave : Boolean = true

        resultList.add(Result("Service", servicesList[currentServiceIndex].title))


        while (i < scrollViewLayout.childCount) {

            //Log.i("SUBMIT", "child $i ${scrollViewLayout.getChildAt(i)}")

            if(scrollViewLayout.getChildAt(i) is ExtEditText) {
                var editText: ExtEditText = scrollViewLayout.getChildAt(i) as ExtEditText

                if (mandatoryFilled(editText)){
                    resultList.add(Result(editText.title, editText.text.toString()))
                }else{
                    canSave = false
                }
            }

            if(scrollViewLayout.getChildAt(i) is ExtRadioGroup){
                var radioGroup : ExtRadioGroup = scrollViewLayout.getChildAt(i) as ExtRadioGroup

                var radioButton : RadioButton? = scrollViewLayout.findViewById(radioGroup.checkedRadioButtonId)

                if (radioButton == null && radioGroup.mandatory) {
                    canSave = false
                }else if (radioButton != null){
                    resultList.add(Result(radioGroup.entry, radioButton.text as String))
                }else{
                    resultList.add(Result(radioGroup.entry, "NO_DATA"))
                }

            }

            if(scrollViewLayout.getChildAt(i) is ExtSwitch){
                var switch : ExtSwitch = scrollViewLayout.getChildAt(i) as ExtSwitch
                resultList.add(Result(switch.entry, switch.isChecked.toString()))
            }

            if(scrollViewLayout.getChildAt(i) is ExtCheckBox){
                var cBox : ExtCheckBox = scrollViewLayout.getChildAt(i) as ExtCheckBox
                resultList.add(Result(cBox.entry, cBox.isChecked.toString()))
            }

            i++
        }

        if (canSave) {
            val user: UserData = UserData(resultList)
            var savedData : UserList = Parser().readUserList(context!!.externalCacheDir?.path + "myfile.txt")
            savedData.userList.add(user)


            Parser().saveUserList(savedData, context!!.externalCacheDir?.path + "myfile.txt")


        }else{
            Toast.makeText(context,"Un champ obligatoire n'est pas rempli",Toast.LENGTH_SHORT).show()
        }

    }

    fun updateImage(){
        //Affichage de l'image en haut du formulaire grâce à picasso
        val picasso = Picasso.Builder(context!!)
            .listener { _, _, e -> e.printStackTrace() }
            .build()
        picasso.setIndicatorsEnabled(true)

        var imageUrl : String = servicesList[currentServiceIndex].elements[0].value[0]

        picasso.load(imageUrl).into(imageView)
    }

    //Verification que le champs en parametre est rempli si il est obligatoire
    fun mandatoryFilled(view : View) : Boolean{
        if(view is ExtEditText) {
            if (view.mandatory){
                return !(view.text.isBlank())
            }else{
                return true
            }
        }

        if(view is ExtRadioGroup){
            if (view.mandatory){
                return (view.checkedRadioButtonId != null)
            }else{
                return true
            }

        }

        if(view is ExtSwitch){
            return true
        }

        if(view is ExtCheckBox){
            return true
        }

        return false
    }
}