package com.example.prv2

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.prv2.ui.theme.PrvTheme
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            PrvTheme {
                OnboardingScreen(
                    ClickAboutme = {
                    val intent = Intent(this@MainActivity, ActivityAbout::class.java)
                startActivity(intent)
            },
                    ClickEdu = {
                        val intent = Intent(this@MainActivity, ActivityEdu::class.java)
                        startActivity(intent)
                    },
                    ClickContact = {
                        val intent = Intent(this@MainActivity, ActivityCont::class.java)
                        startActivity(intent)
                    })
            }
        }
    }
}
class DataFetcher(databaseReference: DatabaseReference) {
    var email by mutableStateOf("")
        private set
    var messenger by mutableStateOf("")
        private set
    var phone by mutableStateOf("")
        private set
    var mainTitle by mutableStateOf("")
        private set
    var butEduEn by mutableStateOf("")
        private set
    var butEduRu by mutableStateOf("")
        private set
    init {
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val data = snapshot.value as? Map<*, *>
                    data?.let {
                        email = data["email"].toString()
                        messenger = data["messenger"].toString()
                        phone = data["phone"].toString()
                        mainTitle = data["mainTitle"].toString()
                        butEduEn = data["butEduEn"].toString()
                        butEduRu = data["butEduRu"].toString()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Обработка ошибок
            }
        }

        // Включаем слушатель при создании экземпляра DataFetcher
        databaseReference.addValueEventListener(valueEventListener)
    }
}
fun connectToFirebaseAndFetchData(): DataFetcher {
    val databaseReference = FirebaseDatabase.getInstance("https://popovkotlin-67a40-default-rtdb.firebaseio.com").reference
    return DataFetcher(databaseReference)
}
val dataFetcher = connectToFirebaseAndFetchData()
@Composable
fun OnboardingScreen(
    ClickAboutme: () -> Unit,
    ClickEdu: () -> Unit,
    ClickContact: () -> Unit,
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
            Text("${dataFetcher.mainTitle}")
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
                onClick = { ClickAboutme() }

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
                onClick = { ClickEdu() }
            ) {
                Text("${dataFetcher.butEduEn}")
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
                onClick = { ClickContact() }
            ) {
                Text("Contact`s")
            }
        }
    }
}