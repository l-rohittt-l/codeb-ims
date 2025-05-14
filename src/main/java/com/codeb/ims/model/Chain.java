package com.codeb.ims.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "customer_chains", uniqueConstraints = {
    @UniqueConstraint(columnNames = "chain_name")
})
public class Chain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chain_id")
    private Long chainId;

    @Column(name = "chain_name", nullable = false, unique = true)
    private String chainName;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @JsonIgnoreProperties({"chains", "createdAt", "updatedAt"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    public Chain() {}

    public Chain(String chainName, Group group) {
        this.chainName = chainName;
        this.group = group;
        this.isActive = true;
    }

    // Getters and Setters

    public Long getChainId() {
        return chainId;
    }

    public void setChainId(Long chainId) {
        this.chainId = chainId;
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
