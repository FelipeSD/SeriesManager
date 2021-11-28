package com.seriesmanager.dao.episode

import com.seriesmanager.model.Episode

interface EpisodeDAO {
    fun createEpisode(episode: Episode, seasonSequence: String, serieName: String): Long
    fun findEpisode(episodeSequence: String): Episode
    fun getEpisodes(seasonSequence: String, serieName: String): MutableList<Episode>
    fun updateEpisode(episode: Episode, seasonSequence: String, serieName: String): Int
    fun deleteEpisode(episodeSequence: String, seasonSequence: String, serieName: String): Int
}