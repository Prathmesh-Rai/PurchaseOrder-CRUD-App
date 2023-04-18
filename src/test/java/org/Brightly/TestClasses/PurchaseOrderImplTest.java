package org.Brightly.TestClasses;

import io.quarkus.grpc.GrpcClient;
import io.quarkus.test.Mock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.Brightly.*;
import org.Brightly.Converter_Proto_to_POJO.PurchaseOrderConverter;
import org.Brightly.Converter_Proto_to_POJO.SupplierConverter;
import org.Brightly.Entities.Address;
import org.Brightly.Entities.Item;
import org.Brightly.Entities.PurchaseOrder;
import org.Brightly.Entities.Supplier;
import org.Brightly.Repository.PurchaseOrderRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
//import static org.junit.jupiter.api.Assertions.assertThat;


import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
public class PurchaseOrderImplTest {

    @InjectMock
    PurchaseOrderRepositoryImpl purchaseOrderRepository;

    @GrpcClient
    PurchaseOrderRPCs purchaseOrderGrpcService;

    @Test
    void createPurchaseOrder(){
        PurchaseOrderRequest poRequest = PurchaseOrderRequest.newBuilder().setPoID(1).build();
       PurchaseOrder purchaseOrder = PurchaseOrderConverter.generatePurchaseOrder(poRequest);
        purchaseOrder.setPoID(poRequest.getPoID());
        Mockito.when(purchaseOrderRepository.createPurchaseOrder(any()))
                .thenReturn(Uni.createFrom().item(purchaseOrder));

        Uni<PurchaseOrderResponse> responseBody = purchaseOrderGrpcService.createPurchaseOrder(poRequest);
        PurchaseOrderResponse orderResponse =  responseBody.await().atMost(Duration.ofSeconds(3));

        Assertions.assertEquals(poRequest.getPoID(),orderResponse.getPoId());
    }

    @Test
    void getPurchaseOrder(){
        PurchaseOrder po = new PurchaseOrder() ;
        po.setPoID(1);
        Item item = new Item();
        item.setItemI(2);
        item.setDescription("desc");
        item.setPrice(100);
        item.setQuantity(100);
        item.setName("Prath");
        item.setStatus(Item.itemStatus.available);
        po.setItems(List.of(item));
        po.setDescription("Desc");
        po.setName("ABC");
        po.setNotes("Notes");
        Address sampleAddress = new Address() ;
        sampleAddress.setCity("city_name");
        sampleAddress.setState("state_name");
        sampleAddress.setCountry("country_name");
        sampleAddress.setPin("pin_value");
        Supplier sampleSupplier = new Supplier() ;
        sampleSupplier.setSupplierAddress(sampleAddress);
        sampleSupplier.setEmail("sampleSupplier_email");
        sampleSupplier.setFax("sample_supplier_fax");
        sampleSupplier.setPhone("sample_supplier_phoneNo");
        sampleSupplier.setName("sample_supplier_name");
        po.setSupplier(sampleSupplier);
        po.setShippingAddress(sampleAddress);
        Mockito.when(purchaseOrderRepository.getPurchaseOrder(1l)).thenReturn(Uni.createFrom().item(po)) ;
        GetPurchaseOrderRequest request = GetPurchaseOrderRequest.newBuilder().setPoId(1).build() ;
        Uni<GetPurchaseOrderResponse> response = purchaseOrderGrpcService.getPurchaseOrder(request) ;
        GetPurchaseOrderResponse responseBody = response.await().indefinitely() ;
        Assertions.assertEquals(po.getPoID(),responseBody.getPoID());
    }

    @Test
    void getAllPurchaseOrders(){
        List<PurchaseOrder> poList = new ArrayList<>() ;
        poList.add(new PurchaseOrder()) ;
        poList.add(new PurchaseOrder()) ;
        poList.add(new PurchaseOrder()) ;
        Mockito.when(purchaseOrderRepository.getAllPurchaseOrder()).thenReturn(Multi.createFrom().iterable(poList)) ;
        Empty emptyRequest = Empty.newBuilder().build() ;
        Multi<GetPurchaseOrderResponse> responseMulti = purchaseOrderGrpcService.getAllPurchaseOrders(emptyRequest) ;
        List<GetPurchaseOrderResponse> responseList = responseMulti.collect().asList().await().indefinitely() ;
        Assertions.assertEquals(poList.size(),responseList.size());
    }

    @Test
    void updatePurchaseOrder(){
        PurchaseOrder po = new PurchaseOrder() ;
        po.setPoID(1);
        Mockito.when(purchaseOrderRepository.updatePurchaseOrder(any())).thenReturn(Uni.createFrom().item(po)) ;
        PurchaseOrderRequest poRequest = PurchaseOrderRequest.newBuilder().setPoID(1).build() ;
        Uni<PurchaseOrderResponse> responseUni = purchaseOrderGrpcService.updatePurchaseOrder(poRequest) ;
        PurchaseOrderResponse updatedItem = responseUni.await().indefinitely() ;
        Assertions.assertEquals(po.getPoID(),updatedItem.getPoId());
    }

    @Test
    void updateStatus(){
        PurchaseOrder po = new PurchaseOrder() ;
        po.setPoID(1l);
        po.setDescription("sample description");
        po.setName("sample name");
        po.setNotes("sample notes");
        Address sampleAddress = new Address() ;
        sampleAddress.setPin("pin");
        sampleAddress.setCountry("country");
        sampleAddress.setState("state");
        sampleAddress.setCity("city");
        po.setShippingAddress(sampleAddress);
        Supplier sampleSupplier = new Supplier() ;
        sampleSupplier.setName("supplier name");
        sampleSupplier.setPhone("supplier phone");
        sampleSupplier.setFax("supplier fax");
        sampleSupplier.setEmail("supplier email");
        sampleSupplier.setSupplierAddress(sampleAddress);
        po.setSupplier(sampleSupplier);
        po.setStatus(PurchaseOrder.POStatus.submitted);
        Mockito.when(purchaseOrderRepository.updateStatus(any())).thenReturn(Uni.createFrom().item(po)) ;
        updateStatusRequest request = updateStatusRequest.newBuilder().setPoId(1l).build() ;
       Uni<GetPurchaseOrderResponse> responseUni = purchaseOrderGrpcService.updateStatus(request) ;
        GetPurchaseOrderResponse response = responseUni.await().indefinitely();
        POStatus result = response.getStatus() ;
        PurchaseOrder.POStatus status = PurchaseOrder.POStatus.draft ;
        if( result == POStatus.submitted ){
            status = PurchaseOrder.POStatus.submitted;
        }
        Assertions.assertEquals(po.getStatus(),status);
    }


}

