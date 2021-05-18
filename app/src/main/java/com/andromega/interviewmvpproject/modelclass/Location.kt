package com.andromega.interviewmvpproject.modelclass


data class Location(
    val address: String,
    val cc: String,
    val city: String,
    val country: String,
    val crossStreet: String,
    val distance: Int,
    val formattedAddress: List<String>,
    val lat: Double,
    val lng: Double,
    val neighborhood: String,
    val postalCode: String,
    val state: String
)