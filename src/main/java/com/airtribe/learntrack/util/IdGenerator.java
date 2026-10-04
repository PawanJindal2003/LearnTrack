package com.airtribe.learntrack.util;

/**
 * Generates sequential IDs using static counters (one per entity type).
 */
public final class IdGenerator {

    private static int studentIdCounter = 1;
    private static int courseIdCounter = 1;
    private static int enrollmentIdCounter = 1;
    private static int trainerIdCounter = 1;

    private IdGenerator() {
    }

    public static String getNextStudentId() {
        return "STU-" + studentIdCounter++;
    }

    public static String getNextCourseId() {
        return "CRS-" + courseIdCounter++;
    }

    public static String getNextEnrollmentId() {
        return "ENR-" + enrollmentIdCounter++;
    }

    public static String getNextTrainerId() {
        return "TRN-" + trainerIdCounter++;
    }
}
