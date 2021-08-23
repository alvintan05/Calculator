package com.aldev.calculator

import android.util.Log
import androidx.lifecycle.ViewModel
import net.objecthunter.exp4j.ExpressionBuilder

class MainViewModel: ViewModel() {

    private var resultStatus = false

    // represent error state
    private var stateError = false

    // represent last value is number
    private var stateNumeric = false

    // represent last value is operator
    private var stateOperator = false

    // represent last value is dot
    private var stateDot = false

    fun addNumberToText(): String {
        if (resultStatus) {
            clearFormula()
            resultStatus = false
            return "formula"
        }

        if (stateError) {
            clearAll()
            stateError = false
            return "all"
        }

        stateNumeric = true
        stateOperator = false

        return ""
    }

    fun addOperatorToText(): Boolean {
        if (stateNumeric && !stateOperator) {
            stateOperator = true
            resultStatus = false
            stateDot = false
            return true
        }
        return false
    }

    fun addDotToText(): Boolean {
        if (stateNumeric && !stateDot) {
            stateDot = true
            resultStatus = false
            return true
        }
        return false
    }

    fun calculateValue(value: String): String {
        if (stateOperator || value.isEmpty() || resultStatus) {
            return ""
        }

        val expression = ExpressionBuilder(value).build()

        return try {
            val calculationResult = expression.evaluate().toBigDecimal().stripTrailingZeros()

            resultStatus = true
            stateNumeric = true
            stateOperator = false

            stateDot = calculationResult.toPlainString().contains(".")

            calculationResult.toPlainString()
        } catch (e: ArithmeticException) {
            stateError = true
            "Error"
        }
    }

    fun clearAll() {
        resultStatus = false
        stateOperator = false
        stateNumeric = true
        stateDot = false
    }

    fun clearFormula() {
        stateDot = false
    }

}