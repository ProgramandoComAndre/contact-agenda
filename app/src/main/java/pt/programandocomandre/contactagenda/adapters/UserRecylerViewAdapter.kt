package pt.programandocomandre.contactagenda.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pt.programandocomandre.contactagenda.databinding.RecycleviewListitemContactBinding
import pt.programandocomandre.contactagenda.models.Contact

class UserRecylerViewAdapter(private val mList: MutableList<Contact>, val context: Context): RecyclerView.Adapter<UserRecylerViewAdapter.ViewHolder>() {
    inner class ViewHolder(val viewBinding: RecycleviewListitemContactBinding): RecyclerView.ViewHolder(viewBinding.root) {
        fun bindData(contact: Contact)
        {
            viewBinding.listItemContactNameTv.text = contact.name
            viewBinding.listItemEmailTv.text = contact.email
            viewBinding.listItemPhoneNumberTv.text = contact.phoneNumber
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
        holder.bindData(mList[position])
    }
}
