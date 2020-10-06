package com.example.widjet.main.tdo;

import com.example.widjet.main.entity.PrazdnikEntity;

import java.util.Arrays;

public class PrazdnikDTO {
    private long id;
    private String name;
    private String description;
    private String img;
    private boolean post;
    private String[] date;

    public PrazdnikDTO() {
    }

    public PrazdnikDTO(PrazdnikEntity prazdnikEntity) {
        this.name = prazdnikEntity.getName();
        this.description = prazdnikEntity.getDescription();
        this.img = prazdnikEntity.getImg();
        this.id = prazdnikEntity.getId();
    }

    public PrazdnikDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isPost() {
        return post;
    }

    public void setPost(boolean post) {
        this.post = post;
    }

    public String[] getDate() {
        return date;
    }

    public void setDate(String[] date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "PrazdnikDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", img='" + img + '\'' +
                ", post=" + post +
                ", date=" + Arrays.toString(date) +
                '}';
    }
}
