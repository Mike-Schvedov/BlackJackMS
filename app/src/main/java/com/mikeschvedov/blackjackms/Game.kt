package com.mikeschvedov.blackjackms

class Game {
    private lateinit var mDeck: Deck
    private lateinit var playerHand: Hand
    private lateinit var dealerHand: Hand

    enum class Condition(var desc: String) {
        PLAYER_WIN("The Player Won"),
        DEALER_WIN("The Dealer Won"),
        TIE("It is a Tie"),
        DEFAULT("Still nothing")
    }

    init {
        startNewGame()
    }

    private fun startNewGame() {
        mDeck = Deck()
        playerHand = Hand(mutableListOf(mDeck.dealCard(), mDeck.dealCard()))
        dealerHand = Hand(mutableListOf(mDeck.dealCard()))
    }

    fun hit(hand: Hand): Card {
        val newCard = mDeck.dealCard()
        hand.addCard(newCard)
        return newCard
    }

    fun stand(): Condition {
        // Dealer takes cards until 17
        while (dealerHand.getHandValue() < 17) {
            hit(dealerHand)
        }
        val dealerValue = dealerHand.getHandValue()
        val playerValue = playerHand.getHandValue()
        // When reached 17 or more
        when {
            // If Dealer has less than player
            dealerValue < playerValue -> return Condition.PLAYER_WIN
            // If Dealer has the same as player
            dealerValue == playerValue -> return Condition.TIE
            // If Dealer has more than 21 (busted)
            dealerValue > 21 -> return Condition.PLAYER_WIN
            // Dealer has more than player
            dealerValue > playerValue -> return Condition.DEALER_WIN
        }
        return Condition.DEFAULT
    }

    fun checkIf21(): Boolean {
        //if the player has 21 with 2 cards.
        if (playerHand.getHandValue() == 21) {
            return true
        }
        return false
    }

    fun checkBlackJack(): Boolean {
        //if the player has 21 with 2 cards.
        if (playerHand.getHandValue() == 21) {
            return true
        }
        return false
    }

    fun checkIfBusted(): Boolean {
        //if the player has more than 21
        if (playerHand.getHandValue() > 21) {
            return true
        }
        return false
    }

    fun getPlayerHand(): Hand {
        return playerHand
    }

    fun getDealerHand(): Hand {
        return dealerHand
    }
}