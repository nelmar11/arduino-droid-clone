# A simple Arduino sketch editor inspired by ArduinoDroid

This project is a lightweight, mobile-first web app that mimics the feel of an Arduino development environment:

- board selection
- project file navigation
- sketch editing in a code area
- compile and upload actions
- serial monitor output

## Run locally

```bash
npm install
npm run dev -- --host 0.0.0.0 --port 3000
```

Then open http://localhost:3000.

## Build

```bash
npm run build
```

## Notes

This is a front-end prototype designed for quick iteration. It includes mocked compilation and upload behavior rather than a real Arduino toolchain.
