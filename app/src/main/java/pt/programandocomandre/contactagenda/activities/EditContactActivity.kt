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
import pt.programandocomandre.contactagenda.databinding.ActivityEditContactBinding
import pt.programandocomandre.contactagenda.models.Contact
import pt.programandocomandre.contactagenda.services.ContactService
import pt.programandocomandre.contactagenda.services.dtos.CreateContactRequest
import pt.programandocomandre.contactagenda.services.dtos.EditContactRequest

class EditContactActivity : AppCompatActivity() {
    private val viewBinding by lazy { ActivityEditContactBinding.inflate(layoutInflater) }
    private lateinit var contact: Contact
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        contact = intent.getParcelableExtra("contact")!!

        viewBinding.editContactNameEt.setText(contact.name)
        viewBinding.editContactEmailAddressEt.setText(contact.email)
        viewBinding.editContactPhoneNumberEt.setText(contact.phoneNumber)

        viewBinding.editContactAddContactBtn.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val email = if(viewBinding.editContactEmailAddressEt.text.toString()
                            .isNotEmpty()
                    ) {
                        viewBinding.editContactEmailAddressEt.text.toString()
                    }else {
                        null
                    }
                    val mobilePhone = if(viewBinding.editContactPhoneNumberEt.text.toString()
                            .isNotEmpty()
                    ) {
                        viewBinding.editContactPhoneNumberEt.text.toString()
                    }
                    else {
                        null
                    }

                    withContext(Dispatchers.IO) {

                        ContactService.provideService().editContact(EditContactRequest(viewBinding.editContactNameEt.text.toString(), email, mobilePhone), contact)

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
                            this@EditContactActivity,
                            ex.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
}