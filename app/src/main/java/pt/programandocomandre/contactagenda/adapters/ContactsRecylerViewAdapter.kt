package pt.programandocomandre.contactagenda.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pt.programandocomandre.contactagenda.databinding.RecycleviewListitemContactBinding
import pt.programandocomandre.contactagenda.events.ContactActionButtonOnClickListener
import pt.programandocomandre.contactagenda.models.Contact

class ContactsRecylerViewAdapter(private val mList: MutableList<Contact>, val context: Context, val deleteOnClickListener: ContactActionButtonOnClickListener, val editOnClickListener: ContactActionButtonOnClickListener): RecyclerView.Adapter<ContactsRecylerViewAdapter.ViewHolder>() {


    inner class ViewHolder(val viewBinding: RecycleviewListitemContactBinding): RecyclerView.ViewHolder(viewBinding.root) {
        fun bindData(contact: Contact, position: Int)
        {
            viewBinding.listItemContactNameTv.text = contact.name
            viewBinding.listItemEmailTv.text = contact.email
            viewBinding.listItemPhoneNumberTv.text = contact.phoneNumber
            viewBinding.listItemEditButtonBtn.setOnClickListener {
                editOnClickListener.onClick(contact, position)
            }
            viewBinding.listItemDeleteButtonBtn.setOnClickListener {
                deleteOnClickListener.onClick(contact, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = RecycleviewListitemContactBinding.inflate(LayoutInflater.from(context),parent, false)
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(mList[position], position)
    }

    fun updateContacts(newContacts: List<Contact>) {
        val mListSize = mList.size
        mList.clear()
        notifyItemRangeRemoved(0, mListSize)
        mList.addAll(newContacts)
        notifyItemRangeChanged(0, newContacts.size)
    }
}
