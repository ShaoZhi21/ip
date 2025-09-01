# CheckStyle Examples and Best Practices

This document provides examples of how to use CheckStyle effectively in the Jimmy project.

## Inline Suppressions

### Suppressing Magic Numbers

```java
//CHECKSTYLE.OFF: MagicNumber
int timeout = 5000; // This magic number is acceptable for timeout
//CHECKSTYLE.ON: MagicNumber
```

### Suppressing Line Length

```java
//CHECKSTYLE.OFF: LineLength
String veryLongString = "This is a very long string that exceeds the 120 character limit but is necessary for this specific use case";
//CHECKSTYLE.ON: LineLength
```

### Suppressing Multiple Rules

```java
//CHECKSTYLE.OFF: MagicNumber, LineLength
int magicNumber = 42;
String longString = "This is a very long string with a magic number that we want to suppress";
//CHECKSTYLE.ON: MagicNumber, LineLength
```

## Common Violations and Fixes

### 1. Magic Number Violation

**Violation:**
```java
if (status == 3) { // Magic number
    // do something
}
```

**Fix:**
```java
private static final int STATUS_COMPLETED = 3;
if (status == STATUS_COMPLETED) {
    // do something
}
```

### 2. Line Length Violation

**Violation:**
```java
String veryLongVariableName = "This is a very long string that exceeds the 120 character limit and causes a CheckStyle violation";
```

**Fix:**
```java
String veryLongVariableName = "This is a very long string that exceeds the 120 character limit " +
    "and causes a CheckStyle violation";
```

### 3. Unused Imports

**Violation:**
```java
import java.util.ArrayList; // Unused import
import java.util.List;
import java.util.Map;

public class Example {
    private List<String> items;
    private Map<String, String> data;
}
```

**Fix:**
```java
import java.util.List;
import java.util.Map;

public class Example {
    private List<String> items;
    private Map<String, String> data;
}
```

### 4. Whitespace Around Operators

**Violation:**
```java
int result=a+b*c; // Missing spaces around operators
```

**Fix:**
```java
int result = a + b * c; // Proper spacing
```

### 5. Final Parameters

**Violation:**
```java
public void processData(String input) { // Parameter not final
    // method body
}
```

**Fix:**
```java
public void processData(final String input) { // Parameter is final
    // method body
}
```

## Best Practices

1. **Use constants instead of magic numbers**
2. **Keep lines under 120 characters**
3. **Remove unused imports**
4. **Use proper spacing around operators**
5. **Consider making parameters final**
6. **Use suppressions sparingly and document why they're needed**

## Running CheckStyle

```bash
# Check main source code
./gradlew checkstyleMain

# Check test code
./gradlew checkstyleTest

# Check both
./gradlew checkstyleMain checkstyleTest
```

## Viewing Reports

CheckStyle generates HTML reports in `build/reports/checkstyle/`:
- Open `main.html` to view main source code violations
- Open `test.html` to view test code violations

The reports provide detailed information about each violation, including:
- File and line number
- Rule violated
- Description of the violation
- Suggestions for fixing
