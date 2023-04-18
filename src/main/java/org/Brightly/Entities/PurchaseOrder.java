package org.Brightly.Entities;

import lombok.*;
import org.wildfly.common.annotation.NotNull;

import javax.persistence.*;
import java.util.List;
//@Builder
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
//@Getter
//@Setter
public class PurchaseOrder {
    @Id
    @GeneratedValue
    long poID ;
    @NotNull
    String name ;
    String description ;
    String notes ;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Supplier", referencedColumnName = "supplierID")
    Supplier supplier ;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Address" , referencedColumnName = "id")
    Address shippingAddress ;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "PO")
    List<Item> items ;
    public enum POStatus {draft,submitted}
    POStatus status ;

    public long getPoID() {
        return poID;
    }

    public void setPoID(long poID) {
        this.poID = poID;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public POStatus getStatus() {
        return status;
    }

    public void setStatus(POStatus status) {
        this.status = status;
    }
}
