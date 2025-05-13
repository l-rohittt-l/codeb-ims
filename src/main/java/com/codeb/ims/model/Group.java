package com.codeb.ims.model;

import jakarta.persistence.*;

@Entity
@Table(name = "groups", uniqueConstraints = {
    @UniqueConstraint(columnNames = "groupName")
})
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String groupName;

    @Column(nullable = false)
    private boolean isActive = true;

    // Constructors
    public Group() {}

    public Group(String groupName) {
        this.groupName = groupName;
        this.isActive = true;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
