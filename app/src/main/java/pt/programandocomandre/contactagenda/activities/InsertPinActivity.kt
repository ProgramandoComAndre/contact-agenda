package pt.programandocomandre.contactagenda.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.forEachIndexed
import pt.programandocomandre.contactagenda.AuthUtils
import pt.programandocomandre.contactagenda.DataUtils
import pt.programandocomandre.contactagenda.R
import pt.programandocomandre.contactagenda.databinding.ActivityCreateContactBinding
import pt.programandocomandre.contactagenda.databinding.ActivityInsertPinBinding

class InsertPinActivity : AppCompatActivity() {
    lateinit var pin: String
    private val viewBinding by lazy { ActivityInsertPinBinding.inflate(layoutInflater) }
    var pos = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        readFromSharedPreferences()
        viewBinding.insertPinFirstRowLl.forEachIndexed { index, view ->

            view.setOnClickListener {
                updatePin(it)
            }

        }

        viewBinding.insertPinSecondRowLl.forEachIndexed { index, view ->
            view.setOnClickListener {
                updatePin(it)
            }
        }

        viewBinding.insertPinThirdRowLl.forEachIndexed { index, view ->
            view.setOnClickListener {
                updatePin(it)
            }
        }

        viewBinding.insertPinFourthRowLl.forEachIndexed { index, view ->
            view.setOnClickListener {
                updatePin(it)
            }
        }
    }


    fun updatePin(view: View) {
        val btn = view as Button
        if(btn.text == "OK") {
            var firstNumber = viewBinding.insertPinFirstNumberV.tag as String?
            val secondNumber = viewBinding.insertPinSecondNumberV.tag as String?
            val thirdNumber = viewBinding.insertPinThirdNumberV.tag as String?
            val fourthNumber = viewBinding.insertPinFourthNumberV.tag as String?

            if(firstNumber.isNullOrEmpty() || secondNumber.isNullOrEmpty() || thirdNumber.isNullOrEmpty() || fourthNumber.isNullOrEmpty()) {
                Toast.makeText(this, "Wrong pin", Toast.LENGTH_LONG).show()
                return
            }

            val confirmPin = "${firstNumber}${secondNumber}${thirdNumber}${fourthNumber}"

            if(pin == confirmPin) {

                    var intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()

            }
            else {
                Toast.makeText(this, "Wrong pin", Toast.LENGTH_LONG).show()
            }
        }
        else if(btn.text == "CE") {
            if(pos == 5) {

                viewBinding.insertPinFourthNumberV.isEnabled = false
                viewBinding.insertPinFourthNumberV.tag = null
                pos = 4
            }
            else if(pos == 4) {

                viewBinding.insertPinThirdNumberV.isEnabled = false
                viewBinding.insertPinThirdNumberV.tag = null
                pos = 3
            }
            else if(pos == 3) {

                viewBinding.insertPinSecondNumberV.isEnabled = false
                viewBinding.insertPinSecondNumberV.tag = null
                pos = 2

            }
            else {
                viewBinding.insertPinFirstNumberV.isEnabled = false
                viewBinding.insertPinFirstNumberV.tag = null
                pos = 1
            }
        }
        else {
            if(pos == 1) {

                viewBinding.insertPinFirstNumberV.isEnabled = true
                viewBinding.insertPinFirstNumberV.tag = view.text
                pos = 2
            }
            else if(pos == 2) {

                viewBinding.insertPinSecondNumberV.isEnabled = true
                viewBinding.insertPinSecondNumberV.tag = view.text
                pos = 3
            }
            else if(pos == 3) {

                viewBinding.insertPinThirdNumberV.isEnabled = true
                viewBinding.insertPinThirdNumberV.tag = view.text
                pos = 4
            }
            else {
                viewBinding.insertPinFourthNumberV.isEnabled = true
                viewBinding.insertPinFourthNumberV.tag = view.text
                pos = 5
            }
        }
    }
    fun readFromSharedPreferences() {
        val sharedPreferences = DataUtils.getSharedPreferences(this)
        DataUtils.writePin("1234", this)
        pin = sharedPreferences.getString("pin", "")!!
        if(pin == "") {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}