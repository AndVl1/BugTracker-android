package ru.andvl.bugtracker.presentation.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import ru.andvl.bugtracker.MainViewModel
import ru.andvl.bugtracker.R


@Composable
fun LoginPage(viewModel: MainViewModel) {
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
			onLoginClickListener = { viewModel.onLoginButtonClickListener() },
			onRegisterClickListener = { viewModel.onRegisterClickListener() }
		)
	}
}

@Composable
fun LoginBlock(
	login: MutableState<String>,
	password: MutableState<String>,
	passwordVisibility: MutableState<Boolean>,
	onLoginClickListener: () -> Unit,
	onRegisterClickListener: () -> Unit,
) {

	ConstraintLayout(
//		modifier = Modifier.fillMaxWidth().fillMaxHeight(),
		modifier = Modifier
			.padding(16.dp)
			.fillMaxHeight()
	) {
		val (
			imageRefs,
			loginRefs,
			passRefs,
			buttonRefs,
			registerRefs
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
			label = { Text(text = stringResource(R.string.login_text_field)) },
			modifier = Modifier
				.constrainAs(loginRefs) {
					top.linkTo(imageRefs.bottom, margin = 8.dp)
					start.linkTo(parent.start)
					end.linkTo(parent.end)
				}
				.fillMaxWidth(),
		)

		TextField(
			value = password.value,
			onValueChange = {
				password.value = it
			},
			label = { Text(text = stringResource(R.string.password_text_field)) },
			modifier = Modifier
				.constrainAs(passRefs) {
					top.linkTo(loginRefs.bottom, margin = 8.dp)
					start.linkTo(loginRefs.start)
					end.linkTo(loginRefs.end)
				}
				.fillMaxWidth(),
			visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
			trailingIcon = {
				IconButton(onClick = {
					passwordVisibility.value = !passwordVisibility.value
				}) {
					Icon(
						painter = painterResource(id = R.drawable.ic_eye_black_24dp),
						contentDescription = stringResource(R.string.pass_icon_description)
					)
				}
			}
		)

		Button(
			onClick = { /*TODO*/ },
			modifier = Modifier
				.constrainAs(buttonRefs) {
					top.linkTo(passRefs.bottom, margin = 8.dp)
					end.linkTo(passRefs.end)
					start.linkTo(passRefs.start)
				}
				.fillMaxWidth()
				.height(56.dp),
		) {
			Text(text = stringResource(R.string.login_button_text))
		}

		Row(modifier = Modifier.constrainAs(registerRefs) {
			bottom.linkTo(parent.bottom)
			start.linkTo(parent.start)
			end.linkTo(parent.end)
		}) {
			Text(
				text = stringResource(R.string.not_register_question),
			)

			ClickableText(
				onClick = {/* TODO */ },
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
		onLoginClickListener = {},
		onRegisterClickListener = {},
	)
}