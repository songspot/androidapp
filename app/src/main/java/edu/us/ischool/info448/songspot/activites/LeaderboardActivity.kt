package edu.us.ischool.info448.songspot.activites

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.*
import edu.us.ischool.info448.songspot.R

class LeaderboardActivity : AppCompatActivity() {

    private lateinit var database : DatabaseReference

    // Hard-coded for now.
    val genreList : Array<String> = arrayOf(
        "Today's Pop Hits",
        "Rap Caviar",
        "Hot Country",
        "Ultimate Indie",
        "Rock Classics",
        "All Out 70s")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)

        database = FirebaseDatabase.getInstance().reference
        val leaderboardListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var listOfUsers: MutableSet<User> = mutableSetOf()
                // Sort users by score here.
                val genres: Iterable<DataSnapshot> = snapshot.child("scores").child("genres").children
                genres.forEach {
                    val users: Iterable<DataSnapshot> = it.children
                    users.forEach {
                        val category = it.child("category").value as String
                        val username = it.child("username").value as String
                        val score = it.child("score").value as Long
                        listOfUsers.add(User(username, score, category))
                    }
                }
                val sortedList = listOfUsers.sorted().reversed()
                val scoresList = findViewById<ListView>(R.id.scores_list)
                val topNscores = mutableListOf<Int>()
                val topNgenres = mutableListOf<String>()
                val topNusers = mutableListOf<String>()

                for (i in 0..10) {
                    if (i >= sortedList.size) {
                        break
                    }
                    topNscores.add(sortedList.get(i).score.toInt())
                    topNgenres.add(sortedList.get(i).category)
                    topNusers.add(sortedList.get(i).username)
                }

                val listAdapter = ScoresAdapter(applicationContext, topNgenres.toTypedArray(), topNscores.toTypedArray(), topNusers.toTypedArray())
                scoresList.adapter = listAdapter
            }

            override fun onCancelled(p0: DatabaseError) {}
        }
        database.addValueEventListener(leaderboardListener)

        val backButton = findViewById<ImageButton>(R.id.leaderboard_back_button)
        backButton.setOnClickListener {
            val intent = Intent(this, GenrePickerActivity::class.java)
            startActivity(intent)
        }

    }

    class ScoresAdapter(context: Context, private val genreList: Array<String>, private val scoreList: Array<Int>, private val userList: Array<String>) : BaseAdapter() {
        private val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int { return genreList.size }

        override fun getItem(p0: Int): Any { return genreList[p0] }

        override fun getItemId(p0: Int): Long { return p0.toLong() }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val rowView = inflater.inflate(R.layout.leaderboard_list_item, p2, false)
            val usernameView = rowView.findViewById<TextView>(R.id.leaderboard_item_username)
            val genreView = rowView.findViewById<TextView>(R.id.leaderboard_item_genre)
            val scoreView = rowView.findViewById<TextView>(R.id.leaderboard_item_score)

            //usernameView.text = (p0 + 1).toString() + ". username"
            usernameView.text = (p0 + 1).toString() + ". " + userList.get(p0)
            genreView.text = genreList[p0]
            scoreView.text = scoreList[p0].toString()

            return rowView
        }
    }

    data class User(var username: String, var score: Long, var category: String): Comparable<User> {

        override fun compareTo(other: User): Int {
            val diff = score - other.score
            return diff.toInt()
        }
    }
}
