package com.enigma.challengespringrestful.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.regex.PatternSyntaxException;

@Component
@RequiredArgsConstructor
public class ValidationUtils {
    public static boolean isNotEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isPositive(BigDecimal value) {
        return value != null && value.compareTo(BigDecimal.ZERO) > 0;
    }

    public static boolean isPositiveOrZero(BigDecimal value) {
        return value != null && value.compareTo(BigDecimal.ZERO) >= 0;
    }

    public static boolean isInRange(BigDecimal value, BigDecimal min, BigDecimal max) {
        return value != null && value.compareTo(min) >= 0 && value.compareTo(max) <= 0;
    }

    public static boolean isFutureOrPresent(LocalDateTime dateTime) {
        return dateTime != null && !dateTime.isBefore(LocalDateTime.now());
    }

    public static boolean isFuture(LocalDateTime dateTime) {
        return dateTime != null && dateTime.isAfter(LocalDateTime.now());
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) return false;
        String regex = "^(\\+62|62|0)([2-9][0-9]{7,8})$"; // Adjust regex as needed
        try {
            return phoneNumber.matches(regex);
        } catch (PatternSyntaxException e) {
            return false;
        }
    }

    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        try {
            return email.matches(emailRegex);
        } catch (PatternSyntaxException e) {
            return false;
        }
    }

    public static boolean matchesPattern(String value, String pattern) {
        if (value == null || pattern == null) return false;
        try {
            return value.matches(pattern);
        } catch (PatternSyntaxException e) {
            return false;
        }
    }
}