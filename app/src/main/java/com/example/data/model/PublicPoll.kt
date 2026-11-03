package com.example.data.model

data class PollOption(
    val id: String,
    val textHindi: String,
    val initialVotes: Int
)

data class PublicPoll(
    val id: String,
    val questionHindi: String,
    val subtitleHindi: String = "आपकी राय हमारे लिए महत्वपूर्ण है",
    val options: List<PollOption>,
    val userSelectedOptionId: String? = null,
    val totalVotesOverride: Int? = null,
    val isClosed: Boolean = false,
    val category: String = "राष्ट्रीय / राजस्थान"
) {
    val totalVotes: Int
        get() = totalVotesOverride ?: options.sumOf { it.initialVotes }

    fun getPercentageFor(optionId: String): Int {
        val total = totalVotes
        if (total == 0) return 0
        val opt = options.find { it.id == optionId } ?: return 0
        return ((opt.initialVotes.toDouble() / total) * 100).toInt()
    }
}
