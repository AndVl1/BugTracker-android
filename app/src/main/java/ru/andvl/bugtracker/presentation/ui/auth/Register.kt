package ru.andvl.bugtracker.presentation.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import ru.andvl.bugtracker.R

@Composable
fun RegisterCheckEmail(login: MutableState<String>) {

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
            label = { Text(text = stringResource(R.string.register_email_check)) },
            modifier = Modifier
                .constrainAs(loginRefs) {
                    top.linkTo(imageRefs.bottom, margin = elementsMargin)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(),
        )

        Button(
            onClick = { /*TODO*/ },
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
fun RegisterEnterPassword(password: MutableState<String>) {

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
            value = password.value,
            onValueChange = {
                password.value = it
            },
            label = { Text(text = stringResource(R.string.password_text_field)) },
            modifier = Modifier
                .constrainAs(loginRefs) {
                    top.linkTo(imageRefs.bottom, margin = elementsMargin)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(),
        )

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .constrainAs(buttonRefs) {
                    top.linkTo(loginRefs.bottom, margin = elementsMargin)
                    end.linkTo(loginRefs.end)
                    start.linkTo(loginRefs.start)
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
    RegisterEnterPassword(password = mutableStateOf(""))
}

@Composable
@Preview
fun RegisterEmailPagePreview() {
    RegisterCheckEmail(login = mutableStateOf(""))
}
