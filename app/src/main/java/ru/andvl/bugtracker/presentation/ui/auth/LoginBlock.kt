package ru.andvl.bugtracker.presentation.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.andvl.bugtracker.MainViewModel
import ru.andvl.bugtracker.R
import ru.andvl.bugtracker.navigation.Destinations
import timber.log.Timber

@Composable
fun LoginPage(
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
        LoginBlock(
            login = viewModel.login,
            password = viewModel.password,
            passwordVisibility = viewModel.passwordVisibility,
            isLoggedIn = viewModel.areLoginAndPasswordCorrect,
            onLoginClickListener = { viewModel.onLoginButtonClickListener() },
            onRegisterClickListener = { navController.navigate(Destinations.CheckEmail) },
            onPasswordVisibilityChangeListener = { viewModel.onPasswordVisibilityChanged() },
        )
    }
}

@Composable
fun LoginBlock(
    login: MutableState<String>,
    password: MutableState<String>,
    passwordVisibility: MutableState<Boolean>,
    isLoggedIn: StateFlow<Boolean>,
    onLoginClickListener: () -> Unit,
    onRegisterClickListener: () -> Unit,
    onPasswordVisibilityChangeListener: () -> Unit,
) {

    val layoutPadding = dimensionResource(id = R.dimen.auth_padding)
    val loginStatus = isLoggedIn.collectAsState()

    ConstraintLayout(
//      modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        modifier = Modifier
            .padding(layoutPadding)
            .fillMaxHeight()
    ) {
        val (
            imageRefs,
            loginRefs,
            passRefs,
            buttonRefs,
            registerRefs
        ) = createRefs()

        val elementsMargin = dimensionResource(id = R.dimen.elements_margin)
        val buttonHeight = dimensionResource(id = R.dimen.auth_button_height)
        val imageMarginTop = dimensionResource(id = R.dimen.auth_content_margin_top)

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
                login.value = it
            },
            label = { Text(text = stringResource(R.string.login_text_field)) },
            modifier = Modifier
                .constrainAs(loginRefs) {
                    top.linkTo(imageRefs.bottom, margin = elementsMargin)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(),
            maxLines = 1,
        )

        TextField(
            value = password.value,
            onValueChange = {
                password.value = it
            },
            label = { Text(text = stringResource(R.string.password_text_field)) },
            modifier = Modifier
                .constrainAs(passRefs) {
                    top.linkTo(loginRefs.bottom, elementsMargin)
                    start.linkTo(loginRefs.start)
                    end.linkTo(loginRefs.end)
                }
                .fillMaxWidth(),
            visualTransformation = if (passwordVisibility.value)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(
                    onClick = {
                        onPasswordVisibilityChangeListener()
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_eye_black_24dp),
                        contentDescription = stringResource(R.string.pass_icon_description)
                    )
                }
            },
            maxLines = 1,
        )

        Button(
            onClick = {
                onLoginClickListener()
//                MainScope().launch {
//                    isLoggedIn.collect{
//                        when(it) {
//                            true -> Timber.d("true")
//                            false -> Timber.d("false")
//                        }
//                    }
//                }
                when (loginStatus.value) {
                    true -> { Timber.d("Login success") }
                    false -> { Timber.d("Login error") }
                }
                      },
            modifier = Modifier
                .constrainAs(buttonRefs) {
                    top.linkTo(passRefs.bottom, margin = elementsMargin)
                    end.linkTo(passRefs.end)
                    start.linkTo(passRefs.start)
                }
                .fillMaxWidth()
                .height(buttonHeight),
        ) {
            Text(text = stringResource(R.string.login_button_text))
        }

        Row(
            modifier = Modifier.constrainAs(registerRefs) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            Text(
                text = stringResource(R.string.not_register_question),
            )

            ClickableText(
                onClick = { onRegisterClickListener() },
                text = AnnotatedString(stringResource(R.string.register_navigate_button)),
            )
        }
    }
}

@Composable
@Preview
fun LoginPreview() {
    LoginBlock(
        login = mutableStateOf(""),
        password = mutableStateOf(""),
        passwordVisibility = mutableStateOf(false),
        isLoggedIn = MutableStateFlow(false).asStateFlow(),
        onLoginClickListener = {},
        onRegisterClickListener = {},
        onPasswordVisibilityChangeListener = {},
    )
}
