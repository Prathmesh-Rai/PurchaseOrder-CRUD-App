package org.Brightly.Converter_POJO_to_Proto;

import org.Brightly.Supplier;

public class SupplierConverter {
    public static Supplier generateSupplier(org.Brightly.Entities.Supplier pojo_supplier){
        Supplier proto_supplier = Supplier.newBuilder()
                .setSupplierID(pojo_supplier.getSupplierID())
                .setSupplierAdd(AddressConverter.generateAddress(pojo_supplier.getSupplierAddress()))
                .setEmail(pojo_supplier.getEmail())
                .setFax(pojo_supplier.getFax())
                .setPhoneNo(pojo_supplier.getPhone())
                .setName(pojo_supplier.getName())
                .build();
        return proto_supplier;
    }
}
