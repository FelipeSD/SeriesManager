package com.seriesmanager.adapter

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.seriesmanager.R
import com.seriesmanager.databinding.LayoutListItemBinding
import com.seriesmanager.model.Episode

class EpisodeAdapter(
    private val episodeList: MutableList<Episode>
) : RecyclerView.Adapter<EpisodeAdapter.EpisodeLayoutHolder>() {
    var position: Int = -1

    inner class EpisodeLayoutHolder(layoutSeasonBinding: LayoutListItemBinding)
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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EpisodeAdapter.EpisodeLayoutHolder = EpisodeLayoutHolder(LayoutListItemBinding.inflate(
        LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: EpisodeAdapter.EpisodeLayoutHolder, positionItem: Int) {
        val episode = episodeList[positionItem]

        with(holder){
            mainTitleTv.text = episode.name
            secondaryTv.text = episode.duration.toString()
            tertiaryTv.text = episode.sequence.toString()
        }

        holder.itemView.setOnLongClickListener{
            position = positionItem
            false
        }
    }

    override fun getItemCount(): Int {
        return episodeList.size
    }

}