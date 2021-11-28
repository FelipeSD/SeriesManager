package com.seriesmanager.controller

import com.seriesmanager.EpisodeActivity
import com.seriesmanager.dao.episode.EpisodeFirebase
import com.seriesmanager.dao.episode.EpisodeSqlite
import com.seriesmanager.model.Episode

class EpisodeController(episodeActivity: EpisodeActivity) {
    private val episodeDAO = EpisodeSqlite(episodeActivity)
    private val episodeFirebase = EpisodeFirebase()

    fun createEpisode(episode: Episode, seasonSequence: String, serieName: String) = episodeFirebase.createEpisode(episode, seasonSequence, serieName)
    fun findEpisode(episodeSequence: String) = episodeFirebase.findEpisode(episodeSequence)
    fun getEpisodes(seasonSequence: String, serieName: String) = episodeFirebase.getEpisodes(seasonSequence, serieName)
    fun updateEpisode(episode: Episode, seasonSequence: String, serieName: String) = episodeFirebase.updateEpisode(episode, seasonSequence, serieName)
    fun deleteEpisode(episodeSequence: String, seasonSequence: String, serieName: String) = episodeFirebase.deleteEpisode(episodeSequence, seasonSequence, serieName)
}