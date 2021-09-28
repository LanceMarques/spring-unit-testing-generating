package com.tcc.springunittestinggenerating.utils.enums;

public enum BounderTestType {

    EMPTY(0), UNDERFLOW(-1), MINIMUM_ALLOWED(0), MAXIMUM_ALLOWED(0), OVERFLOW(1);

    private Integer sizeIncrement;

    BounderTestType(int sizeIncrement) {
        this.sizeIncrement = sizeIncrement;
    }

    public Integer getSizeIncrement() {
        return sizeIncrement;
    }

}
