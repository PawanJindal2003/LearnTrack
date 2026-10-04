# Design Notes

## Why `ArrayList` instead of `array`?

Arrays have a **fixed size**. LearnTrack must support an unknown number of students, courses, and enrollments at runtime. `ArrayList` grows automatically when elements are added and provides convenient methods such as `add` and `remove`. Repositories (`StudentRepository`, `CourseRepository`, `EnrollmentRepository`) each wrap a private `ArrayList` and expose simple find/save operations.

## Where static members are used and why

`IdGenerator` holds **private static counters** (`studentIdCounter`, `courseIdCounter`, etc.) and exposes methods like `getNextStudentId()`. IDs must be unique across the whole application for the lifetime of the process, so a single shared counter per entity type is appropriate. The class has a private constructor so it is used only as a utility, not instantiated.

## Inheritance and what we gain

`Person` holds common fields: `id`, name, email, and `active`. `Student` and `Trainer` extend `Person` and call `super(...)` in their constructors. Subclasses add their own fields (`batch`, `subject`, …) and **override** `getDisplayName()` for specialized console output.

**Polymorphism** is demonstrated in the UI: a `Student` is assigned to a `Person` reference and `getDisplayName()` is called—the JVM invokes the **Student** override at runtime.

## Layering: entity, repository, service, UI

- **Entity**: data and behavior tied to one domain object.
- **Repository**: in-memory storage and lookup (could be swapped later for a database).
- **Service**: validation and business rules (e.g. no duplicate active enrollment).
- **UI** (`ConsoleApp`): menus, reading input, printing messages—no business rules.

This keeps methods small and names clear (`addStudent`, `findById`, `enrollStudent`).

Simplified class and layer diagrams are in the [README](../README.md#class-diagram).

## Enrollment: IDs and object references

`Enrollment` stores both `studentId` / `courseId` (for reporting and rubric alignment) and references to `Student` / `Course` (for convenient display and integrity when listing enrollments).

## Deactivate vs remove

The menu uses **deactivate** (soft delete: `active = false`) so historical data and enrollments remain consistent. `StudentService.removeStudent()` permanently removes a student from the list for API completeness and testing; the console flow prefers deactivation.

## Active vs inactive students (view vs operate)

| Operation | Active students | Deactivated students |
|-----------|-----------------|----------------------|
| View all students | Listed | Not listed |
| Search by ID | Shown | Shown (`Active: false` in output) |
| Update | Allowed | Rejected (`InvalidInputException`) |
| New enrollment | Allowed | Rejected |
| Deactivate | Allowed | N/A (already inactive) |
| Activate | “Already active” | Sets `active = true` |
| View display name (polymorphism) | Allowed | Rejected |

**Rationale:** day-to-day lists show only the **active roster**, but **lookup by ID** supports auditing and the activate workflow. Any operation that changes data or enrolls a student still requires an active record.

Courses follow the same idea for listing (active only) versus activate/deactivate by ID (inactive courses can still be found in the repository by ID).

## Exception handling

- **`EntityNotFoundException`**: no student, course, or enrollment with the given ID.
- **`InvalidInputException`**: blank input, invalid numbers, deactivated student used for a restricted operation, duplicate active enrollment, etc.

The UI catches these types and prints a short message instead of terminating the program.
