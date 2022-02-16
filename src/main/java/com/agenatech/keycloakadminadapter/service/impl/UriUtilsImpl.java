package com.agenatech.keycloakadminadapter.service.impl;

import com.agenatech.solutions.profiles.service.UriUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class UriUtilsImpl implements UriUtils {
    public String getLocationId(ResponseEntity response){
        URI uri = response.getHeaders().getLocation();
        String[] segments = uri.getPath().split("/");
        return  segments[segments.length-1];
    }
}
