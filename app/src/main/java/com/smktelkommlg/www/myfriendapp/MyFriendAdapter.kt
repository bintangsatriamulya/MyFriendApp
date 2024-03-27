package com.smktelkommlg.www.myfriendapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyFriendAdapter(private val context: Context, private val friendItems: List<MyFriend>) :
    RecyclerView.Adapter<MyFriendAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_friends, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(friendItems[position])
    }

    override fun getItemCount(): Int {
        return friendItems.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvFriendName: TextView = itemView.findViewById(R.id.edtName)
        private val tvFriendEmail: TextView = itemView.findViewById(R.id.edtEmail)
        private val tvFriendTlp: TextView = itemView.findViewById(R.id.edtTelp)

        fun bindItem(item: MyFriend) {
            tvFriendName.text = item.nama
            tvFriendEmail.text = item.email
            tvFriendTlp.text = item.telp
        }
    }
}