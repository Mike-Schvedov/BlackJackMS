package com.mikeschvedov.blackjackms

class Hand(var cards: MutableList<Card>) {
    //TODO fold, reduce
    fun addCard(card: Card) {
        cards.add(card)
    }

    fun getHandValue(): Int {
        var result = 0
        var numOfAces = 0

        cards.forEach { card ->
            result += card.rank.value
            if (card.rank == Card.Rank.A)
                numOfAces++
        }

        while (result > 21 && numOfAces > 0) {
            result -= 10
            numOfAces--
        }
        return result
    }

}