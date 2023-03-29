package org.Brightly.Repository;

import io.smallrye.mutiny.Uni;
import org.Brightly.Entities.PurchaseOrder;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public interface PurchaseOrderRepository  {

    Uni<PurchaseOrder> createPurchaseOrder(PurchaseOrder purchaseOrder);

}
