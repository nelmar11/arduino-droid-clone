:root {
  color: #e5eefb;
  background: #0b1220;
  font-family: Inter, 'Segoe UI', sans-serif;
  line-height: 1.5;
  font-weight: 400;
  color-scheme: dark;
  font-synthesis: none;
  text-rendering: optimizeLegibility;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

* {
  box-sizing: border-box;
}

html,
body,
#root {
  margin: 0;
  min-height: 100%;
  min-width: 0;
  height: 100%;
  background:
    radial-gradient(circle at top, rgba(77, 119, 255, 0.28), transparent 26%),
    linear-gradient(180deg, #08111d 0%, #0d1727 100%);
}

button,
textarea,
input {
  font: inherit;
}

button {
  cursor: pointer;
}

.app-shell {
  display: grid;
  grid-template-columns: 320px minmax(0, 1fr);
  min-height: 100vh;
}

.side-panel {
  background: rgba(8, 15, 26, 0.85);
  border-right: 1px solid rgba(143, 168, 219, 0.18);
  padding: 24px 18px;
}

.brand-block {
  display: flex;
  align-items: center;
  gap: 14px;
  margin-bottom: 24px;
}

.logo {
  width: 42px;
  height: 42px;
  display: grid;
  place-items: center;
  border-radius: 12px;
  background: linear-gradient(135deg, #52d1ff, #5d7bff);
  color: white;
  font-weight: 800;
  box-shadow: 0 12px 22px rgba(82, 209, 255, 0.35);
}

.eyebrow {
  margin: 0;
  font-size: 0.7rem;
  letter-spacing: 0.08em;
  text-transform: uppercase;
  color: #8ea6d6;
}

h1,
h2,
h3,
p {
  margin: 0;
}

h1 {
  font-size: 1.2rem;
}

h2 {
  font-size: 1.5rem;
}

.panel-section {
  margin-top: 22px;
}

.section-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 12px;
  color: #dfe9ff;
  font-weight: 600;
}

.section-header button,
.topbar-actions button,
.serial-card button,
.sim-controls button {
  border: none;
  background: rgba(127, 146, 202, 0.12);
  color: #edf4ff;
  border-radius: 8px;
  padding: 8px 12px;
  transition: 150ms ease;
}

.section-header button:hover,
.topbar-actions button:hover,
.serial-card button:hover,
.sim-controls button:hover {
  background: rgba(127, 146, 202, 0.2);
}

.board-list,
.file-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.board-item,
.file-item {
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border: 1px solid rgba(138, 170, 232, 0.2);
  border-radius: 12px;
  padding: 12px 14px;
  background: rgba(15, 23, 36, 0.8);
  color: #eef5ff;
  text-align: left;
}

.board-item small,
.file-item small {
  color: #94abc8;
}

.board-item.selected,
.file-item.active {
  border-color: rgba(96, 165, 250, 0.8);
  background: rgba(26, 71, 130, 0.44);
  box-shadow: inset 0 0 0 1px rgba(96, 165, 250, 0.4);
}

.workspace {
  display: flex;
  flex-direction: column;
  padding: 24px;
  gap: 22px;
}

.topbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
  padding: 18px 20px;
  background: rgba(10, 17, 28, 0.7);
  border: 1px solid rgba(143, 168, 219, 0.18);
  border-radius: 18px;
}

.topbar-actions {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}

