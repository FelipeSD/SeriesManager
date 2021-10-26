package com.seriesmanager.adapter

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.seriesmanager.R
import com.seriesmanager.model.Serie
import com.seriesmanager.databinding.SerieListItemBinding

class SerieAdapter(
    private val onItemClickListener: OnItemClickListener,
    private val seriesList: MutableList<Serie>
) : RecyclerView.Adapter<SerieAdapter.SerieLayoutHolder>() {
    var position: Int = -1

    inner class SerieLayoutHolder(layoutSerieBinding: SerieListItemBinding)
        : RecyclerView.ViewHolder(layoutSerieBinding.root), View.OnCreateContextMenuListener {
        val serieTv: TextView = layoutSerieBinding.serieTv
        val genreTv: TextView = layoutSerieBinding.genreTv
        val releaseYearTv: TextView = layoutSerieBinding.releaseYearTv

        init {
            itemView.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            view: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            MenuInflater(view?.context).inflate(R.menu.context_menu_main, menu)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SerieLayoutHolder {
        return SerieLayoutHolder(SerieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


    override fun onBindViewHolder(holder: SerieLayoutHolder, positionItem: Int) {
        val serie = seriesList[positionItem]

        with(holder){
            serieTv.text = serie.name
            genreTv.text = serie.genre
            releaseYearTv.text = serie.releaseYear.toString()
        }

        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(position)
        }

        holder.itemView.setOnLongClickListener{
            position = positionItem
            false
        }
    }

    override fun getItemCount(): Int {
        return seriesList.size
    }
}