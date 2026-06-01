# Task Management System

## Project Overview

**Complexity:** Medium

**Time Estimate:** 6–8 hours

**Technology Stack:** Java 21 (LTS), IntelliJ IDEA Community Edition, JUnit 5

In this lab, you will extend the Project/Task Management System from Week 2 by introducing advanced Java concepts, i.e., **Collections, Functional Programming, File I/O (NIO), Regex, and Concurrency**, to make the system more dynamic, expressive, persistent, and concurrent.

You will refactor Week 2's array-based logic into Collections (`ArrayList`, `HashMap`) for flexibility, integrate **Functional Programming constructs (Lambdas, Streams, Functional Interfaces, Method References)** to process and transform collection data more efficiently, add File I/O to persist projects and tasks, use Regex for input validation, and implement basic concurrency to simulate simultaneous task updates.

All data is stored in-memory during runtime but also saved and loaded from simple `.json`-like text files. Exception handling, testing, and Git versioning from Week 2 continue here.

This lab prepares you for Week 4, where you'll transition from file storage to databases and explore advanced persistence patterns.

---

## Learning Objectives

By completing this lab, you will be able to:

- Refactor array-based logic to use Java Collections Framework (`List`, `Map`, `Set`)
- Implement functional programming with **Lambdas, Functional Interfaces**, and **Method References**
- Use the **Streams API** to perform filtering, mapping, and reduction operations on collections
- Implement file persistence with `java.nio.file.Files` and `Paths`
- Validate user inputs using regular expressions (`Pattern`, `Matcher`)
- Apply basic concurrency with `Thread`, `Runnable`, and `synchronized`
- Handle checked exceptions using `try-with-resources` for clean resource management
- Write and extend JUnit 5 tests for persistence, functional, and concurrent operations
- Maintain code quality through consistent naming, SOLID principles, and Git branching

---

## System Features Overview

Your enhanced system now supports all Week 2 features plus five new technical capabilities.

### Feature 1: Project Catalog with Collections and Streams

- Use `HashMap<String, Project>` to store and access projects.
- Dynamically add/remove projects without array size limits.
- Use **Streams API** to filter, sort, and aggregate project information (e.g., find completed projects).
- Use **Lambdas** for concise iteration and data transformation.

### Feature 2: Task Management with Regex and Functional Validation

- Store tasks in `ArrayList<Task>` per project.
- Enforce task ID pattern `T\d{3}` (e.g., `T001`).
- Validate all inputs using Regex before processing.
- Implement **Predicate** or custom functional interfaces for filtering and validating task data.

### Feature 3: File Persistence (I/O with NIO)

- Save all projects and tasks to `projects_data.json` on exit.
- Auto-load the file on startup using `Files.readAllLines()`.
- Handle missing files and malformed data gracefully.
- Use **Functional style reading/writing** with `Files.lines()` and stream processing.

### Feature 4: Concurrent and Functional Task Updates

- Simulate multi-user task updates with multiple threads.
- Use `synchronized` methods to ensure thread safety.
- Employ **parallel streams** or **executor services** for parallel updates.
- Display progress as updates occur concurrently.

### Feature 5: Enhanced Testing & Git Integration

- Add JUnit tests for collection behaviors, Streams, file I/O, and threading.
- Create Git branches for new features like `feature/file-io`.
- Commit with descriptive messages after major milestones.

---

## Console UI Examples

### 1. Application Startup & File Load

```
╔════════════════════════════════════════╗
║    JAVA PROJECT MANAGEMENT SYSTEM v3.0 ║
╚════════════════════════════════════════╝
Loading projects from file...
✓ 5 projects loaded successfully from projects_data.json
```

### 2. Project Catalog (using HashMap and Streams)

```
─────────────────────────────────────────────────────────
 ID   │ PROJECT NAME    │ TYPE      │ TASKS │ COMPLETED (%)
─────────────────────────────────────────────────────────
 P001 │ Alpha Tracker   │ Software  │ 5     │ 80%
 P002 │ IoT Sensor Kit  │ Hardware  │ 3     │ 50%
─────────────────────────────────────────────────────────
```

*Filter completed projects using Streams:*

```java
projects.values().stream()
    .filter(p -> p.getCompletionRate() > 70)
    .forEach(System.out::println);
```

### 3. Adding Task with Regex and Functional Validation

```
Enter new task ID: TX01
✗ Error: Invalid Task ID format. Use pattern T### (e.g., T001)
Enter new task ID: T004
Enter task name: Write Unit Tests
✓ Task "Write Unit Tests" added successfully.
```

