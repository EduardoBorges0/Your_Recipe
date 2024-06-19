package com.app.receitas.view.Settings.PersonalData

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.receitas.view.Settings.PersonalData.ui.theme.ReceitasTheme
import com.app.receitas.view.navigations.ui.theme.YellowButton

class AboutView : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReceitasTheme {
                AboutViewComposable()
            }
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun AboutViewComposable() {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(YellowButton)
            .padding(top = 20.dp)
            .clip(RoundedCornerShape(20.dp))
            .padding(26.dp)
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = 36.dp)
        ) {
            Text(
                text = "Bom, eu gostaria de agradecer a todos que tiveram a curiosidade de abrir esse aplicativo, seja por que quer ver uma receita, seja só por curiosidade. Esse aplicativo tem o objetivo de ajudar as pessoas a encontrar uma receita de forma mais eficiente, em um aplicativo só, hoje em dia existem sites como o da ana maria braga que já tem receitas pré prontas, e isso não é nada ruim, porém gosto da ideia, e imagino que você também já que clicou e abriu esse aplicativo, que possamos interagir e postar nossa própria receita e ver a receita de outros, receitas passadas de familia para familia, e não apenas receitas preparadas por um chefe. Com isso dito, espero que vocês possam aproveitar o máximo que o aplicativo tem a oferecer, e caso queiram sugerir ideias novas para o aplicativo, que possam melhorar a experiencia de vocês, mandem um email para eduborges0101@gmail.com, lá responderei a todos e revisarei para melhorar mais e mais esse aplicativo, e caso você seja um desenvolvedor e queira ver o código fonte desse aplicativo, cogite a ideia de entrar no meu github e dar uma olhada melhor lá, o username é EduardoBorges0. Então é isso rapaziada, agradeço a todos novamente por terem lido até aqui e estarem dando uma olhada no aplicativo, até a próxima." ,

                color = Color.White,
                fontSize = 18.sp
            )
        }
    }
}
