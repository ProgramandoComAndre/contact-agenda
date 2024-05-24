package pt.programandocomandre.contactagenda.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pt.programandocomandre.contactagenda.R
import pt.programandocomandre.contactagenda.databinding.ActivityCreateContactBinding
import pt.programandocomandre.contactagenda.databinding.ActivityMainBinding
import pt.programandocomandre.contactagenda.services.ContactService
import pt.programandocomandre.contactagenda.services.dtos.CreateContactRequest

class CreateContactActivity : AppCompatActivity() {
    private val viewBinding by lazy { ActivityCreateContactBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        viewBinding.addContactAddContactBtn.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val email = if(viewBinding.addContactEmailAddressEt.text.toString()
                            .isNotEmpty()
                    ) {
                        viewBinding.addContactEmailAddressEt.text.toString()
                    }else {
                        null
                    }
                    val mobilePhone = if(viewBinding.addContactPhoneNumberEt.text.toString()
                            .isNotEmpty()
                    ) {
                        viewBinding.addContactPhoneNumberEt.text.toString()
                    }
                    else {
                        null
                    }

                    withContext(Dispatchers.IO) {

                        ContactService.provideService().createContact(CreateContactRequest(viewBinding.addContactNameEt.text.toString(), email, mobilePhone))

                    }

                    runOnUiThread {
                        val intent = Intent()
                        setResult(RESULT_OK,intent)
                        finish()
                    }
                }
                catch (ex:Exception)
                {
                    runOnUiThread {
                        Toast.makeText(
                            this@CreateContactActivity,
                            ex.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
}