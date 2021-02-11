package com.gubake.ui.profileteman

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.gubake.R
import com.gubake.data.database.Teman
import com.gubake.data.database.TemanDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class TemanAdapter(
    private var listTeman: List<Teman>,
    val context: Context
) : RecyclerView.Adapter<TemanAdapter.ViewHolder>() {
    private val tag : String = "ProfileTeman"

    private val mDB: TemanDatabase = TemanDatabase.getInstance(context)!!
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_teman, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nama = listTeman[position].nama
        val email = listTeman[position].email
        holder.tvNama.text = nama
        holder.tvEmail.text = email
        holder.ivEdit.setOnClickListener {
            val view = LayoutInflater.from(context).inflate(R.layout.addfriend_dialog, null, false)
            val dialogBuilder = androidx.appcompat.app.AlertDialog.Builder(context)
            dialogBuilder.setView(view)
            val dialog = dialogBuilder.create()
            dialog.setCancelable(true)
            val btSaveFriend = view.findViewById<Button>(R.id.btSaveFriend)
            val btDeleteFriend = view.findViewById<Button>(R.id.btDeleteFriend)
            val etNama = view.findViewById<EditText>(R.id.etNama)
            val etEmail = view.findViewById<EditText>(R.id.etEmail)
            val btClose = view.findViewById<ImageView>(R.id.btClose)
            etNama.setText(nama)
            etEmail.setText(email)
            btSaveFriend.setOnClickListener {
                listTeman[position].nama = etNama.text.toString()
                listTeman[position].email = etEmail.text.toString()
                editTeman(listTeman, position)
                dialog.dismiss()
            }

            btDeleteFriend.setOnClickListener {
                deleteTeman(listTeman, position)
                dialog.dismiss()
            }
            btClose.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    private fun editTeman(list: List<Teman>, position: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            val result = mDB.temanDao().updateTeman(list[position])
            launch(Dispatchers.Main) {
                if (result != 0) {
                    notifyDataSetChanged()
                    Toast.makeText(context, "Teman kamu berhasil diubah", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Teman kamu gagal diubah", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun deleteTeman(list: List<Teman>, position: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            val result = mDB.temanDao().deleteTeman(list[position])
            launch(Dispatchers.Main) {
                if (result != 0) {
                    Log.e(tag,"Delete Teman")
                    (context as ProfileTeman).fetchData()
                    Toast.makeText(context, "Teman kamu berhasil dihapus", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Teman kamu gagal dihapus", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listTeman.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNama: TextView = itemView.findViewById(R.id.tvNama)
        var tvEmail: TextView = itemView.findViewById(R.id.tvEmail)
        var ivEdit: ImageView = itemView.findViewById(R.id.ivEdit)
    }

}