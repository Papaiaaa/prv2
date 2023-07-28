package com.example.prv2

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.prv2.R
import com.example.prv2.ui.theme.PrvTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            PrvTheme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
    private fun OnContactsClicked(texts: List<String>) {
        println(texts)
        // Ваш код обработки нажатия на кнопку Contact's
        // Например, открытие новой активити, переход по ссылке и т.д.
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    val greetings = listOf(
        "CV",
        "Educatıon",
        "About me",
        "Contacts",
        "About App"
    )
    val titls = listOf(
        "Резюме",
        "Образование",
        "Обо мне",
        "Контакты",
        "О приложении"
    )
    val url = "https://example.com"
    val texts = listOf(
        "292929",
        "Rubius LLC\n" +
                "\n" +
                "Повышение квалификации \"Project Manager\"\n" +
                "\n" +
                "Квалификация: \"Менеджер проектов в IT\"\n" +
                "\n" +
                "Год окончания: 2023\n" +
                "\n" +
                "ООО «Нетология»\n" +
                "\n" +
                "Программа профессиональной переподготовки Project manager\n" +
                "\n" +
                "Квалификация: Руководитель в области информационных технологий\n" +
                "\n" +
                "Год окончания: 2022\n",
        "292929",
        "Мобильный: +7 (900) 922-38-67\n" +
                "Мессенджеры: +7 (952) 803-56-13\n" +
                "E-mail: info@popovrv.org\n" +
                "LinkedIN https://www.linkedin.com/in/roman-popov1989/",
        "| Kotlin | Jetpack Compose |  Android Studio Flamingo 2022.2.1 |"
    )
    val onContactsClicked: () -> Unit = {
        // Ваш код обработки нажатия на кнопку Contact's
        // Например, открытие новой активити, переход по ссылке и т.д.
    }
    Surface(modifier, color = MaterialTheme.colorScheme.background) {
        if (shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false }, onContactsClicked = onContactsClicked)
        } else {
            Greetings(greetings, texts, titls)
        }
    }
}

////new start///
@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    onContactsClicked: () -> Unit,
    buttonHeight: Dp = 50.dp, // Размер высоты кнопки (по умолчанию 48.dp)
    buttonWidth: Dp = 180.dp, // Размер ширины кнопки (по умолчанию 180.dp)
    modifier: Modifier = Modifier
) {
    var isButtonVisible by remember { mutableStateOf(false) }
    // Получаем ориентацию устройства
    val configuration = LocalConfiguration.current
    val isVertical = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    val buttonAnimatable = remember { Animatable(0f) }
    // Анимация прозрачности кнопки с задержкой в 100 мс для создания эффекта появления
    val buttonAlpha by animateFloatAsState(
        targetValue = if (isButtonVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 3000, delayMillis = 100)
    )

    val transition = updateTransition(targetState = isButtonVisible, label = "ButtonTransition")
    val translateY by transition.animateDp(
        transitionSpec = { tween(durationMillis = 1500, easing = LinearOutSlowInEasing) }
    ) { state ->
        if (state) 0.dp else 600.dp // Change the vertical position here (200.dp is just an example)
    }

    LaunchedEffect(true) {
        delay(100)
        isButtonVisible = true
        buttonAnimatable.animateTo(1f)
    }
    val context = LocalContext.current

    if (isVertical)
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.prv),
                contentDescription = "logo",
                modifier = Modifier
                    .padding(16.dp)
                    .heightIn(min = 160.dp, max = 260.dp)
                    .widthIn(min = 160.dp, max = 260.dp)
                    .clip(RoundedCornerShape(120.dp))
            )
            Text("Welcome to my first App on the Kotlin")
        }
    else
        Column() {

        }
////////////
    var isSecondButtonVisible by remember { mutableStateOf(false) }
    val secondButtonAnimatable = remember { Animatable(0f) }
    val secondButtonAlpha by animateFloatAsState(
        targetValue = if (isSecondButtonVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 3000, delayMillis = 200)
    )
    val secondButtonTransition = updateTransition(targetState = isSecondButtonVisible)
    val secondButtonTranslateY by secondButtonTransition.animateDp(
        transitionSpec = { tween(durationMillis = 1600, easing = LinearOutSlowInEasing) }
    ) { state ->
        if (state) 110.dp else 600.dp
    }
/////////////////////////////////
    var isCvButtonVisible by remember { mutableStateOf(false) }
    val cvButtonAnimatable = remember { Animatable(0f) }
    val cvButtonAlpha by animateFloatAsState(
        targetValue = if (isCvButtonVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 3000, delayMillis = 450)
    )
    val cvButtonTransition = updateTransition(targetState = isCvButtonVisible)
    val cvButtonTranslateY by cvButtonTransition.animateDp(
        transitionSpec = { tween(durationMillis = 1900, easing = LinearOutSlowInEasing) }
    ) { state ->
        if (state) 220.dp else 600.dp
    }
    ////////////////////////////////////
    LaunchedEffect(true) {
        delay(300)
        isSecondButtonVisible = true
        secondButtonAnimatable.animateTo(1f)
    }
    LaunchedEffect(true) {
        delay(500)
        isCvButtonVisible = true
        cvButtonAnimatable.animateTo(1f)
    }
    if (isVertical)
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier
                    .padding(top = translateY.value.dp)
                    .height(buttonHeight)
                    .width(buttonWidth)
                    .alpha(buttonAlpha),
                onClick = onContinueClicked

            ) {
                Text("About me")
            }
        }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (isSecondButtonVisible) {
            Button(
                modifier = Modifier
                    .padding(top = secondButtonTranslateY.value.dp)
                    .height(buttonHeight)
                    .width(buttonWidth)
                    .alpha(secondButtonAlpha),
                onClick = onContinueClicked
            ) {
                Text("Education")
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (isCvButtonVisible) {
            Button(
                modifier = Modifier
                    .padding(top = cvButtonTranslateY.value.dp)
                    .height(buttonHeight)
                    .width(buttonWidth)
                    .alpha(cvButtonAlpha),
                onClick = onContactsClicked
            ) {
                Text("Contact`s")
            }
        }
    }

}


@Composable
fun Greetings(greetings: List<String>, texts: List<String>, titls: List<String>) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        itemsIndexed(items = greetings) { index, greeting ->
            Greeting(greeting = greeting, text = texts[index], titls = titls[index])
        }
    }
}

@Composable
private fun Greeting(greeting: String, text: String, titls: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(greeting = greeting, text = text, titls = titls)
    }
}

@Composable
private fun CardContent(greeting: String, text: String, titls: String) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Text(text = titls)
            Text(
                text = greeting, style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            if (expanded) {
                Text(
                    text = text.repeat(1),
                )
            }
        }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Filled.ExpandLess else Filled.ExpandMore,
                contentDescription = if (expanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }
            )
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    PrvTheme {
        val titls = listOf("2344", "34534", "3242", "2353453", "1088888")
        val greetings = listOf("2344", "34534", "3242", "2353453", "1088888")
        val texts = listOf("292929","пуаакцаууу","292929","пуаакцаууу","11111111")
        Greetings(titls, texts,greetings)
    }
}
/*
@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    PrvTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}

@Preview
@Composable
fun MyAppPreview() {
    PrvTheme {
        MyApp(Modifier.fillMaxSize())
    }
}

 */