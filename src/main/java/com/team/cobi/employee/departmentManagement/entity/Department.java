package com.team.cobi.employee.departmentManagement.entity;

import com.team.cobi.base.BaseEntity;
import com.team.cobi.employee.departmentManagement.dto.DepartmentCreateRequest;
import com.team.cobi.employee.departmentManagement.dto.DepartmentUpdateRequest;
import com.team.cobi.employee.employeeManagement.dto.EmployeeUpdateRequest;
import com.team.cobi.employee.employeeManagement.entity.Employee;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Department extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @Type(type = "true_false")
    @Column(name = "delete_flag")
    private boolean deleteFlag = false;

    @ApiModelProperty("부서명")
    @Column(name = "department_name")
    private String departmentName;


//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "created_by", insertable = false, updatable = false)
//    private Employee employee;

    // 등록
    public Department(DepartmentCreateRequest request) {
        this.departmentName = request.getDepartmentName();
    }

    // 수정
    public void update(DepartmentUpdateRequest request) {
        this.departmentName = request.getDepartmentName();
    }
    
    // 삭제
    public void delete() {
        this.deleteFlag = true;
    }
}
