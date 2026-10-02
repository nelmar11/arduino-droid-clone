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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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

    val files = listOf("sketch.ino", "mosfet_driver.ino", "README.md")
    var selectedBoard by remember { mutableStateOf(boards.first().first) }
    var selectedFileTab by remember { mutableIntStateOf(0) }
    var led13On by remember { mutableStateOf(false) }
    var led12On by remember { mutableStateOf(false) }
    var buttonPressed by remember { mutableStateOf(false) }
    var mosfetGateOn by remember { mutableStateOf(false) }
    var relayOn by remember { mutableStateOf(false) }
    var motorRunning by remember { mutableStateOf(false) }
    var buzzerOn by remember { mutableStateOf(false) }
    var capacitorCharged by remember { mutableStateOf(false) }
    var resistorActive by remember { mutableStateOf(false) }
    var potValue by remember { mutableFloatStateOf(58f) }
    var tempValue by remember { mutableFloatStateOf(24f) }
    var servoAngle by remember { mutableFloatStateOf(90f) }

    val mosfetLoadOn = mosfetGateOn && potValue > 25f
    val electronicParts = listOf(
        "LED" to led13On,
        "Resistor" to resistorActive,
        "Capacitor" to capacitorCharged,
        "MOSFET" to mosfetGateOn,
        "Switch" to buttonPressed,
        "Pot" to (potValue > 30f),
        "Relay" to relayOn,
        "Motor" to motorRunning,
        "Buzzer" to buzzerOn,
        "Servo" to (servoAngle > 45f),
        "Sensor" to (tempValue > 20f),
        "Diode" to mosfetLoadOn
    )

    val arduinoCode = """const int LED_PIN = 13;
const int MOSFET_GATE = 9;
const int BUTTON_PIN = 2;
const int POT_PIN = A0;
const int RELAY_PIN = 7;

void setup() {
  pinMode(LED_PIN, OUTPUT);
  pinMode(MOSFET_GATE, OUTPUT);
  pinMode(RELAY_PIN, OUTPUT);
  pinMode(BUTTON_PIN, INPUT);
  Serial.begin(9600);
}

void loop() {
  int sensor = analogRead(POT_PIN);
  int btnState = digitalRead(BUTTON_PIN);
  bool gate = (sensor > 300) || btnState;

  analogWrite(MOSFET_GATE, gate ? 255 : 0);
  digitalWrite(RELAY_PIN, gate ? HIGH : LOW);
  digitalWrite(LED_PIN, gate ? HIGH : LOW);

  Serial.print(\"POT:\");
  Serial.println(sensor);
  delay(100);
}"""

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF08111D)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .width(280.dp)
                    .fillMaxHeight()
                    .background(Color(0xFF0A1420))
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(Color(0xFF52D1FF), Color(0xFF5D7BFF))
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("A", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("Mobile IDE", color = Color(0xFF8EA6D6), fontSize = 10.sp, letterSpacing = 0.8.sp)
                        Text("ArduinoDroid Lite", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Boards", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                    IconButton(onClick = { }, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = Color(0xFF8EA6D6), modifier = Modifier.size(16.dp))
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))

                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                    items(boards) { (board, chip) ->
                        val selected = selectedBoard == board
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (selected) Color(0xFF1F4A79) else Color(0xFF121E2D))
                                .border(
                                    width = 1.dp,
                                    color = if (selected) Color(0xFF62A5FF) else Color(0xFF2A3D5C),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable { selectedBoard = board }
                                .padding(horizontal = 14.dp, vertical = 12.dp)
                        ) {
                            Column {
                                Text(board, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                                Text(chip, color = Color(0xFF9FB6DC), fontSize = 10.sp)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Files", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                    IconButton(onClick = { }, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = Color(0xFF8EA6D6), modifier = Modifier.size(16.dp))
                    }
                }
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
                        Text(file, color = Color.White, fontSize = 11.sp)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .background(Color(0xFF08111D))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFF0D1727))
                        .border(1.dp, Color(0xFF2A3D5C), RoundedCornerShape(14.dp))
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column {
                        Text("Project", color = Color(0xFF8EA6D6), fontSize = 9.sp, letterSpacing = 0.6.sp, fontWeight = FontWeight.SemiBold)
                        Text(files[selectedFileTab], color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(onClick = { }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A2638)), modifier = Modifier.height(36.dp)) {
                            Text("Save", fontSize = 11.sp)
                        }
                        Button(onClick = { }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF59A9FF)), modifier = Modifier.height(36.dp)) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null)
                            Text("Compile", fontSize = 11.sp)
                        }
                        Button(onClick = { }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2EC699)), modifier = Modifier.height(36.dp)) {
                            Icon(Icons.Default.Bolt, contentDescription = null)
                            Text("Upload", fontSize = 11.sp)
                        }
                    }
                }

                TabRow(
                    selectedTabIndex = selectedFileTab,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                        .background(Color(0xFF0D1727)),
                    containerColor = Color(0xFF0D1727),
                    contentColor = Color.White
                ) {
                    files.forEachIndexed { index, file ->
                        Tab(
                            selected = selectedFileTab == index,
                            onClick = { selectedFileTab = index },
                            modifier = Modifier.background(if (selectedFileTab == index) Color(0xFF162A3D) else Color.Transparent)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(file, fontSize = 11.sp, fontWeight = if (selectedFileTab == index) FontWeight.SemiBold else FontWeight.Normal)
                                if (selectedFileTab == index) {
                                    IconButton(onClick = { }, modifier = Modifier.size(16.dp)) {
                                        Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(12.dp))
                                    }
                                }
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF0D1727))
                        .border(1.dp, Color(0xFF2A3D5C), RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(selectedBoard, color = Color(0xFF8EA6D6), fontSize = 10.sp)
                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                Text("${arduinoCode.lines().size} lines", color = Color(0xFF8EA6D6), fontSize = 10.sp)
                                Text("${arduinoCode.length} chars", color = Color(0xFF8EA6D6), fontSize = 10.sp)
                            }
                        }
                        Divider(color = Color(0xFF2A3D5C), thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(arduinoCode.lines().size) { lineIndex ->
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        "${lineIndex + 1}",
                                        color = Color(0xFF5A6D8A),
                                        fontSize = 10.sp,
                                        modifier = Modifier.width(30.dp).padding(end = 8.dp),
                                        fontFamily = FontFamily.Monospace
                                    )
                                    Text(
                                        arduinoCode.lines().getOrNull(lineIndex) ?: "",
                                        color = Color(0xFFDCE7FF),
                                        fontSize = 10.sp,
                                        fontFamily = FontFamily.Monospace,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Card(
                        modifier = Modifier.weight(0.6f),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF0D1727)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF2A3D5C))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Serial Monitor", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 11.sp)
                                Text("9600", color = Color(0xFF8EA6D6), fontSize = 10.sp)
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(0xFF08131D))
                                    .padding(10.dp)
                                    .verticalScroll(rememberScrollState())
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text("[INFO] Device connected", color = Color(0xFFDDE8FF), fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                                    Text("[INFO] Board: $selectedBoard", color = Color(0xFFDDE8FF), fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                                    Text("POT:${potValue.toInt()}", color = Color(0xFF7FEAB1), fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                                    Text("MOSFET:${if (mosfetLoadOn) "ON" else "OFF"}", color = Color(0xFF7FEAB1), fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                                    Text("RELAY:${if (relayOn) "ON" else "OFF"}", color = Color(0xFF7FEAB1), fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                                    Text("MOTOR:${if (motorRunning) "RUN" else "STOP"}", color = Color(0xFF7FEAB1), fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                                }
                            }
                            Button(onClick = { }, modifier = Modifier.fillMaxWidth().height(28.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A2638))) {
                                Text("Clear", fontSize = 10.sp)
                            }
                        }
                    }

                    ElectronicSimulator(
                        modifier = Modifier.weight(0.4f),
                        led13On = led13On,
                        led12On = led12On,
                        buttonPressed = buttonPressed,
                        mosfetGateOn = mosfetGateOn,
                        mosfetLoadOn = mosfetLoadOn,
                        relayOn = relayOn,
                        motorRunning = motorRunning,
                        buzzerOn = buzzerOn,
                        capacitorCharged = capacitorCharged,
                        resistorActive = resistorActive,
                        potValue = potValue,
                        tempValue = tempValue,
                        servoAngle = servoAngle,
                        electronicParts = electronicParts,
                        onLed13Toggle = { led13On = !led13On },
                        onLed12Toggle = { led12On = !led12On },
                        onButtonToggle = { buttonPressed = !buttonPressed },
                        onMosfetGateToggle = { mosfetGateOn = !mosfetGateOn },
                        onRelayToggle = { relayOn = !relayOn },
                        onMotorToggle = { motorRunning = !motorRunning },
                        onBuzzerToggle = { buzzerOn = !buzzerOn },
                        onCapacitorToggle = { capacitorCharged = !capacitorCharged },
                        onResistorToggle = { resistorActive = !resistorActive },
                        onPotChange = { potValue = it },
                        onTempChange = { tempValue = it },
                        onServoChange = { servoAngle = it }
                    )
                }
            }
        }
    }
}

