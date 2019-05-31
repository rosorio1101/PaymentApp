package com.rosorio.mercadopago.domain.entity

import java.io.Serializable

data class PaymentMethod(
    val id: String,
    val name: String,
    val paymentTypeId: String,
    val status: String,
    val secureThumbnail: String,
    val thumbnail: String,
    val deferredCapture: String,
    val settings: List<Settings>,
    val additionalInfoNeeded: List<String>,
    val minAllowedAmount: Double,
    val maxAllowedAmount: Double,
    val accreditationTime: Int,
    val financialInstitutions: List<String>,
    val processingModes: List<String>
): Serializable