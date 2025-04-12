package com.example.projetoaula

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.projetoaula.ui.theme.ProjetoAulaTheme
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjetoAulaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                       AppAula()

                }
            }
        }
    }
}

@Composable
fun AppAula() {

    val focusRequester = remember { FocusRequester() }
    val context = LocalContext.current
    var nome by remember { mutableStateOf("") }

    var endereco by rememberSaveable { mutableStateOf("") }
    var bairro by rememberSaveable { mutableStateOf("") }
    var cep by rememberSaveable { mutableStateOf("") }
    var cidade by rememberSaveable { mutableStateOf("") }
    var estado by rememberSaveable { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    var dadosCadastrados by remember { mutableStateOf<Map<String, String>?>(null) }

    //Coluna
    Column(
        Modifier
            .fillMaxSize(),

        ) {


        //Linha 01
        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            Text("App Aula")
        }
        Spacer(modifier = Modifier.height(16.dp))


//Linha 02
        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            var ValueOfTextField by remember { mutableStateOf("") }

            TextField(
                value = ValueOfTextField,
                onValueChange = { ValueOfTextField = it },
                label = { Text("Nome") }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

//Linha 03
        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            var ValueOfTextField by remember { mutableStateOf("") }

            TextField(
                value = ValueOfTextField,
                onValueChange = { ValueOfTextField = it },
                label = { Text("Endereço") }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            var ValueOfTextField by remember { mutableStateOf("") }

            TextField(
                value = ValueOfTextField,
                onValueChange = { ValueOfTextField = it },
                label = { Text("Bairro") }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            var ValueOfTextField by remember { mutableStateOf("") }

            TextField(
                value = ValueOfTextField,
                onValueChange = { ValueOfTextField = it },
                label = { Text("Cep") }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            var ValueOfTextField by remember { mutableStateOf("") }

            TextField(
                value = ValueOfTextField,
                onValueChange = { ValueOfTextField = it },
                label = { Text("Cidade") }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            var ValueOfTextField by remember { mutableStateOf("") }

            TextField(
                value = ValueOfTextField,
                onValueChange = { ValueOfTextField = it },
                label = { Text("Estado") }
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        //Linha 04
        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            Column(
                Modifier
                    .fillMaxWidth(0.4f),


                ) {
                Button(onClick = {}) {
                    Text("Cadastrar")
                }
            }


            Column(
            ) {
                Button(onClick = {}) {
                    Text("Cancelar")
                }
            }
            Column(

            ) {
               Button(onClick = {
                val db = Firebase.firestore
                   if (nome.isBlank() || endereco.isBlank() || bairro.isBlank() || cep.isBlank() || cidade.isBlank() || estado.isBlank()) {
                    Log.w(TAG, "Campos vazios. Cadastro não realizado.")
                    showToast(context, "Por favor, preencha todos os campos.")

                    return@Button
                }

                val user = hashMapOf(
                    "nome" to nome,
                    "endereco" to endereco,
                    "bairro" to bairro,
                    "cep" to cep,
                    "cidade" to cidade,
                    "estado" to estado
                )

                // Add a new document with a generated ID
                db.collection("users")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        showToast(context, "Dados cadastrados com sucesso!")
                    }
                    .addOnFailureListener { e ->
                        showToast(context, "Erro ao cadastrar. Tente novamente.")
                    }

                dadosCadastrados = user.mapValues { it.value.toString() }

                coroutineScope.launch {
                    nome = ""
                    endereco = ""
                    bairro = ""
                    cep = ""
                    estado = ""
                    cidade = ""
                }
                focusRequester.requestFocus()
            })

                   // Add a new document with a generated ID
                   db.collection("users")
                       .add(user)
                       .addOnSuccessListener { documentReference ->
                           Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                       }
                       .addOnFailureListener { e ->
                           Log.w(TAG, "Error adding document", e)
                       }
            
                       
               }

            }
        }
    }
}


@Preview
@Composable
fun AppAulaPreview(){
    ProjetoAulaTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            AppAula()
        }
    }
}