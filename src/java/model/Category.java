package model;

import java.sql.Timestamp;

public class Category {
    private int categoryId;
    private String categoryName;
    private Timestamp createdAt;

    public Category() {
    }

   
    public Category(int categoryId,String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    // Getter & Setter
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}

