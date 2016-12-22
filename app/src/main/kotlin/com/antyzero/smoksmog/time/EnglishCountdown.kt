package com.antyzero.smoksmog.time

class EnglishCountdown : Countdown {

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
        return ago(hours, "hour", "hours")
    }

    private fun minutesAgo(minutes: Int): String {
        return ago(minutes, "minute", "minutes")
    }

    private fun secondsAgo(seconds: Int): String {
        return ago(seconds, "seconds", "seconds")
    }

    private fun ago(amount: Int, single: String, many: String): String {
        if (amount == 1) {
            return amount.toString() + " " + single
        } else {
            return amount.toString() + " " + many
        }
    }
}
