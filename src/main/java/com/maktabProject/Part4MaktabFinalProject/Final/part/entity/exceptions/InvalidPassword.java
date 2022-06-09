package com.maktabProject.Part4MaktabFinalProject.Final.part.entity.exceptions;

    public class InvalidPassword extends RuntimeException{
        public InvalidPassword() {
        }

        public InvalidPassword(String message) {
            super(message);
        }
}
