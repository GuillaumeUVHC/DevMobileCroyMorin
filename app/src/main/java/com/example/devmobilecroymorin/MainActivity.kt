package com.example.devmobilecroymorin

import android.app.AlertDialog
import android.os.Bundle
import android.os.Debug
import android.os.Environment
import android.os.StatFs
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.devmobilecroymorin.adapters.PageAdapter
import com.example.devmobilecroymorin.parser.Parser
import kotlinx.android.synthetic.main.activity_main.*
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import kotlinx.android.synthetic.main.popup.view.*
import java.io.File
import java.io.FileFilter
import java.util.regex.Pattern

class MainActivity : AppCompatActivity(){

    lateinit var m : Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Configuation du viewPager
        configureViewPager()
    }

    fun configureViewPager(){
        //Le view Pager permet de naviguer entre les différentes pages de notre application
        view_pager.adapter = PageAdapter(supportFragmentManager)
        activity_main_tabs.setupWithViewPager(view_pager)
        activity_main_tabs.tabMode = TabLayout.MODE_SCROLLABLE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //Menu de la action ar comportant le bouton "?"
        super.onCreateOptionsMenu(menu)
        val inflater = menuInflater
        m = menu
        inflater.inflate(R.menu.pop_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.hw_info -> {
                //Dans le Menu "m", on active tous les items dans le groupe d'identifiant "R.id.group2"

                /*  On récupère ici les informations concernant la mémoire RAM
                On obtient un pourcentage d'utilisation en divisant la mémoire utilisée par la mémoire totale*/
                val nativeHeapSize = Debug.getNativeHeapSize()                  //Mémoire totale
                val nativeHeapFreeSize = Debug.getNativeHeapFreeSize()          //Mémoire disponible
                val usedMemInBytes = nativeHeapSize - nativeHeapFreeSize        //Mémoire utilisée
                val usedMemInPercentage = usedMemInBytes * 100 / nativeHeapSize //Pourcentage de mémoire utilisée

                // On récupère grâce à la fonction définie ci-dessous le nombre de coeur du processeur
                val nbOfCore = getNumCores()

                // On récupère l'architecture du processeur grâce à la fonction system getProperty
                val cpuArchitecture = System.getProperty("os.arch")

                // On récupère la mémoire ROM disponible
                val stat = StatFs(Environment.getExternalStorageDirectory().path)
                val bytesAvailable = stat.blockSizeLong * stat.availableBlocksLong
                val megAvailable = bytesAvailable / (1024 * 1024)

                // On génère le layout pour la popup
                val messageView = layoutInflater.inflate(R.layout.popup, null, false)
                val builder = AlertDialog.Builder(this)
                    .setView(messageView)
                    .setTitle("Infos sytème")

                // On ajoute dans le textview les infos que l'on souhaite afficher dans la popup
                val popupText = messageView.findViewById<TextView>(R.id.popup_text)
                popupText.text = "Mémoire vive utilisée : $usedMemInPercentage% \n" +
                        "Nombre de coeurs du processeur : $nbOfCore \n" +
                        "Architecture du système : $cpuArchitecture\n" +
                        "Mémoire ROM disponible : $megAvailable MB"

                //On affiche la popup
                val mAlertDialog = builder.show()

                //On paramètre le bouton de sortie de la popup
                messageView.btn_validation.setOnClickListener {
                    mAlertDialog.dismiss()
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }



    // Fonction réalisée à partir de multiples recherches sur internet.
    fun getNumCores(): Int {
        //Classe permettant de récupérer les infos CPU dans le dossier listant les devices
        class CpuFilter : FileFilter {
            override fun accept(pathname: File): Boolean {
                //On vérifie que le fichier a bien le nom cpu + un chiffre (= nom d'un coeur)
                return Pattern.matches("cpu[0-9]+", pathname.name)
            }
        }

        try {
            //On récupère le dossier système contenant les infos processeur
            val dir = File("/sys/devices/system/cpu/")
            //On récupère que les fichiers définis dans la fonction "accept" ci-dessus
            val files = dir.listFiles(CpuFilter())
            //On retourne le nombre de coeurs qui correspond au nombre de fichiers récupérés ci-dessus
            return files.size
        } catch (e: Exception) {
            //Si erreur on retourne un seul coeur (ça n'existe plus les processeurs à un coeur donc facilement repérable)
            return 1
        }

    }

}
