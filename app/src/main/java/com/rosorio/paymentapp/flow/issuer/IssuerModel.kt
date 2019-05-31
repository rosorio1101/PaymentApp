package com.rosorio.paymentapp.flow.issuer

import com.rosorio.mercadopago.domain.entity.Issuer

data class IssuerModel(
    val related: Issuer,
    var selected: Boolean = false
) {
    companion object {
        fun from(issuer: Issuer) = IssuerModel(issuer)

        fun from(issuers: List<Issuer>) = issuers.map { from(it) }
    }
}