package pt.programandocomandre.contactagenda.events

import pt.programandocomandre.contactagenda.models.Contact

interface ContactActionButtonOnClickListener {
    fun onClick(contact: Contact, position: Int)
}