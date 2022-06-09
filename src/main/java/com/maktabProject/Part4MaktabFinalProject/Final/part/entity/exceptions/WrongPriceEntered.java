package com.maktabProject.Part4MaktabFinalProject.Final.part.entity.exceptions;

public class WrongPriceEntered extends RuntimeException{
    public WrongPriceEntered() {
    }

    public WrongPriceEntered(String message) {
        super(message);
    }
}
