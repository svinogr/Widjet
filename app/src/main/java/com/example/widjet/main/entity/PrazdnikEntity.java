package com.example.widjet.main.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PrazdnikEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String description;
    private String img;
    private boolean post;

        public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public void setDescription(String desription) {
        this.description = desription;
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

    @Override
    public String toString() {
        return "PrazdnikEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", img='" + img + '\'' +
                ", post=" + post +
                "};";
    }
}
