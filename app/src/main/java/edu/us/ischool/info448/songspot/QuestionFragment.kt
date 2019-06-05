package edu.us.ischool.info448.songspot

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.getColor
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


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [QuestionFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [QuestionFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class QuestionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var questionNumber: String? = null
    private var secondsLeft: Int = 10
    private var readySecondsLeft: Int = 12
    private var listener: OnNextQuestionListener? = null
    private var correctButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            questionNumber = it.getString("QUESTION_NUMBER")

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

        val timer2 = Timer()
        timer2.schedule(object : TimerTask() {
            override fun run() {
                // start "Ready Period" Timer
                activity.runOnUiThread(Runnable {
                    // tasks to be done every 1000 milliseconds
                    if (readySecondsLeft > 10) {
                        Toast.makeText(activity, "Ready?", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(activity, readySecondsLeft.toString(), Toast.LENGTH_SHORT).show()
                    }
                    if (readySecondsLeft == 0) {
                        Toast.makeText(activity, "Start!", Toast.LENGTH_SHORT).show()
                        answer1.setEnabled(true)
                        answer2.setEnabled(true)
                        answer3.setEnabled(true)
                        answer4.setEnabled(true)

                        // cancels "ready Period" timer
                        timer2.cancel()

                        // starts question timer as soon as "Ready Period" is over
                        val timer = Timer()
                        timer.schedule(object : TimerTask() {
                            override fun run() {
                                activity.runOnUiThread(Runnable {
                                    val timerDisplay = view.findViewById<TextView>(R.id.timer)
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
            activateBlinking(answer1)
        }
        answer2.setOnClickListener {
            activateBlinking(answer2)
        }
        answer3.setOnClickListener {
            activateBlinking(answer3)
        }
        answer4.setOnClickListener {
            activateBlinking(answer4)
        }



        return view
    }

    // takes in a button, and a boolean representing whether the answer is correct
    // starts a blinking animation that is green if the answer is correct, red if otherwise
    private fun activateBlinking(button: Button) {
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */

    // interface to be implemented by activity
    interface OnNextQuestionListener {
        // takes in a question index and transitions to the next question fragment if the final question
        // hasn't been reached.
        fun onNextQuestion(questionNumber:String)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment QuestionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(questionNumber: String) =
            QuestionFragment().apply {
                arguments = Bundle().apply {
                    putString("QUESTION_NUMBER", questionNumber)
                }
            }
    }
}
