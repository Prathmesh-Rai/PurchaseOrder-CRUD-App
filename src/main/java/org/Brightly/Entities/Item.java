package org.Brightly.Entities;

import lombok.*;

import javax.persistence.*;

//@Builder
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
//@Getter
//@Setter
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int itemID ;
    String name ;
    String description ;
    int quantity ;
    int price ;
    @ManyToOne
    PurchaseOrder PO ;
    public enum itemStatus{available,unavailable} ;
    itemStatus status ;

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public PurchaseOrder getPO() {
        return PO;
    }

    public void setPO(PurchaseOrder PO) {
        this.PO = PO;
    }

    public itemStatus getStatus() {
        return status;
    }

    public void setStatus(itemStatus status) {
        this.status = status;
    }
}
