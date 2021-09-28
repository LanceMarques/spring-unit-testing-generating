package com.tcc.springunittestinggenerating.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "entity")
public class EntityModel {

    @Id
    private Integer id;

    @Size(min = 5, max = 50)
    @Column(name = "name")
    private String name;

    public EntityModel() {
    }

    public EntityModel(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

