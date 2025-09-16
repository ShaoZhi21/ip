# Jimmy project template

This is a project template for a greenfield Java project. It's named Jimmy, a musical task management assistant. Given below are instructions on how to use it.

## Setting up in Intellij

Prerequisites: JDK 17, update Intellij to the most recent version.

1. Open Intellij (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project first)
1. Open the project into Intellij as follows:
   1. Click `Open`.
   1. Select the project directory, and click `OK`.
   1. If there are any further prompts, accept the defaults.
1. Configure the project to use **JDK 17** (not other versions) as explained in [here](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk).<br>
   In the same dialog, set the **Project language level** field to the `SDK default` option.
1. After that, locate the `src/main/java/Launcher.java` file, right-click it, and choose `Run Launcher.main()` (if the code editor is showing compile errors, try restarting the IDE). If the setup is correct, you should see Jimmy's GUI window open with the title "Jimmy - Task Management Assistant".

**Warning:** Keep the `src\main\java` folder as the root folder for Java files (i.e., don't rename those folders or move Java files to another folder outside of this folder path), as this is the default location some tools (e.g., Gradle) expect to find Java files.

## CheckStyle

This project uses CheckStyle to enforce coding standards and detect style violations.

### Running CheckStyle

To check if your code complies with the style rules:

```bash
# Check main source code
./gradlew checkstyleMain

# Check test code
./gradlew checkstyleTest

# Check both main and test code
./gradlew checkstyleMain checkstyleTest
```

### Viewing Reports

CheckStyle generates HTML reports in the `build/reports/checkstyle/` directory:
- `main.html` - Report for main source code
- `test.html` - Report for test code

### Suppressing Rules

#### Global Suppressions

Edit `config/checkstyle/suppressions.xml` to suppress rules for specific files or patterns:

```xml
<suppress checks="MagicNumber" files=".*Test\.java"/>
<suppress checks="UnusedImports" files=".*Test\.java"/>
```

#### Inline Suppressions

To suppress a rule for a specific code segment, use:

```java
//CHECKSTYLE.OFF: RuleName
// Your code here
//CHECKSTYLE.ON: RuleName
```

Example:
```java
//CHECKSTYLE.OFF: MagicNumber
int timeout = 5000; // This magic number is acceptable here
//CHECKSTYLE.ON: MagicNumber
```

### Configuration Files

- `config/checkstyle/checkstyle.xml` - Contains the style rules
- `config/checkstyle/suppressions.xml` - Contains global suppressions

### Common Rules

Some common CheckStyle rules enforced in this project:
- **LineLength**: Lines should not exceed 120 characters
- **MagicNumber**: Avoid using magic numbers (use constants instead)
- **FinalParameters**: Method parameters should be final
- **UnusedImports**: Remove unused import statements
- **WhitespaceAround**: Proper spacing around operators and braces
