package com.seriesmanager.controller

import com.seriesmanager.SeasonActivity
import com.seriesmanager.dao.season.SeasonFirebase
import com.seriesmanager.dao.season.SeasonSqlite
import com.seriesmanager.model.Season

class SeasonController(seasonActivity: SeasonActivity) {
    private val seasonDAO = SeasonSqlite(seasonActivity)
    private val seasonFirebase = SeasonFirebase()

    fun createSeason(season: Season, serieName: String) = seasonFirebase.createSeason(season, serieName)
    fun findSeason(sequence: String) = seasonFirebase.findSeason(sequence)
    fun getSeasons(serieName: String) = seasonFirebase.getSeasons(serieName)
    fun updateSeason(season: Season, serieName: String) = seasonFirebase.updateSeason(season, serieName)
    fun deleteSeason(sequence: String, serieName: String) = seasonFirebase.deleteSeason(sequence, serieName)
}