package com.example.navigationcomponenttest.model

class LoadDataException : Exception()

class DuplicateException(
    val duplicatedValue: String,
) : Exception("The list can't contain duplicated items")