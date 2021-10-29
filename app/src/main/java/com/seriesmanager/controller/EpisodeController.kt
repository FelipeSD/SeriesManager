package com.seriesmanager.controller

import com.seriesmanager.EpisodeActivity
import com.seriesmanager.dao.EpisodeSqlite
import com.seriesmanager.model.Episode

class EpisodeController(episodeActivity: EpisodeActivity) {
    private val episodeDAO = EpisodeSqlite(episodeActivity)

    fun createEpisode(episode: Episode, seasonSequence: String) = episodeDAO.createEpisode(episode, seasonSequence)
    fun findEpisode(episodeSequence: String) = episodeDAO.findEpisode(episodeSequence)
    fun getEpisodes(seasonSequence: String) = episodeDAO.getEpisodes(seasonSequence)
    fun updateEpisode(episode: Episode, seasonSequence: String) = episodeDAO.updateEpisode(episode, seasonSequence)
    fun deleteEpisode(episodeSequence: String) = episodeDAO.deleteEpisode(episodeSequence)
}