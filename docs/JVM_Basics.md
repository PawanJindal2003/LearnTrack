# JVM Basics

## JDK, JRE, and JVM

- **JVM (Java Virtual Machine)** runs compiled Java programs. It reads **bytecode** and executes it on your operating system.
- **JRE (Java Runtime Environment)** is the JVM plus standard libraries needed to **run** Java applications. It does not include development tools.
- **JDK (Java Development Kit)** is the full toolkit for **writing** Java programs: compiler (`javac`), debugger tools, and the JRE.

When you develop LearnTrack, you use the **JDK** to compile `.java` files into `.class` bytecode, then the **JVM** runs `ConsoleApp`.

## What is bytecode?

When you run `javac` or Maven compile, Java source code is translated into **bytecode** (`.class` files). Bytecode is not machine code for one CPU; it is an instruction set for the JVM. The JVM interprets or JIT-compiles bytecode into native instructions at runtime.

## Write once, run anywhere

Java source is compiled to bytecode, and the **same bytecode** can run on any device that has a compatible JVM (Windows, Linux, macOS, etc.). You do not recompile separately for each operating system—the JVM on each platform handles the differences. That portability is what people mean by **“write once, run anywhere”**, as long as you target a supported JVM version.
