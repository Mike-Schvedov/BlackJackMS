package com.mikeschvedov.blackjackms

import java.util.*

data class Deck(private val cards: MutableList<Card> = mutableListOf()) {

    init {
        Card.Suit.values().forEach { suit ->
            Card.Rank.values().forEach { rank ->
                cards.add(Card(suit, rank))
            }
        }
        shuffle()
    }

    fun dealCard(): Card = cards.removeFirst()

    private fun shuffle() = cards.shuffle()
}