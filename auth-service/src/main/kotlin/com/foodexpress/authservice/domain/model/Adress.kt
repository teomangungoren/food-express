package com.foodexpress.authservice.domain.model

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import org.jetbrains.annotations.NotNull

@Embeddable
data class Adress(
    @NotNull
    @Column(nullable = false)
    val street:String,
    @NotNull
    @Column(nullable = false)
    val zipcode:String,
    @NotNull
    @Column(nullable = false)
    val city:String
)