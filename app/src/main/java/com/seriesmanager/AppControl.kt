package com.seriesmanager

import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

abstract class AppControl : AppCompatActivity(){

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId){
        R.id.refreshMi -> {
            refreshList()
            true
        }
        R.id.logoutMi -> {
            Auth.firebaseAuth.signOut()
            finish()
            true
        }
        else -> { false }
    }

    override fun onStart() {
        super.onStart()
        if(Auth.firebaseAuth.currentUser == null){
            finish()
        }
    }

    abstract fun refreshList()
}