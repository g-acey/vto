package ord.ibda.vto.ui.editprofile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ord.ibda.vto.R
import ord.ibda.vto.ui.editprofile.viewmodel.EditProfileEvent
import ord.ibda.vto.ui.editprofile.viewmodel.EditProfileViewModel

@Composable
fun EditProfileScreen(
    goBack: () -> Unit,
    goProfile: () -> Unit,
    editProfileViewModel: EditProfileViewModel,
    modifier: Modifier = Modifier
) {
    val editProfileState by editProfileViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        editProfileViewModel.onEvent(EditProfileEvent.LoadUserProfile)
    }

    Scaffold(
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 30.dp)
            ) {
                Button(
                    onClick = {
                        editProfileViewModel.onEvent(EditProfileEvent.SaveProfile)
                        goProfile()
                    },
                    enabled = editProfileState.canSave,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(53.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimaryContainer),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "Save",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.labelLarge,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        },
        modifier = Modifier
            .padding(top = 25.dp)
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { goBack() }
                )
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = "Personal Details",
                    style = MaterialTheme.typography.displaySmall
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = editProfileState.username,
                onValueChange = { editProfileViewModel.onEvent(EditProfileEvent.UpdateUsername(it)) },
                label = { Text("Username") },
                isError = editProfileState.isErrorUsername,
                supportingText = {
                    if (editProfileState.isErrorUsername) {
                        Text(text = "* Username already taken")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = editProfileState.password,
                onValueChange = { editProfileViewModel.onEvent(EditProfileEvent.UpdatePassword(it)) },
                label = { Text("Password") },
                isError = editProfileState.isErrorPassword,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                maxLines = 1,
                supportingText = {
                    if (editProfileState.isErrorPassword) {
                        Text(
                            text = stringResource(R.string.password_criteria)
                        )
                    }
                }
            )

//            Spacer(modifier = Modifier.height(16.dp))
//
//            OutlinedTextField(
//                value = email,
//                onValueChange = { email = it },
//                placeholder = { Text("Email") },
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Row(
//                horizontalArrangement = Arrangement.spacedBy(16.dp),
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                OutlinedTextField(
//                    value = birthday,
//                    onValueChange = { birthday = it },
//                    placeholder = { Text("Birthday") },
//                    modifier = Modifier.weight(1f)
//                )
//                OutlinedTextField(
//                    value = gender,
//                    onValueChange = { gender = it },
//                    placeholder = { Text("Gender") },
//                    modifier = Modifier.weight(1f)
//                )
//            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun EditProfileScreenPreview() {
//    AppTheme {
//        EditProfileScreen(goBack = {})
//    }
//}