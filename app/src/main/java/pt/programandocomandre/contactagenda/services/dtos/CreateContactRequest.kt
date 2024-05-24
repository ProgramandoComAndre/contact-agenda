package pt.programandocomandre.contactagenda.services.dtos

import android.util.Patterns
import pt.programandocomandre.contactagenda.services.errors.AtLeastOneFieldFilledException
import pt.programandocomandre.contactagenda.services.errors.InvalidEmailException
import pt.programandocomandre.contactagenda.services.errors.InvalidPhoneNumber
import pt.programandocomandre.contactagenda.services.errors.NameNotFilledException

open class CreateContactRequest(val name: String, val email: String? = null, val phoneNumber: String?=null) {
    init {
        if(name.length == 0) {
            throw NameNotFilledException()
        }

        if(email == null && phoneNumber == null) {
            throw AtLeastOneFieldFilledException()
        }


        email?.let {

            if(!validateEmail(it)) {
                throw InvalidEmailException()
            }
        }

        phoneNumber?.let {
            if(!validatePhoneNumber(it)) {
                throw InvalidPhoneNumber()
            }
        }


    }

    private fun validatePhoneNumber(phoneNumber: String?): Boolean {
        val phoneNumberPattern = Patterns.PHONE
        return if(phoneNumber != null) {
            phoneNumberPattern.matcher(phoneNumber).matches()
        }else {
            false
        }
    }

    private fun validateEmail(email: String?):Boolean {
        val emailPattern = Patterns.EMAIL_ADDRESS
        return if(email != null) {
            emailPattern.matcher(email).matches()
        }else {
            false
        }
    }
}