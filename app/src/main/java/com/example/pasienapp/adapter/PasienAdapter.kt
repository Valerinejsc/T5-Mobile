package com.example.pasienapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pasienapp.R
import com.example.pasienapp.model.Pasien

class PasienAdapter(private val listPasien: List<Pasien>) :
    RecyclerView.Adapter<PasienAdapter.PasienViewHolder>() {

    inner class PasienViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNama: TextView           = itemView.findViewById(R.id.tvNama)
        val tvJenisKelamin: TextView   = itemView.findViewById(R.id.tvJenisKelamin)
        val tvTanggalLahir: TextView   = itemView.findViewById(R.id.tvTanggalLahir)
        val tvAlamat: TextView         = itemView.findViewById(R.id.tvAlamat)
        val tvTelepon: TextView        = itemView.findViewById(R.id.tvTelepon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasienViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pasien, parent, false)
        return PasienViewHolder(view)
    }

    override fun onBindViewHolder(holder: PasienViewHolder, position: Int) {
        val pasien = listPasien[position]
        holder.tvNama.text           = pasien.nama
        holder.tvJenisKelamin.text   = pasien.jenis_kelamin
        holder.tvTanggalLahir.text   = pasien.tanggal_lahir
        holder.tvAlamat.text         = pasien.alamat
        holder.tvTelepon.text        = pasien.no_telepon
    }

    override fun getItemCount(): Int = listPasien.size
}