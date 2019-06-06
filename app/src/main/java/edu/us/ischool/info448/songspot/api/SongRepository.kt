package edu.us.ischool.info448.songspot.api
import android.content.Context
import com.adamratzman.spotify.SpotifyAPI
import com.adamratzman.spotify.spotifyApi
import edu.us.ischool.info448.songspot.models.Song

class SongRepository {

    // TODO: Fill this in with valid playlist and their spotify URI
    private val playlistNameToUriMap = hashMapOf(
        "Indie" to "spotify:playlist:37i9dQZF1DX2sUQwD7tbmL",
        "Piano" to "spotify:playlist:37i9dQZF1DX7K31D69s4M1"
    )

    private var context: Context
    private var api: SpotifyAPI

    private lateinit var songs: ArrayList<Song>

    constructor(context: Context) {
        this.context = context

        api = spotifyApi {
            credentials {
                clientId = "33d1e95c57e6460e806a7a9699406d17"
                clientSecret = "ccfa3f280483413eb9d2fad4abebf1cc"
            }
        }.buildCredentialed()
    }

    // Fetches 40 songs from the given playlist to be used for the quiz
    fun fetchCategorySongs(name: String, completion: () -> Unit) {
        val uri = playlistNameToUriMap.getValue(name)

        api.playlists.getPlaylistTracks(uri, 40).queue {response ->
            response.items.map {item ->
                val track = item.track

                val trackName = track.name
                val trackUri = track.uri.uri
                val artist = track.artists.map {artist ->
                    artist.name
                }.joinToString(" & ")

                songs.add(Song(trackName, artist, trackUri))
            }
            completion()
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
}