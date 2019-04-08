package com.example.devmobilecroymorin.parser

import kotlinx.serialization.Serializable

@Serializable
data class JsonData(
    val services: List<Service>
)

@Serializable
data class Service(
    val elements: List<Element>,
    val title: String
)

@Serializable
data class Element(
    val mandatory: String,
    val section: String,
    val type: String,
    val value: List<String>
)