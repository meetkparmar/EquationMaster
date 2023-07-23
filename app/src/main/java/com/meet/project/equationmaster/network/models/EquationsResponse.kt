package com.meet.project.equationmaster.network.models

data class EquationsResponse(
    var result: List<String> = listOf(),
    var error: String? = null
)