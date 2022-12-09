package com.lwt.realestatemanager.screens.simulator

import androidx.lifecycle.ViewModel
import com.lwt.realestatemanager.model.simulator.Loan
import com.lwt.realestatemanager.model.simulator.LoanResult
import com.lwt.realestatemanager.model.simulator.MonthlyPayment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.pow

class SharedViewModel: ViewModel() {
    //States for textFields
    private var _typedLoanAmount = MutableStateFlow("")
    val typedLoanAmount: StateFlow<String> get() = _typedLoanAmount
    private var _typedLoanInterest = MutableStateFlow("")
    val typedLoanInterest: StateFlow<String> get() = _typedLoanInterest
    private var _typedLoanPeriod = MutableStateFlow("")
    val typedLoanPeriod: StateFlow<String> get() = _typedLoanPeriod
    private var _typedLoanGracePeriod = MutableStateFlow("")
    val typedLoanGracePeriod: StateFlow<String> get() = _typedLoanGracePeriod
    private var _typedLoanInitialFee = MutableStateFlow("")
    val typedLoanInitialFee: StateFlow<String> get() = _typedLoanInitialFee
    //Loan type state
    private var _isAnnuity = MutableStateFlow(true)
    val isAnnuity: StateFlow<Boolean> get() = _isAnnuity
    //State for missed data
    private var _isFieldMissed  = MutableStateFlow(false)
    val isFieldMissed: StateFlow<Boolean> get() = _isFieldMissed
    //Loan calculation result
    private var _loanResult = MutableStateFlow(LoanResult())
    val loanResult: StateFlow<LoanResult> get() = _loanResult


    fun setLoanType(isAnnuity: Boolean){
        _isAnnuity.value = isAnnuity
    }
    fun swapLoanType(){
        _isAnnuity.value = !_isAnnuity.value
        _loanResult.value = LoanResult()
    }

    /*Next methods set value of typed data*/
    fun setTypedLoanAmount(value: String){
        _typedLoanAmount.value = value
    }
    fun setTypedLoanInterest(value: String){
        _typedLoanInterest.value = value
    }
    fun setTypedLoanPeriod(value: String){
        _typedLoanPeriod.value = value
    }
    fun setTypedLoanGracePeriod(value: String){
        _typedLoanGracePeriod.value = value
    }
    fun setTypedLoanInitialFee(value: String){
        _typedLoanInitialFee.value = value
    }

    /**Method validates that mandatory data is provided*/
    private fun isUserInputIsValid(): Boolean{
        _isFieldMissed.value = false
        return when{
            _typedLoanAmount.value.isEmpty() -> false
            _typedLoanInterest.value.isEmpty() -> false
            _typedLoanPeriod.value.isEmpty() -> false
            else -> true
        }
    }

    /**Method maps loan data from text fields into Loan model and
     * requests corresponding calculation type.*/
    fun mapLoanData(){
        if (!isUserInputIsValid()){
            _isFieldMissed.value = true
        } else{

            val gracePeriod = if (_typedLoanGracePeriod.value.isNotEmpty()) {
                _typedLoanGracePeriod.value.toInt()
            } else{
                0
            }
            val initialFee = if (_typedLoanInitialFee.value.isNotEmpty()) {
                _typedLoanInitialFee.value.toInt()
            } else{
                0
            }

            val loan = Loan(
                isAnnuityType = _isAnnuity.value,
                amount = _typedLoanAmount.value.toInt(),
                interest = _typedLoanInterest.value.toInt(),
                period = _typedLoanPeriod.value.toInt(),
                gracePeriod = gracePeriod,
                initialFee = initialFee,
                isInitialFeeInPercent = true
            )

            if (loan.isAnnuityType)
                estimateAsAnnuityLoan(loan)
            else
                estimateAsDifferentialLoan(loan)

        }
    }

