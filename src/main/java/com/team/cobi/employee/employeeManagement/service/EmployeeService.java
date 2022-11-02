package com.team.cobi.employee.employeeManagement.service;

import com.team.cobi.employee.employeeManagement.dto.*;
import com.team.cobi.employee.employeeManagement.repository.EmployeeQueryRepository;
import com.team.cobi.employee.employeeManagement.repository.EmployeeRepository;
import com.team.cobi.employee.employeeManagement.entity.Employee;
import com.team.cobi.sales.dto.SalesClientCodeResponse;
import com.team.cobi.sales.entity.Client;
import com.team.cobi.util.exception.NullException;
import com.team.cobi.util.exception.UtilClass;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeQueryRepository employeeQueryRepository;

    @Transactional
    public void createEmployee(EmployeeCreateRequest request) {
        String employeeNumber = createEmployeeNumber();
        String password = passwordEncode(employeeNumber);
        MultipartFile multipartFile = request.getPhoto();
        String photo = null;

        // 파일업로드 처리
        uploadFile(multipartFile);
        if (multipartFile != null) {
            photo = "/images/".concat(multipartFile.getOriginalFilename());
        }

        employeeRepository.save(new Employee(request.getName(), photo, request.getResidentRegNum(), request.getPhone(), request.getAddress(), request.getDetailAddress(), request.getDepartmentId(), request.getPositionId(), employeeNumber, password, request.getEmploymentStatus()));
    }

    @Transactional(readOnly = true)
    public Page<EmployeeListResponse> getEmployeePage(SearchEmployeeList search, Pageable pageable) {
        return employeeQueryRepository.searchPage(search, pageable);
    }

    @Transactional(readOnly = true)
    public Employee getEmployeeDetail(String id) {
        return getEmployee(id);
    }

    @Transactional
    public void updateEmployee(String id, EmployeeUpdateRequest request) {
        Employee employee = getEmployee(id);

        MultipartFile multipartFile = request.getPhoto();
        String photo = null;

        // 파일업로드 처리
        uploadFile(multipartFile);
        if (multipartFile != null) {
            photo = "/images/".concat(multipartFile.getOriginalFilename());
        }

        employee.update(request, photo);
        employeeRepository.save(employee);
    }

    @Transactional
    public void deleteEmployee(String id) {
        Employee employee = getEmployee(id);
        employee.delete();
        employeeRepository.save(employee);
    }

    @Transactional(readOnly = true)
    public Employee getEmployee(String id) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee == null) throw new NullException();
        return employee;
    }


    @Transactional(readOnly = true)
    public String createEmployeeNumber() {
        Date date = new Date();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("YYMMdd");
        String dateFormat = dateFormatter.format(date);

        int count = employeeRepository.countByEmployeeNumberLike(dateFormatter.format(date) + "%") + 1;
        String employeeNumber;
        if (count < 10) {
            employeeNumber = dateFormat.concat("0".concat(String.valueOf(count)));
        } else {
            employeeNumber = dateFormatter.format(date).concat(String.valueOf(count));
        }
        return employeeNumber;
    }

    private String passwordEncode(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }

    private void uploadFile(MultipartFile file) {
        String path = UtilClass.getAbsolutePath();
        String saveDirectory = path.concat("/images/");
        try {
            assert file != null;
            file.transferTo(new File(saveDirectory + file.getOriginalFilename()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 주문서 등록에서 클라이언트 리스트 출력을 위한 서비스 (진아)
    @Transactional
    public List<EmployeeCodeResponse> getEmployeeCodes() {
        List<Employee> employeeList = employeeRepository.findByDeleteFlagFalseOrderByEmployeeNumber();
        return employeeList.stream().map(EmployeeCodeResponse::new).collect(Collectors.toList());
    }
}
