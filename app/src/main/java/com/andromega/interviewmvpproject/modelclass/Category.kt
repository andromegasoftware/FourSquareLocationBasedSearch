package com.andromega.interviewmvpproject.modelclass


import com.google.gson.annotations.SerializedName

data class Category(
    val id: String,
    val name: String,
    val pluralName: String,
    val primary: Boolean,
    val shortName: String
)