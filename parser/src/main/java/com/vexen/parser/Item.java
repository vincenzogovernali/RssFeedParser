package com.vexen.parser;

import java.util.Date;
import java.util.List;

import lombok.NonNull;

public interface Item {


    String getLink();


    Date getPublicationDate();


    String getTitle();


    String getDescription();


    String getImageLink();


    String getAuthor();


    String getId();

    @NonNull
    List<? extends Enclosure> getEnclosures();
}
