package com.rosorio.mercadopago.domain.entity

data class Issuer(
    val id: String,
    val name: String,
    val secureThumbnail: String,
    val thumbnail: String,
    val processingMode: String,
    val merchantAccountId: String
)