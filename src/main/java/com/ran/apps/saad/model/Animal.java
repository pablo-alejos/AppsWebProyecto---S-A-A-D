package com.ran.apps.saad.model;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "animal")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "race", nullable = false)
    private String race;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "fur", nullable = false)
    private String fur;

    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "vaccinated", nullable = false)
    private boolean vaccinated;

    @Column(name = "adopted", nullable = false)
    private boolean adopted;

    @Column(name = "responsable", nullable = true)
    private String responsable;

    @Column(name = "img", nullable = true)
    private byte[] img;

    @Column(name = "str", nullable = true)
    private String str;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFur() {
        return fur;
    }

    public void setFur(String fur) {
        this.fur = fur;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isVaccinated() {
        return vaccinated;
    }

    public void setVaccinated(boolean vaccinated) {
        this.vaccinated = vaccinated;
    }

    public boolean isAdopted() {
        return adopted;
    }

    public void setAdopted(boolean adopted) {
        this.adopted = adopted;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }


    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return "Animal [adopted=" + adopted + ", color=" + color + ", date=" + date + ", fur=" + fur + ", id=" + id
                + ", img=" + Arrays.toString(img) + ", race=" + race + ", responsable=" + responsable + ", str=" + str
                + ", type=" + type + ", vaccinated=" + vaccinated + "]";
    }


}