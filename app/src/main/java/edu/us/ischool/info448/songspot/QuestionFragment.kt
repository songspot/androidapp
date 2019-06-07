package edu.us.ischool.info448.songspot

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.getColor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.util.*
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.AlphaAnimation
import android.widget.Button
import android.widget.Toast


class QuestionFragment : Fragment() {
    private var questionsCount: Int? = null
    private var questionNumber: Int? = null
    private var secondsLeft: Int = 11
    private var readySecondsLeft: Int = 12
    private var listener: OnNextQuestionListener? = null
    private var correctButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            questionNumber = it.getInt("QUESTION_NUMBER")
            questionsCount = it.getInt("QUESTIONS_COUNT")
            Log.d("debugging", questionNumber.toString())
            Log.d("debugging", questionsCount.toString())
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_question, container, false)

        // get references to all four answer choices
        val answer1 = view.findViewById<Button>(R.id.answer1)
        val answer2 = view.findViewById<Button>(R.id.answer2)
        val answer3 = view.findViewById<Button>(R.id.answer3)
        val answer4 = view.findViewById<Button>(R.id.answer4)

        // reference to timer display
        val timerDisplay = view.findViewById<TextView>(R.id.timer)
        val timer = Timer()
        val timer2 = Timer()
        timer2.schedule(object : TimerTask() {
            override fun run() {
                // start "Ready Period" Timer
                activity.runOnUiThread(Runnable {
                    // tasks to be done every 1000 milliseconds
                    if (readySecondsLeft > 9) {
                        timerDisplay.text = "Ready?"
                    } else if (readySecondsLeft > 0) {
                        timerDisplay.text = "Set.."
                    } else if (readySecondsLeft == 0){
                        timerDisplay.text = "Go!"
                    }
                    if (readySecondsLeft == 0) {

                        answer1.setEnabled(true)
                        answer2.setEnabled(true)
                        answer3.setEnabled(true)
                        answer4.setEnabled(true)

                        // cancels "ready Period" timer
                        timer2.cancel()

                        // starts question timer as soon as "Ready Period" is over

                        timer.schedule(object : TimerTask() {
                            override fun run() {
                                activity.runOnUiThread(Runnable {

                                    // tasks to be done every 1000 milliseconds
                                    secondsLeft--
                                    timerDisplay.text = secondsLeft.toString()
                                    if (secondsLeft == 0) {
                                        // tasks to be done if time runs out
                                        timer.cancel()
                                        activateBlinking(answer1)
                                        activateBlinking(answer2)
                                        activateBlinking(answer3)
                                        activateBlinking(answer4)
                                    }
                                })
                            }
                        }, 1000, 1000)
                    }
                    readySecondsLeft--
                })

            }
        }, 1000, 1000)


        // sets onclicklisteners for all buttons
        answer1.setOnClickListener {
            timer.cancel()
            activateBlinking(answer1)
        }


        answer2.setOnClickListener {
            activateBlinking(answer2)
            timer.cancel()
        }
        answer3.setOnClickListener {
            activateBlinking(answer3)
            timer.cancel()
        }
        answer4.setOnClickListener {
            activateBlinking(answer4)
            timer.cancel()
        }

        return view
    }

    // takes in a button, and a boolean representing whether the answer is correct
    // starts a blinking animation that is green if the answer is correct, red if otherwise
    // also handles transitions between questions
    private fun activateBlinking(button: Button) {
        var time = 0
        val timer2 = Timer()
        timer2.schedule(object : TimerTask() {
            override fun run() {
                // start "Ready Period" Timer
                activity.runOnUiThread(Runnable {
                    if (time == 0) {
                        if (!button.equals(correctButton)) {
                            button.backgroundTintList = (ContextCompat.getColorStateList(activity, R.color.colorRed));
                        } else {
                            button.backgroundTintList = (ContextCompat.getColorStateList(activity, R.color.colorGreen));
                        }
                        val mAnimation = AlphaAnimation(1f, 0f)
                        mAnimation.duration = 200
                        mAnimation.interpolator = LinearInterpolator()
                        mAnimation.repeatCount = 1
                        mAnimation.repeatMode = Animation.REVERSE
                        button.startAnimation(mAnimation)
                    }
                    if (time == 3) {
                        questionNumber = questionNumber!! + 1
                        listener!!.onNextQuestion(questionNumber!!, questionsCount!!)
                        timer2.cancel()
                    }
                    time++
                })
            }

        }, 0, 1000)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnNextQuestionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnNextQuestionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnNextQuestionListener {
        // takes in a question index and transitions to the next question fragment if the final question
        // hasn't been reached.
        fun onNextQuestion(questionNumber:Int, questionsCount: Int)
    }

    companion object {
        @JvmStatic
        fun newInstance(questionNumber: Int, questionsCount: Int) =
            QuestionFragment().apply {
                arguments = Bundle().apply {
                    putInt("QUESTION_NUMBER", questionNumber)
                    putInt("QUESTIONS_COUNT", questionsCount)
                }
            }
    }
}