### 4. Saving Data to File (.json-like format)

```
Saving project data...
Data written to projects_data.json successfully!
```

### Example: projects_data.json

```json
[
  {
    "projectId": "P001",
    "name": "Alpha Tracker",
    "type": "Software",
    "tasks": [
      {"id": "T001", "name": "Design DB", "status": "Completed"},
      {"id": "T002", "name": "Implement API", "status": "In Progress"}
    ]
  },
  {
    "projectId": "P002",
    "name": "IoT Sensor Kit",
    "type": "Hardware",
    "tasks": [
      {"id": "T003", "name": "Build Prototype", "status": "Completed"}
    ]
  }
]
```

### 5. Concurrent Task Updates Example

```
╔════════════════════════════════════════╗
║    PARALLEL TASK UPDATE SIMULATION     ║
╚════════════════════════════════════════╝
Starting 3 threads...
Thread-1 updating T001 -> In Progress
Thread-2 updating T002 -> Completed
Thread-3 updating T003 -> Completed
All threads finished successfully.
✓ Task updates applied concurrently and safely.
```

### 6. JUnit Test Example

```java
@Test
void testFilterCompletedTasksUsingStreams() {
    List<Task> tasks = List.of(
        new Task("T001", "Design UI", "Completed"),
        new Task("T002", "Write Tests", "Pending")
    );
    long completed = tasks.stream().filter(t -> t.getStatus().equals("Completed")).count();
    assertEquals(1, completed);
}
```

### 7. File Error Handling

```
✗ Error: Unable to load projects_data.json (File not found)
→ Starting with empty catalog.
```

---

## Expected User Workflows

**Workflow 1: Startup and File Load**

System reads `projects_data.json`. Displays number of projects loaded. Proceeds to Main Menu.

**Workflow 2: Add Task with Regex and Functional Validation**

