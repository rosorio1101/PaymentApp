package com.rosorio.mercadopago.domain.entity

import java.io.Serializable

data class PayerCost(
    val installments: Int,
    val installmentRate: Double,
    val discountRate: Double,
    val reimbursementRate: Double,
    val labels: List<String>,
    val installmentRateCollector: List<String>,
    val minAllowedAmount: Int,
    val maxAllowedAmount: Double,
    val recommendedMessage: String,
    val installmentAmount: Double,
    val totalAmount: Double
): Serializable