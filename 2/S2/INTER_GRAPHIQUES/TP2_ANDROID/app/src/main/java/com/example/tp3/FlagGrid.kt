package com.example.tp3

import android.annotation.SuppressLint
import android.content.Context
import android.os.SystemClock
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tp3.flags.ICSFlag
import kotlinx.coroutines.delay


@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun FlagGrid() {
    val allFlags = ICSFlag.allFlags.values

    BoxWithConstraints(Modifier.fillMaxSize()) {
        val isPortrait = maxHeight > maxWidth
        val flagsPerRow = if (isPortrait) 2 else 4

        val spacing = 16.dp
        val totalSpacing = spacing * (flagsPerRow + 1)
        val flagWidth = (maxWidth - totalSpacing) / flagsPerRow

        LazyColumn(Modifier.fillMaxSize()) {
            items(allFlags.chunked(flagsPerRow)) { chunk ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(spacing),
                    horizontalArrangement = Arrangement.spacedBy(
                        spacing, Alignment.CenterHorizontally
                    )
                ) {
                    chunk.forEach {
                        it.Flag(
                            Modifier
                                .size(flagWidth)
                                .border(2.dp, Color.Black)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FlagGrid(
    selectedFlag: Char?,
    onClick: (Char) -> Unit,
    modifier: Modifier = Modifier,
    allFlags: Collection<ICSFlag> = ICSFlag.allFlags.values
) {

    BoxWithConstraints(modifier) {
        val isPortrait = maxHeight > maxWidth
        val flagsPerRow = if (isPortrait) 2 else 4

        val spacing = 16.dp
        val totalSpacing = spacing * (flagsPerRow + 1)
        val flagWidth = (maxWidth - totalSpacing) / flagsPerRow

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(allFlags.chunked(flagsPerRow)) { chunk ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(spacing),
                    horizontalArrangement = Arrangement.spacedBy(
                        spacing, Alignment.CenterHorizontally
                    )
                ) {
                    chunk.forEach {
                        val borderColor = if (selectedFlag == it.letter) Color.Red else Color.Black
                        it.Flag(Modifier
                            .size(flagWidth)
                            .clickable { onClick(it.letter) }
                            .border(2.dp, borderColor))
                    }
                }
            }
        }
    }
}

@Composable
fun FlagInfoBox(flag: Char) {
    val rFlag = ICSFlag.findFlag(flag)

    if (rFlag != null) {
        Row(Modifier.height(intrinsicSize = IntrinsicSize.Max)) {
            rFlag.Flag(
                Modifier
                    .weight(20f)
                    .fillMaxHeight()
                    .border(1.dp, color = Color.Black)
            )
            Column(
                Modifier
                    .weight(80f)
                    .padding(start = 15.dp)
            ) {
                Text(
                    rFlag.codeWord,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(rFlag.message, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}


@Composable
@Preview
fun FlagManager() {
    var letterClicked by rememberSaveable { mutableStateOf<Char?>(null) }

    Column(Modifier.fillMaxSize()) {
        FlagGrid(
            letterClicked,
            onClick = { letterClicked = if (it == letterClicked) null else it },
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        )

        letterClicked?.let { FlagInfoBox(it) }
    }

}


@Composable
fun LetterBar(
    numberOfLines: Int,
    selectedLetter: Char? = null,
    letters: List<Char> = ('A'..'Z').toList(),
    onClick: (Char) -> Unit
) {
    val lettersPerLine = (letters.size + numberOfLines - 1) / numberOfLines

    Column {
        if (letters.isNotEmpty()) {
            letters.chunked(lettersPerLine).forEach { line ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    line.forEach {
                        val borderColor =
                            if (selectedLetter == it.uppercaseChar()) Color.Red else Color.Black
                        Text(
                            text = it.toString(),
                            modifier = Modifier
                                .padding(2.dp)
                                .border(
                                    width = if (it == selectedLetter) 2.dp else 1.dp,
                                    color = borderColor,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .clickable { onClick(it) }
                                .padding(4.dp),
                            style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        } else {
            Text("Jeux fini Bravo", style = MaterialTheme.typography.titleLarge)
        }
    }
}


@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun FlagLetterPairer(
    onPairFound: (Char) -> Unit,
    flags: List<ICSFlag>,
    attemptsCallback: () -> Unit = {},
    modifier: Modifier
) {

    var letterClickedFlag by rememberSaveable { mutableStateOf<Char?>(null) }
    var letterClicked by rememberSaveable { mutableStateOf<Char?>(null) }

    var letters = flags.map { it.letter }.toList().sortedBy { it }

    fun checkMatch() {
        if (letterClickedFlag != null && letterClicked != null) {
            attemptsCallback()
            if (letterClickedFlag == letterClicked) {
                onPairFound(letterClickedFlag!!)
            }
            letterClickedFlag = null
            letterClicked = null
        }
    }

    BoxWithConstraints(modifier.fillMaxSize()) {
        val isPortrait = maxHeight > maxWidth
        Column(Modifier.fillMaxSize()) {
            LetterBar(
                if (isPortrait) 2 else 1, selectedLetter = letterClickedFlag, letters = letters
            ) { letterClickedFlag = if (it == letterClickedFlag) null else it; checkMatch() }
            FlagGrid(
                letterClicked,
                onClick = { letterClicked = if (it == letterClicked) null else it; checkMatch() },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                allFlags = flags
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Echafaudage(modifier: Modifier) {
    var flags = ICSFlag.allFlags

    var shuffledFlags by rememberSaveable { mutableStateOf(flags.values.shuffled()) }

    var foundPairs by rememberSaveable { mutableStateOf(setOf<Char>()) }
    var attempts by rememberSaveable { mutableStateOf(0) }

    var startTime by rememberSaveable { mutableStateOf(SystemClock.elapsedRealtime()) }
    var elapsedTime by rememberSaveable { mutableStateOf(0L) }

    var isCheating by rememberSaveable { mutableStateOf(false) }

    var triggerCheat by rememberSaveable { mutableStateOf(false) }


    LaunchedEffect(key1 = startTime) {
        while (true) {
            elapsedTime = SystemClock.elapsedRealtime() - startTime
            delay(1000)
        }
    }

    LaunchedEffect(triggerCheat) {
        if (triggerCheat) {
            isCheating = true
            shuffledFlags = shuffledFlags.sortedBy { it.letter }

            delay(2000)

            isCheating = false
            triggerCheat = false
        }
    }

    fun restartGame() {
        shuffledFlags = flags.values.shuffled()
        foundPairs = emptySet()
        attempts = 0
        startTime = SystemClock.elapsedRealtime()
        isCheating = false
        triggerCheat = false
    }

    fun handlePairFound(letter: Char) {
        foundPairs = foundPairs + letter
        shuffledFlags = shuffledFlags.filterNot { it.letter == letter }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Jeu des drapeaux") }, actions = {
                Text(
                    "Rejouer",
                    modifier = Modifier
                        .clickable { restartGame() }
                        .padding(horizontal = 16.dp))
            })
        },
        bottomBar = {
            BottomAppBar {
                Text(
                    text = "Paires : ${foundPairs.size}/${flags.size} - Tentatives : $attempts - Temps : ${elapsedTime / 1000}s",
                    modifier = Modifier.padding(8.dp)
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { triggerCheat = true }) {
                Text("Triche")
            }
        },
    ) { padding ->
        FlagLetterPairer(
            onPairFound = { handlePairFound(it); attempts += 1 },
            flags = shuffledFlags,
            attemptsCallback = { attempts += 1 },
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
fun VerticalFillBar(
    backgroundColor: Color = Color.White,
    foregroundColor: Color = Color.Red,
    fillRatio: Float,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.background(backgroundColor)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = fillRatio)
                .align(Alignment.BottomCenter)
                .background(foregroundColor)
        )
    }

}

@Composable
fun CountdownBar(initialCountdown: Int, elapsedTime: Int, modifier: Modifier = Modifier) {
    val remaining = (initialCountdown - elapsedTime)
    val minutes = remaining / 60
    val seconds = remaining % 60
    val timeText = "%02d : %02d".format(minutes, seconds)

    val fillRatio = (1f - (elapsedTime.toFloat() / initialCountdown.toFloat()))

    Box(modifier = modifier.fillMaxSize()) {
        VerticalFillBar(fillRatio = fillRatio, modifier = Modifier.fillMaxSize())
        Text(
            text = timeText,
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier
                .align(Alignment.Center)
                .clip(RoundedCornerShape(15))
                .background(Color.Black)
                .padding(10.dp)
        )
    }
}

@Preview
@Composable
fun tmp12() {
    CountdownBar(60, 30, Modifier)
}

@Composable
fun Countdown(duration: Int, running: Boolean, onEnd: () -> Unit, modifier: Modifier) {
    var elapsedTime by rememberSaveable { mutableStateOf(0) }

    LaunchedEffect(duration) {
        elapsedTime = 0
    }

    LaunchedEffect(running) {
        if (running) {
            while (elapsedTime < duration && running) {
                delay(1000)
                elapsedTime++
            }
            if (elapsedTime >= duration) {
                onEnd()
            }
        }
    }

    CountdownBar(
        initialCountdown = duration,
        elapsedTime = elapsedTime,
        modifier = modifier.padding(horizontal = 16.dp)
    )
}


@Composable
fun DurationSelector(
    currentDuration: Int, presetDurations: List<Int>, onSelectedDuration: (Int) -> Unit
) {

    var open by rememberSaveable { mutableStateOf(false) }
    Column(modifier = Modifier.padding(16.dp)) {
        Button(modifier = Modifier.fillMaxWidth(), onClick = { open = !open }) {
            Text(
                text = "$currentDuration sec",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.ExtraBold
            )
        }
        DropdownMenu(
            expanded = open,
            onDismissRequest = { open = false },
        ) {
            for (i in presetDurations) {
                DropdownMenuItem(
                    text = { Text(text = "$i s") },
                    onClick = { open = false; onSelectedDuration(i) })
            }
        }
    }
}

@Preview
@Composable
fun ParameterizedCountdown() {

    var currentDuration by rememberSaveable { mutableStateOf(120) }
    var selectedValue by remember { mutableStateOf(120) }
    var start by rememberSaveable { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Countdown(
            currentDuration,
            start,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            onEnd = { currentDuration = selectedValue; start = false })
        DurationSelector(
            currentDuration,
            presetDurations = listOf(10, 20, 30, 60, 120, 240, 300, 360)
        ) {
            selectedValue = it
            currentDuration = selectedValue
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = { currentDuration = selectedValue; start = true }) {
                Text(
                    text = "Start",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Button(onClick = { currentDuration = selectedValue; start = false }) {
                Text(
                    text = "Stop",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

@Composable
fun FlagLetterPairerCountdown(context: Context, modifier: Modifier) {

    var currentDuration by rememberSaveable { mutableStateOf(30) }

    var startTimer by rememberSaveable { mutableStateOf(false) }
    var gameFirstStart by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(gameFirstStart) {
        if (!gameFirstStart) {
            while (true) {
                context.playSounds(listOf(ResourceSound(R.raw.popeye)))
            }
        }
    }

    var flags = ICSFlag.allFlags

    var shuffledFlags by rememberSaveable { mutableStateOf(flags.values.shuffled()) }

    var foundPairs by rememberSaveable { mutableStateOf(setOf<Char>()) }
    var attempts by rememberSaveable { mutableStateOf(0) }

    fun handlePairFound(letter: Char) {
        if (startTimer) {
            foundPairs = foundPairs + letter
            shuffledFlags = shuffledFlags.filterNot { it.letter == letter }
            attempts += 1
        }
    }

    Box(modifier = modifier
        .fillMaxSize()
        .clickable { gameFirstStart = true; startTimer = true }) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            FlagLetterPairer(
                onPairFound = { handlePairFound(it) },
                flags = shuffledFlags,
                attemptsCallback = { if (startTimer) attempts += 1 },
                modifier = Modifier.weight(9f)
            )
            if (startTimer) {
                Countdown(
                    currentDuration,
                    startTimer,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    onEnd = { startTimer = false }
                )
            } else {
                Text("Paire: ${foundPairs.size}/${flags.size}")
            }
        }
    }
}

enum class MorseSymbol { DOT, DASH }

val MORSE_ALPHABET = mapOf(
    'A' to arrayOf(MorseSymbol.DOT, MorseSymbol.DASH),
    'B' to arrayOf(MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT),
    'C' to arrayOf(MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DOT),
    'D' to arrayOf(MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DOT),
    'E' to arrayOf(MorseSymbol.DOT),
    'F' to arrayOf(MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DOT),
    'G' to arrayOf(MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DOT),
    'H' to arrayOf(MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT),
    'I' to arrayOf(MorseSymbol.DOT, MorseSymbol.DOT),
    'J' to arrayOf(MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DASH),
    'K' to arrayOf(MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DASH),
    'L' to arrayOf(MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DOT),
    'M' to arrayOf(MorseSymbol.DASH, MorseSymbol.DASH),
    'N' to arrayOf(MorseSymbol.DASH, MorseSymbol.DOT),
    'O' to arrayOf(MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DASH),
    'P' to arrayOf(MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DOT),
    'Q' to arrayOf(MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DASH),
    'R' to arrayOf(MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DOT),
    'S' to arrayOf(MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT),
    'T' to arrayOf(MorseSymbol.DASH),
    'U' to arrayOf(MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DASH),
    'V' to arrayOf(MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DASH),
    'W' to arrayOf(MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DASH),
    'X' to arrayOf(MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DASH),
    'Y' to arrayOf(MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DASH),
    'Z' to arrayOf(MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DOT),
)

suspend fun Context.playMorseSound(letter: Char) {
    val sounds = MORSE_ALPHABET[letter]
    if (sounds != null) {
        for (sound in sounds) {
            when (sound) {
                MorseSymbol.DOT -> this.playSounds(listOf(ResourceSound(R.raw.dot)))
                MorseSymbol.DASH -> this.playSounds(listOf(ResourceSound(R.raw.dash)))
            }
            this.playSounds(listOf(Silence(500)))
        }
    }
}