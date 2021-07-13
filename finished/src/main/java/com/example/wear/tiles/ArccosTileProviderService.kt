package com.example.wear.tiles

import android.graphics.Color
import androidx.wear.tiles.*
import androidx.wear.tiles.ColorBuilders.argb
import androidx.wear.tiles.DimensionBuilders.*
import androidx.wear.tiles.LayoutElementBuilders.*
import androidx.wear.tiles.R
import androidx.wear.tiles.RequestBuilders.ResourcesRequest
import androidx.wear.tiles.RequestBuilders.TileRequest
import androidx.wear.tiles.ResourceBuilders.Resources
import androidx.wear.tiles.TileBuilders.Tile
import androidx.wear.tiles.TimelineBuilders.Timeline
import androidx.wear.tiles.TimelineBuilders.TimelineEntry

import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.guava.future
import kotlinx.coroutines.launch


class ArccosTileProviderService : TileProviderService() {

    private val serviceScope = CoroutineScope(Dispatchers.IO)

    private var padding = 0f

    companion object {
        private const val RESOURCES_VERSION = "1"
    }

    // Builds tile UI
    override fun onTileRequest(requestParams: TileRequest) = serviceScope.future {
        Tile.builder()
            // If there are any graphics/images defined in the Tile's layout, the system will
            // retrieve them using onResourcesRequest() and match them with this version number.
            .setResourcesVersion(RESOURCES_VERSION)

            // Creates a timeline to hold one or more tile entries for a specific time periods.
            .setTimeline(
                Timeline.builder().addTimelineEntry(
                    TimelineEntry.builder().setLayout(
                        Layout.builder().setRoot(
                            Box.builder()
                                .setHeight(expand())
                                .setWidth(expand())
                                .addContent(
                                    createArc(Color.LTGRAY, 360f)
                                )
                                .addContent(
                                    createArc(Color.GREEN, 120f)
                                )
                                .addContent(
                                    Column.builder()
                                        .setHeight(expand())
                                        .setWidth(wrap())
                                        .addContent(
                                            Row.builder()
                                                .setWidth(wrap())
                                                .setHeight(wrap())
                                                .addContent(
                                                    Text.builder().setText("Hole #6 ")
                                                        .setFontStyle(
                                                            FontStyle.builder()
                                                                .setColor(argb(Color.GREEN))
                                                                .setSize(sp(12f))
                                                                .setWeight(FONT_WEIGHT_BOLD)
                                                        )
                                                )
                                                .addContent(
                                                    Text.builder().setText("/ 18")
                                                        .setFontStyle(
                                                            FontStyle.builder()
                                                                .setColor(argb(Color.LTGRAY))
                                                                .setSize(sp(12f))
                                                                .setWeight(FONT_WEIGHT_BOLD)
                                                        )
                                                )
                                                .setModifiers(
                                                    ModifiersBuilders.Modifiers.builder()
                                                        .setPadding(
                                                            ModifiersBuilders.Padding.builder()
                                                                .setTop(
                                                                    dp(20f)
                                                                )
                                                                .setBottom(dp(8f))
                                                        )
                                                )
                                        )
                                        .addContent(
                                            Column.builder()
                                                .setWidth(expand())
                                                .setHeight(wrap())
                                                .addContent(
                                                    Text.builder().setText("1,273")
                                                        .setFontStyle(
                                                            FontStyle.builder().setSize(sp(20f))
                                                                .setWeight(FONT_WEIGHT_BOLD)
                                                                .setItalic(true)
                                                        )
                                                )
                                                .addContent(
                                                    Text.builder().setText("Steps")
                                                        .setFontStyle(
                                                            FontStyle.builder().setSize(sp(16f))
                                                                .setColor(argb(Color.LTGRAY))
                                                        )
                                                )
                                        )
//                                        .addContent(
//                                            Spacer.builder().setHeight(dp(8f))
//                                        )
                                        .addContent(
                                            Column.builder()
                                                .setWidth(wrap())
                                                .setHeight(wrap())
                                                .addContent(
                                                    Text.builder().setText("802")
                                                        .setFontStyle(
                                                            FontStyle.builder().setSize(sp(40f))
                                                                .setWeight(FONT_WEIGHT_BOLD)
                                                                .setItalic(true)
                                                        )
                                                )
                                                .addContent(
                                                    Text.builder().setText("Calories")
                                                        .setFontStyle(
                                                            FontStyle.builder().setSize(sp(16f))
                                                                .setColor(argb(Color.LTGRAY))
                                                        )
                                                )
                                        )
                                )
                        )
                    )
                )
            ).build()
    }

    private fun createArc(colorResId: Int, angle: Float) = Arc
        .builder()
        .addContent(
            ArcLine.builder()
                .setColor(argb(colorResId))
                .setLength(degrees(angle))
                .setThickness(dp(6f))
        )
        .setAnchorAngle(degrees(0f))
        .setAnchorType(ARC_ANCHOR_START)
        .build()

    // Supplies tile UI resources
    override fun onResourcesRequest(requestParams: ResourcesRequest): ListenableFuture<Resources> {
        return Futures.immediateFuture(
            Resources.builder()
                .setVersion(RESOURCES_VERSION)
                .build()
        )
    }


    override fun onDestroy() {
        super.onDestroy()
        // Cleans up the coroutine
        serviceScope.cancel()
    }

}
