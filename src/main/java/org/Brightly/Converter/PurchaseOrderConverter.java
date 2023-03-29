package org.Brightly.Converter;

import org.Brightly.Entities.Item;
import org.Brightly.Entities.PurchaseOrder;

import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderConverter {
    public static PurchaseOrder generatePurchaseOrder(org.Brightly.PurchaseOrderRequest request){
        PurchaseOrder pojo_purchaseOrder = new PurchaseOrder() ;

        // convert's list<items> of proto type into list<items> of pojo type.
        List<Item> list = new ArrayList<>();
        request.getItemsList().stream().forEach(item ->{
            Item item1 =new Item();
            //item1.setItemID(item.getItemID());
            item1.setName(item.getName());
            item1.setDescription(item.getDescription());
            item1.setPrice(item.getPrice());
            item1.setQuantity(item.getQuantity());
            item1.setStatus(Item.itemStatus.available);
            list.add(item1);
        });

        //pojo_purchaseOrder.setPoID(request.getPoID());
        pojo_purchaseOrder.setName(request.getName());
        pojo_purchaseOrder.setNotes(request.getNotes());
        pojo_purchaseOrder.setDescription(request.getDescription());
        pojo_purchaseOrder.setItems(list);
        pojo_purchaseOrder.setShippingAddress(AddressConverter.generateAddress(request.getShippingAddress()));
        pojo_purchaseOrder.setSupplier(SupplierConverter.generateSupplier(request.getSupplier())) ;
        return pojo_purchaseOrder ;
    }
}
