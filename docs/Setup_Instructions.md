# Setup Instructions

## JDK version

This project is built and tested with **JDK 21** (`maven.compiler.release` in `pom.xml`).

Verify your installation:

```bash
java -version
javac -version
```

You should see version **21** (or newer) from the JDK, not only a JRE.

## IDE setup

1. Import the project as a **Maven** project.
2. Set the project SDK to **JDK 21**.
3. Run the main class: `com.airtribe.learntrack.ui.ConsoleApp`.

## Hello World (terminal)

Create `HelloWorld.java`:

```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, LearnTrack!");
    }
}
```

Compile and run:

```bash
javac HelloWorld.java
java HelloWorld
```

Expected output:

```
Hello, LearnTrack!
```

This confirms the JDK compiler (`javac`) and JVM (`java`) are configured correctly.

## Running LearnTrack

```bash
cd learntrack
mvn clean compile exec:java
```

Use the numbered menus to add students and courses, then create enrollments.

**Tip:** After deactivating a student, they disappear from “View all students” but can still be found with **Search student by ID** (status shows `Active: false`). Use **Activate student** before update or new enrollments.
