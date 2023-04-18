package org.Brightly.GRPCService;

import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.Brightly.*;
import org.Brightly.Converter_POJO_to_Proto.AddressConverter;
import org.Brightly.Converter_POJO_to_Proto.SupplierConverter;
import org.Brightly.Converter_Proto_to_POJO.PurchaseOrderConverter;
import org.Brightly.Entities.PurchaseOrder;
import org.Brightly.Repository.PurchaseOrderRepositoryImpl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@GrpcService
public class
PurchaseOrderGrpcService extends MutinyPurchaseOrderRPCsGrpc.PurchaseOrderRPCsImplBase {


    private final PurchaseOrderRepositoryImpl purchaseOrderService ;

    public PurchaseOrderGrpcService(PurchaseOrderRepositoryImpl purchaseOrderService){
        this.purchaseOrderService = purchaseOrderService;
    }

    @Inject
    PurchaseOrderRepositoryImpl purchaseOrderRepository;

    @Override
    public Uni<PurchaseOrderResponse> createPurchaseOrder(PurchaseOrderRequest request) {

        PurchaseOrder purchaseOrder = PurchaseOrderConverter.generatePurchaseOrder(request);
        purchaseOrder.setStatus(PurchaseOrder.POStatus.draft);

        Uni<PurchaseOrder> purchaseOrderUni = purchaseOrderRepository.createPurchaseOrder(purchaseOrder);

        return purchaseOrderUni.onItem().transform(order -> PurchaseOrderResponse.newBuilder().setPoId(order.getPoID()).build());
    }

    @Override
    public Uni<GetPurchaseOrderResponse> getPurchaseOrder(GetPurchaseOrderRequest request) {
        long request_id = request.getPoId();
        Uni<PurchaseOrder> purchaseOrderUni = purchaseOrderRepository.getPurchaseOrder(request_id);
        return purchaseOrderUni.onItem().transform(purchaseOrder ->
                org.Brightly.Converter_POJO_to_Proto.PurchaseOrderConverter.generatePurchaseOrderRequest(purchaseOrder)) ;
    }

    @Override
    public Uni<PurchaseOrderResponse> updatePurchaseOrder(PurchaseOrderRequest request) {
        PurchaseOrder purchaseOrder = PurchaseOrderConverter.generatePurchaseOrder(request);
        purchaseOrder.setPoID(request.getPoID());
       Uni<PurchaseOrder> purchaseOrderUni = purchaseOrderRepository.updatePurchaseOrder(purchaseOrder);
       return purchaseOrderUni.onItem().transform(purchaseOrder1 ->
            PurchaseOrderResponse.newBuilder().setPoId(purchaseOrder1.getPoID()).build());
    }

    @Override
    public Multi<GetPurchaseOrderResponse> getAllPurchaseOrders(Empty request) {
        return purchaseOrderRepository.getAllPurchaseOrder()
                .onItem().transform(purchaseOrder ->
                        org.Brightly.Converter_POJO_to_Proto.PurchaseOrderConverter.generatePurchaseOrderRequest(purchaseOrder));

    }

    @Override
    public Uni<GetPurchaseOrderResponse> updateStatus(updateStatusRequest request) {
        Uni<PurchaseOrder> responseUni = purchaseOrderRepository.updateStatus(request) ;
        Uni<GetPurchaseOrderResponse> result = responseUni.onItem().transform(purchaseOrder -> {
            GetPurchaseOrderResponse response = GetPurchaseOrderResponse.newBuilder()
                    .setDescription(purchaseOrder.getDescription())
                    .setName(purchaseOrder.getName())
                    .setNotes(purchaseOrder.getNotes())
                    .setPoID(purchaseOrder.getPoID())
                    .setStatus(POStatus.submitted)
                    .setShippingAddress(AddressConverter.generateAddress(purchaseOrder.getShippingAddress()))
                    .setSupplier(SupplierConverter.generateSupplier(purchaseOrder.getSupplier()))
                    .build() ;
            return response ;
        }) ;
        return result ;
    }
}

