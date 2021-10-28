package com.seriesmanager.adapter

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.seriesmanager.R
import com.seriesmanager.databinding.LayoutListItemBinding
import com.seriesmanager.model.Serie

class SerieAdapter(
    private val onItemClickListener: OnItemClickListener,
    private val seriesList: MutableList<Serie>
) : RecyclerView.Adapter<SerieAdapter.SerieLayoutHolder>() {
    var position: Int = -1

    inner class SerieLayoutHolder(layoutSerieBinding: LayoutListItemBinding)
        : RecyclerView.ViewHolder(layoutSerieBinding.root), View.OnCreateContextMenuListener {
        val mainTitleTv: TextView = layoutSerieBinding.mainTitleTv
        val secondaryTv: TextView = layoutSerieBinding.secondaryTv
        val tertiaryTv: TextView = layoutSerieBinding.tertiaryTv

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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SerieLayoutHolder =
        SerieLayoutHolder(LayoutListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: SerieLayoutHolder, positionItem: Int) {
        val serie = seriesList[positionItem]

        with(holder){
            mainTitleTv.text = serie.name
            secondaryTv.text = serie.genre
            tertiaryTv.text = serie.releaseYear.toString()
        }

        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(positionItem)
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