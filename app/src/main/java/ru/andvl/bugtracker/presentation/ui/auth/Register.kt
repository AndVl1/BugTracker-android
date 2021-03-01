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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import ru.andvl.bugtracker.R

@Composable
fun RegisterCheckEmail(login: MutableState<String>,) {
	ConstraintLayout(
		modifier = Modifier
			.fillMaxWidth()
			.padding(16.dp)
	) {
		val (
			imageRefs,
			loginRefs,
			buttonRefs,
		) = createRefs()

		Image(
			painter = painterResource(id = R.drawable.bug),
			contentDescription = "Bug image",
			modifier = Modifier.constrainAs(imageRefs) {
				top.linkTo(parent.top, margin = 128.dp)
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
					top.linkTo(imageRefs.bottom, margin = 8.dp)
					start.linkTo(parent.start)
					end.linkTo(parent.end)
				}
				.fillMaxWidth(),
		)

		Button(
			onClick = { /*TODO*/ },
			modifier = Modifier
				.constrainAs(buttonRefs) {
					top.linkTo(loginRefs.bottom, margin = 8.dp)
					end.linkTo(loginRefs.end)
					start.linkTo(loginRefs.start)
				}
				.fillMaxWidth()
				.height(56.dp),
		) {
			Text(text = stringResource(R.string.register_check_button))
		}
	}
}


@Composable
fun RegisterEnterPassword(password: MutableState<String>) {
	ConstraintLayout(
		modifier = Modifier
			.fillMaxWidth()
			.padding(16.dp)
	) {
		val (
			imageRefs,
			loginRefs,
			buttonRefs,
		) = createRefs()

		Image(
			painter = painterResource(id = R.drawable.bug),
			contentDescription = "Bug image",
			modifier = Modifier.constrainAs(imageRefs) {
				top.linkTo(parent.top, margin = 128.dp)
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
					top.linkTo(imageRefs.bottom, margin = 8.dp)
					start.linkTo(parent.start)
					end.linkTo(parent.end)
				}
				.fillMaxWidth(),
		)

		Button(
			onClick = { /*TODO*/ },
			modifier = Modifier
				.constrainAs(buttonRefs) {
					top.linkTo(loginRefs.bottom, margin = 8.dp)
					end.linkTo(loginRefs.end)
					start.linkTo(loginRefs.start)
				}
				.fillMaxWidth()
				.height(56.dp),
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