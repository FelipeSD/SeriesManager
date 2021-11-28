package com.seriesmanager.dao.episode

import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.seriesmanager.dao.season.SeasonFirebase.Companion.BD_SEASON
import com.seriesmanager.dao.serie.SerieFirebase.Companion.BD_SERIES
import com.seriesmanager.model.Episode

class EpisodeFirebase: EpisodeDAO {
    companion object {
        private val BD_EPISODES = "episodes"
    }

    private val episodesRtDb = Firebase.database.getReference(BD_SERIES)

    private val episodesList = mutableListOf<Episode>()

    override fun createEpisode(episode: Episode, seasonSequence: String, serieName: String): Long {
        createOrUpdateEpisode(episode, seasonSequence, serieName)
        return 0L
    }

    override fun findEpisode(episodeSequence: String): Episode {
        return episodesList.firstOrNull{
            it.name == episodeSequence
        } ?: Episode()
    }

    override fun getEpisodes(seasonSequence: String, serieName: String): MutableList<Episode> {
        episodesRtDb.child(serieName).child(BD_SEASON).child(seasonSequence).child(BD_EPISODES).get().addOnSuccessListener { snapshot ->
            episodesList.clear()
            snapshot.children.forEach {
                val episode = it.getValue<Episode>() ?: Episode()
                episodesList.add(episode)
            }
        }

        return episodesList
    }

    override fun updateEpisode(episode: Episode, seasonSequence: String, serieName: String): Int {
        createOrUpdateEpisode(episode, seasonSequence, serieName)
        return 1
    }

    override fun deleteEpisode(episodeSequence: String, seasonSequence: String, serieName: String): Int {
        episodesRtDb.child(serieName).child(BD_SEASON).child(seasonSequence).child(BD_EPISODES).child(episodeSequence).removeValue()
        return 1
    }

    private fun createOrUpdateEpisode(episode: Episode, seasonSequence: String, serieName: String){
        episodesRtDb.child(serieName).child(BD_SEASON).child(seasonSequence).child(BD_EPISODES).child(episode.sequence).setValue(episode)
    }
}