@Composable
fun ElectronicSimulator(
    modifier: Modifier = Modifier,
    led13On: Boolean,
    led12On: Boolean,
    buttonPressed: Boolean,
    mosfetGateOn: Boolean,
    mosfetLoadOn: Boolean,
    relayOn: Boolean,
    motorRunning: Boolean,
    buzzerOn: Boolean,
    capacitorCharged: Boolean,
    resistorActive: Boolean,
    potValue: Float,
    tempValue: Float,
    servoAngle: Float,
    electronicParts: List<Pair<String, Boolean>>,
    onLed13Toggle: () -> Unit,
    onLed12Toggle: () -> Unit,
    onButtonToggle: () -> Unit,
    onMosfetGateToggle: () -> Unit,
    onRelayToggle: () -> Unit,
    onMotorToggle: () -> Unit,
    onBuzzerToggle: () -> Unit,
    onCapacitorToggle: () -> Unit,
    onResistorToggle: () -> Unit,
    onPotChange: (Float) -> Unit,
    onTempChange: (Float) -> Unit,
    onServoChange: (Float) -> Unit,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0D1727)),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF2A3D5C))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text("Electronic Parts", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 11.sp)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF162A3D))
                    .padding(12.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ComponentVisualization(label = "LED13", isActive = led13On, type = "led", onClick = onLed13Toggle)
                        ComponentVisualization(label = "LED12", isActive = led12On, type = "led", onClick = onLed12Toggle)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ComponentVisualization(label = "BTN", isActive = buttonPressed, type = "button", onClick = onButtonToggle)
                    }

                    MosfetVisualization(
                        gateOn = mosfetGateOn,
                        loadOn = mosfetLoadOn,
                        onGateToggle = onMosfetGateToggle
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PartChip(label = "Relay", active = relayOn, onClick = onRelayToggle)
                        PartChip(label = "Motor", active = motorRunning, onClick = onMotorToggle)
                        PartChip(label = "Buzzer", active = buzzerOn, onClick = onBuzzerToggle)
                        PartChip(label = "Cap", active = capacitorCharged, onClick = onCapacitorToggle)
                        PartChip(label = "Res", active = resistorActive, onClick = onResistorToggle)
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("POT", color = Color(0xFF8EA6D6), fontSize = 9.sp)
                        Slider(value = potValue, onValueChange = onPotChange, valueRange = 0f..100f, modifier = Modifier.fillMaxWidth())
                        Text("${potValue.toInt()}%", color = Color(0xFF8EA6D6), fontSize = 8.sp)
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("TEMP", color = Color(0xFF8EA6D6), fontSize = 9.sp)
                        Slider(value = tempValue, onValueChange = onTempChange, valueRange = 0f..100f, modifier = Modifier.fillMaxWidth())
                        Text("${tempValue.toInt()}°C", color = Color(0xFF8EA6D6), fontSize = 8.sp)
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("SERVO", color = Color(0xFF8EA6D6), fontSize = 9.sp)
                        Slider(value = servoAngle, onValueChange = onServoChange, valueRange = 0f..180f, modifier = Modifier.fillMaxWidth())
                        Text("${servoAngle.toInt()}°", color = Color(0xFF8EA6D6), fontSize = 8.sp)
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("Component Library", color = Color(0xFF8EA6D6), fontSize = 9.sp)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            electronicParts.forEach { (label, active) ->
                                PartChip(label = label, active = active, onClick = {})
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ComponentVisualization(
    label: String,
    isActive: Boolean,
    type: String,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (isActive) Color(0xFF2A4A6F) else Color(0xFF1A2A3F))
            .border(1.dp, if (isActive) Color(0xFF62A5FF) else Color(0xFF3A4A6F), RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(
                    when (type) {
                        "led" -> if (isActive) Color(0xFFFFD54A) else Color(0xFF5D6B82)
                        "button" -> if (isActive) Color(0xFF51D39A) else Color(0xFF5D6B82)
                        else -> Color(0xFF5D6B82)
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            if (type == "led" && isActive) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFFF06E))
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(label, color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun PartChip(
    label: String,
    active: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(if (active) Color(0xFF2A4A6F) else Color(0xFF233448))
            .border(1.dp, if (active) Color(0xFF62A5FF) else Color(0xFF3A4A6F), RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(label, color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun MosfetVisualization(
    gateOn: Boolean,
    loadOn: Boolean,
    onGateToggle: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF1A2A3F))
            .border(1.dp, Color(0xFF3A4A6F), RoundedCornerShape(12.dp))
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("N-MOSFET", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 10.sp)
            Button(
                onClick = onGateToggle,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (gateOn) Color(0xFF2EC699) else Color(0xFF3A4A6F)
                ),
                modifier = Modifier.height(26.dp)
            ) {
                Text(if (gateOn) "Gate ON" else "Gate OFF", fontSize = 9.sp)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF2B3C52))
                    .border(1.dp, Color(0xFF5E7AA4), RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("G", color = if (gateOn) Color(0xFF7FEAB1) else Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }

            Box(
                modifier = Modifier
                    .size(width = 70.dp, height = 18.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (loadOn) Color(0xFF3BA779) else Color(0xFF4B5A70))
            )

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(if (loadOn) Color(0xFFFFD54A) else Color(0xFF5D6B82))
            )
        }

        Text(
            text = "Load ${if (loadOn) "ACTIVE" else "OFF"}",
            color = if (loadOn) Color(0xFF7FEAB1) else Color(0xFF8EA6D6),
            fontSize = 9.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ArduinoDroidLiteTheme {
        ArduinoDroidApp()
    }
}
