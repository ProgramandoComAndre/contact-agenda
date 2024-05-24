package pt.programandocomandre.contactagenda.services.dtos

class EditContactRequest(name:String, email: String?, phoneNumber: String?): CreateContactRequest(name, email, phoneNumber) {
}