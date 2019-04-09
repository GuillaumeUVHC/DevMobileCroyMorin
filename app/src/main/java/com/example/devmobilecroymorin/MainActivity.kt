package com.example.devmobilecroymorin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.devmobilecroymorin.adapters.PageAdapter
import com.example.devmobilecroymorin.parser.Parser
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Instantiate parser
        val myParser = Parser()
        //Parse Json file from assets
        val myJsonFile : String = assets.open("service.json").bufferedReader().readText()
        //val myJsonData : JsonData = Json.parse(JsonData.serializer(), myJsonFile)

        //Log.i("DATA", myJsonData.toString())

        //Add bottomNavigation
        //this.configBottomNaviagtionMenu()

        //Adding services list to bundle
        //val bundle : Bundle = Bundle()
        //bundle.putString("jsonFile", myJsonFile)

        //Add ServiceFragment to view
        /*var ft : FragmentTransaction = supportFragmentManager.beginTransaction()
        var sf : ServicesFragment = ServicesFragment()
        //Passing bundle to fragment
        sf.arguments = bundle
        ft.add(R.id.fragment_container, sf)
        ft.commit()*/

        //Send Json

        configureViewPager()
    }

    /*
    CONFIG
     */

    /*fun configBottomNaviagtionMenu(){
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem -> updateMainFragment(menuItem.itemId) }
    }*/

    fun updateMainFragment(itemId : Int) : Boolean {

        when (itemId){
            R.id.action_services -> showServices()
            R.id.action_form -> showForm()
            else -> Log.i("WHEN", "ELSE")
        }

        return true
    }

    fun showServices(){

    }

    fun showForm(){
        Log.i("WHEN", "Showing form")
    }

    fun configureViewPager(){
        view_pager.adapter = PageAdapter(supportFragmentManager)
    }

    /*override fun onFragmentInteraction(uri: Uri) {
    }*/


}
