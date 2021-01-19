package com.gubake.ui.pilihLawan

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gubake.R
import com.gubake.data.database.Pemain
import com.gubake.ui.profileteman.ProfileTeman
import kotlinx.android.synthetic.main.item_teman.view.*

class DaftarPemainAdapter(private val listPemain: List<Pemain>, val context: Context) :
    RecyclerView.Adapter<DaftarPemainAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_teman, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userName = listPemain[position].username
        val email = listPemain[position].email
        holder.itemView.tvNama.text = userName
        holder.itemView.tvEmail.text = email

        holder.itemView.ivEdit.visibility = View.INVISIBLE

        holder.itemView.setOnClickListener {
            val intent = Intent (context, ProfileTeman::class.java)
            intent.putExtra("nama", userName)
            context.startActivity(intent)
            (context as DaftarPemain).finish()
        }
    }

    override fun getItemCount(): Int {
        return listPemain.size
    }

}