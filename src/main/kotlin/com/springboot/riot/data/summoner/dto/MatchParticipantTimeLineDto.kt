package com.springboot.riot.data.summoner.dto

data class MatchParticipantTimeLineDto(
    var gameId: Long = 0L,
    val participantId: Int = 0,
    val role: String? = null,
    val lane: String? = null,

    val goldPerMinDeltas: MatchParticipantTimeLineDeltaDto? = null,
    val creepsPerMinDeltas: MatchParticipantTimeLineDeltaDto? = null,
    val xpPerMinDeltas: MatchParticipantTimeLineDeltaDto? = null,
    val csDiffPerMinDeltas: MatchParticipantTimeLineDeltaDto? = null,
    val xpDiffPerMinDeltas: MatchParticipantTimeLineDeltaDto? = null,
    val damageTakenPerMinDeltas: MatchParticipantTimeLineDeltaDto? = null,
    val damageTakenDiffPerMinDeltas: MatchParticipantTimeLineDeltaDto? = null,

)