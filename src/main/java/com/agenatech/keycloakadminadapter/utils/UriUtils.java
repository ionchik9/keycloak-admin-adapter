package com.agenatech.keycloakadminadapter.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class UriUtils {
    public static String getLocationId(ResponseEntity response){
        URI uri = response.getHeaders().getLocation();
        String[] segments = uri.getPath().split("/");
        return  segments[segments.length-1];
    }
}
