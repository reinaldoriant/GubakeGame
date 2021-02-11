package com.gubake.ui.pilihlawan

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.gubake.R
import com.gubake.data.database.Teman
import com.gubake.ui.playgamevsplayer.PlayGameVsPlayer


class PilihLawanAdapter(
    private var listTeman: List<Teman>,
    val context: Context
) : RecyclerView.Adapter<PilihLawanAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_teman, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nama = listTeman[position].nama
        val email = listTeman[position].email
        holder.tvNama.text = nama
        holder.tvEmail.text = email
        holder.ivEdit.visibility = View.GONE
        holder.itemView.setOnClickListener {

            val intent = Intent(context, PlayGameVsPlayer::class.java)
            intent.putExtra("NAMA_TEMAN", nama)
            context.startActivity(intent)
            (context as PilihLawan).finish()
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