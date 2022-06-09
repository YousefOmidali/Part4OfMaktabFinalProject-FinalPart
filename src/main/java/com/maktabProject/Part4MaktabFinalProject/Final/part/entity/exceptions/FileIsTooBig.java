package com.maktabProject.Part4MaktabFinalProject.Final.part.entity.exceptions;

public class FileIsTooBig extends RuntimeException {
    public FileIsTooBig() {
    }

    public FileIsTooBig(String message) {
        super(message);
    }
}
