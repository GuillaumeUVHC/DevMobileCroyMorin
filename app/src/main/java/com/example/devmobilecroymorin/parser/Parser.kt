package com.example.devmobilecroymorin.parser

import android.content.Context
import android.util.Log
import kotlinx.serialization.json.JSON
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileNotFoundException

/* Classe permettante d'extraire les informations du service.json
* */
class Parser() {

    //Deprecated
    fun parseJSON(fileContent: String) : JsonData {
        return Json.parse(JsonData.serializer(), fileContent)
    }


    fun jsonUserList(u : UserList) : String{
        //Mettre la liste des users sous forme de JSON pour l'enregistrer
        var jsonData = Json.stringify(UserList.serializer(), u)
        return jsonData
    }

    fun saveUserList(u : UserList, path : String?){
        //Ecriture dans le fichier
        var file : File = File(path)
        var fileContents = ""

        fileContents = Parser().jsonUserList(u)
        file.writeText(fileContents)
    }

    fun readUserList(path : String?) : UserList {
        //Lecture des users sauvagardés
        var file: File = File(path)
        var fileContents = ""
        var u: UserList = UserList(arrayListOf())

        try {
            fileContents = file.readText()
            if (!fileContents.isEmpty()) {
                u = Json.parse(UserList.serializer(), fileContents)
            }
        }catch (e : FileNotFoundException){
            Log.i("PARSER","Le fichier de log n'existe pas")
        }

        return u
    }

}