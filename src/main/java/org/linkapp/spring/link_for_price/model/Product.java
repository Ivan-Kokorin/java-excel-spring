package org.linkapp.spring.link_for_price.model;

import javax.persistence.*;

@Entity
@Table(name = "f_products")
public class Product {
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "url")
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }
}
