package com.team.cobi.logistics.service;

import com.team.cobi.logistics.dto.OutboundCreateRequest;
import com.team.cobi.logistics.dto.OutboundListResponse;
import com.team.cobi.logistics.dto.SearchInboundList;
import com.team.cobi.logistics.entity.Outbound;
import com.team.cobi.logistics.repository.OutboundQueryRepository;
import com.team.cobi.logistics.repository.OutboundRepository;
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
public class OutboundService {

    private final OutboundRepository outboundRepository;

    private final OrderFormRepository orderFormRepository;

    private final OutboundQueryRepository outboundQueryRepository;

    @Transactional
    public void createOutbound(OutboundCreateRequest request) {
        outboundRepository.save(new Outbound(request));
    }

    @Transactional
    public Page<OutboundListResponse> getOutboundPage(SearchInboundList search, Pageable pageable) {
        return outboundQueryRepository.outboundPage(search, pageable);
    }

    @Transactional
    public Page<OutboundListResponse> getOutboundHoldPage(SearchInboundList search, Pageable pageable) {
        return outboundQueryRepository.outboundHoldPage(search, pageable);
    }

    @Transactional(readOnly = true)
    public Outbound getOutbound(String id) {
        Outbound outbound = outboundRepository.findById(id).orElse(null);
        log.info("dajkdjadjaos" + outbound);
        if (outbound == null) throw new NullException();
        return outbound;
    }

    @Transactional
    public OutboundListResponse getOutboundDetail(String id) { return outboundQueryRepository.outboundDetail(id); }

    @Transactional
    public void updateOutbound(String id) {
        Outbound outbound = getOutbound(id);
        outbound.update();
        outboundRepository.save(outbound);
    }

    @Transactional
    public void deleteOutbound(String id) {
        Outbound outbound = getOutbound(id);
        outbound.delete();
        outboundRepository.save(outbound);
    }


    @Transactional
    public void updateSalesStatus(String id) {
        OrderForm orderForm = getOrderForm(id);
        orderForm.updateSalesStatus();
        orderFormRepository.save(orderForm);
    }

    @Transactional
    public void insertSalesAccess(String id) {
        outboundRepository.save(new Outbound(id));
    }

    @Transactional(readOnly = true)
    public OrderForm getOrderForm(String id) {
        OrderForm orderForm = orderFormRepository.findById(id).orElse(null);
        if(orderForm == null) throw new NullException();
        return orderForm;
    }

    @Override
    public String toString() {
        return " ============ OutboundService => " +
                "outboundQueryRepository => " + outboundQueryRepository;
    }


}
