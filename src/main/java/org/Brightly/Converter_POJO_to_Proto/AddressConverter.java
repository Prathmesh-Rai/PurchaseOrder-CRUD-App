package org.Brightly.Converter_POJO_to_Proto;

import org.Brightly.Entities.Address;

public class AddressConverter {
    public static org.Brightly.Address generateAddress(Address address){
        org.Brightly.Address proto_address = org.Brightly.Address.newBuilder()
                .setCity(address.getCity())
                .setCountry(address.getCountry())
                .setPin(address.getPin())
                .setState(address.getState())
                .build() ;
        return proto_address ;
    }
}
