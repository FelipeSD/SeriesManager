package com.seriesmanager.adapter

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.seriesmanager.R
import com.seriesmanager.databinding.LayoutListItemBinding
import com.seriesmanager.model.Season

class SeasonAdapter (
    private val onItemClickListener: OnItemClickListener,
    private val seasonList: MutableList<Season>
) : RecyclerView.Adapter<SeasonAdapter.SeasonLayoutHolder>() {
    var position: Int = -1

    inner class SeasonLayoutHolder(layoutSeasonBinding: LayoutListItemBinding)
        : RecyclerView.ViewHolder(layoutSeasonBinding.root), View.OnCreateContextMenuListener {
        val mainTitleTv: TextView = layoutSeasonBinding.mainTitleTv
        val secondaryTv: TextView = layoutSeasonBinding.secondaryTv
        val tertiaryTv: TextView = layoutSeasonBinding.tertiaryTv

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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonLayoutHolder =
        SeasonLayoutHolder(LayoutListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: SeasonLayoutHolder, positionItem: Int) {
        val season = seasonList[positionItem]

        with(holder){
            mainTitleTv.text = season.sequence
            secondaryTv.text = season.numberEpisodes
            tertiaryTv.text = season.releaseYear
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
        return seasonList.size
    }
}