package com.jobportal.utils;

import java.util.*;

public class SkillMatcher {

    // Convert skills string to normalized set
    private static Set<String> convertToSet(String skills) {

        if (skills == null || skills.isEmpty()) {
            return Collections.emptySet();
        }

        return Arrays.stream(skills.split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .filter(s -> !s.isEmpty())
                .collect(HashSet::new, HashSet::add, HashSet::addAll);
    }

    // Calculate match percentage
    public static int calculateMatch(String jobSkills, String userSkills) {

        Set<String> jobSet = convertToSet(jobSkills);
        Set<String> userSet = convertToSet(userSkills);

        if (jobSet.isEmpty()) {
            return 0;
        }

        int matchCount = 0;

        for (String skill : jobSet) {
            if (userSet.contains(skill)) {
                matchCount++;
            }
        }

        return (matchCount * 100) / jobSet.size();
    }

    // Find missing skills
    public static List<String> findMissingSkills(String jobSkills, String userSkills) {

        Set<String> jobSet = convertToSet(jobSkills);
        Set<String> userSet = convertToSet(userSkills);

        jobSet.removeAll(userSet);

        return new ArrayList<>(jobSet);
    }
}