package com.aldev.calculator

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aldev.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val TAG = "MainActivity"

    //private var resultStatus = false

    // represent error state
    //private var stateError = false

    // represent last value is number
    //private var stateNumeric = false

    // represent last value is operator
    //private var stateOperator = false

    // represent last value is dot
    //private var stateDot = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvFormula.isSelected = true

        binding.btnClear.setOnClickListener { clearAll() }
        binding.btnDelete.setOnClickListener { delete() }
        binding.btn9.setOnClickListener { addNumberToText(getString(R.string._9)) }
        binding.btn8.setOnClickListener { addNumberToText(getString(R.string._8)) }
        binding.btn7.setOnClickListener { addNumberToText(getString(R.string._7)) }
        binding.btn6.setOnClickListener { addNumberToText(getString(R.string._6)) }
        binding.btn5.setOnClickListener { addNumberToText(getString(R.string._5)) }
        binding.btn4.setOnClickListener { addNumberToText(getString(R.string._4)) }
        binding.btn3.setOnClickListener { addNumberToText(getString(R.string._3)) }
        binding.btn2.setOnClickListener { addNumberToText(getString(R.string._2)) }
        binding.btn1.setOnClickListener { addNumberToText(getString(R.string._1)) }
        binding.btn0.setOnClickListener { addNumberToText(getString(R.string._0)) }

        binding.btnDivide.setOnClickListener { addOperatorToText(getString(R.string.divide)) }
        binding.btnMultiply.setOnClickListener { addOperatorToText("*") }
        binding.btnPlus.setOnClickListener { addOperatorToText(getString(R.string.plus)) }
        binding.btnMinus.setOnClickListener { addOperatorToText(getString(R.string.minus)) }

        binding.btnDot.setOnClickListener { addDotToText() }

        binding.btnEqual.setOnClickListener { calculateValue(binding.tvFormula.text.toString()) }
    }

    private fun clearAll() {
        binding.tvFormula.text = ""
        binding.tvHistory.text = ""
        viewModel.clearAll()
    }

    private fun delete() {
        if (binding.tvFormula.text.isNotEmpty()) {
            val text = binding.tvFormula.text.toString()
            binding.tvFormula.text = text.substring(0, text.length - 1)
        }
    }

    private fun clearFormula() {
        binding.tvFormula.text = ""
        viewModel.clearFormula()
    }

    private fun addNumberToText(input: String) {
        when (viewModel.addNumberToText()) {
            "formula" -> clearFormula()
            "all" -> clearAll()
        }
        binding.tvFormula.append(input)
    }

    private fun addOperatorToText(input: String) {
        if (viewModel.addOperatorToText()) {
            binding.tvFormula.append(input)
        }
    }

    private fun addDotToText() {
        if (viewModel.addDotToText()) {
            binding.tvFormula.append(getString(R.string.dot))
        }
    }

    private fun formatNumberIntoThousandFormat(input: String) {

    }

    private fun calculateValue(value: String) {
        when (val result = viewModel.calculateValue(value)) {
            "" -> return
            "Error" -> binding.tvFormula.text = result
            else -> {
                binding.tvHistory.text = value
                binding.tvFormula.text = result
            }
        }
    }
}