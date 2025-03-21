package com.example.myapplication


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { AppTopBar() }) { innerPadding ->
                    DonateScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar() {
    TopAppBar(
        title = { Text("Donate App") },
        actions = {
            IconButton(
                onClick = {
                    Log.v("Donate", "Settings clicked")
                }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Settings")
            }
        }
    )
}


@Composable
fun DonateScreen(modifier: Modifier = Modifier) {

    var selectedPayment by remember { mutableStateOf("PayPal") }
    var amount by remember { mutableIntStateOf(0) }
    var totalDonated by remember { mutableIntStateOf(0) }
    var context = LocalContext.current

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    Toast.makeText(context, "Replace with your own action", Toast.LENGTH_SHORT).show()
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


            LinearProgressIndicator(progress = totalDonated / 1000f)

            Spacer(modifier = Modifier.height(16.dp))

            // Donate Button
            Button(
                onClick = {
                    totalDonated += amount
                    Log.v("Donate", "Donate button clicked with $amount via $selectedPayment")
                    Log.v("Donate", "Current total: ")
                    Toast.makeText(context, "Donated $amount via $selectedPayment!", Toast.LENGTH_SHORT).show()
                }
            )
            {
                Text(text = stringResource(id = R.string.donateButton))
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        DonateScreen()
    }
}