package ru.netokogy.pusher.dto

data class PostWithAuthorsAndComments(
    val post: Post,
    val postAuthor:Author,
    val commentsWithAuthor: List<Comment>,
    )
//data class CommentWithAuthor(
//    val comment:Comment,
//    val commentAuthor: Author,

//)