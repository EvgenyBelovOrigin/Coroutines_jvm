package ru.netokogy.pusher.dto

data class Attachment(
    val url: String,
    val description: String,
    val type: AttachmentType,
)

enum class AttachmentType (

)

