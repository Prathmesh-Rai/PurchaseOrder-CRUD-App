package org.Brightly.Converter_POJO_to_Proto;

import org.Brightly.*;
import org.Brightly.Entities.PurchaseOrder;

import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderConverter {
    public static GetPurchaseOrderResponse generatePurchaseOrderRequest(PurchaseOrder request){
        List<Item> proto_list = new ArrayList<>() ;
        request.getItems().stream().forEach(item -> {
            Item item1 = Item.newBuilder()
                    .setItemID(item.getItemID())
                    .setDescription(item.getDescription())
                    .setName(item.getName())
                    .setPrice(item.getPrice())
                    .setQuantity(item.getQuantity())
                    .setStatus(ItemStatus.available)
                    .build() ;
            proto_list.add(item1) ;
        });
        GetPurchaseOrderResponse proto_purchaseOrder = GetPurchaseOrderResponse.newBuilder()
                .setDescription(request.getDescription())
                .setName(request.getName())
                .setNotes(request.getNotes())
                .setPoID(request.getPoID())
                .setShippingAddress(AddressConverter.generateAddress(request.getShippingAddress()))
                .setSupplier(SupplierConverter.generateSupplier(request.getSupplier()))
                .setStatus(POStatus.draft)
                .build() ;
        return proto_purchaseOrder;
    }
}
