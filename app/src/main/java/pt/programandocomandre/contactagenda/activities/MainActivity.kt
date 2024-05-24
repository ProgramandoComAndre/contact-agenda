package pt.programandocomandre.contactagenda.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

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
import pt.programandocomandre.contactagenda.models.Contact
import pt.programandocomandre.contactagenda.services.ContactService

class MainActivity : AppCompatActivity() {

    val viewBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val registerActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, "Contact inserted successfully", Toast.LENGTH_LONG).show()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        viewBinding.mainContactListRv.layoutManager = LinearLayoutManager(this).apply {
            this.orientation= LinearLayoutManager.VERTICAL
        }

        viewBinding.mainAddContactBtn.setOnClickListener {
            registerActivityResultLauncher.launch(Intent(this, CreateContactActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        var contactList:List<Contact> = mutableListOf()
        lifecycleScope.launch {
            contactList = withContext(Dispatchers.IO) {
                ContactService.provideService().listContacts()
            }

            runOnUiThread {
                viewBinding.mainContactListRv.adapter =
                    ContactsRecylerViewAdapter(contactList, this@MainActivity)
            }
        }




    }
}