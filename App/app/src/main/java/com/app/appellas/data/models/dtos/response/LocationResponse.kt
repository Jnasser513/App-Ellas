package com.app.appellas.data.models.dtos.response

data class LocationResponse(
    val id: Long,
    val latitud: Double,
    val longitud: Double,
    val maps_id: String,
    val titulo: String,
    val descripcion: String,
    val user: UserResponse
)
