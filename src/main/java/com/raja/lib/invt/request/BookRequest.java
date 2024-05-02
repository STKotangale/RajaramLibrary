package com.raja.lib.invt.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRequest {

    private String bookName;

    private String isBlock;

    private Long authorId;

    private Long publicationId;
}
