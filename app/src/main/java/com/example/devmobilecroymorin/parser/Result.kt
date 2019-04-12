package com.example.devmobilecroymorin.parser

import kotlinx.serialization.Serializable

@Serializable
data class Result(val entry : String, val value: String)