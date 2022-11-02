package com.team.cobi.purchase.entity;

import com.team.cobi.base.BaseEntity;
import com.team.cobi.purchase.dto.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(name="request_io")
/*
* Entity
*  - table 그자체 1개테이블 : 1개entity
*  - 근데 화면에서 값 insert 할 때 table명이 다르다고 오류가 뜰 때가 있음
*  - 그래서 @Entity(name="DB에서 설정한 table 이름") 으로 써줘야함(19행 참고)
*/
@Getter
@NoArgsConstructor // 디폴트생성자 (lombok기능)
@AllArgsConstructor // 모든 매개변수 생성자 (lombok기능)

public class OrderForm extends BaseEntity implements Serializable {
    // 영속성 컨텍스트
    @Id // 이 테이블의 기본키라는 뜻
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    // 자동 생성되게 하는 @GeneratedValue 중 uuid2 타입으로 지정
    // 왜 그런진 모르지만 uuid로는 warn 로그가 쌓인다나 뭐래나
    // 효성이가 왜 uuid / uuid2인지 그걸 자바에서도 확인해오기
    @Column(name = "id")
    private String id;

    @Type(type = "true_false")
    @Column(name = "delete_flag")
    private boolean deleteFlag = false;

    // 단일 테이블의 법칙?

    /*
    * @Column(name="Db의 table 속 column명과 화면에서 어쩌구저쩌구 매칭 = 같아도 댐)
    * private 자료타입 어쩌구저쩌구;
    * 이때 DB에서 INT로 줬으면 똑같이 int로 주고, VARCHAR(n)이면 String, DATE 이면 DATE로 줘야함
    */

    // 요청타입
    @Column(name="type")
    private String type;

    @Column(name="estimate_id")
    private String estimateId;

    // 요청상태
    @Column(name="status")
    private String status;

    @Column(name="end_date")
    private LocalDateTime endDate;

    @Column(name="purchase_number")
    private String purchaseNumber;

    @Column(name="sales_number")
    private String salesNumber;


    // insert 주문서 할 때 필요한 값들(PurchaseCreateRequest dto에 담겨져 있음)
    public OrderForm(OrderFormCreateRequest request) {
        this.estimateId = request.getId();
        this.type = request.getType();
        this.endDate = request.getEndDate();
        this.status = request.getStatus();
    }
    
    // 견적서 승인버튼을 누를 시 request_io table insert를 위한 바구니 만들기
//    public OrderForm(String id) {
//        // 화면에서 실어보낸 estimate id(id)값을 내 request_io 테이블의 estimateId로 받음
//        this.estimateId = id;
//        // insert 할 때 status값은 default로 승인대기처리
//        this.type = "구입";
//        this.status = "승인대기";
//    }

    public OrderForm(String id, PurchaseEstimateRequest request, String purchaseNumber) {
        this.estimateId = id;
        // insert 할 때 status값은 default로 승인대기처리
        this.purchaseNumber = purchaseNumber;
        this.type = request.getType();
        this.status = "승인대기";
    }

    public OrderForm(String id, SalesEstimateRequest request, String salesNumber) {
        this.estimateId = id;
        // insert 할 때 status값은 default로 승인대기처리
        this.salesNumber = salesNumber;
        this.type = request.getType();
        this.status = "승인대기";
    }

    // update 주문서 할 때 필요한 값들(PurchaseUpdateRequest dto에 담겨져 있음)
    public void update(OrderFormUpdateRequest request) {
        this.status = request.getStatus();
    }

    public void updatePurchaseStatus() {
        this.status = "승인(입고지시)";
    }

    public void updateSalesStatus() {
        this.status = "승인(출하지시)";
    }

    public OrderForm(OrderFormDetailResponse response) {
        this.id = response.getId();
        this.status = "승인(입고지시)";
    }
    

    // delete 메서드를 실행하면 deleteFlag를 true로 준다(삭제하는건 아니지만 삭제처리되는 셈)
    public void delete() {
        this.deleteFlag = true;
    }


}
