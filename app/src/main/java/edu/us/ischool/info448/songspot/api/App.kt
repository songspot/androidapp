package edu.us.ischool.info448.songspot.api

import android.app.Application

class App: Application() {
    companion object {
        lateinit var sharedInstance: App
            private set
    }

    lateinit var spotifyRemote: SpotifyRemote
    lateinit var songRepository: SongRepository

    override fun onCreate() {
        super.onCreate()

        sharedInstance = this
        spotifyRemote = SpotifyRemote(applicationContext)
        songRepository = SongRepository()
    }
}