User inputs task ID. Regex ensures correct format (T###). Functional validation applied to confirm uniqueness. On success, the task is added.

**Workflow 3: Save and Exit**

User selects "Exit". System serializes all data to file. Confirms successful write.

**Workflow 4: Concurrent and Functional Task Update**

User triggers "Simulate Concurrent Updates". Threads or parallel streams start updating tasks. Progress shown until all threads complete safely.

---

## User Stories

### Epic 1: Project Management with Collections and Functional Programming

- **US-1.1:** Refactor Array Storage to Collections
  - Replace arrays with `ArrayList` and `HashMap`; use generics for type safety.
- **US-1.2:** Stream-Based Search and Filtering
  - As a user, I want to quickly filter completed projects using Streams for efficient data processing.
  - Technical Requirements: Implement `filter()`, `map()`, and `collect()` operations with lambdas.

### Epic 2: Regex Validation for Inputs

- **US-2.1:** Validate Task IDs using regex `T\d{3}`; invalid formats throw `InvalidInputException`.
- **US-2.2:** Validate Project IDs using regex `P\d{3}`; inform user if incorrect.

### Epic 3: File Persistence

- **US-3.1:** Save and Load Projects using `Files.writeString()` and `Files.readString()`; handle exceptions.
- **US-3.2:** Auto-load on Startup and Save on Exit.

### Epic 4: Concurrency & Thread Safety

- **US-4.1:** Implement parallel task updates with Runnable threads or parallel streams.
- **US-4.2:** Track completion and ensure thread safety with synchronized methods.

### Epic 5: Testing & Git Integration

- **US-5.1:** Add tests for Streams, file I/O, and threading.
- **US-5.2:** Use Git feature branches and merge after validation.

---

## Project Structure

```
project-management-system/
│
├── src/
│   ├── Main.java
│   ├── models/
│   │   ├── Project.java
│   │   ├── SoftwareProject.java
│   │   ├── HardwareProject.java
│   │   ├── Task.java
│   │   ├── User.java
│   │   ├── RegularMember.java
│   │   ├── AdminMember.java
│   │   └── StatusReport.java
│   │
│   ├── interfaces/
│   │   ├── Completable.java
│   │   └── TaskFilter.java              # Functional interface for task filtering
│   │
│   ├── services/
│   │   ├── ProjectService.java
│   │   ├── TaskService.java
│   │   ├── ReportService.java
│   │   ├── ConcurrencyService.java
│   │   └── StreamService.java           # New for Stream and Lambda utilities
│   │
│   └── utils/
│       ├── ConsoleMenu.java
│       ├── ValidationUtils.java
│       ├── FileUtils.java
│       ├── RegexValidator.java
│       └── FunctionalUtils.java         # Functional helper methods
│
├── tests/
│   ├── ProjectTest.java
│   ├── TaskTest.java
│   ├── StreamOperationsTest.java        # New test class for Streams
│   └── FilePersistenceTest.java
│
├── data/
│   └── projects_data.json
│
└── README.md
```

---

## Implementation Phases

**Phase 1: Collections & Functional Refactor (2 hrs.)**

Refactor arrays to `ArrayList` and `HashMap`. Introduce lambdas and Streams for filtering and mapping. Ensure all existing tests pass.

**Phase 2: Add Regex Validation (1 hr.)**

Create `RegexValidator.java`. Validate IDs using `Pattern` and `Matcher`.

**Phase 3: Implement File Persistence (2 hrs.)**

Add `FileUtils` with `saveProjects()` and `loadProjects()` using NIO and streams.

**Phase 4: Concurrency Integration (2–3 hrs.)**

Implement concurrent updates with threads or parallel streams. Extend JUnit tests.

---

## Minimum Requirements Checklist

- [ ] JDK 21 and IntelliJ installed
- [ ] Arrays refactored to Collections (`ArrayList`, `HashMap`)
- [ ] Functional Programming (Lambdas, Streams, Functional Interfaces) implemented
- [ ] Regex validation implemented for IDs
- [ ] File persistence working (read & write)
- [ ] `try-with-resources` used for I/O
- [ ] Concurrent updates implemented with Threads or parallel streams
- [ ] JUnit tests cover stream and concurrency features
- [ ] Proper exception handling in all methods
- [ ] Git commits and feature branches used
- [ ] Clean, readable, SOLID code
- [ ] README updated

---

## Grading Rubric

| # | Criteria | Points | Excellent (90–100%) | Good (75–89%) | Satisfactory (60–74%) |
|---|----------|--------|---------------------|---------------|----------------------|
| 1 | Collections & Functional Refactor | 18 | Arrays replaced with Collections; Streams, Lambdas, and Functional Interfaces used effectively | Partial usage or inconsistent functional code | Minimal FP integration or misuse |
| 2 | File Persistence | 18 | Files saved/loaded via NIO; stream-based reads/writes; error handling clean | Works but lacks optimization | Inconsistent I/O or missing validation |
| 3 | Regex Validation | 12 | Robust ID validation with clear error messages | Works but partially applied | Weak or missing validation |
| 4 | Concurrency Implementation | 17 | Threads and parallel streams implemented; no race conditions | Threads run but minor sync issues | Limited concurrency demo |
| 5 | Testing & Git | 13 | Comprehensive JUnit and structured Git commits | Basic tests; some gaps | Few tests or unclear history |
| 6 | Code Quality & Docs | 10 | Modular, readable, fully documented | Minor clarity issues | Minimal documentation |
| 7 | Data Structures & Algorithms (DSA) | 10 | Efficient collection algorithms with functional logic | Logical structure, minor inefficiencies | Redundant logic |
| 8 | **Total** | **100** | | | |

---

## Submission Requirements

**Deliverables:**

- Public GitHub repository with Week 3 code (`src`, `tests`, `data`)
- `README.md` with setup steps and sample console output
- Screenshot of concurrent or stream-based operations
- Updated UML/Class diagram showing Streams integration
- JUnit test results screenshot
- Submission form link
- Branch naming: `feature/collections-fp`, `feature/file-io`, `feature/concurrency`

---

## Testing the Application

**Test Scenario 1:** Load Projects from File — Verify automatic loading from saved `.json` file.

**Test Scenario 2:** Save Projects to File — Confirm new data persists correctly after restart.

**Test Scenario 3:** Add Project Using Collections — Ensure dynamic list management and retrieval.

**Test Scenario 4:** Validate IDs and Inputs with Regex — Confirm proper format enforcement and error display.

**Test Scenario 5:** Concurrency – Parallel Task Updates — Run multi-threaded update simulation; verify data safety.

**Test Scenario 6:** Exception Handling for File Operations — Handle missing or malformed files gracefully.

**Test Scenario 7:** Performance and Memory — Add multiple projects and tasks; verify stability.

**Test Scenario 8:** Functional Programming – Stream Operations
- Use `filter()` to list completed tasks.
- Use `map()` to extract task names.
- Use `reduce()` to compute total completion.

Confirm all results accurate.

**Test Scenario 9:** Git Workflow and Versioning — Ensure commits and merges reflect all FP and concurrency changes.
