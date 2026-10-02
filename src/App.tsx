import { useMemo, useState } from 'react';

type Board = {
  name: string;
  chip: string;
  memory: string;
  voltage: string;
};

type ProjectFile = {
  name: string;
  language: string;
  active?: boolean;
};

const boards: Board[] = [
  { name: 'Arduino Uno', chip: 'ATmega328P', memory: '32 KB', voltage: '5V' },
  { name: 'Nano 33 IoT', chip: 'SAMD21', memory: '256 KB', voltage: '3.3V' },
  { name: 'ESP32 DevKit', chip: 'ESP32-WROOM', memory: '4 MB', voltage: '3.3V' },
  { name: 'Mega 2560', chip: 'ATmega2560', memory: '256 KB', voltage: '5V' },
];

const projectFiles: ProjectFile[] = [
  { name: 'sketch.ino', language: 'C++', active: true },
  { name: 'README.md', language: 'Markdown' },
  { name: 'board.json', language: 'JSON' },
];

const initialSketch = `const int ledPin = 13;

void setup() {
  pinMode(ledPin, OUTPUT);
  Serial.begin(9600);
}

void loop() {
  digitalWrite(ledPin, HIGH);
  Serial.println("LED ON");
  delay(500);

  digitalWrite(ledPin, LOW);
  Serial.println("LED OFF");
  delay(500);
}`;

const initialConsole = [
  '[INFO] Device connected successfully.',
  '[INFO] Board: Arduino Uno',
  '[INFO] Port: /dev/ttyUSB0',
  '[INFO] Ready for sketch upload.',
];

function App() {
  const [selectedBoard, setSelectedBoard] = useState<Board>(boards[0]);
  const [activeFile, setActiveFile] = useState('sketch.ino');
  const [sketch, setSketch] = useState(initialSketch);
  const [consoleLines, setConsoleLines] = useState<string[]>(initialConsole);
  const [status, setStatus] = useState<'ready' | 'compiling' | 'uploading' | 'error'>('ready');

  const compileStats = useMemo(() => {
    const lineCount = sketch.split('\n').length;
    const charCount = sketch.length;
    return {
      lineCount,
      charCount,
      board: selectedBoard.name,
    };
  }, [selectedBoard.name, sketch]);

  const handleCompile = () => {
    setStatus('compiling');
    setConsoleLines([
      '[INFO] Compiling sketch...',
      `[INFO] Target board: ${selectedBoard.name}`,
      '[INFO] Analyzing dependencies...',
      '[INFO] Generating binary...',
    ]);

    window.setTimeout(() => {
      setStatus('ready');
      setConsoleLines((current) => [
        ...current,
        '[SUCCESS] Build successful. Binary generated in ./build/arduino_droid_lite.ino.bin',
        '[INFO] Memory usage: 18% of program storage space.',
      ]);
    }, 1200);
  };

  const handleUpload = () => {
    setStatus('uploading');
    setConsoleLines([
      '[INFO] Preparing upload...',
      '[INFO] Opening serial port...',
      '[INFO] Transferring sketch...',
    ]);

    window.setTimeout(() => {
      setStatus('ready');
      setConsoleLines((current) => [
        ...current,
        '[SUCCESS] Upload complete. Device reset successfully.',
        '[MONITOR] Serial monitor connected.',
      ]);
    }, 1400);
  };

  return (
    <div className="app-shell">
      <aside className="side-panel">
        <div className="brand-block">
          <div className="logo">A</div>
          <div>
            <p className="eyebrow">Mobile IDE</p>
            <h1>ArduinoDroid Lite</h1>
          </div>
        </div>

        <div className="panel-section">
          <div className="section-header">
            <span>Boards</span>
            <button type="button">Add</button>
          </div>

          <div className="board-list">
            {boards.map((board) => (
              <button
                key={board.name}
                type="button"
                className={`board-item ${selectedBoard.name === board.name ? 'selected' : ''}`}
                onClick={() => setSelectedBoard(board)}
              >
                <span>{board.name}</span>
                <small>{board.chip}</small>
              </button>
            ))}
          </div>
        </div>

        <div className="panel-section">
          <div className="section-header">
            <span>Files</span>
            <button type="button">New</button>
          </div>

          <div className="file-list">
            {projectFiles.map((file) => (
              <button
                key={file.name}
                type="button"
                className={`file-item ${activeFile === file.name ? 'active' : ''}`}
                onClick={() => setActiveFile(file.name)}
              >
                <span>{file.name}</span>
                <small>{file.language}</small>
              </button>
            ))}
          </div>
        </div>
      </aside>

      <main className="workspace">
        <header className="topbar">
          <div>
            <p className="eyebrow">Project</p>
            <h2>{activeFile}</h2>
          </div>

          <div className="topbar-actions">
            <button type="button" className="ghost-btn">
              Save
            </button>
            <button type="button" className="primary-btn" onClick={handleCompile}>
              Compile
            </button>
            <button type="button" className="accent-btn" onClick={handleUpload}>
              Upload
            </button>
          </div>
        </header>

        <section className="editor-panel">
          <div className="editor-toolbar">
            <span className="status-badge status-${status}">{status.toUpperCase()}</span>
            <span>{compileStats.board}</span>
            <span>{compileStats.lineCount} lines</span>
            <span>{compileStats.charCount} chars</span>
          </div>

          <textarea
            value={sketch}
            onChange={(event) => setSketch(event.target.value)}
            spellCheck={false}
            aria-label="Arduino sketch editor"
          />
        </section>

        <section className="inspector-row">
          <div className="stats-card">
            <p className="eyebrow">Board config</p>
            <h3>{selectedBoard.name}</h3>
            <ul>
              <li>Chip: {selectedBoard.chip}</li>
              <li>Memory: {selectedBoard.memory}</li>
              <li>Voltage: {selectedBoard.voltage}</li>
            </ul>
          </div>

          <div className="serial-card">
            <div className="section-header small-header">
              <span>Serial Monitor</span>
              <button type="button">Clear</button>
            </div>

            <div className="console">
              {consoleLines.map((line, index) => (
                <div key={`${line}-${index}`} className="console-line">
                  {line}
                </div>
              ))}
            </div>
          </div>
        </section>
      </main>
    </div>
  );
}

export default App;
