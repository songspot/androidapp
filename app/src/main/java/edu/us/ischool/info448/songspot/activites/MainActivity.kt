package edu.us.ischool.info448.songspot.activites

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import edu.us.ischool.info448.songspot.R
import edu.us.ischool.info448.songspot.api.App

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        val app = App.sharedInstance

        app.spotifyRemote.connect {
            app.spotifyRemote.playSong("")
        }
    }
}
