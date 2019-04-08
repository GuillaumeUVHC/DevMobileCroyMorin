package com.example.devmobilecroymorin.parser

import kotlinx.serialization.json.Json

/* Classe permettante d'extraire les informations du service.json
* */
class Parser() {

    fun parseJSON(fileContent: String) : JsonData {

        return Json.parse(JsonData.serializer(), fileContent)

    }
}