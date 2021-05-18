package com.andromega.interviewmvpproject.modelclass


data class Venue(
    val allowMenuUrlEdit: Boolean,
    val categories: List<Category>,
    val hasPerk: Boolean,
    val id: String,
    val location: Location,
    val name: String,
    val referralId: String,
    val storeId: String,
    val url: String,
    val venueRatingBlacklisted: Boolean,
    val verified: Boolean
)