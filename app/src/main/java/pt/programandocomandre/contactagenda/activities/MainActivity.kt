package pt.programandocomandre.contactagenda.activities

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.programandocomandre.contactagenda.ContactsDatabase
import pt.programandocomandre.contactagenda.R
import pt.programandocomandre.contactagenda.daos.ContactDao
import pt.programandocomandre.contactagenda.databinding.ActivityMainBinding
import pt.programandocomandre.contactagenda.models.Contact

class MainActivity : AppCompatActivity() {

    val viewBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val contactDao by lazy { ContactsDatabase.getDatabase(applicationContext).contactsDao() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)


        lifecycleScope.launch(Dispatchers.IO) {
            contactDao.insertContact(Contact(0, "Andre", "andrerafael112@gmail.com", "123456789"))
        }

    }
}