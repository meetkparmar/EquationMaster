package com.meet.project.equationmaster.network.models

data class History(
    var id: Long,
    var equationsList: List<String> = listOf(),
    var answers: List<String> = listOf(),
)