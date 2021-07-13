/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.wear.tiles

import android.graphics.Color
import androidx.wear.tiles.ColorBuilders.argb
import androidx.wear.tiles.DimensionBuilders.*
import androidx.wear.tiles.LayoutElementBuilders
import androidx.wear.tiles.LayoutElementBuilders.*
import androidx.wear.tiles.ModifiersBuilders
import androidx.wear.tiles.RequestBuilders.ResourcesRequest
import androidx.wear.tiles.RequestBuilders.TileRequest
import androidx.wear.tiles.ResourceBuilders.AndroidImageResourceByResId
import androidx.wear.tiles.ResourceBuilders.ImageResource
import androidx.wear.tiles.ResourceBuilders.Resources
import androidx.wear.tiles.TileBuilders.Tile
import androidx.wear.tiles.TileProviderService
import androidx.wear.tiles.TimelineBuilders.Timeline
import androidx.wear.tiles.TimelineBuilders.TimelineEntry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.guava.future

class GoalsTileService : TileProviderService() {

    companion object {
        private const val RESOURCES_VERSION = "1"
    }

    // For coroutines, use a custom scope we can cancel when the service is destroyed
    private val serviceScope = CoroutineScope(Dispatchers.IO)

    // TODO: Build a Tile.
    override fun onTileRequest(requestParams: TileRequest) = serviceScope.future {
        // Creates Tile.
        Tile.builder()
            // If there are any graphics/images defined in the Tile's layout, the system will
            // retrieve them via onResourcesRequest() and match them with this version number.
            .setResourcesVersion(RESOURCES_VERSION)

            // Creates a timeline to hold one or more tile entries for a specific time periods.
            .setTimeline(
                Timeline.builder().addTimelineEntry(
                    TimelineEntry.builder().setLayout(
                        Layout.builder().setRoot(
                            Box.builder()
                                .setHeight(wrap())
                                .setWidth(wrap())
                                .addContent(
                                    createTestCurvedText()
                                )
                                .setVerticalAlignment(VERTICAL_ALIGN_BOTTOM)
                        )
                    )
                )
            ).build()
    }

    // TODO: Supply resources (graphics) for the Tile.
    override fun onResourcesRequest(requestParams: ResourcesRequest) = serviceScope.future {
        Resources.builder()
            .setVersion(RESOURCES_VERSION)
            .addIdToImageMapping(
                "ic_run",
                ImageResource.builder()
                    .setAndroidResourceByResId(
                        AndroidImageResourceByResId.builder()
                            .setResourceId(com.example.wear.tiles.R.drawable.ic_run)
                    )
            )
            .build()
    }

    // TODO: Review onDestroy() - cancellation of the serviceScope
    override fun onDestroy() {
        super.onDestroy()
        // Cleans up the coroutine
        serviceScope.cancel()
    }

    /*
     This creates text at the bottom that is upside down and backwards
     As far as I can tell there is no api on either the arc or the arc text to fix this
     */
    private fun createTestCurvedText() = Arc
        .builder()
        .setAnchorAngle(DegreesProp.builder().setValue(180f))
        .addContent(
            makeArcTextWithFont()
        )
        .build()

    private fun makeArcTextWithFont() = ArcText
        .builder()
        .setText("Testing Curved Text")
        .setFontStyle(
            FontStyle.builder()
                .setColor(argb(Color.WHITE))
                .setSize(sp(12f))
                .setWeight(
                    FontWeightProp.builder().setValue(FONT_WEIGHT_BOLD).build()
                )
        )
        .build()

}
