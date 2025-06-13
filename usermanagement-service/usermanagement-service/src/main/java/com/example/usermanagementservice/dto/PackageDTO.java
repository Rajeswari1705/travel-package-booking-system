package com.example.usermanagementservice.dto;
 
// This class represents the structure of package returned by travel service
public class PackageDTO {
    private Long id;
    private String name;
    private String description;
 
    // Getters and setters
    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
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
 
    public void setDescription(String description) {
        this.description = description;
    }
}