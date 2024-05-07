package com.application.knc.model

data class DealersData(
    val current_page: Int,
    val `data`: ArrayList<Dealer>,
    val per_page: Int
)