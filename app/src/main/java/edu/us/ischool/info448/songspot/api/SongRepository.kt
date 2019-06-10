package edu.us.ischool.info448.songspot.api
import com.adamratzman.spotify.SpotifyAPI
import com.adamratzman.spotify.spotifyApi
import edu.us.ischool.info448.songspot.models.Song

class SongRepository {

    private val playlistNameToUriMap = hashMapOf(
        "Today's Pop Hits" to "spotify:playlist:37i9dQZF1DXcBWIGoYBM5M",
        "Rap Caviar" to "spotify:playlist:37i9dQZF1DX0XUsuxWHRQd",
        "Hot Country" to "spotify:playlist:37i9dQZF1DX1lVhptIYRda",
        "Ultimate Indie" to "spotify:playlist:37i9dQZF1DX2Nc3B70tvx0",
        "Rock Classics" to "spotify:playlist:37i9dQZF1DWXRqgorJj26U",
        "All Out 70s" to "spotify:playlist:37i9dQZF1DWTJ7xPn4vNaz"
    )

    private lateinit var accessToken: String

    private lateinit var api: SpotifyAPI

    private var songs = arrayListOf<Song>()

    // Fetches 40 songs from the given playlist to be used for the quiz
    fun fetchCategorySongs(name: String, completion: (ArrayList<Song>) -> Unit) {
        val uri = playlistNameToUriMap.getValue(name)

        api.playlists.getPlaylistTracks(uri, 20).queue {response ->
            response.items.forEach {
                val track = it.track

                val trackName = track.name
                val trackUri = track.uri.uri
                val artist = track.artists.map {artist ->
                    artist.name
                }.joinToString(" & ")

                val song = Song(trackName, artist, trackUri)
                songs.add(song)
            }
            completion(songs)
        }
    }

    // passes a random selection of 4 songs fetched from the given Spotify playlist
    // to the completion handler to be used
    fun getQuestionSongs(): Array<Song> {
        var results = arrayListOf<Song>()

        while(results.size < 4 || results.size >= songs.size) {
            val song = songs.random()

            if (!results.contains(song)) {
                results.add(song)
            }
        }

        val resultsArray = arrayOfNulls<Song>(results.size)
        return results.toArray(resultsArray)
    }

    fun setAccessToken(token: String) {
        this.accessToken = token
        api = spotifyApi {
            credentials {
                clientId = App.sharedInstance.clientId
                clientSecret = App.sharedInstance.clientSecret
            }
            authentication {
                tokenString = accessToken
            }

        }.buildCredentialed()
    }

    fun getAccessToken(): String? {
        return accessToken
    }
}