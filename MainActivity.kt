package com.example.calci

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.ArithmeticException
import net.objecthunter.exp4j.ExpressionBuilder


class MainActivity : AppCompatActivity() {
    lateinit var txtInput: TextView

    var lastNumeric:Boolean=false

    var stateError:Boolean=false

    var lastDot:Boolean=false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.txtInput =findViewById(R.id.txtInput)
    }

    fun onDigit(view: View){
        if(stateError){
            txtInput.text = (view as Button).text // If current state is Error, replace the error message
            stateError = false
        }
        else {
            txtInput.append((view as Button).text)
        }
        lastNumeric =true
    }
    fun onDecimalPoint(view: View){
        if (lastNumeric && !stateError && !lastDot){
            txtInput.append(".")
            lastNumeric = false
            lastDot = true

        }

    }
    fun onOperator(view: View){
        if (lastNumeric && !stateError){
            txtInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false

        }

    }

    fun onClear(view: View){
        this.txtInput.text =""
        lastNumeric= false
        lastDot =false
        stateError=false

    }

    fun onEqual(view: View){
        if(lastNumeric && !stateError){
            val txt = txtInput.text.toString()
            val expression =  ExpressionBuilder(txt).build()
            try {
                val result = expression.evaluate()
                txtInput.text = result.toString()
                lastDot = true

            }
            catch (ex:ArithmeticException){
                txtInput.text="Error"
                stateError = true
                lastNumeric =false
            }
        }
    }
}