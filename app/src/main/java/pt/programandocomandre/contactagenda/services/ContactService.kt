package pt.programandocomandre.contactagenda.services

import pt.programandocomandre.contactagenda.daos.ContactDao
import pt.programandocomandre.contactagenda.models.Contact
import pt.programandocomandre.contactagenda.services.dtos.CreateContactRequest

class ContactService(val contactDao: ContactDao) {
    fun listContacts(): List<Contact> {


        return contactDao.getAllContacts()
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