package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    /*
    * Todo: make grid lines between the images
    * Todo: better logic in `checkForWin`
    * Todo: make computer an opponent
     */

    private val red_X = R.drawable.redcross
    private val black_O = R.drawable.blackcircle
    private val background = R.drawable.background
    private val blocks = IntArray(9)
    private lateinit var images: Array<ImageView>

    private var turnNum = 1

    private val DRAW = 0
    private val CONTINUE_PLAY = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.images = arrayOf(
            findViewById(R.id.img1), findViewById(R.id.img2), findViewById(R.id.img3),
            findViewById(R.id.img4), findViewById(R.id.img5), findViewById(R.id.img6),
            findViewById(R.id.img7), findViewById(R.id.img8), findViewById(R.id.img9)
        )
    }

    fun onClick(view: View) {
        val img = view as ImageView

        // if the image has been clicked before, ignore
        if (!img.isClickable) {
            return
        }
        // setting the flag for future clicks
        img.isClickable = false

        if (turnNum % 2 == 0) {
            img.setImageResource(this.red_X)
        } else {
            img.setImageResource(this.black_O)
        }
        // updating that the block has been clicked
        this.updateBlock(img)

        val status = this.checkForWin()
        if (status != CONTINUE_PLAY) {
            if (status == DRAW) {
                Toast.makeText(this.applicationContext, "Draw", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this.applicationContext, "Player number $status has won!", Toast.LENGTH_SHORT).show()
            }
            // if the game is finished, don't allow anymore plays
            this.gameOver()
        }
        turnNum = 3 - turnNum
    }

//    fun computerPlay() : Int {
//        Thread.sleep(200)
//        var num = Random.nextInt(9)
//        while (this.blocks[num] != 0) {
//            num = Random.nextInt(9)
//        }
//        return num
//    }

    fun updateBlock(img: ImageView) {
        val imgID = resources.getResourceEntryName(img.id)
        val index: Int = imgID[3].toInt() - '0'.toInt() - 1
        this.blocks[index] = this.turnNum
    }

    fun checkForWin(): Int {
        var winner = CONTINUE_PLAY

        // check horizontal
        for (i in 0..6 step 3) {
            if (blocks[i] == blocks[i + 1] && blocks[i + 1] == blocks[i + 2]) {
                if (blocks[i] != 0) {
                    winner = turnNum
                    break
                }
            }
        }

        // check vertical
        if (winner == CONTINUE_PLAY) {
            for (i in 0..2) {
                if (blocks[i] == blocks[i + 3] && blocks[i + 3] == blocks[i + 6]) {
                    if (blocks[i] != 0) {
                        winner = turnNum
                        break
                    }
                }
            }
        }
        // checking the diagonals
        if (winner == CONTINUE_PLAY) {
            if ((blocks[0] == (blocks[4]) && blocks[4] == (blocks[8])) ||       // top left to bottom right diagonal
                (blocks[2] == (blocks[4]) && blocks[4] == (blocks[6]))          // top right to bottom left diagonal
            ) {
                if (blocks[4] != 0) {
                    winner = turnNum
                }
            }
        }
        // if none of the checks above returned a winner, check for a draw
        if (winner == CONTINUE_PLAY) {
            if (this.checkForDraw()) {
                winner = DRAW
            }
        }
        return winner
    }

    fun checkForDraw(): Boolean {
        for (i in this.blocks) {
            if (i == 0) {
                return false
            }
        }
        return true
    }

    fun gameOver() {
//        val images: Array<ImageView> = arrayOf(
//            findViewById(R.id.img1), findViewById(R.id.img2),
//            findViewById(R.id.img3), findViewById(R.id.img4), findViewById(R.id.img5), findViewById(R.id.img6),
//            findViewById(R.id.img7), findViewById(R.id.img8), findViewById(R.id.img9)
//        )
        for (image in this.images) {
            image.isClickable = false
        }
    }

    fun restart(view: View) {
//        val images: Array<ImageView> = arrayOf(
//            findViewById(R.id.img1), findViewById(R.id.img2),
//            findViewById(R.id.img3), findViewById(R.id.img4), findViewById(R.id.img5), findViewById(R.id.img6),
//            findViewById(R.id.img7), findViewById(R.id.img8), findViewById(R.id.img9)
//        )
        // resetting the view
        for (image in this.images) {
            image.isClickable = true
            image.setImageResource(this.background)
        }

        // resetting the logic
        for (i in this.blocks.indices) {
            this.blocks[i] = 0
        }
    }
}

