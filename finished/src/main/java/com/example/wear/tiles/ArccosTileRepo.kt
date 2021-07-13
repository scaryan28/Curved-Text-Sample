package com.example.wear.tiles

class ArccosTileRepo() {

    private var distances: InPlayDistances = InPlayDistances(200, 205)

    fun update() {
//        Timber.v("Updating distances to ${distances.distance}, ${distances.playsLikeDistance} in repo")
        this.distances.distance++
        this.distances.playsLikeDistance++
    }

    fun getDistances() = distances

    data class InPlayDistances(var distance: Int, var playsLikeDistance: Int)
}