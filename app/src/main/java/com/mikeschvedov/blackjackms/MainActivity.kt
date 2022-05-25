package com.mikeschvedov.blackjackms

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.mikeschvedov.blackjackms.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val playerViewList: MutableList<TextView> = mutableListOf()
    private val dealerViewList: MutableList<TextView> = mutableListOf()
    lateinit var binding: ActivityMainBinding

    //TODO read motionlayout carousel
    //TODO show card overlapping elevation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* ----- New Game Button ----- */
        binding.newGameButton.setOnClickListener {
            runNewGame()
        }
        /* ----- Exit Button ----- */
        binding.exitButton.setOnClickListener {
            this.finishAffinity()
        }
    }

    private fun runNewGame() {
        // Initializing the game instance
        val newGame = Game()
        // First UI changes after game initialization
        initialUISetup(newGame)
        // Asking the player to hit or stand
        binding.gameStatus.text = getString(R.string.hit_or_stand)
        // Checking if there is blackjack
        if (newGame.checkBlackJack()) {
            binding.gameStatus.text = "BlackJack\nPlayer Wins"
            gameOver()
        }

        /* ----- Hit Button ----- */
        binding.buttonHit.setOnClickListener {
            newGame.hit(newGame.getPlayerHand())
            updateCardsUI(newGame)
            if (newGame.checkIf21()) {
                binding.gameStatus.text = getString(R.string.player_has_21)
                executeStand(newGame)
            }
            if (newGame.checkIfBusted()) {
                binding.gameStatus.text = "Busted!\nGame Over"
                gameOver()
            }
        }
        /* ----- Stand Button ----- */
        binding.buttonStand.setOnClickListener {
            executeStand(newGame)
        }
    }

    private fun initialUISetup(newGame: Game) {
        // UI updates that are also relevant when restarting
        resetViewsAndUI()
        // hiding the cards (if it is a restart)
        hidingCards()
        // showing the player's initial cards and score
        updateCardsUI(newGame)
    }

    private fun executeStand(newGame: Game) {
        val condition = newGame.stand()
        // Display the condition description
        binding.gameStatus.text = condition.desc
        updateCardsUI(newGame)
        gameOver()
    }

    private fun hidingCards() {
        playerViewList.forEach {
            it.visibility = View.GONE
        }
        dealerViewList.forEach {
            it.visibility = View.GONE
        }
    }

    private fun updateCardsUI(newGame: Game) {
        /* Updating Player UI */
        var count = 0
        newGame.getPlayerHand().cards.forEach {
            // To prevent unnecessary UI updates, we only update the new cards
            if (playerViewList[count].visibility == View.GONE) {
                playerViewList[count].apply {
                    // We set the cards data into the text
                    text = it.toString()
                    // We make sure the rank is displayed with the color of its suit
                    if (it.suit == Card.Suit.CLUBS || it.suit == Card.Suit.SPADES) {
                        setTextColor(Color.BLACK)
                    } else {
                        setTextColor(Color.RED)
                    }
                    // We make the card visible
                    visibility = View.VISIBLE
                }
            }
            count++
        }
        // We update the players score as well
        binding.playerScore.text = newGame.getPlayerHand().getHandValue().toString()

        /* Updating Dealer UI */
        var count2 = 0
        newGame.getDealerHand().cards.forEach {
            dealerViewList[count2].apply {
                text = it.toString()
                if (it.suit == Card.Suit.CLUBS || it.suit == Card.Suit.SPADES) {
                    setTextColor(Color.BLACK)
                } else {
                    setTextColor(Color.RED)
                }
                visibility = View.VISIBLE
            }
            count2++
        }
        binding.dealerScore.text = newGame.getDealerHand().getHandValue().toString()
    }

    private fun gameOver(){
        // We hide the hit and stand buttons
        binding.buttonHit.visibility = View.GONE
        binding.buttonStand.visibility = View.GONE
        // We make the restart button visible
        binding.buttonRestart.visibility = View.VISIBLE
        binding.buttonRestart.setOnClickListener {
            restartGame()
        }

    }

    private fun restartGame() {
        runNewGame()
    }

    private fun resetViewsAndUI() {
        binding.apply {
            // We show the views that should be hidden while the main menu is open
            buttonHit.visibility = View.VISIBLE
            buttonStand.visibility = View.VISIBLE
            dealerScore.visibility = View.VISIBLE
            titleTextview.visibility = View.GONE
            flowMainMenu.visibility = View.GONE
            authorTextview.visibility = View.GONE
            buttonRestart.visibility = View.GONE
            // Adding the views of the cards into lists
            // We can get all views from flow grouping
            //TODO read about maping
            flowPlayer.referencedIds.forEach {
                playerViewList.add(findViewById<TextView>(it))
            }
            flowDealer.referencedIds.forEach {
                dealerViewList.add(findViewById<TextView>(it))
            }
        }
    }
}