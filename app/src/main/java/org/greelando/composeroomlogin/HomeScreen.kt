package org.greelando.composeroomlogin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.greelando.composeroomlogin.model.StudentEntity
import org.greelando.composeroomlogin.viewmodel.HomeViewModel

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel = hiltViewModel<HomeViewModel>()
    Content(homeViewModel = viewModel, navController)
}

@Composable
fun Content(
    homeViewModel: HomeViewModel,
    navController: NavController
) {

    LaunchedEffect(key1 = true, block = {
        homeViewModel.getStudentDetails()
    })

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f),
            contentAlignment = Alignment.TopCenter
        ) {
            TopContent(homeViewModel = homeViewModel)
        }

        Text(
            text = "Student List",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        BottomContent(homeViewModel = homeViewModel, navController)
    }
}

@Composable
fun TopContent(
    homeViewModel: HomeViewModel,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.End,

    ) {
        NextButton()

    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val name by homeViewModel.studentName.collectAsStateWithLifecycle()
        val studentRoll by homeViewModel.studentRollNo.collectAsStateWithLifecycle()
        val checked by homeViewModel.isChecked.collectAsStateWithLifecycle()
        val onNameEntered: (value: String) -> Unit = remember {
            return@remember homeViewModel::setStudentName
        }
        val onRollNoEntered: (value: String) -> Unit = remember {
            return@remember homeViewModel::setStudentRollNo
        }
        val onCheck: (value: Boolean) -> Unit = remember {
            return@remember homeViewModel::setChecked
        }
        val onSubmit: (value: StudentEntity) -> Unit = remember {
            return@remember homeViewModel::insertStudent
        }


        OutlinedTextField(
            value = name,
            onValueChange = {
                onNameEntered(it)
            },
            placeholder = {
                Text(text = "Enter the Name of Student")
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(), maxLines = 1
        )
        Spacer(modifier = Modifier.height(15.dp))
        OutlinedTextField(
            value = studentRoll, onValueChange = {
                onRollNoEntered(it)
            },
            placeholder = {
                Text(text = "Enter the Name of Student")
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(),
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(15.dp))

        Checkbox(checked = checked, onCheckedChange = {
            onCheck(it)
        })
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedButton(onClick = {
            onSubmit(
                StudentEntity(
                    name = name,
                    studentRollNo = studentRoll,
                    passOrFail = checked
                )
            )
        }) {
            Text(text = "Submit")
        }

    }
}

@Composable
fun NextButton() {
    val navController = rememberNavController()
    Button(
        onClick = {
        },
        elevation = ButtonDefaults.elevatedButtonElevation(
        defaultElevation = 10.dp,
        pressedElevation = 15.dp,
        disabledElevation = 0.dp,
    ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White
        ),
        shape = CutCornerShape(10.dp)

    ) {
        Text(text = "Login", color = Color.Black)
    }
}

@Composable
fun BottomContent(
    homeViewModel: HomeViewModel,
    navController: NavController
) {

    val contents by homeViewModel.studentDetailsList.collectAsStateWithLifecycle()

    val mod = Modifier
        .padding(15.dp)
        .fillMaxWidth()
        .height(80.dp)

    LazyColumn(
        content = {

            items(contents) {
                val item = ImmutableStudent(it)
                StudentCard(wrapper = item, homeViewModel = homeViewModel, navController = navController)
            }
        }, modifier = Modifier.fillMaxSize()
    )
}


@Immutable
data class ImmutableStudent(val studentEntity: StudentEntity)

@Composable
fun StudentCard(
    wrapper: ImmutableStudent,
    homeViewModel: HomeViewModel,
    navController: NavController,
    mod: Modifier = Modifier,
) {


    val onCheckedChange: (value: StudentEntity) -> Unit = remember {
        return@remember homeViewModel::updateStudent
    }
    Card(
        onClick = {

            navController.navigate("detail/${wrapper.studentEntity.id}")
        }, modifier = mod
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically

        )
        {
            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(3f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {


                Text(
                    text = wrapper.studentEntity.name,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = wrapper.studentEntity.studentRollNo,
                    maxLines = 1,
                    fontWeight = FontWeight.Normal,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {

                Checkbox(checked = wrapper.studentEntity.passOrFail, onCheckedChange = {
                    onCheckedChange(
                        StudentEntity(
                            wrapper.studentEntity.id,
                            wrapper.studentEntity.name,
                            wrapper.studentEntity.studentRollNo,
                            it
                        )
                    )
                })
            }


        }
    }
}