package com.cg.model.dto;

import com.cg.model.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


@NoArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
public class ImageResDTO {
    private String id;
    private String url;

    public ImageResDTO(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public Image toImage() {
        return new Image()
                .setId(id)
                .setUrl(url);
    }
}
