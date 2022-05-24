package com.agenatech.keycloakadminadapter.utils;

import java.net.URI;

public class UriUtils {

    private UriUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String getLocationId(URI locationUri){
            String[] segments = locationUri.getPath().split("/");
            return segments[segments.length-1];
    }
}
