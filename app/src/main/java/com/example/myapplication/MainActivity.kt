package com.example.myapplication


import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { AppTopBar(navController = navController) }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "donate",
                        modifier = Modifier.padding(innerPadding)
                    )
                    {
                        composable("donate") {
                            DonateScreen(navController, Modifier)
                        }
                        composable("report") {
                            ReportScreen()
                        }
                    }
                }

            }

        }
    }
}

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


@Composable
fun DonateScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: DonationViewModel = viewModel()
) {

    var selectedPayment by remember { mutableStateOf("PayPal") }
    var amount by remember { mutableIntStateOf(0) }
    val context = LocalContext.current

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    Toast.makeText(context, "Replace with your own action", Toast.LENGTH_SHORT)
                        .show()
                }
            ) {
                Text("+")
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = stringResource(id = R.string.donateTitle),
                fontSize = 24.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                style = MaterialTheme.typography.headlineSmall

            )

            Spacer(modifier = Modifier.height(16.dp))

            // Subtitle
            Text(
                text = stringResource(id = R.string.donateSubtitle),
                fontSize = 18.sp,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Payment Method (Radio Buttons)
            Text("Select Payment Method:", fontSize = 16.sp)
            Row {
                listOf("PayPal", "Direct").forEach { method ->
                    RadioButton(
                        selected = (selectedPayment == method),
                        onClick = { selectedPayment = method }
                    )
                    Text(method, modifier = Modifier.padding(end = 8.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Number Picker (Amount Selection)
            Text("Select Donation Amount: $amount", fontSize = 16.sp)
            Slider(
                value = amount.toFloat(),
                onValueChange = { amount = it.toInt() },
                valueRange = 0f..1000f,
                steps = 100
            )

            // Progress Bar
            Spacer(modifier = Modifier.height(16.dp))

            LinearProgressIndicator(progress = { viewModel.totalDonated.value / 1000f })

            Spacer(modifier = Modifier.height(16.dp))

            // Donate Button
            Button(
                onClick = {
                    viewModel.addDonation(amount, selectedPayment) //lưu donate vào viewmodel

                    Toast.makeText(
                        context,
                        "Donated $amount via $selectedPayment!",
                        Toast.LENGTH_SHORT
                    ).show()
                    navController.navigate("report")
                }
            )
            {
                Text(text = stringResource(id = R.string.donateButton))
            }
        }
    }

}

