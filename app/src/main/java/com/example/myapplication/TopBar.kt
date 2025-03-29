package com.example.myapplication

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    navController: NavHostController
) {
    var showMenu by remember { mutableStateOf(false) }
    TopAppBar(
        title = { Text("Donate App") },
        actions = {
            // Show Menu
            IconButton(onClick = {showMenu=true}) {
                Icon(imageVector = Icons.AutoMirrored.Filled.List, contentDescription = "Menu")
            }

            //Dropdown Menu
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                //Menu Items

                DropdownMenuItem(
                    text = { Text("Donation") },
                    onClick = {
                        navController.navigate("donate") // Chuyển về màn hình Donate
                        showMenu = false
                    }
                )

                DropdownMenuItem(
                    text = { Text("Report") },
                    onClick = {
                        navController.navigate("report")
                        showMenu = false
                    }
                )

                DropdownMenuItem(
                    text = { Text("Settings") },
                    onClick = {
                        // TODO: Thêm chức năng Settings sau này
                        showMenu = false
                    }
                )
            }

        }
    )
}

