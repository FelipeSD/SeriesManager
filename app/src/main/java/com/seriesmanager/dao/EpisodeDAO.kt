package com.seriesmanager.dao

import com.seriesmanager.model.Episode

interface EpisodeDAO {
    fun createEpisode(episode: Episode, seasonSequence: String): Long
    fun findEpisode(episodeSequence: String): Episode
    fun getEpisodes(seasonSequence: String): MutableList<Episode>
    fun updateEpisode(episode: Episode, seasonSequence: String): Int
    fun deleteEpisode(episodeSequence: String): Int
}