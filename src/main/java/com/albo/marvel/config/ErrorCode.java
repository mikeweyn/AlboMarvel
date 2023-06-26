package com.albo.marvel.config;

public enum ErrorCode {

    INTERNAL_ERROR(100, "Error interno del servidor"),
    BAD_REQUEST(105, "La request esta mal formateada");

    private final int value;
    private final String reasonPhrase;

    ErrorCode(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public int value() {
        return this.value;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }
}
