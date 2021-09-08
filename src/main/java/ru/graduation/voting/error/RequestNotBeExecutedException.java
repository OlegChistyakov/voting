package ru.graduation.voting.error;

public class RequestNotBeExecutedException extends IllegalRequestDataException {
    public RequestNotBeExecutedException(String msg) {
        super(msg);
    }
}
