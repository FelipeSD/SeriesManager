package com.seriesmanager.dao.serie

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.seriesmanager.Auth
import com.seriesmanager.model.Serie

class SerieFirebase: SerieDAO {
    companion object {
        val BD_SERIES = "series"
    }

    private val seriesRtDb = Firebase.database.getReference(Auth.firebaseAuth.currentUser?.uid!!).child(BD_SERIES)

    private val seriesList = mutableListOf<Serie>()

    init {
        seriesRtDb.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val newSerie: Serie? = snapshot.value as? Serie

                if(newSerie != null){
                    if(seriesList.find{it.name == newSerie.name} == null){
                        seriesList.add(newSerie)
                    }
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val updatedSerie: Serie? = snapshot.value as? Serie
                if(updatedSerie != null){
                    seriesList[seriesList.indexOfFirst{it.name == updatedSerie.name}] = updatedSerie
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val removedSerie: Serie? = snapshot.value as? Serie
                if(removedSerie != null){
                    seriesList.remove(removedSerie)
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        seriesRtDb.addListenerForSingleValueEvent(object: ValueEventListener {
            // recover data
            override fun onDataChange(snapshot: DataSnapshot) {
                seriesList.clear()
                snapshot.children.forEach {
                    val serie: Serie = it.getValue<Serie>()?: Serie()
                    seriesList.add(serie)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun createSerie(serie: Serie): Long {
        createOrUpdateSerie(serie)
        return 0L
    }

    override fun findSerie(name: String): Serie {
        return seriesList.firstOrNull{
            it.name == name
        } ?: Serie()
    }

    override fun getSeries(): MutableList<Serie> = seriesList

    override fun updateSerie(serie: Serie): Int {
        createOrUpdateSerie(serie)
        return 1
    }

    override fun deleteSerie(name: String): Int {
        seriesRtDb.child(name).removeValue()
        return 1
    }

    private fun createOrUpdateSerie(serie: Serie){
        seriesRtDb.child(serie.name).setValue(serie)
    }
}