.primary-btn {
  background: linear-gradient(135deg, #5ad2ff, #5987ff) !important;
  color: #071321 !important;
  font-weight: 700;
}

.accent-btn {
  background: linear-gradient(135deg, #63f2b1, #2ec699) !important;
  color: #081a16 !important;
  font-weight: 700;
}

.simulator-panel,
.editor-panel,
.stats-card,
.serial-card {
  background: rgba(10, 17, 28, 0.7);
  border: 1px solid rgba(143, 168, 219, 0.18);
  border-radius: 18px;
}

.simulator-panel {
  padding: 18px;
}

.simulator-header {
  display: flex;
  justify-content: space-between;
  gap: 12px;
  margin-bottom: 16px;
  align-items: center;
}

.board-visual {
  position: relative;
  padding: 26px 18px 18px;
  border-radius: 20px;
  border: 1px solid rgba(143, 168, 219, 0.18);
  background:
    linear-gradient(180deg, rgba(13, 22, 35, 0.95), rgba(8, 17, 27, 0.9)),
    #0d1727;
  overflow: hidden;
}

.board-shadow {
  position: absolute;
  inset: auto 18px 10px 18px;
  height: 12px;
  background: rgba(83, 102, 188, 0.22);
  filter: blur(16px);
  border-radius: 999px;
}

.microcontroller {
  position: relative;
  z-index: 1;
  background: linear-gradient(180deg, rgba(76, 98, 148, 0.28), rgba(16, 34, 52, 0.7));
  border: 1px solid rgba(138, 170, 232, 0.22);
  border-radius: 18px;
  padding: 18px 16px 12px;
}

.chip-name {
  font-weight: 700;
  color: #dfe9ff;
  margin-bottom: 14px;
}

.pin-row {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 8px;
}

.pin-tag {
  border: 1px solid rgba(148, 178, 255, 0.25);
  background: rgba(111, 130, 194, 0.12);
  padding: 6px 9px;
  border-radius: 999px;
  font-size: 0.75rem;
  color: #dfe9ff;
}

.components-row {
  position: relative;
  z-index: 1;
  display: grid;
  grid-template-columns: repeat(3, minmax(100px, 1fr));
  gap: 14px;
  margin-top: 18px;
}

.component {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 10px;
  min-height: 110px;
  border-radius: 16px;
  border: 1px solid rgba(138, 170, 232, 0.18);
  background: rgba(11, 18, 30, 0.88);
  color: #edf4ff;
}

.component-label {
  font-size: 0.78rem;
  letter-spacing: 0.08em;
  text-transform: uppercase;
  color: #a8bde5;
}

.led-light {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  background: radial-gradient(circle, rgba(255, 255, 255, 0.8), rgba(119, 130, 150, 0.8));
  box-shadow: inset 0 0 16px rgba(255, 255, 255, 0.45), 0 0 18px rgba(148, 168, 255, 0.35);
}

.led.active .led-light {
  background: radial-gradient(circle, rgba(255, 255, 121, 1), rgba(255, 170, 0, 0.9));
  box-shadow: 0 0 22px rgba(255, 220, 117, 0.9);
}

.button {
  position: relative;
}

.button::before {
  content: '';
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: linear-gradient(180deg, #dfe9ff, #7a8599);
  box-shadow: inset 0 0 0 3px rgba(20, 28, 42, 0.7);
}

.button.pressed::before {
  background: linear-gradient(180deg, #7ef1c3, #41b78d);
  transform: translateY(3px);
}

.sensor strong {
  font-size: 1.2rem;
}

.sim-controls {
  position: relative;
  z-index: 1;
  margin-top: 18px;
  display: flex;
  align-items: center;
  gap: 12px;
  flex-wrap: wrap;
}

.sim-controls button {
  background: rgba(124, 146, 196, 0.14);
}

.sim-controls label {
  display: inline-flex;
  align-items: center;
  gap: 10px;
  padding: 8px 12px;
  border-radius: 10px;
  background: rgba(124, 146, 196, 0.1);
  color: #dfe9ff;
}

.sim-controls input {
  accent-color: #79d1ff;
}

.editor-panel {
  padding: 0 0 12px;
}

.editor-toolbar {
  display: flex;
  gap: 14px;
  align-items: center;
  flex-wrap: wrap;
  padding: 14px 18px;
  border-bottom: 1px solid rgba(143, 168, 219, 0.12);
  color: #dfe9ff;
  font-size: 0.88rem;
}

.status-badge {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  min-width: 90px;
  padding: 6px 10px;
  border-radius: 999px;
  font-size: 0.7rem;
  font-weight: 700;
  letter-spacing: 0.08em;
  text-transform: uppercase;
}

.status-ready {
  background: rgba(57, 200, 115, 0.2);
  color: #7feeb1;
}

.status-compiling {
  background: rgba(250, 204, 21, 0.18);
  color: #ffd76a;
}

.status-uploading {
  background: rgba(96, 165, 250, 0.18);
  color: #8ec5ff;
}

.status-error {
  background: rgba(239, 68, 68, 0.18);
  color: #ff9a9a;
}

textarea {
  width: 100%;
  min-height: 330px;
  border: 0;
  resize: vertical;
  background: rgba(8, 11, 18, 0.7);
  color: #dce7ff;
  padding: 18px 20px 22px;
  font-family: 'SFMono-Regular', Consolas, 'Liberation Mono', monospace;
  line-height: 1.6;
  font-size: 0.95rem;
  outline: none;
}

.inspector-row {
  display: grid;
  grid-template-columns: minmax(220px, 0.8fr) minmax(0, 1.7fr);
  gap: 20px;
}

.stats-card,
.serial-card {
  padding: 18px 18px 16px;
}

.stats-card ul {
  margin: 14px 0 0;
  padding-left: 18px;
  color: #dfe9ff;
  display: grid;
  gap: 8px;
}

.small-header {
  margin-bottom: 14px;
}

.console {
  border-radius: 12px;
  border: 1px solid rgba(138, 170, 232, 0.14);
  background: rgba(8, 11, 18, 0.9);
  min-height: 180px;
  max-height: 240px;
  overflow: auto;
  padding: 14px;
  display: grid;
  gap: 8px;
}

.console-line {
  font-family: 'SFMono-Regular', Consolas, monospace;
  color: #dfe9ff;
  opacity: 0.92;
}

@media (max-width: 900px) {
  .app-shell {
    grid-template-columns: 1fr;
  }

  .side-panel {
    border-right: none;
    border-bottom: 1px solid rgba(143, 168, 219, 0.18);
  }

  .inspector-row {
    grid-template-columns: 1fr;
  }

  .workspace {
    padding: 16px;
  }

  .topbar {
    flex-direction: column;
    align-items: flex-start;
  }
}
