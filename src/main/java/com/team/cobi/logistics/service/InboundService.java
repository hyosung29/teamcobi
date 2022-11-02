package com.team.cobi.logistics.service;

import com.team.cobi.logistics.dto.InboundCreateRequest;
import com.team.cobi.logistics.dto.InboundListResponse;
import com.team.cobi.logistics.dto.InboundUpdateRequest;
import com.team.cobi.logistics.dto.SearchInboundList;
import com.team.cobi.logistics.entity.Inbound;
import com.team.cobi.logistics.repository.InboundQueryRepository;
import com.team.cobi.logistics.repository.InboundRepository;
import com.team.cobi.purchase.entity.OrderForm;
import com.team.cobi.purchase.repository.OrderFormRepository;
import com.team.cobi.util.exception.NullException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InboundService {

    private final InboundRepository inboundRepository;
    private final OrderFormRepository orderFormRepository;

    private final InboundQueryRepository inboundQueryRepository;



    @Transactional
    public void createInbound(InboundCreateRequest request) {
        inboundRepository.save(new Inbound(request));
    }


    @Transactional
    public Page<InboundListResponse> getInboundPage(SearchInboundList search, Pageable pageable) {
        return inboundQueryRepository.inboundPage(search, pageable);
    }

    @Transactional
    public Page<InboundListResponse> getInboundHoldPage(SearchInboundList search, Pageable pageable) {
        return inboundQueryRepository.inboundHoldPage(search, pageable);
    }

    @Transactional
    public InboundListResponse getInboundDetail(String id) {
        return inboundQueryRepository.inboundDetail(id);
    }



    @Transactional
    public void updateInbound(String id, InboundUpdateRequest request) {
        Inbound inbound = getInbound(id);
        inbound.update(request);
        inboundRepository.save(inbound);
    }


    // 진아꺼
    @Transactional
    public void updatePurchaseStatus(String id) {
        OrderForm orderForm = getOrderForm(id);
        orderForm.updatePurchaseStatus();
        orderFormRepository.save(orderForm);
    }

    @Transactional
    public void insertOrderAccess(String id) {
        inboundRepository.save(new Inbound(id));
    }

    @Transactional(readOnly = true)
    public Inbound getInbound(String id) {
        Inbound inbound = inboundRepository.findById(id).orElse(null);
        if (inbound == null) throw new NullException();
        return inbound;
    }

    @Transactional(readOnly = true)
    public OrderForm getOrderForm(String id) {
        OrderForm orderForm = orderFormRepository.findById(id).orElse(null);
        if (orderForm == null) throw new NullException();
        return orderForm;
    }


    @Transactional
    public void deleteWarehouse(String id) {
        Inbound inbound = getInbound(id);  // Notice(Entity) = DB, getNotice(id) 동일한 id값 을 가져와라
        inbound.delete();
        inboundRepository.save(inbound);
    }

    @Override
    public String toString() {
        return " ======== InboundService => " +
                "inboundQueryRepository => " + inboundQueryRepository;
    }

}
