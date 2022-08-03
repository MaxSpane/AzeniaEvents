package com.example.azenia

data class StubhubData(
    val id: Int,
    val name: String,
    val events: List<StubhubData>,
    val children: List<StubhubData>,
    val venueName: String?,
    val city: String?,
    val price: Int?,
    val distanceFromVenue: Double?,
    val date: String?
)