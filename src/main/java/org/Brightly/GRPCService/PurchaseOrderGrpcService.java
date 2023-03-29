package org.Brightly.GRPCService;

import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import org.Brightly.*;
import org.Brightly.Converter.PurchaseOrderConverter;
import org.Brightly.Entities.Item;
import org.Brightly.Entities.PurchaseOrder;
import org.Brightly.Entities.Supplier;
import org.Brightly.Repository.PurchaseOrderRepository;
import org.Brightly.Repository.PurchaseOrderRepositoryImpl;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@GrpcService
public class PurchaseOrderGrpcService extends MutinyPurchaseOrderRPCsGrpc.PurchaseOrderRPCsImplBase {

    @Inject
    PurchaseOrderRepositoryImpl purchaseOrderRepository ;

    @Override
    public Uni<PurchaseOrderResponse> createPurchaseOrder(PurchaseOrderRequest request) {

      PurchaseOrder purchaseOrder = PurchaseOrderConverter.generatePurchaseOrder(request) ;
      purchaseOrder.setStatus(PurchaseOrder.POStatus.draft);

      Uni<PurchaseOrder> purchaseOrderUni =  purchaseOrderRepository.persistAndFlush(purchaseOrder);

       return purchaseOrderUni.onItem().transform(order -> PurchaseOrderResponse.newBuilder().setPoId(order.getPoID()).build());
    }
}
