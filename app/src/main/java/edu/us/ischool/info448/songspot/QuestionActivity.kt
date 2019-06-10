package edu.us.ischool.info448.songspot

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.*
import edu.us.ischool.info448.songspot.activites.GenrePickerActivity
import edu.us.ischool.info448.songspot.activites.ResultsActivity
import edu.us.ischool.info448.songspot.api.App

class QuestionActivity : AppCompatActivity(), QuestionFragment.OnNextQuestionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        // by default starts Question Fragment
        val questionFragment = QuestionFragment.newInstance(1,5, 0)
        supportFragmentManager.beginTransaction().run {
            add(R.id.fragment_container, questionFragment, "QUESTION_FRAGMENT")
            commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        App.sharedInstance.spotifyRemote.disconnect()
    }

    override fun onNextQuestion(questionNumber: Int, questionsCount:Int, points: Int) {
        if (questionNumber < (questionsCount + 1)) {
            Log.d("debugging", "Reached redirect!")
            val questionFragment = QuestionFragment.newInstance(questionNumber, questionsCount, points)
            supportFragmentManager.beginTransaction().run {
                replace(R.id.fragment_container, questionFragment, "QUESTION_FRAGMENT")
                commit()
            }
        } else {
            val database: DatabaseReference = FirebaseDatabase.getInstance().reference
            val category = App.sharedInstance.selectedGenre
            val currUser = App.sharedInstance.username


            database.child("scores").child("genres").addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.child(category).child(currUser).exists()) {
                        val currPoints = dataSnapshot.child(category).child(currUser).child("score").value as Long

                        if (points > currPoints) {
                            val user = User(currUser, points, category)
                            database.child("scores").child("genres").child(category).child(currUser).setValue(user)
                        }

                    } else {
                        val user = User(currUser, points, category)
                        database.child("scores").child("genres").child(category).child(currUser).setValue(user)
                    }
                }
                override fun onCancelled(p0: DatabaseError) {
                }
            })

            val intent = Intent(baseContext, ResultsActivity::class.java)
            intent.putExtra("SCORE", points)

            startActivity(intent)
        }
    }
}

data class User(var username: String, var score: Int, var category: String): Comparable<User> {

    override fun compareTo(other: User): Int {
        return score - other.score
    }
}


