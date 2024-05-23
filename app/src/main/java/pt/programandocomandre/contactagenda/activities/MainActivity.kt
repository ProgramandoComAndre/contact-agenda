package pt.programandocomandre.contactagenda.activities

import android.os.Bundle

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

class MainActivity : AppCompatActivity() {

    val viewBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val contactDao by lazy { ContactsDatabase.getDatabase(applicationContext).contactsDao() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        viewBinding.mainContactListRv.layoutManager = LinearLayoutManager(this).apply {
            this.orientation= LinearLayoutManager.VERTICAL
        }




    }

    override fun onResume() {
        super.onResume()
        var contactList:List<Contact> = mutableListOf()
        lifecycleScope.launch {
            contactList = withContext(Dispatchers.IO) {
                contactDao.getAllContacts()
            }

            runOnUiThread {

                viewBinding.mainContactListRv.adapter = ContactsRecylerViewAdapter(contactList, this@MainActivity)
            }


        }




    }
}