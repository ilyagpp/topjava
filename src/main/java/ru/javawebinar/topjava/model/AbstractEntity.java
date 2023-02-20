package ru.javawebinar.topjava.model;

public class AbstractEntity {


    private Long id;

    public AbstractEntity(Long id) {
        this.id = id;
    }

    public AbstractEntity(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isNew(Long id) {
        return id == null;
    }
    
}
