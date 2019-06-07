package edu.us.ischool.info448.songspot.api
import android.util.Log
import com.adamratzman.spotify.SpotifyClientAPI
import com.adamratzman.spotify.SpotifyAPI
import com.adamratzman.spotify.spotifyApi
import edu.us.ischool.info448.songspot.models.Song

class SongRepository {

    // TODO: Fill this in with valid playlist and their spotify URI
    private val playlistNameToUriMap = hashMapOf(
        "Indie" to "spotify:playlist:37i9dQZF1DX2sUQwD7tbmL",
        "Piano" to "spotify:playlist:37i9dQZF1DX7K31D69s4M1"
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
                clientId = "33d1e95c57e6460e806a7a9699406d17"
                clientSecret = "ccfa3f280483413eb9d2fad4abebf1cc"
            }
            authentication {
                tokenString = accessToken
            }

        }.buildCredentialed()
    }
}