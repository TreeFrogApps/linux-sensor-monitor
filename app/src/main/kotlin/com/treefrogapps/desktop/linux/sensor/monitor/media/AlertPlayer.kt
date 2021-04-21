package com.treefrogapps.desktop.linux.sensor.monitor.media

import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javax.inject.Inject

class AlertPlayer @Inject constructor() {

    companion object {
        @JvmStatic private fun MediaPlayer.isPlaying(): Boolean = status == MediaPlayer.Status.PLAYING
        @JvmStatic private fun MediaPlayer.isNotPlaying(): Boolean = !isPlaying()
    }

    private val mediaPlayer: MediaPlayer by lazy { MediaPlayer(Media(AlertPlayer::class.java.getResource("/media/alert.mp3")?.toExternalForm())) }

    fun playAlert() {
        with(mediaPlayer) {
            stop()
            play()
        }
    }
}