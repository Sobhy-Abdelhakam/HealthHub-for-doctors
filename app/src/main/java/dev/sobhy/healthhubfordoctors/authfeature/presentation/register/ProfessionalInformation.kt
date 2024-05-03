package dev.sobhy.healthhubfordoctors.authfeature.presentation.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardDoubleArrowLeft
import androidx.compose.material.icons.filled.KeyboardDoubleArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import dev.sobhy.healthhubfordoctors.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessionalInformation(
    modifier: Modifier = Modifier,
    specialization: () -> String,
    onSpecializationChange: (String) -> Unit,
    professionalTitle: () -> String,
    onProfessionalTitleChange: (String) -> Unit,
    onNextClick: () -> Unit,
) {
    var menuExpanded by remember {
        mutableStateOf(false)
    }
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        item {
            Text(
                text = stringResource(R.string.professional_information),
                style = MaterialTheme.typography.headlineSmall,
            )
        }
        item {
            ExposedDropdownMenuBox(
                expanded = menuExpanded,
                onExpandedChange = { menuExpanded = !menuExpanded },
                modifier = Modifier.fillMaxWidth().padding(16.dp),
            ) {
                TextField(
                    value = specialization(),
                    onValueChange = { },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                    label = {
                        Text(text = stringResource(R.string.specialization))
                    },
                    singleLine = true,
                    readOnly = true,
                    keyboardOptions =
                        KeyboardOptions(
                            imeAction = ImeAction.Next,
                        ),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuExpanded)
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                )
                ExposedDropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    doctorSpecialties.forEach { specialty ->
                        DropdownMenuItem(
                            text = {
                                Text(text = specialty)
                            },
                            onClick = {
                                onSpecializationChange(specialty)
                                menuExpanded = false
                            },
                        )
                    }
                }
            }
        }
        item {
            TextField(
                value = professionalTitle(),
                onValueChange = onProfessionalTitleChange,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                label = {
                    Text(text = stringResource(R.string.professional_title))
                },
                singleLine = true,
                keyboardOptions =
                    KeyboardOptions(
                        imeAction = ImeAction.Done,
                    ),
            )
        }
        item {
            val currentLanguage = LocalConfiguration.current.locale.language
            val buttonEnabled by remember {
                derivedStateOf {
                    specialization().isNotEmpty() && professionalTitle().isNotEmpty()
                }
            }
            Row(modifier = Modifier.padding(16.dp)) {
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    enabled = buttonEnabled,
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
}

@Composable
fun SpecialtiesDialog(
    showDialog: Boolean,
    showChange: (Boolean) -> Unit,
) {
//    if (showDialog) {
    Dialog(
        onDismissRequest = { showChange(false) },
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .background(color = MaterialTheme.colorScheme.background),
        ) {
            Text(text = "Specialties", style = MaterialTheme.typography.titleLarge)
            doctorSpecialties.forEach { specialty ->
                Text(text = specialty, style = MaterialTheme.typography.titleMedium)
            }
        }
    }
//    }
}

val doctorSpecialties =
    listOf(
        "General Practice",
        "Internal Medicine",
        "Pediatrics",
        "Obstetrics and Gynecology",
        "Surgery",
        "Psychiatry",
        "Anesthesiology",
        "Radiology",
        "Dermatology",
        "Orthopedics",
        "Neurology",
        "Cardiology",
        "Oncology",
        "Ophthalmology",
        "Otolaryngology",
        "Urology",
        "Gastroenterology",
        "Endocrinology",
        "Nephrology",
        "Pulmonology",
    )
