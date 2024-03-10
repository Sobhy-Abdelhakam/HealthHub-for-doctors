package dev.sobhy.healthhubfordoctors.authfeature.presentation.register

import android.text.format.DateFormat
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.KeyboardDoubleArrowLeft
import androidx.compose.material.icons.filled.KeyboardDoubleArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dev.sobhy.healthhubfordoctors.R
import java.time.Year

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalInformation(
    modifier: Modifier = Modifier,
    name: String,
    onNameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    gender: String,
    onGenderChange: (String) -> Unit,
    date: String,
    onDateChange: (String) -> Unit,
    onNextClick: () -> Unit,
) {
    var showDatePicker by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        item {
            Text(
                text = stringResource(R.string.personal_information),
                style = MaterialTheme.typography.headlineSmall,
            )
        }
        item {
            TextField(
                value = name,
                onValueChange = onNameChange,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                label = {
                    Text(text = stringResource(R.string.name))
                },
                singleLine = true,
                keyboardOptions =
                    KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                    ),
            )
        }
        item {
            TextField(
                value = email,
                onValueChange = onEmailChange,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                label = {
                    Text(text = stringResource(R.string.email))
                },
                singleLine = true,
                keyboardOptions =
                    KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next,
                    ),
            )
        }
        item {
            TextField(
                value = phoneNumber,
                onValueChange = onPhoneNumberChange,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                label = {
                    Text(text = stringResource(R.string.phone_number))
                },
                singleLine = true,
                keyboardOptions =
                    KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Next,
                    ),
                prefix = {
                    Text(text = "+20")
                },
            )
        }
        item {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = stringResource(R.string.gender))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier =
                        Modifier.clickable {
                            onGenderChange("Male")
                        },
                ) {
                    RadioButton(
                        selected = gender == "Male",
                        onClick = { onGenderChange("Male") },
                    )
                    Text(text = stringResource(R.string.male))
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier =
                        Modifier.clickable {
                            onGenderChange("Female")
                        },
                ) {
                    RadioButton(
                        selected = gender == "Female",
                        onClick = { onGenderChange("Female") },
                    )
                    Text(text = stringResource(R.string.female))
                }
            }
        }
        item {
            TextField(
                value =
                    if (date.isEmpty()) {
                        ""
                    } else {
                        DateFormat.format("dd/MM/yyyy", date.toLong()).toString()
                    },
                onValueChange = onDateChange,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                label = {
                    Text(text = stringResource(R.string.data_of_birth))
                },
                singleLine = true,
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = "Calendar Icon",
                        )
                    }
                },
            )
        }
        item {
            val currentLanguage = LocalConfiguration.current.locale.language
            Row(modifier = Modifier.padding(16.dp)) {
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    enabled = name.isNotEmpty() && email.isNotEmpty() && phoneNumber.isNotEmpty() && gender.isNotEmpty() && date.isNotEmpty(),
                    onClick = onNextClick,
                ) {
                    Text(text = stringResource(R.string.next))
                    Icon(
                        imageVector =
                            if (currentLanguage == "ar") {
                                Icons.Default.KeyboardDoubleArrowLeft
                            } else {
                                Icons.Default.KeyboardDoubleArrowRight
                            },
                        contentDescription = null,
                    )
                }
            }
        }
    }
    val datePickerState =
        rememberDatePickerState(
            yearRange = Year.now().value - 80..Year.now().value - 25,
        )
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                        onDateChange(datePickerState.selectedDateMillis.toString())
                    },
                    enabled = datePickerState.selectedDateMillis != null,
                ) {
                    Text(text = stringResource(R.string.confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(text = stringResource(R.string.cancel))
                }
            },
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
