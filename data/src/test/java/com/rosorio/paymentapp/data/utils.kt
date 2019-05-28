package com.rosorio.paymentapp.data

import com.rosorio.mercadopago.domain.entity.*

fun createPayment(id: String) = PaymentMethod(
    id,
    "name",
    "paymentTypeId",
    "status",
    "secureThumbnail",
    "thumbnail",
    "defferedCapture",
    listOf(
        Settings(
            CardNumber("validation", 0),
            Bin(
                "pattern",
                "installmentsPattern",
                null
            ),
            SecurityCode(
                0,
                "cardLocation",
                "mode"
            )
        )
    ),
    listOf(
        ""
    ),
    1.0,
    1.0,
    1,
    listOf(),
    listOf("")

)

fun createIssuer(id: String) = Issuer(
    id,
    "name",
    "secureThumbnail",
    "thumbnail",
    "processingMode",
    "merchantAccountId"
)

fun createInstallment(id: String, issuer: Issuer) = Installment(
    paymentMethodId = id,
    paymentTypeId = "paymentTypeId",
    issuer = issuer,
    processingMode = "processingMode",
    merchantAccountId = "merchantAccountId",
    payerCost = listOf(),
    agreements = "agreements"
)