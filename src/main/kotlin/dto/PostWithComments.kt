package ru.netokogy.pusher.dto

data class PostWithComments(
    val post: Post,
    val comments: List<Comment>,
)
