package pt.programandocomandre.contactagenda.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import pt.programandocomandre.contactagenda.models.Contact

@Dao
interface ContactDao{
    @Query("SELECT * FROM contacts")
    fun getAllContacts(): List<Contact>

    @Query("SELECT * FROM contacts WHERE id=:contactId")
    fun getContactById(contactId: Int): Contact

    @Update
    fun updateContacts(vararg contacts: Contact)

    @Delete
    fun deleteContact(vararg contact: Contact)
}