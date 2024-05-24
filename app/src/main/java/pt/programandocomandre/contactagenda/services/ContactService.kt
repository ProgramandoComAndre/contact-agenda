package pt.programandocomandre.contactagenda.services

import android.util.Log
import pt.programandocomandre.contactagenda.daos.ContactDao
import pt.programandocomandre.contactagenda.models.Contact
import pt.programandocomandre.contactagenda.services.dtos.CreateContactRequest
import pt.programandocomandre.contactagenda.services.dtos.EditContactRequest

class ContactService(val contactDao: ContactDao) {
    fun listContacts(): List<Contact> {


        return contactDao.getAllContacts()
    }

    fun deleteContact(contact: Contact): Boolean {

        return try {
            contactDao.deleteContact(contact)
            true
        } catch(ex: Exception) {
            Log.e("deleteContact", ex.message, ex)
            false
        }
    }

    fun editContact(request: EditContactRequest, contact: Contact) {
        contact.email = request.email
        contact.name = request.name
        contact.phoneNumber = request.phoneNumber
        contactDao.updateContacts(contact)
    }

    fun createContact(createContactRequest: CreateContactRequest): Contact {
        val contact = Contact(0,  createContactRequest.name, createContactRequest.email, createContactRequest.phoneNumber)
        contactDao.insertContact(contact)

        return contact
    }

    companion object {
        private var Instance : ContactService? = null
        fun provideService(contactDao: ContactDao): ContactService {
            return Instance?: ContactService(contactDao).also {
                Instance = it
            }
        }

        fun provideService() : ContactService {
            if(Instance == null) {
                throw Exception("Missing DAO")
            }

            return Instance!!
        }
    }
}