package com.example.myapplication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.Card
import androidx.compose.foundation.lazy.items



// Tạo file ReportScreen.kt trong package hiện tại
@Composable
fun ReportScreen(viewModel: DonationViewModel = viewModel()) {
    val donations = viewModel.donations  // ✅ Sử dụng trực tiếp danh sách từ ViewModel

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Donation Report", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(donations) { donation ->
                DonationItem(donation = donation)
            }
        }
    }
}

@Composable
fun DonationItem(donation: Donation) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Amount: $${donation.amount}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Method: ${donation.method}", style = MaterialTheme.typography.bodyMedium)
        }

    }
}