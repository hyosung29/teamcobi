package com.team.cobi.employee.positionManagement.entity;

import com.team.cobi.base.BaseEntity;
import com.team.cobi.employee.positionManagement.dto.PositionCreateRequest;
import com.team.cobi.employee.positionManagement.dto.PositionUpdateRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Position extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @Type(type = "true_false")
    @Column(name = "delete_flag")
    private boolean deleteFlag = false;

    @ApiModelProperty("직급명")
    @Column(name = "position_name")
    private String positionName;


//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "created_by", insertable = false, updatable = false)
//    private Employee employee;

    // 등록
    public Position(PositionCreateRequest request) {
        this.positionName = request.getPositionName();
    }

    // 수정
    public void update(PositionUpdateRequest request) {
        this.positionName = request.getPositionName();
    }
    
    // 삭제
    public void delete() {
        this.deleteFlag = true;
    }
}
