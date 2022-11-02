package com.team.cobi.sales.service;

import com.team.cobi.purchase.repository.PurchaseClientQueryRepository;
import com.team.cobi.purchase.repository.PurchaseClientRepository;
import com.team.cobi.sales.dto.*;
import com.team.cobi.sales.entity.Client;
import com.team.cobi.sales.repository.SalesClientQueryRepository;
import com.team.cobi.sales.repository.SalesClientRepository;
import com.team.cobi.util.exception.NullException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class SalesClientService {

    private final SalesClientRepository salesClientRepository;
    private final SalesClientQueryRepository salesClientQueryRepository;
    private final PurchaseClientRepository purchaseClientRepository;
    private final PurchaseClientQueryRepository purchaseClientQueryRepository;

    @Transactional //모두 성공하거나, 모두 실패하거나
    public void createSalesClient(SalesClientCreateRequest request) {
        log.info("Sales Create:" + request.toString());
        request.setType("판매");
        int clientNum = createClientNum();
        salesClientRepository.save(new Client(request.getType(),request.getClientName(), request.getCeoName(), request.getRegisterNum(), request.getAddress(), request.getDetailAddress(), request.getBusinessType(), request.getZipCode(), request.getEmail(),  request.getTelephone(), request.getFax(), request.getCellPhone(), clientNum)); //insert 후에는 save 해서 db 저장
    }

    public void createPurchaseClient(SalesClientCreateRequest request) {
        log.info("Purchase Create:" + request.toString());
        request.setType("구매");
        int clientNum = createClientNum();
        salesClientRepository.save(new Client(request.getType(),request.getClientName(), request.getCeoName(), request.getRegisterNum(), request.getAddress(), request.getDetailAddress(), request.getBusinessType(), request.getZipCode(), request.getEmail(),  request.getTelephone(), request.getFax(), request.getCellPhone(), clientNum)); //insert 후에는 save 해서 db 저장
    }

    @Transactional(readOnly = true)
    public int createClientNum() {
        int clientNum = salesClientRepository.countAllBy();
        log.info("확인 : " + clientNum);
        return clientNum;
    }

    @Transactional(readOnly = true) //읽기 전용
    public Page<SalesClientListResponse> getSalesClientPage(SearchSalesClientList search, Pageable pageable){
        return salesClientQueryRepository.searchPage(search, pageable);
    }

    @Transactional(readOnly = true) //읽기 전용
    public Page<SalesClientListResponse> getPurchaseClientPage(SearchSalesClientList search, Pageable pageable){
        return purchaseClientQueryRepository.searchPage(search, pageable);
    }

    @Transactional(readOnly = true)
    public Client getSalesClientDetail(String id) {
        return getSalesClient(id);
    }

    @Transactional(readOnly = true)
    public Client getPurchaseClientDetail(String id) {
        return getPurchaseClient(id);
    }

    @Transactional(readOnly = false)
    public Client getSalesClient(String id){
        Client client = salesClientRepository.findById(id).orElse(null);
        if(client == null) throw new NullException();
        return client;
    }

    @Transactional(readOnly = false)
    public Client getPurchaseClient(String id){
        Client client = purchaseClientRepository.findById(id).orElse(null);
        if(client == null) throw new NullException();
        return client;
    }

    @Transactional
    public void updateSalesClient(String id, SalesClientUpdateRequest request) {
        Client client = getSalesClient(id); //db에 매개변수로 받은 id와 같은게 있어? 있으면
        client.update(request);  //바구니 변수에 담아온 request 값을 db에 업데이트해
        salesClientRepository.save(client);    //dao.저장
    }

    public void deleteSalesClient(String id) {
        Client client = getSalesClient(id);
        client.delete();
        salesClientRepository.save(client);
    }

    // 주문서 등록에서 클라이언트 리스트 출력을 위한 서비스 (진아) -> 규휘가 사용
    @Transactional
    public SalesClientCodeResponse getSalesClientCodes(String id) {
        return salesClientQueryRepository.clientItem(id);
    }

    @Transactional
    public SalesClientCodeResponse getPurchaseClientCodes(String id) {
        return purchaseClientQueryRepository.clientItem(id);
    }

    @Transactional
    public List<SalesClientCodeResponse> getSalesClientCode() {
        String type = "판매";
        List<Client> clientList = salesClientRepository.findByDeleteFlagFalseAndType(type);
        return clientList.stream().map(SalesClientCodeResponse::new).collect(Collectors.toList());
    }
}