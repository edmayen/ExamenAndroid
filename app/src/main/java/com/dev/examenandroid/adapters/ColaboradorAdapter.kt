package com.dev.examenandroid.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.examenandroid.R
import com.dev.examenandroid.base.BaseViewHolder
import com.dev.examenandroid.data.model.Colaborador
import kotlinx.android.synthetic.main.item_colaboradores.view.*

class ColaboradorAdapter(
    private val context: Context,
    private val colaboradoresList: List<Colaborador>,
    private val  itemClickListener: OnColaboradorCLickListener
): RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnColaboradorCLickListener{
        fun onColaboradorClick(colaborador: Colaborador)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return ColaboradoresViewHolder(LayoutInflater.from(context).inflate(R.layout.item_colaboradores, parent, false))
    }

    override fun getItemCount(): Int = colaboradoresList.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is ColaboradoresViewHolder -> holder.bind(colaboradoresList[position], position)
            else -> throw IllegalArgumentException("Se olvido de pasar el ViewModel en el bind.")
        }
    }

    inner class ColaboradoresViewHolder(itemView: View): BaseViewHolder<Colaborador>(itemView){
        override fun bind(item: Colaborador, position: Int) {
            itemView.tvRvItemName.text = item.name
            itemView.tvRvItemContact.text = item.mail
            itemView.setOnClickListener { itemClickListener.onColaboradorClick(item) }
        }
    }
}