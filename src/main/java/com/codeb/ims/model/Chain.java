package com.codeb.ims.model;

import jakarta.persistence.*;

@Entity
@Table(name = "customer_chains", uniqueConstraints = {
    @UniqueConstraint(columnNames = "chainName")
})
public class Chain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String chainName;

    @Column(nullable = false)
    private boolean isActive = true;

    // Constructors
    public Chain() {}

    public Chain(String chainName) {
        this.chainName = chainName;
        this.isActive = true;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChainName() {
        return chainName;
    }

    public void setChainName(String chainName) {
        this.chainName = chainName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
