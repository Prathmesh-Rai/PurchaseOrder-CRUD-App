package org.Brightly.Converter;

import org.Brightly.Address;

public class AddressConverter {


    public static org.Brightly.Entities.Address generateAddress(Address address){
        org.Brightly.Entities.Address pojo_address = new org.Brightly.Entities.Address() ;
        pojo_address.setPin(address.getPin());
        pojo_address.setCountry(address.getCountry());
        pojo_address.setState(address.getState());
        pojo_address.setCity(address.getCity());
        return pojo_address ;
    }
}
