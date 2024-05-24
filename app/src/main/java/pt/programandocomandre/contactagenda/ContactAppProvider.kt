package pt.programandocomandre.contactagenda

import android.app.Application
import pt.programandocomandre.contactagenda.services.ContactService

class ContactAppProvider: Application() {
    override fun onCreate() {
        super.onCreate()
        val database = ContactsDatabase.getDatabase(this)
        ContactService.provideService(database.contactsDao())
    }
}