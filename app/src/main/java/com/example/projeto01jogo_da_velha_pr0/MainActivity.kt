package com.example.projeto01jogo_da_velha_pr0

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projeto01jogo_da_velha_pr0.ui.theme.Projeto01Jogo_Da_Velha_PR0Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Projeto01Jogo_Da_Velha_PR0Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    JogoVelhaAPP()
                }
            }
        }
    }
}

@Composable
fun JogoVelhaAPP() {
    var tabuleiro by remember { mutableStateOf(List(9) { "" }) }
    var jogadorAtual by remember { mutableStateOf("X") }
    var vencedor by remember { mutableStateOf("") }
    val fimJogo = vencedor.isNotEmpty() || tabuleiro.none { it.isEmpty() }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_01_pink),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Box(
                    modifier = Modifier.background(Color(android.graphics.Color.parseColor("#513722"))),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "JOGO DA VELHA",
                        fontSize = 43.sp,
                        color = Color(android.graphics.Color.parseColor("#AF8D6D")),
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Tabuleiro(
                tabuleiro = tabuleiro,
                fimJogo = fimJogo,
                onClick = { index ->
                    if (tabuleiro[index].isEmpty() && !fimJogo) {
                        tabuleiro = tabuleiro.toMutableList().apply {
                            this[index] = jogadorAtual
                        }
                        if (checkVencedor(tabuleiro, jogadorAtual)) {
                            vencedor = jogadorAtual
                        } else {
                            jogadorAtual = if (jogadorAtual == "X") "O" else "X"
                            if (tabuleiro.all { it.isNotEmpty() }) {
                                vencedor = "DEU VELHA"
                            }
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            ReiniciarButton(
                onClick = {
                    tabuleiro = List(9) { "" }
                    jogadorAtual = "X"
                    vencedor = ""
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            VencedorPlayer(vencedor)
        }
    }
}

@Composable
fun Tabuleiro(tabuleiro: List<String>, fimJogo: Boolean, onClick: (Int) -> Unit) {
    repeat(3) { rowIndex ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            repeat(3) { colIndex ->
                val index = rowIndex * 3 + colIndex
                Button(
                    onClick = { onClick(index) },
                    modifier = Modifier
                        .weight(1f)
                        .size(64.dp)
                        .background(
                            color = if (tabuleiro[index].isEmpty() && !fimJogo) Color(
                                android.graphics.Color.parseColor(
                                    "#AF8D6D"
                                )
                            ) else Color(android.graphics.Color.parseColor("#AF8D6D"))
                        ),
                    colors = ButtonDefaults.buttonColors(Color(android.graphics.Color.parseColor("#513722"))),
                    enabled = tabuleiro[index].isEmpty() && !fimJogo
                ) {
                    Text(
                        text = tabuleiro[index],
                        fontSize = 30.sp
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun ReiniciarButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(
                color = Color(android.graphics.Color.parseColor("#AF8D6D"))
            )
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxSize(),
            colors = ButtonDefaults.buttonColors(Color(android.graphics.Color.parseColor("#513722")))
        ) {
            Text(
                text = "Reiniciar",
                fontSize = 24.sp,
                color = Color(android.graphics.Color.parseColor("#AF8D6D")),
            )
        }
    }
}

@Composable
fun VencedorPlayer(vencedor: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .width(200.dp)
            .background(
                color = Color(android.graphics.Color.parseColor("#263277"))
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (vencedor != "DEU VELHA" && vencedor.isNotEmpty()) {
            Text(
                text = "$vencedor VENCEU!",
                fontSize = 50.sp,
                textAlign = TextAlign.Center,
                color = Color(android.graphics.Color.parseColor("#FFA3C2"))
            )
        } else {
            if (vencedor == "DEU VELHA") {
                Text(
                    text = "DEU VELHA",
                    fontSize = 50.sp,
                    textAlign = TextAlign.Center,
                    color = Color(android.graphics.Color.parseColor("#FFA3C2"))
                )
            }
        }
    }
}

fun checkVencedor(tabuleiro: List<String>, jogador: String): Boolean {
    for (i in 0 until 3) {
        if (tabuleiro[i * 3] == jogador && tabuleiro[i * 3 + 1] == jogador && tabuleiro[i * 3 + 2] == jogador) {
            return true
        }
    }
    for (i in 0 until 3) {
        if (tabuleiro[i] == jogador && tabuleiro[i+ 3] == jogador && tabuleiro[i + 6] == jogador) {
            return true
        }
    }
    if (tabuleiro[0] == jogador && tabuleiro[4] == jogador && tabuleiro[8] == jogador) {
        return true
    }
    if (tabuleiro[2] == jogador && tabuleiro[4] == jogador && tabuleiro[6] == jogador) {
        return true
    }
    return false
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Projeto01Jogo_Da_Velha_PR0Theme {
        JogoVelhaAPP()
    }
}