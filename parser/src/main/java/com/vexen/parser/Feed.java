package com.vexen.parser;

import java.util.Date;
import java.util.List;

import lombok.NonNull;

public interface Feed {


    String getLink();


    Date getPublicationDate();


    Date getUpdatedDate();

    @NonNull
    String getTitle();


    String getDescription();


    String getCopyright();


    String getImageLink();


    String getAuthor();

    @NonNull
    List<? extends Item> getItems();
}
