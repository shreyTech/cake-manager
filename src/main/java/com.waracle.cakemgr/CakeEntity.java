package com.waracle.cakemgr;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Cake", uniqueConstraints = {@UniqueConstraint(columnNames = "ID"), @UniqueConstraint(columnNames = "title")})
public class CakeEntity implements Serializable {

    private static final long serialVersionUID = -1798070786993154676L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @Column(name = "TITLE", unique = true, nullable = false, length = 100)
    private String title;

    @Column(name = "DESCRIPTION", nullable = false, length = 100)
    private String desc;

    @Column(name = "IMAGE", nullable = false, length = 300)
    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}