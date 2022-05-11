package com.bawp.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    private var tvInput: TextView? = null //variable to input the numbers in input field
    var lastNum: Boolean = true  // var to check if previous input was a number or not
    var lastDot: Boolean = true  // var to check if previuos input was a decimal or not
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput = findViewById(R.id.tvInput)
    }
    // Inputting the numbers we press on the input bar
    fun Ondigit(view: View){
        tvInput?.append((view as Button).text)
        lastNum = true
        lastDot = false
    }
    // Defining the CLR button which clears the input bar
    fun OnClear(view: View){
        tvInput?.text = ""
    }
    //Inputs decimal in i/p bar, if the last input was decimal then decimal wont be pressed again.
    fun OnDecimalPoint(view: View){
        if(lastNum && !lastDot){
            tvInput?.append(".")
            lastDot = true                 // lastDot true after putting  decimal in i/p field
            lastNum = false
        }
    }
    // if there isnt any operator till now in the input bar then whatever we click gets added in input bar and lastNum is true
    fun onOperator(view: View){
        // as tvInput is nullable so we use let operation here to access
        tvInput?.text?.let {
            if(lastNum && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastDot = false
                lastNum = false
            }
        }
    }
    //defining the function of equal button in the calculator.
    fun onEqual(view: View){
        if(lastNum) {
            var tvValue = tvInput?.text.toString()
            var prefix = ""
            try {
                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if(tvValue.contains("-")){
                    val splitValue = tvValue.split("-")

                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one;
                    }
                   tvInput?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())


                }else if(tvValue.contains("+")){
                    val splitValue = tvValue.split("+")

                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one;
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())


                }else if(tvValue.contains("/")){
                    val splitValue = tvValue.split("/")

                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one;
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())


                } else if(tvValue.contains("*")){
                    val splitValue = tvValue.split("*")

                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one;
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())


                }


            } catch (e: ArithmeticException) { //exceptional arithmetic operations eg dividing by zero etc
                e.printStackTrace()
            }
        }
    }
    private fun removeZeroAfterDot(result: String) : String {
        var value = result;
        if(result.contains(".0")){
            value = result.substring(0, result.length-2)
        }
        return value
    }
    // check whether an operator is present in the input bar or an operator is pressed
    private fun isOperatorAdded(value : String) : Boolean {
        return if(value.startsWith("-")){      // if minus is at starting then ignore it
            false
        } else {
            value.contains("+")
                    || value.contains("-")
                    || value.contains("*")
                    || value.contains("/")

        }

    }

}