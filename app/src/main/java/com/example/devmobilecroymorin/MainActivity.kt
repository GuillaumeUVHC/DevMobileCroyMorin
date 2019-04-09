package com.example.devmobilecroymorin

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.devmobilecroymorin.adapters.PageAdapter
import com.example.devmobilecroymorin.parser.Parser
import kotlinx.android.synthetic.main.activity_main.*
import com.example.devmobilecroymorin.R
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem


class MainActivity : AppCompatActivity(){

    lateinit var m : Menu

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
        activity_main_tabs.setupWithViewPager(view_pager)
        activity_main_tabs.tabMode = TabLayout.MODE_SCROLLABLE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        val inflater = menuInflater
        //R.menu.menu est l'id de notre menu
        m = menu
        inflater.inflate(R.menu.pop_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.hw_info -> {
                //Dans le Menu "m", on active tous les items dans le groupe d'identifiant "R.id.group2"
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
