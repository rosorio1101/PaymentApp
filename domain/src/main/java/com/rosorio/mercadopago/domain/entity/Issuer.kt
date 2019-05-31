package com.rosorio.mercadopago.domain.entity

import java.io.Serializable

data class Issuer(
    val id: String,
    val name: String,
    val secureThumbnail: String,
    val thumbnail: String,
    val processingMode: String,
    val merchantAccountId: String
): Serializable