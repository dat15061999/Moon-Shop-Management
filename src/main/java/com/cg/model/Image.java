package com.cg.model;


import com.cg.model.dto.ImageResDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;


@Getter
@NoArgsConstructor
@Setter
@AllArgsConstructor
@Builder
@Entity
@Table(name = "images")
@Accessors(chain = true)
public class Image {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_folder")
    private String fileFolder;

    @Column(name = "file_url")
    private String url;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "cloud_id")
    private String cloudId;

    @ManyToOne
    @JsonIgnore
    private Product product;

    public ImageResDTO toImageResDTO() {
        return new ImageResDTO()
                .setUrl(url)
                ;
    }
}
