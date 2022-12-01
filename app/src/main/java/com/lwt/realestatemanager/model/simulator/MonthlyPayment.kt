
package com.lwt.realestatemanager.model.simulator

/**Monthly payment model*/
data class MonthlyPayment(
    val month: Int,
    /**Principal payment amount*/
    val mainDebtAmount: Float,
    /**Interest payment amount*/
    val interestAmount: Float,
    /**Total payment (principal + interest)*/
    val totalAmount: Float,
    /**Remaining payment amount*/
    val remainder: Float
)