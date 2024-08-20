package ru.netokogy.pusher

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okio.Timeout
import ru.netokogy.pusher.dto.Comment
import ru.netokogy.pusher.dto.Post
import ru.netokogy.pusher.dto.PostWithComments
import java.io.IOException
import java.lang.RuntimeException
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


private const val BASE_URL = "http://192.168.1.36:9999"
private val gson = Gson()

private val client = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor(::println).apply {
        level = HttpLoggingInterceptor.Level.BASIC
    })
    .connectTimeout(30, TimeUnit.SECONDS)
    .build()


fun main() = runBlocking {
    val job = with(CoroutineScope(EmptyCoroutineContext)) {
        launch {
            try {
                val postsTypeToken = object : TypeToken<List<Post>>() {}
                val posts: List<Post> = client.apiCall(
                    "$BASE_URL/api/slow/posts"
                ).let { response ->
                    if (!response.isSuccessful) {
                        response.close()
                        throw RuntimeException(response.message)
                    }
                    val body = response.body ?: throw RuntimeException("response body is null")
                    gson.fromJson(body.string(), postsTypeToken.type)
                }
                val id = posts.last().id
                launch {
                    try {
                        val commentsTypeToken = object : TypeToken<List<Comment>>() {}
                        val comments: List<Comment> = client.apiCall(
                            "$BASE_URL/api/slow/posts/$id/comments"
                        ).let { response ->
                            if (!response.isSuccessful) {
                                response.close()
                                throw RuntimeException(response.message)
                            }
                            val body = response.body ?: throw RuntimeException("response body is null")
                            gson.fromJson(body.string(), commentsTypeToken.type)
                        }
                        println(comments)
                        println(this)
                        println("$isActive in child job")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    delay(3000)
    println(job.children.joinToString(", "))
    job.cancel()
    println(job.children.joinToString(", "))
    job.join()
}

suspend fun OkHttpClient.apiCall(url: String): Response {
    return suspendCoroutine { continuation ->
        Request.Builder()
            .url(url)
            .build()
            .let(::newCall)
            .enqueue(object : Callback {

                override fun onResponse(call: Call, response: Response) {
                    continuation.resume(response)
                }

                override fun onFailure(call: Call, e: IOException) {
                    continuation.resumeWithException(e)
                }


            })

    }
}
