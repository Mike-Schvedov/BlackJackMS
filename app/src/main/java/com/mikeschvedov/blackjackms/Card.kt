package com.mikeschvedov.blackjackms


data class Card(val suit: Suit, val rank: Rank) {

    enum class Rank(val value: Int) {
        TWO(2), THREE(3), FOUR(4),
        FIVE(5), SIX(6), SEVEN(7),
        EIGHT(8), NINE(9), TEN(10),
        J(10), Q(10), K(10), A(11)
    }

    enum class Suit(val value: Char) {
        DIAMONDS('♦'), CLUBS('♣'), HEARTS('❤'), SPADES('♠')
    }

    override fun toString(): String {
        val special : List<Rank> = mutableListOf(Rank.J, Rank.Q, Rank.K, Rank.A)
        return if (special.contains(rank)){
            "${rank.name} ${suit.value}"
        }else{
            "${rank.value} ${suit.value}"
        }
    }

    fun getValue(): Int = rank.value

}