# Java AWT Ball Animation

A simple Java desktop application built with AWT that animates bouncing balls
and demonstrates multithreading and synchronization.

### Features

* **Add Ball** - creates a new ball in the center of the panel.
* **Shake Balls** - wakes up ball threads waiting after colliding with a panel
  edge.
* Each ball runs in its own thread.
* Balls bounce off the edges of the animation panel.
* A shared MONITOR object coordinates waiting and waking threads using wait ()
  and notifyAll ().

### Technologies

* Java
* AWT (java.awt)
* Multithreading (Thread, Runnable)
* Thread synchronization (synchronized, wait (), notifyAll ())

### Project Structure

```text
.
└── AWT/
    ├── Ball.java
    ├── BallPanel.java
    └── MyFrame.java 
```

### Requirements

* JDK installed
* A desktop environment that supports Java AWT windows

### Compile and Run

Run these commands from the directory containing the AWT folder:

```Bash
javac AWT/*.java
java AWT.MyFrame
```

Make sure MyFrame.java contains a standard Java entry point:

```Java
static void main(String[] args) {
    new MyFrame();
}
```

### Synchronization Overview

All ball threads should wait on the shared Ball.MONITOR object when a collision
is detected. The Shake Balls button calls BallPanel.shake (), which synchronizes
on the same monitor and invokes notifyAll () to notify all waiting threads.

wait () and notifyAll () must be used while holding the lock for the same
monitor object.

### Purpose

This project was created as an exercise in Java AWT animation, concurrent
execution, and coordinating multiple thread with a shared synchronization
monitor.