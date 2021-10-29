package com.seriesmanager.controller

import com.seriesmanager.SeasonActivity
import com.seriesmanager.dao.SeasonSqlite
import com.seriesmanager.model.Season

class SeasonController(seasonActivity: SeasonActivity) {
    private val seasonDAO = SeasonSqlite(seasonActivity)

    fun createSeason(season: Season, serieName: String) = seasonDAO.createSeason(season, serieName)
    fun findSeason(sequence: String) = seasonDAO.findSeason(sequence)
    fun getSeasons(serieName: String) = seasonDAO.getSeasons(serieName)
    fun updateSeason(season: Season, serieName: String) = seasonDAO.updateSeason(season, serieName)
    fun deleteSeason(sequence: String) = seasonDAO.deleteSeason(sequence)
}