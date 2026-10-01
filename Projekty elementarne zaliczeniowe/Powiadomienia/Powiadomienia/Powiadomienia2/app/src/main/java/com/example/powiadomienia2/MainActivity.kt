package com.example.powiadomienia2

import android.Manifest
import android.os.Bundle
import android.widget.RadioGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.powiadomienia2.ui.theme.Powiadomienia2Theme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {

    @ExperimentalPermissionsApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Powiadomienia2Theme {
                val waterNotificationService = WaterNotificationService(this)
                NotificationScreen(waterNotificationService)
            }
        }
    }
    @ExperimentalPermissionsApi
    @Composable
    fun NotificationScreen(waterNotificationService: WaterNotificationService) {
        var notificationTitle by remember { mutableStateOf("") }
        var notificationDescription by remember { mutableStateOf("") }
        var notificationExpandedDescription by remember { mutableStateOf("") }

        var selectedImageOption by remember { mutableStateOf("") }
        var selectedLayoutOption by remember { mutableStateOf("") }

        // Check and request notification permission
        val postNotificationPermission =
            rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

        LaunchedEffect(key1 = true) {
            if (!postNotificationPermission.status.isGranted) {
                postNotificationPermission.launchPermissionRequest()
            }
        }

        // Display buttons for notifications
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {

            OutlinedTextField(
                value = notificationTitle,
                onValueChange = { notificationTitle = it },
                label = { Text("Enter Notification Title") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = notificationDescription,
                onValueChange = { notificationDescription = it },
                label = { Text("Enter Notification Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            OutlinedTextField(
                value = notificationExpandedDescription,
                onValueChange = { notificationExpandedDescription = it },
                label = { Text("Enter Expanded Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            KindRadioGroup(
                mItems = listOf("Image 1", "Image 2"),
                selected = selectedImageOption,
                setSelected = { selectedImageOption = it }
            )

            KindRadioGroup(
                mItems = listOf("Layout 1", "Layout 2"),
                selected = selectedLayoutOption,
                setSelected = { selectedLayoutOption = it }
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    val imageResId = if (selectedImageOption == "Image 1") R.drawable.img_1 else R.drawable.img_2
                    val layoutResId = if (selectedLayoutOption == "Layout 1") R.layout.custom_notification_layout_1 else R.layout.custom_notification_layout_2

                    waterNotificationService.showCustomNotification(
                        notificationTitle,
                        notificationDescription,
                        notificationExpandedDescription,
                        imageResId,
                        layoutResId
                    )
                }
            ) {
                Text(text = "Show Custom Notification")
            }
        }
    }

    @Composable
    fun KindRadioGroup(
        mItems: List<String>,
        selected: String,
        setSelected: (selected: String) -> Unit,
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                mItems.forEach { item ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selected == item,
                            onClick = {
                                setSelected(item)
                            },
                            enabled = true,
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color(0xFFBD5C96)
                            )
                        )
                        Text(text = item, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }
        }


    }
}
