package edu.us.ischool.info448.songspot.api

import android.app.Application

class App: Application() {
    companion object {
        lateinit var sharedInstance: App
            private set
    }

    lateinit var spotifyRemote: SpotifyRemote
    lateinit var songRepository: SongRepository

    val clientId = "33d1e95c57e6460e806a7a9699406d17"
    val redirectUri = "http://localhost:8888/callback/"
    val clientSecret = "ccfa3f280483413eb9d2fad4abebf1cc"

    lateinit var username: String
    lateinit var selectedGenre: String

    override fun onCreate() {
        super.onCreate()

        sharedInstance = this
        spotifyRemote = SpotifyRemote(applicationContext)
        songRepository = SongRepository()
    }
}