    /**Method estimates payment schedule for annuity loan */
    private fun estimateAsAnnuityLoan(loan: Loan){
        val initialFee: Float = loan.amount * (loan.initialFee / 100f)
        var amount: Float = loan.amount - initialFee
        var gracePeriod = loan.gracePeriod
        val interest: Float = loan.interest / 100.0f

        var interestAmountOverall = 0.0f

        val monthlyPaymentList: ArrayList<MonthlyPayment> = ArrayList()

        if (loan.initialFee > 0){
            val monthlyPayment = MonthlyPayment(
                0, 0f, 0f,
                totalAmount = initialFee, remainder = amount
            )
            monthlyPaymentList.add(monthlyPayment)
        }



        val interestRatePerMonth: Float = interest / 12.0f

        var mainDebtInMonth: Float

        var interestDebtInMonth: Float

        var monthlyPayment: MonthlyPayment


        val generalAmountInMonth: Float = amount * (interestRatePerMonth + (interestRatePerMonth /
                ((1 + interestRatePerMonth).pow(loan.period - loan.gracePeriod) - 1)))


        for (i in 1..loan.period){

            interestDebtInMonth = amount * (interest / 12)

            interestAmountOverall += interestDebtInMonth

            if (gracePeriod > 0){

                monthlyPayment = MonthlyPayment(i, 0.0f,
                    interestDebtInMonth, interestDebtInMonth, amount)

                monthlyPaymentList.add(monthlyPayment)

                gracePeriod--
            } else{
                mainDebtInMonth = generalAmountInMonth - interestDebtInMonth
                amount -= mainDebtInMonth
                if (i == loan.period)
                    amount = 0.0f

                monthlyPayment = MonthlyPayment(i, mainDebtInMonth,
                    interestDebtInMonth, generalAmountInMonth, amount)
                monthlyPaymentList.add(monthlyPayment)
            }
        }

        //calculate the total payment amount
        val amountFinal: Float = loan.amount + interestAmountOverall
        val interestNet: Int = ((amountFinal / loan.amount - 1) * 100f).toInt()

        val loanResult = LoanResult(
            isAnnuityType = true,
            amountInitial = loan.amount.toFloat(),
            amountFinal = amountFinal,
            period = loan.period,
            interestGross = loan.interest,
            interestNet = interestNet,
            monthlyPayments = monthlyPaymentList
        )
        _loanResult.value = loanResult
    }

    /**Method estimates payment schedule for differential loan */
    private fun estimateAsDifferentialLoan(loan: Loan){
        val initialFee: Float = loan.amount * (loan.initialFee / 100f)
        var amount: Float = loan.amount - initialFee
        var gracePeriod = loan.gracePeriod
        val interest: Float = loan.interest / 100.0f

        var interestAmountOverall = 0.0f

        val monthlyPaymentList: ArrayList<MonthlyPayment> = ArrayList()

        if (loan.initialFee > 0){
            val monthlyPayment = MonthlyPayment(
                0, 0f, 0f,
                totalAmount = initialFee, remainder = amount
            )
            monthlyPaymentList.add(monthlyPayment)
        }


        val interestRatePerMonth: Float = interest / 12.0f
        var totalAmountInMonth: Float
        var interestAmountInMonth: Float
        var monthlyPayment: MonthlyPayment


        val mainDebtInMonth: Float = amount / (loan.period - loan.gracePeriod)


        for (i in 1..loan.period){
            interestAmountInMonth = amount * (interestRatePerMonth)
            interestAmountOverall += interestAmountInMonth
            if (gracePeriod > 0){

                monthlyPayment = MonthlyPayment(i, 0.0f,
                    interestAmountInMonth, interestAmountInMonth, amount)
                monthlyPaymentList.add(monthlyPayment)
                gracePeriod--
            } else{
                totalAmountInMonth = mainDebtInMonth + interestAmountInMonth
                amount -= mainDebtInMonth
                if (i == loan.period)
                    amount = 0.0f

                monthlyPayment = MonthlyPayment(i, mainDebtInMonth,
                    interestAmountInMonth, totalAmountInMonth, amount)
                monthlyPaymentList.add(monthlyPayment)
            }
        }
        //calculate the total payment amount
        val amountFinal: Float = loan.amount + interestAmountOverall
        val interestNet: Int = ((amountFinal / loan.amount - 1) * 100f).toInt()

        val loanResult = LoanResult(
            isAnnuityType = true,
            amountInitial = loan.amount.toFloat(),
            amountFinal = amountFinal,
            period = loan.period,
            interestGross = loan.interest,
            interestNet = interestNet,
            monthlyPayments = monthlyPaymentList
        )
        _loanResult.value = loanResult
    }
}