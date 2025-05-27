package com.example.tp3

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlin.coroutines.cancellation.CancellationException

sealed interface Sound
data class ResourceSound(val id: Int): Sound
data class Silence(val duration: Int /* in millis */): Sound

suspend fun Context.playSounds(sounds: List<Sound>) {
    val channel = Channel<Boolean>(1)
    val mp = MediaPlayer()
    try {
        sounds.forEach { sound ->
            when(sound) {
                is ResourceSound -> {
                    mp.setOnPreparedListener {
                        channel.trySend(true)
                    }
                    val uri = Uri.parse("android.resource://$packageName/${sound.id}")
                    mp.setDataSource(this, uri)
                    mp.prepareAsync()
                    channel.receive() // wait for the completion of the preparation
                    mp.setOnCompletionListener { channel.trySend(true) }
                    mp.start()
                    try {
                        channel.receive()
                    } catch (e: CancellationException) {
                        // stop the sound when the coroutine is cancelled
                        mp.stop()
                    }
                    mp.reset()
                }
                is Silence -> {
                    delay(sound.duration.toLong())
                }
            }
        }
    } finally {
        mp.release()
    }
}