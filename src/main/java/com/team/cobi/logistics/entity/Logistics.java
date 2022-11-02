package com.team.cobi.logistics.entity;

import com.team.cobi.base.BaseEntity;
import com.team.cobi.logistics.dto.InboundCreateRequest;
import io.swagger.annotations.ApiModelProperty;
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
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Logistics extends BaseEntity implements Serializable {

    // DTO = 내가 화면에서 입력한 값을 주고 받는 작은 바구니
    // Entity = DB 에 저장된 값과 DTO 를 비교할 때 사용
    // 자세한 내용은 디스코드 참고하세요

    @Id
//    @GeneratedValue(generator = "uuid2")
//    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    private String id;          //물류 고유번호

    @Type(type = "true_false")
    @Column(name = "delete_flag")
    private boolean deleteFlag = false;

//    @ApiModelProperty("거래처명")
//    @Column(name = "client")
//    private String client;      //거래처명

    @ApiModelProperty("r_id")
    @Column(name = "order_form_id")
    private String orderFormId;      //요청서ID

    @ApiModelProperty("입고일자")
    @Column(name = "inbound_date")
    private Date inboundDate;  //입고일자

    @ApiModelProperty("입하 예정일")
    @Column(name = "stock_in_date")
    private Date stockInDate; //입하 예정일

    @ApiModelProperty("품목수")
    @Column(name = "item_list_count")
    private int itemListCount;    //품목수 (요청서 내 품목종류의 갯수)

    @ApiModelProperty("물류 상태")
    @Column(name = "status")
    private String status;      //물류 상태(입하지시/입고완료/출고완료)

    @ApiModelProperty("품목")
    @Column(name = "item")
    private String item;        //품목

    @ApiModelProperty("상품수량")
    @Column(name = "item_count")
    private int itemCount;     //상품수량 (특정상품의 갯수)

    @ApiModelProperty("창고명")
    @Column(name = "warehouse")
    private String warehouse;   //창고명

//    public Logistics(InboundCreateRequest request) {
//        this.id = request.getId();
//        this.client = request.getCl;
//        this.requestDate = request.requestDate;
//        this.stockInDate = request.stockInDate;
//        this.itemListCount = request.itemListCount;
//        this.status = request.status;
//        this.item = request.item;
//        this.itemCount = request.itemCount;
//        this.warehouse = request.warehouse;
//    }

    //update의 경우 입고완료/출고완료 변경
//    public void update(InboundCreateRequest request) {
//        this.status = request.getStatus();
//        this.stockInDate = request.getStockInDate();
//    }


}
