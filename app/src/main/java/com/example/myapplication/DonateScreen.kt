package com.example.myapplication

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@Composable
fun DonateScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: DonationViewModel
) {

    var selectedPayment by remember { mutableStateOf("PayPal") }
    var amount by remember { mutableIntStateOf(0) }
    val context = LocalContext.current

    val totalDonated by viewModel.totalDonated.collectAsState()

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

            LinearProgressIndicator(progress = { totalDonated.toFloat() / viewModel.target })

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
//                    navController.navigate("report")
                }
            )
            {
                Text(text = stringResource(id = R.string.donateButton))
            }
        }
    }

}

