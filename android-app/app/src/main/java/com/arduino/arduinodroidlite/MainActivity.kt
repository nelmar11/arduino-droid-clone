package com.arduino.arduinodroidlite

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArduinoDroidLiteTheme {
                ArduinoDroidApp()
            }
        }
    }
}

@Composable
fun ArduinoDroidLiteTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme,
        content = content,
    )
}

@Composable
fun ArduinoDroidApp() {
    val boards = listOf(
        "Arduino Uno" to "ATmega328P",
        "Nano 33 IoT" to "SAMD21",
        "ESP32 DevKit" to "ESP32-WROOM",
        "Mega 2560" to "ATmega2560"
    )

    val files = listOf("sketch.ino", "README.md", "board.json")
    var selectedBoard by remember { mutableStateOf(boards.first().first) }
    var ledOn by remember { mutableStateOf(false) }
    var buttonPressed by remember { mutableStateOf(false) }
    var potValue by remember { mutableFloatStateOf(58f) }
    var code by remember { mutableStateOf("const int ledPin = 13;\n\nvoid setup() {\n  pinMode(ledPin, OUTPUT);\n  Serial.begin(9600);\n}\n\nvoid loop() {\n  digitalWrite(ledPin, HIGH);\n  delay(500);\n  digitalWrite(ledPin, LOW);\n  delay(500);\n}\n") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF08111D)
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF0A1420))
                    .padding(16.dp)
                    .weight(0.9f),
                verticalArrangement = Arrangement.Top
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(Color(0xFF52D1FF), Color(0xFF5D7BFF))
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("A", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.size(12.dp))
                    Column {
                        Text(
                            text = "Mobile IDE",
                            color = Color(0xFF8EA6D6),
                            fontSize = 10.sp,
                            letterSpacing = 0.8.sp,
                        )
                        Text("ArduinoDroid Lite", color = Color.White, fontWeight = FontWeight.SemiBold)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text("Boards", color = Color.White, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(10.dp))

                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(boards) { (board, chip) ->
                        val selected = selectedBoard == board
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    if (selected) Color(0xFF1F4A79) else Color(0xFF121E2D)
                                )
                                .border(
                                    width = 1.dp,
                                    color = if (selected) Color(0xFF62A5FF) else Color(0xFF2A3D5C),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable { selectedBoard = board }
                                .padding(horizontal = 14.dp, vertical = 12.dp)
                        ) {
                            Column {
                                Text(board, color = Color.White)
                                Text(chip, color = Color(0xFF9FB6DC), fontSize = 12.sp)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                Text("Files", color = Color.White, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(10.dp))

                files.forEach { file ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF121E2D))
                            .border(
                                width = 1.dp,
                                color = if (file == "sketch.ino") Color(0xFF62A5FF) else Color(0xFF2A3D5C),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 10.dp)
                    ) {
                        Text(file, color = Color.White)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1.9f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color(0xFF0D1727))
                        .padding(horizontal = 18.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column {
                        Text("Project", color = Color(0xFF8EA6D6), fontSize = 10.sp, letterSpacing = 0.8.sp)
                        Text("sketch.ino", color = Color.White, fontWeight = FontWeight.SemiBold)
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Button(
                            onClick = { },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A2638))
                        ) { Text("Save") }
                        Button(
                            onClick = { },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF59A9FF))
                        ) { Icon(Icons.Default.PlayArrow, contentDescription = null); Text("Compile") }
                        Button(
                            onClick = { },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2EC699))
                        ) { Icon(Icons.Default.Bolt, contentDescription = null); Text("Upload") }
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0D1727)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF2A3D5C))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(selectedBoard, color = Color.White, fontWeight = FontWeight.SemiBold)
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(Color(0xFF263D55))
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text("READY", color = Color(0xFF7FEAB1), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color(0xFF08131D))
                                .padding(14.dp)
                        ) {
                            androidx.compose.foundation.text.BasicTextField(
                                value = code,
                                onValueChange = { code = it },
                                modifier = Modifier.fillMaxWidth(),
                                textStyle = androidx.compose.ui.text.TextStyle(
                                    color = Color(0xFFDCE7FF),
                                    fontSize = 13.sp,
                                    lineHeight = 18.sp,
                                )
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Card(
                        modifier = Modifier.weight(0.75f),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF0D1727)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF2A3D5C))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Board config", color = Color(0xFF8EA6D6), fontSize = 10.sp, letterSpacing = 0.8.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(selectedBoard, color = Color.White, fontWeight = FontWeight.SemiBold)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("Chip: ${boards.first { it.first == selectedBoard }.second}", color = Color(0xFFDFE9FF))
                            Text("Voltage: 5V", color = Color(0xFFDFE9FF))
                            Text("Memory: 32 KB", color = Color(0xFFDFE9FF))
                        }
                    }

                    Card(
                        modifier = Modifier.weight(1.25f),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF0D1727)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF2A3D5C))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Serial Monitor", color = Color.White, fontWeight = FontWeight.SemiBold)
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0xFF08131D))
                                    .padding(12.dp)
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Text("[INFO] Device connected successfully.", color = Color(0xFFDDE8FF))
                                    Text("[INFO] Board: Arduino Uno", color = Color(0xFFDDE8FF))
                                    Text("[INFO] Ready for sketch upload.", color = Color(0xFFDDE8FF))
                                }
                            }
                        }
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0D1727)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF2A3D5C))
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Wokwi-inspired circuit", color = Color.White, fontWeight = FontWeight.SemiBold)

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(18.dp))
                                .background(Color(0xFF0B1724))
                                .padding(16.dp)
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(18.dp))
                                        .background(Color(0xFF162433))
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("ATmega328P", color = Color.White, fontWeight = FontWeight.Bold)
                                        Spacer(modifier = Modifier.height(12.dp))
                                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                            listOf("D0", "D1", "D2", "A0", "A1", "5V", "GND").forEach { pin ->
                                                Box(
                                                    modifier = Modifier
                                                        .clip(RoundedCornerShape(20.dp))
                                                        .background(Color(0xFF253D56))
                                                        .padding(horizontal = 8.dp, vertical = 5.dp)
                                                ) {
                                                    Text(pin, color = Color.White, fontSize = 10.sp)
                                                }
                                            }
                                        }
                                    }
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Box(
                                            modifier = Modifier
                                                .size(42.dp)
                                                .clip(CircleShape)
                                                .background(if (ledOn) Color(0xFFFFD54A) else Color(0xFF5D6B82))
                                        )
                                        Text("LED13", color = Color.White)
                                    }

                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Box(
                                            modifier = Modifier
                                                .size(42.dp)
                                                .clip(CircleShape)
                                                .background(if (buttonPressed) Color(0xFF51D39A) else Color(0xFF5D6B82))
                                        )
                                        Text("BTN", color = Color.White)
                                    }

                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Box(
                                            modifier = Modifier
                                                .size(42.dp)
                                                .clip(CircleShape)
                                                .background(Color(0xFF5D6B82))
                                        )
                                        Text("POT ${potValue.toInt()}%", color = Color.White)
                                    }
                                }

                                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                    Button(onClick = { ledOn = !ledOn }) { Text("Toggle LED") }
                                    Button(onClick = { buttonPressed = !buttonPressed }) { Text("Button") }
                                }

                                Slider(
                                    value = potValue,
                                    onValueChange = { potValue = it },
                                    valueRange = 0f..100f,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ArduinoDroidLiteTheme {
        ArduinoDroidApp()
    }
}
