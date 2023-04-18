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
    long itemID ;
    String name ;
    String description ;
    long quantity ;
    long price ;
    @ManyToOne
    PurchaseOrder PO ;
    public enum itemStatus{available,unavailable} ;
    itemStatus status ;

    public long getItemID() {
        return itemID;
    }

    public void setItemI(long itemID) {
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

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
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
