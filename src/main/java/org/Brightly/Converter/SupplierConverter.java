package org.Brightly.Converter;

import org.Brightly.Entities.Supplier;

public class SupplierConverter {
    public static Supplier generateSupplier(org.Brightly.Supplier request_supplier){
        Supplier pojo_supplier = new Supplier() ;
        pojo_supplier.setPhone(request_supplier.getPhoneNo());
        pojo_supplier.setEmail(request_supplier.getEmail());
        pojo_supplier.setFax(request_supplier.getFax());
        pojo_supplier.setName(request_supplier.getName());
        pojo_supplier.setSupplierAddress(AddressConverter.generateAddress(request_supplier.getSupplierAdd()));
        return pojo_supplier ;
    }
}
