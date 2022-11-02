package com.team.cobi.purchase.service;


import com.team.cobi.purchase.dto.*;
import com.team.cobi.purchase.entity.OrderForm;
import com.team.cobi.purchase.entity.PurchaseEstimate;
import com.team.cobi.purchase.repository.OrderFormQueryRepository;
import com.team.cobi.purchase.repository.OrderFormRepository;
import com.team.cobi.purchase.repository.PurchaseEstimateRepository;
import com.team.cobi.sales.entity.SalesEstimate;
import com.team.cobi.sales.repository.SalesEstimateRepository;
import com.team.cobi.util.exception.NullException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
* JpaRepository는 CrudRepository에 PaingAndSortingRepository, QueryByExampleExecutor를 상속하고 있음
* 1) CrudRepository는 관리되는 엔티티 클래스에 대해 정교한 CRUD 기능을 자체적으로 제공함(내장)
*  - save : 레코드(데이터)를 테이블에 저장(insert) 혹은 업데이트
*  - findOne : PK로 레코드값 한 건 찾기(detail)
*  - findAll : 전체 레코드 불러오기 / paging, pageable 기능
*  - count : 레코드 개수
*  - delete : 레코드 삭제
*/

@Service
@RequiredArgsConstructor // Repository 35행에서 선언하려면 이 어노테이션 필요
// final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션임
@Slf4j // 로그 남기려고 쓰는거 (log.info~)
public class OrderFormService {

    private final OrderFormRepository orderFormRepository;
    // repository(dao) 불러들임
    private final OrderFormQueryRepository orderFormQueryRepository;
    private final PurchaseEstimateRepository purchaseEstimateRepository;

    private final SalesEstimateRepository salesEstimateRepository;


//    @Transactional
//    // @Transactional : 모두 성공하거나, 모두 실패할 경우에만 처리됨. 중간에 초대받지 않은 null 나오면 실행이 안됨.
//                        // 데이터베이스를 다룰 때 트랜잭션을 적용하면 데이터 추가, 갱신, 삭제 등으로 이루어진 작업을 처리하던 중 오류가 발생했을 때 모든 작업들을 원상태로 되돌릴 수 있다.
//    public void createPurchase(OrderFormCreateRequest request) {
//        orderFormRepository.save(new OrderForm(request)); // insert 후에는 save 를 해줘야 db에 값이 저장된다.
//    }

    @Transactional
    public void purchaseEstimateAccess(String id, PurchaseEstimateRequest request) {
        // 받은 id값을 status에 포함해서 insert처리
        String purchaseNumber = createPurchaseNumber();
        orderFormRepository.save(new OrderForm(id, request, purchaseNumber));
    }

    @Transactional(readOnly = true)
    public String createPurchaseNumber() {
        Date date = new Date();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("YYMMdd");
        String dateFormat = dateFormatter.format(date);

        int count = orderFormRepository.countByPurchaseNumberLike(dateFormatter.format(date) + "%") + 1;
        String purchaseNumber;
        if(count < 10) {
            purchaseNumber = dateFormat.concat("0".concat(String.valueOf(count)));
        } else {
            purchaseNumber = dateFormatter.format(date).concat(String.valueOf(count));
        }
        return purchaseNumber;
    }

    @Transactional(readOnly = true)
    public String createSalesNumber() {
        Date date = new Date();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("YYMMdd");
        String dateFormat = dateFormatter.format(date);

        int count = orderFormRepository.countBySalesNumberLike(dateFormatter.format(date) + "%") + 1;
        String salesNumber;
        if(count < 10) {
            salesNumber = dateFormat.concat("0".concat(String.valueOf(count)));
        } else {
            salesNumber = dateFormatter.format(date).concat(String.valueOf(count));
        }
        return salesNumber;
    }

    @Transactional
    public void salesEstimateAccess(String id, SalesEstimateRequest request) {
        // 받은 id값을 status에 포함해서 insert처리
        String salesNumber = createSalesNumber();
        orderFormRepository.save(new OrderForm(id, request, salesNumber));
    }

    @Transactional
    public Page<OrderFormListResponse> getPurchasePage(SearchPurchaseOrderFormList search, Pageable pageable) {
        return orderFormQueryRepository.searchPurchasePage(search, pageable);
        }

    @Transactional
    public Page<OrderFormListResponse> getSalesPage(SearchPurchaseOrderFormList search, Pageable pageable) {
        return orderFormQueryRepository.searchSalesPage(search, pageable);
    }

    @Transactional(readOnly = true)
    public OrderFormDetailResponse getPurchaseDetail(String id) {
        return orderFormQueryRepository.getPurchaseFormDetail(id);
    }

    @Transactional(readOnly = true)
    public OrderFormDetailResponse getSalesDetail(String id) {
        return orderFormQueryRepository.getSalesFormDetail(id);
    }

    @Transactional(readOnly = false)
    public OrderForm getOrderForm(String id) {
        OrderForm orderForm = orderFormRepository.findById(id).orElse(null);
        if (orderForm == null) throw new NullException();
        return orderForm;
    }

    @Transactional
    public void updatePurchase(String id, OrderFormUpdateRequest request) {
        OrderForm orderForm = getOrderForm(id);
        orderForm.update(request);
        orderFormRepository.save(orderForm);
    }

    @Transactional
    public void updateSales(String id, OrderFormUpdateRequest request) {
        OrderForm orderForm = getOrderForm(id);
        orderForm.update(request);
        orderFormRepository.save(orderForm);
    }

    @Transactional
    public void deletePurchase(String id) {
        OrderForm orderForm = getOrderForm(id); // Purchase(Entity) = DB, getPurchase(id) 동일한 id값 을 가져와라
        orderForm.delete();
        orderFormRepository.save(orderForm);
    }
    @Transactional
    public void deleteSales(String id) {
        OrderForm orderForm = getOrderForm(id); // Purchase(Entity) = DB, getPurchase(id) 동일한 id값 을 가져와라
        orderForm.delete();
        orderFormRepository.save(orderForm);
    }

    @Transactional
    public void updateStatus(String id) {
        PurchaseEstimate purchaseEstimate = getPurchaseEstimate(id);
        purchaseEstimate.updateStatus();
        purchaseEstimateRepository.save(purchaseEstimate);
    }

    @Transactional(readOnly = true)
    public PurchaseEstimate getPurchaseEstimate(String id) {
        PurchaseEstimate purchaseEstimate = purchaseEstimateRepository.findById(id).orElse(null);
        if (purchaseEstimate == null) throw new NullException();
        return purchaseEstimate;
    }

    @Transactional
    public void insertOrderAccess(String id, PurchaseEstimateRequest request) {
        String purchaseNumber = createPurchaseNumber();
        orderFormRepository.save(new OrderForm(id, request, purchaseNumber));
    }

    @Transactional
    public void insertOrder2Access(String id, SalesEstimateRequest request) {
        String salesNumber = createSalesNumber();
        orderFormRepository.save(new OrderForm(id, request, salesNumber));
    }

    @Transactional
    public void updateStatus2(String id) {
        SalesEstimate salesEstimate = getSalesEstimate(id);
        salesEstimate.updateStatus();
        salesEstimateRepository.save(salesEstimate);
    }

    @Transactional(readOnly = true)
    public SalesEstimate getSalesEstimate(String id) {
        SalesEstimate salesEstimate = salesEstimateRepository.findById(id).orElse(null);
        if (salesEstimate == null) throw new NullException();
        return salesEstimate;
    }


}
