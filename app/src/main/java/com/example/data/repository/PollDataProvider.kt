package com.example.data.repository

import com.example.data.model.PollOption
import com.example.data.model.PublicPoll

object PollDataProvider {

    fun getDefaultPublicPoll(): PublicPoll {
        return PublicPoll(
            id = "poll_solar_energy_2026",
            questionHindi = "क्या भारत को 2030 तक 50% बिजली अक्षय ऊर्जा (सौर एवं पवन) से बनाने का लक्ष्य समय से पहले हासिल हो जाएगा?",
            subtitleHindi = "फर्स्ट इंडिया जनमत सर्वेक्षण • आपकी राय मायने रखती है",
            category = "राष्ट्रीय एवं राज्य विकास",
            options = listOf(
                PollOption(
                    id = "opt_yes",
                    textHindi = "हाँ, राजस्थान और गुजरात में रिकॉर्ड गति से सौर पार्क बन रहे हैं",
                    initialVotes = 18420
                ),
                PollOption(
                    id = "opt_no",
                    textHindi = "नहीं, ग्रिड कनेक्टिविटी और बैटरी स्टोरेज की चुनौतियां बड़ी हैं",
                    initialVotes = 5380
                ),
                PollOption(
                    id = "opt_cant_say",
                    textHindi = "कह नहीं सकते / औद्योगिक नीतियों की और समीक्षा जरूरी है",
                    initialVotes = 2140
                )
            ),
            userSelectedOptionId = null,
            totalVotesOverride = null
        )
    }
}
