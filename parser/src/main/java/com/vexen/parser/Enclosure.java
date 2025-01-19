package com.vexen.parser;

import lombok.NonNull;


public interface Enclosure {
    @NonNull
    String getLink();


    Integer getLength();


    String getType();
}
