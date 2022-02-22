package com.agenatech.keycloakadminadapter.utils;

import reactor.core.publisher.Mono;

import java.net.URI;

public class UriUtils {
    public static Mono<String> getLocationId(Mono<URI> uriMono){
        return uriMono.map(uri -> {
            String[] segments = uri.getPath().split("/");
            return segments[segments.length-1];
        } );
    }
}
