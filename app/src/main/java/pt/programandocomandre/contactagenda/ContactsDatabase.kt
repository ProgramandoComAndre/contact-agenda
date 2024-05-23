package pt.programandocomandre.contactagenda

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pt.programandocomandre.contactagenda.daos.ContactDao
import pt.programandocomandre.contactagenda.models.Contact


@Database(entities = [Contact::class], version = 1)
abstract class ContactsDatabase: RoomDatabase() {
    abstract fun contactsDao(): ContactDao

    companion object {
        @Volatile
        private var Instance: ContactsDatabase? = null

        fun getDatabase(context: Context): ContactsDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, ContactsDatabase::class.java, "contactsDatabase").build().also { Instance= it }
            }
        }
    }
}