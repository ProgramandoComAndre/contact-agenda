package pt.programandocomandre.contactagenda.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("contacts")
data class Contact(@PrimaryKey(true) val id: Int, @ColumnInfo("name") var name: String, @ColumnInfo("email") var email: String?, @ColumnInfo("phoneNumber") var phoneNumber: String?)
