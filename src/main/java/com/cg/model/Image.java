package com.cg.model;


import com.cg.model.dto.ImageResDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "images")
@Accessors(chain = true)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String url;

    @ManyToOne
    @JsonIgnore
    private Product product;

    public ImageResDTO toImageResDTO() {
        return new ImageResDTO()
                .setId(id)
                .setUrl(url)
                ;
    }
}
