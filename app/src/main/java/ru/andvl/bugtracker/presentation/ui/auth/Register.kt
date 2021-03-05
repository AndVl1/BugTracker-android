package ru.andvl.bugtracker.presentation.ui.auth

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.andvl.bugtracker.MainViewModel
import ru.andvl.bugtracker.R
import ru.andvl.bugtracker.navigation.Destinations
import timber.log.Timber

@Composable
fun CheckEmailPage(
    viewModel: MainViewModel,
    navController: NavController,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        RegisterCheckEmail(
            login = viewModel.emailCheckString,
            isEmailAvailable = viewModel.isEmailAvailable,
            onEmailInputChangeListener = { viewModel.onEmailInputChanged() },
            onButtonClickListener = { navController.navigate(Destinations.NicknamePasswordInput) }
        )
    }
}

@Composable
fun RegisterCheckEmail(
    login: MutableState<String>,
    isEmailAvailable: SharedFlow<Boolean>,
    onEmailInputChangeListener: () -> Unit,
    onButtonClickListener: () -> Unit,
) {

    val layoutPadding = dimensionResource(id = R.dimen.auth_padding)

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(layoutPadding)
    ) {
        val (
            imageRefs,
            loginRefs,
            buttonRefs,
        ) = createRefs()

        val imageMarginTop = dimensionResource(id = R.dimen.auth_content_margin_top)
        val elementsMargin = dimensionResource(id = R.dimen.elements_margin)
        val buttonHeight = dimensionResource(id = R.dimen.auth_button_height)

        val emailAvailabilityState = isEmailAvailable.collectAsState(initial = true)

        Image(
            painter = painterResource(id = R.drawable.bug),
            contentDescription = stringResource(R.string.app_icon),
            modifier = Modifier.constrainAs(imageRefs) {
                top.linkTo(parent.top, margin = imageMarginTop)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        TextField(
            value = login.value,
            onValueChange = {
                onEmailInputChangeListener()
                login.value = it
            },
            label = { Text(text = stringResource(R.string.register_email_check)) },
            modifier = Modifier
                .constrainAs(loginRefs) {
                    top.linkTo(imageRefs.bottom, margin = elementsMargin)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(),
            trailingIcon = {
                if (!emailAvailabilityState.value) {
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_error_outline_black_24dp),
                            contentDescription = stringResource(R.string.email_error),
                        )
                    }
                }
            },
        )

        Button(
            onClick = {
                Timber.d(emailAvailabilityState.value.toString())
                if (emailAvailabilityState.value) {
                    onButtonClickListener()
                }
            },
            modifier = Modifier
                .constrainAs(buttonRefs) {
                    top.linkTo(loginRefs.bottom, margin = elementsMargin)
                    end.linkTo(loginRefs.end)
                    start.linkTo(loginRefs.start)
                }
                .fillMaxWidth()
                .height(buttonHeight),
        ) {
            Text(text = stringResource(R.string.register_check_button))
        }
    }
}

@Composable
fun PasswordNicknamePage(viewModel: MainViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        RegisterEnterPassword(
            nickname = viewModel.nickname,
            password = viewModel.newUserPassword,
        )
    }
}

@Composable
fun RegisterEnterPassword(
    nickname: MutableState<String>,
    password: MutableState<String>,
) {

    val layoutPadding = dimensionResource(id = R.dimen.auth_padding)

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(layoutPadding)
    ) {
        val (
            imageRefs,
            nicknameRefs,
            passwordRefs,
            buttonRefs,
        ) = createRefs()

        val imageMarginTop = dimensionResource(id = R.dimen.auth_content_margin_top)
        val elementsMargin = dimensionResource(id = R.dimen.elements_margin)
        val buttonHeight = dimensionResource(id = R.dimen.auth_button_height)

        Image(
            painter = painterResource(id = R.drawable.bug),
            contentDescription = stringResource(R.string.app_icon),
            modifier = Modifier.constrainAs(imageRefs) {
                top.linkTo(parent.top, margin = imageMarginTop)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        TextField(
            value = nickname.value,
            onValueChange = {
                nickname.value = it
            },
            label = { Text(text = stringResource(R.string.nickname_text_field)) },
            modifier = Modifier
                .constrainAs(nicknameRefs) {
                    top.linkTo(imageRefs.bottom, margin = elementsMargin)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
        )

        TextField(
            value = password.value,
            onValueChange = {
                password.value = it
            },
            label = { Text(text = stringResource(R.string.password_text_field)) },
            modifier = Modifier
                .constrainAs(passwordRefs) {
                    top.linkTo(nicknameRefs.bottom, margin = elementsMargin)
                    start.linkTo(nicknameRefs.start)
                    end.linkTo(nicknameRefs.end)
                }
                .fillMaxWidth(),
        )

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .constrainAs(buttonRefs) {
                    top.linkTo(passwordRefs.bottom, margin = elementsMargin)
                    end.linkTo(passwordRefs.end)
                    start.linkTo(passwordRefs.start)
                }
                .fillMaxWidth()
                .height(buttonHeight),
        ) {
            Text(text = stringResource(R.string.register_navigate_button))
        }
    }
}

@Composable
@Preview
fun RegisterPasswordPreview() {
    RegisterEnterPassword(
        nickname = mutableStateOf(""),
        password = mutableStateOf(""),
    )
}

@Composable
@Preview
fun RegisterEmailPagePreview() {
    RegisterCheckEmail(
        login = mutableStateOf(""),
        onButtonClickListener = {},
        onEmailInputChangeListener = {},
        isEmailAvailable = MutableStateFlow(false).asStateFlow(),
    )
}
