package org.Brightly.Repository;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.Brightly.*;
import org.Brightly.Entities.PurchaseOrder;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class PurchaseOrderRepositoryImpl implements PanacheRepository<PurchaseOrder> {


    private final String POID = "poid";

    public Uni<PurchaseOrder> createPurchaseOrder(PurchaseOrder purchaseOrder) {
      return this.persistAndFlush(purchaseOrder);
    }

    public Uni<PurchaseOrder> getPurchaseOrder(long purchaseOrderId) {
        return this.findById(purchaseOrderId);
    }

    public Multi<PurchaseOrder> getAllPurchaseOrder(){
       return this.streamAll();
    }

    public Uni<PurchaseOrder> updatePurchaseOrder(PurchaseOrder purchaseOrderNew) {
       return this.find(POID , purchaseOrderNew.getPoID())
               .firstResult()
               .onItem()
               .transformToUni(purchaseOrderOld -> {
                   purchaseOrderOld.setName(purchaseOrderNew.getName());
                   purchaseOrderOld.setPoID(purchaseOrderNew.getPoID());
                   purchaseOrderOld.setSupplier(purchaseOrderNew.getSupplier());
                   purchaseOrderOld.setDescription(purchaseOrderNew.getDescription());
                   purchaseOrderOld.setNotes(purchaseOrderNew.getNotes());
                   purchaseOrderOld.setName(purchaseOrderNew.getName());
                   purchaseOrderOld.setStatus(purchaseOrderNew.getStatus());
                   purchaseOrderOld.setShippingAddress(purchaseOrderNew.getShippingAddress());
                  return this.persistAndFlush(purchaseOrderOld);
               });
    }


    public Uni<PurchaseOrder> updateStatus(updateStatusRequest request) {
       return this.findById(request.getPoId()).onItem().ifNotNull().invoke(fetchedRecord->{
           fetchedRecord.setStatus(PurchaseOrder.POStatus.submitted);
       }).onItem().transformToUni(purchaseOrder -> {
         return  persistAndFlush(purchaseOrder);
       });
               //.replaceWith(this.findById(request.getPoId())) ;
    }


}
