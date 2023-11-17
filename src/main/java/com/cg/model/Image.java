package com.cg.model;


import com.cg.model.dto.ImageResDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table (name = "images")
@Accessors(chain = true)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;

    public Image(Long id, String url) {
        this.id = id;
        this.url = url;
    }

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public ImageResDTO toImageResDTO() {
        return new ImageResDTO()
                .setId(id)
                .setUrl(url)
                ;
    }
}
