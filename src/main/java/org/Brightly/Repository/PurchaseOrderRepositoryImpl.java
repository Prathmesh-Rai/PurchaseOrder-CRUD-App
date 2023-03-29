package org.Brightly.Repository;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import org.Brightly.Entities.PurchaseOrder;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PurchaseOrderRepositoryImpl implements PanacheRepository<PurchaseOrder> {
   // @Override
  /*  public Uni<PurchaseOrder> createPurchaseOrder(PurchaseOrder purchaseOrder) {
      return Panache.withTransaction(()->);

    }*/
}
