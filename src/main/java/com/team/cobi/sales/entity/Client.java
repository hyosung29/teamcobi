package com.team.cobi.sales.entity;

import com.team.cobi.base.BaseEntity;
import com.team.cobi.sales.dto.SalesClientCreateRequest;
import com.team.cobi.sales.dto.SalesClientUpdateRequest;
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

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Client extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @Type(type = "true_false")
    @Column(name = "delete_flag")
    private boolean deleteFlag = false;

    @Column(name = "client_name")
    private String clientName;  //거래처명

    @Column(name = "client_num")
    private int clientNum;   //거래처코드

    @Column(name = "type")
    private String type;    //거래유형
    @Column(name = "ceo_name")
    private String ceoName; //대표자명
    @Column(name = "address")
    private String address; //주소
    @Column(name = "detail_address")
    private String detailAddress;   //상세주소
    @Column(name = "zip_code")
    private int zipCode;    //우편번호
    @Column(name = "email")
    private String email;   //이메일

    @Column(name = "register_num")
    private String registerNum;     //사업자번호

    @Column(name = "business_type")
    private String businessType;    //업종
    @Column(name = "tel")
    private String telephone;   //전화번호

    @Column(name = "fax")
    private String fax; //팩스번호

    @Column(name = "cell_phone")
    private String cellPhone;   //휴대폰번호

    public Client(SalesClientCreateRequest request) {
        this.clientName = request.getClientName();
        this.ceoName = request.getCeoName();
        this.address = request.getAddress();
        this.detailAddress = request.getDetailAddress();
        this.zipCode = request.getZipCode();
        this.email = request.getEmail();
        this.registerNum = request.getRegisterNum();
        this.businessType = request.getBusinessType();
        this.telephone = request.getTelephone();
        this.fax = request.getFax();
        this.cellPhone = request.getCellPhone();
        this.type = request.getType();

    }

    public Client(String type, String clientName, String ceoName, String registerNum, String address, String detailAddress, String businessType, int zipCode, String email, String telephone, String fax, String cellPhone, int clientNum) {
        this.clientName = clientName;
        this.ceoName = ceoName;
        this.address = address;
        this.detailAddress = detailAddress;
        this.zipCode = zipCode;
        this.email = email;
        this.registerNum = registerNum;
        this.businessType = businessType;
        this.telephone = telephone;
        this.fax = fax;
        this.cellPhone = cellPhone;
        this.type = type;
        this.clientNum = clientNum;
    }

    public void update(SalesClientUpdateRequest request) {
        this.clientName = request.getClientName();
        this.ceoName = request.getCeoName();
        this.address = request.getAddress();
        this.detailAddress = request.getDetailAddress();
        this.zipCode = request.getZipCode();
        this.email = request.getEmail();
        this.registerNum = request.getRegisterNum();
        this.businessType = request.getBusinessType();
        this.telephone = request.getTelephone();
        this.fax = request.getFax();
        this.cellPhone = request.getCellPhone();
    }

    public void delete() {
        this.deleteFlag = true;
    }
}
