package com.example.shoppingcenternavigator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun GPS() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val istanbul = LatLng(41.015137, 28.979530)
        val capacity = LatLng(40.977401270276204, 28.871080282120634)
        val carousel = LatLng(40.977830565212244, 28.873236778161665)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(carousel, 15f)
        }
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = capacity),
                title = "Capacity Mall",
                snippet = "Marker in Capacity Mall"
            )
            Marker(
                state = MarkerState(position = carousel),
                title = "Carousel Mall",
                snippet = "Marker in Carousel Mall"
            )
        }
    }
}