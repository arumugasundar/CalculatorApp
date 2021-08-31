package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    var lastNumeric: Boolean = false
    var lastDot: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigit(v: View){
        textDisplay.append((v as Button).text)
        lastNumeric = true
    }

    fun onClear(v: View){
        textDisplay.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(v: View){
        if(lastNumeric && !lastDot){
            textDisplay.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(v: View){
        if(lastNumeric && !isOperatorAdded(textDisplay.text.toString())){
            textDisplay.append((v as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    private fun isOperatorAdded(value: String) : Boolean {
        return if(value.startsWith("-")){
                    false
                }else{
                    value.contains("/") || value.contains("*") || value.contains("+") || value.contains("-")
                }
    }

    fun onEqual(v: View){
        if(lastNumeric){
            var value = textDisplay.text.toString()
            var prefix = ""
            try{
                if(value.startsWith("-")){
                    prefix = "-"
                    value = value.substring(1)
                }

                if(value.contains("*")){
                    var splitValue = value.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }

                    textDisplay.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                } else if(value.contains("/")){
                    var splitValue = value.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }

                    textDisplay.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                } else if(value.contains("-")){
                    var splitValue = value.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }

                    textDisplay.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                } else if(value.contains("+")){
                    var splitValue = value.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }

                    textDisplay.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                }

            }catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String): String{
        var value = result
        if(result.contains(".0"))
            value = result.substring(0,result.length - 2)
        return value
    }
}