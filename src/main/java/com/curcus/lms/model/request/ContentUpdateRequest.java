package com.curcus.lms.model.request;
import lombok.Data;
import com.curcus.lms.constants.ContentType;
@Data
public class ContentUpdateRequest {
    private ContentType type;
    private String url;
}
