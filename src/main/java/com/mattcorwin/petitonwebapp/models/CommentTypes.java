package com.mattcorwin.petitonwebapp.models;

public enum CommentTypes {

    TURNIN ("Problem with a turn-in"),
    PAYOUT ("Problem with a pay-out"),
    OTHER ("Other issues or requests");

    private final String name;

    CommentTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
