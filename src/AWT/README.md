# Java AWT Ball Animation

A simple Java desktop application built with AWT that animates balls and
demonstrates multithreading, synchronization, and different concurrency models.

### Features

* **Add Ball** - creates a new ball in the center of the panel.
* **Shake Balls** - wakes up ball waiting after colliding with the panel edges.
* Balls move independently and react to collisions with the panel boundaries.
* Different concurrency models can be compared: traditional threads, scheduled
  tasks, and virtual threads.
* Synchronization mechanisms control when balls stop and resume moving.

### Technologies

* Java
* AWT (java.awt)
* Multithreading (Thread, Runnable)
* Task scheduling (ScheduledExecutorsService)
* Virtual thread (ExecutorService)
* Synchronization (synchronized, wait (), notifyAll (), Lock, Condition,
  Semaphore)

### Project Structure

```text
.
└── AWT/
    ├── Ball.java
    ├── BallPanel.java
    ├── MyFrame.java
    ├── ThreadBall.java
    ├── ScheduledBall.java
    ├── VBall.java
    ├── VThreadBall.java
    └── VsThreadBall.java 
```

### Requirements

* JDK installed
* A desktop environment that supports Java AWT windows
* Java 21 or later for virtual threads.

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

#### MONITOR

A shared MONITOR object coordinates threads using synchronized, wait (), and
notifyAll ().

* wait () suspends a thread and releases the monitor lock.
* notifyAll () notifies all threads waiting on the same monitor.

Both methods must be called while holding the lock associated with that monitor.

#### Lock and Condition

Lock provides explicit control over locking, while Condition allows thread to
wait for a particular condition.

The await () method suspends a thread, and signalAll () notifies waiting
threads. The waiting condition should be checked in a loop to ensure that a
thread resumes only when the required state has been reached.

#### Semaphore

A semaphore controls execution using permits. The acquire () methods obtains a
permit, while release () adds one.

Unike monitor notification, semaphore permits remain available until the are
acquired. Incorrect placement of these calls can cause a ball to resume too
early or wait indefinitely.

### Comparison and Conclusions

Traditional thread are straightforward and suitable for applications with a
relatively small number of concurrent tasks. Scheduled tasks simplify periodic
updates and centralize their execution. Virtual threads reduce the overhead of
supporting many concurrent tasks, especially when thread spend time waiting.

For an application with many balls, virtual threads appear to be a promising
solution because they are lightweight and scalable. Lock/Condition provides
explicit control over waiting conditions, while Semaphore offers a simple
permits-based mechanism.