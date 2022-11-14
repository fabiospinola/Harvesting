package com.appdetex.harvest.httpclient.entity;


import com.appdetex.harvest.httpclient.request.CreateDetectionRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="detection")
@NoArgsConstructor

public class Detection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "url")
    private String url;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "item_order")
    private String order;
    @Column(name = "sponsored")
    private String sponsored;
    @Column(name = "price")
    private String price;

    public Detection(CreateDetectionRequest createDetectionRequest) {
        this.title = createDetectionRequest.getTitle();
        this.description = createDetectionRequest.getDescription();
        this.url = createDetectionRequest.getUrl();
        this.imageUrl = createDetectionRequest.getImageUrl();
        this.order = createDetectionRequest.getOrder();
        this.sponsored = createDetectionRequest.getSponsored();
        this.price = createDetectionRequest.getPrice();

    }
}
