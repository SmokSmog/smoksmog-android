package com.antyzero.smoksmog.time

class PolishCountdown : Countdown {

    override fun get(givenSeconds: Int): String {

        val seconds = givenSeconds and 60
        val minutes = givenSeconds / 60 % 60
        val hours = givenSeconds / 60 / 60

        if (minutes == 0) {
            return secondsAgo(seconds)
        } else if (hours == 0) {
            return minutesAgo(minutes)
        }

        return hoursAgo(hours)
    }

    private fun hoursAgo(hours: Int): String {
        return ago(hours, "godzinę", "godziny", "godzin")
    }

    private fun minutesAgo(minutes: Int): String {
        return ago(minutes, "minutę", "minuty", "minut")
    }

    private fun secondsAgo(seconds: Int): String {
        return ago(seconds, "sekundę", "sekundy", "sekund")
    }

    private fun ago(amount: Int, single: String, some: String, many: String): String {
        if (amount == 1) {
            return amount.toString() + " " + single
        } else if (endsWithTwoToFour(amount)) {
            return amount.toString() + " " + some
        } else {
            return amount.toString() + " " + many
        }
    }

    private fun endsWithTwoToFour(seconds: Int): Boolean {
        val modulo = seconds % 10
        if (seconds >= 10 && seconds < 20) {
            return false
        } else if (modulo >= 2 && modulo <= 4) {
            return true
        }
        return false
    }
}
