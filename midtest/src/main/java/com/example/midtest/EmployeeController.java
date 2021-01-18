package com.example.midtest;

import com.example.midtest.csv.EmployeeService;
import com.example.midtest.exception.ResourceNotFoundException;
import org.aspectj.bridge.Message;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@Controller
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/employee")
    public Employee addEmployee(@Valid @RequestBody Employee employee) throws ValidationException {
        Date date = new Date();
        SimpleDateFormat DateFor = new SimpleDateFormat("ddMMyyyy");
        String dateNow = DateFor.format(date);

        Random random = new Random();
        String randNum = String.format("%04d", random.nextInt(10000));

        if (!employee.getGender().equals("MALE") && !employee.getGender().equals("FEMALE")){
            throw new ValidationException("Please Fill Gender with MALE or FEMALE!");
        }
        if (!employee.getDept_no().equals("ENG") && !employee.getDept_no().equals("FIN") && !employee.getDept_no().equals("HRD")){
            throw new ValidationException("Please Fill Dept No with FIN, ENG, or HRD!");
        }

        employee.setEmp_no(employee.getDept_no()+dateNow+randNum);
        return employeeRepository.save(employee);
    }

    @GetMapping("/employee")
    public List<Employee> getEmployee(){
        return employeeRepository.findAll();
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") String id) throws ResourceNotFoundException{
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));
        return ResponseEntity.ok().body(employee);
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable(value = "id") String id,
                                                   @Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));

        employee.setFirst_name(employeeDetails.getFirst_name());
        employee.setLast_name(employeeDetails.getLast_name());
        employee.setGender(employeeDetails.getGender());
        employee.setBirth_date(employeeDetails.getBirth_date());
        employee.setDept_no(employeeDetails.getDept_no());

        return ResponseEntity.ok(employeeRepository.save(employee));
    }

    @DeleteMapping("/employee/{id}")
    public String delEmployee(@PathVariable(value = "id") String id){
        Optional<Employee> employee = employeeRepository.findById(id);
        String result = "";

        if(employee.isEmpty()) {
            result = "Id "+id+" Tidak Ditemukan";
            return result;
        }else{
            employeeRepository.deleteById(id);
            result = "Id "+id+" Berhasil Dihapus";
        }

        return result;
    }


    @GetMapping("/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        List<Employee> listEmploy = employeeService.listAll();

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"Emp No", "First Name", "Last Name", "Gender", "Birth Date", "Dept No"};
        String[] nameMapping = {"emp_no", "first_name", "last_name", "gender", "birth_date", "dept_no"};

        csvWriter.writeHeader(csvHeader);

        for (Employee employee : listEmploy) {
            csvWriter.write(employee, nameMapping);
        }

        csvWriter.close();
    }
}
