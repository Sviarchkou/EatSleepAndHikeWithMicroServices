package com.example.app.security;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PUBLIC, makeFinal = true)
public class SecurityConstants {

    static long JWT_EXPIRATION = 43200000;
    static String JWT_SECRET = "SomeVERYLargeSecretKeyForComputerSystemAndNetworksCourseWorkIn4SemesterOf2YearWithSomeStupidEnding137123?!%#";
}
