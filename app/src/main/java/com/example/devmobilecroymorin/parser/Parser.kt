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

    fun parseJSON(fileContent: String) : JsonData {

        return Json.parse(JsonData.serializer(), fileContent)

    }

    fun jsonUserList(u : UserList) : String{

        var test : List<Int> = listOf(1,2,3)

        var jsonData = Json.stringify(UserList.serializer(), u)
        Log.i("SAVE", jsonData)
        return jsonData
    }

    fun saveUserList(u : UserList, path : String?){
        var file : File = File(path)
        var fileContents = ""

        fileContents = Parser().jsonUserList(u)
        file.writeText(fileContents)
    }

    fun readUserList(path : String?) : UserList {
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

        return u;
    }

}