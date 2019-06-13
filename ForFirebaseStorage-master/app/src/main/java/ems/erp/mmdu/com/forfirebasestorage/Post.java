package ems.erp.mmdu.com.forfirebasestorage;
import java.io.Serializable;
import java.util.*;
public class Post  implements Serializable {
    private String addedBy;
    private String title;
    private Double price;
    private String categories;
    private String subCategories;
    private Map<String,Double> bids;
    private String dateAdded;
    private boolean status;
    private String imageLink;
    private String soldTo;
    private String description;
    private String pid;

    public Post(){}

    public String getPid() {
        return pid;
    }

    public Post(String addedBy, String title , Double price, String categories, String subCategories, String dateAdded, boolean status, String description, String imageLink) {
        this.addedBy = addedBy;
        this.title = title;
        this.price = price;
        this.categories = categories;
        this.subCategories = subCategories;
        this.dateAdded = dateAdded;
        this.status = status;
        this.imageLink = "";
        this.soldTo = "";
        this.description = description;
        this.imageLink = imageLink;
        this.bids = new HashMap<String, Double>();
        this.pid =  new Long(new Date().getTime()).toString();
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(String subCategories) {
        this.subCategories = subCategories;
    }

    public Map<String, Double> getBids() {
        return bids;
    }

    public void setBids(Map<String, Double> bids) {
        this.bids = bids;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getSoldTo() {
        return soldTo;
    }

    public void setSoldTo(String soldTo) {
        this.soldTo = soldTo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}
