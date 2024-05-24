package pt.programandocomandre.contactagenda.activities

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pt.programandocomandre.contactagenda.ContactsDatabase
import pt.programandocomandre.contactagenda.R
import pt.programandocomandre.contactagenda.adapters.ContactsRecylerViewAdapter
import pt.programandocomandre.contactagenda.daos.ContactDao
import pt.programandocomandre.contactagenda.databinding.ActivityMainBinding
import pt.programandocomandre.contactagenda.events.ContactActionButtonOnClickListener
import pt.programandocomandre.contactagenda.models.Contact
import pt.programandocomandre.contactagenda.services.ContactService

class MainActivity : AppCompatActivity() {

    val viewBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val registerActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, "Contact inserted successfully", Toast.LENGTH_LONG).show()
            loadContacts() // Carrega os contatos novamente após a inserção bem-sucedida
        }
    }

    val editActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, "Contact edited successfully", Toast.LENGTH_LONG).show()
            loadContacts() // Carrega os contatos novamente após a inserção bem-sucedida
        }
    }

    private lateinit var adapter: ContactsRecylerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        viewBinding.mainContactListRv.layoutManager = LinearLayoutManager(this).apply {
            this.orientation = LinearLayoutManager.VERTICAL
        }

        adapter = ContactsRecylerViewAdapter(
            mutableListOf(),
            this,
            deleteContactActionButtonOnClickListener,
            editContactActionButtonOnClickListener
        )

        viewBinding.mainContactListRv.adapter = adapter

        viewBinding.mainAddContactBtn.setOnClickListener {
            registerActivityResultLauncher.launch(Intent(this, CreateContactActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        loadContacts()
    }

    private fun loadContacts() {
        lifecycleScope.launch {
            val contactList = withContext(Dispatchers.IO) {
                ContactService.provideService().listContacts()
            }

            adapter.updateContacts(contactList)
        }
    }

    private val deleteContactActionButtonOnClickListener = object : ContactActionButtonOnClickListener {
        override fun onClick(contact: Contact, position: Int) {
            val dialog = AlertDialog.Builder(this@MainActivity)
                .setMessage("Are you sure you want to remove this contact? This can't be undone!!")
                .setPositiveButton("Yes") { dialog, which ->
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            ContactService.provideService().deleteContact(contact)
                        }
                        loadContacts()
                    }
                }
                .setNegativeButton("No") { dialog, which ->
                    dialog.cancel()
                }
                .create()
            dialog.show()
        }
    }

    private val editContactActionButtonOnClickListener = object : ContactActionButtonOnClickListener {
        override fun onClick(contact: Contact, position: Int) {
            val intent = Intent(this@MainActivity, EditContactActivity::class.java)
            intent.putExtra("contact", contact)
            editActivityResultLauncher.launch(intent)
        }
    }
}
