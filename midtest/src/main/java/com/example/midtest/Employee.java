package com.example.midtest;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.TemporalType.DATE;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    private String emp_no;

    @NotNull(message = "Please Fill First Name!")
    @NotBlank(message = "Please Fill First Name!")
    @Column(name = "first_name", nullable = false, updatable = false)
    private String first_name;

    @NotNull(message = "Please Fill Last Name!")
    @NotBlank(message = "Please Fill Last Name!")
    @Column(name = "last_name", nullable = false, updatable = false)
    private String last_name;

    @NotNull(message = "Please Fill Gender!")
    @NotBlank(message = "Please Fill Gender!")
    @Column(name = "gender", nullable = false, updatable = false)
    private String gender;

    @NotNull(message = "Please Fill Birth Date!")
    @JsonFormat(shape = JsonFormat.Shape.STRING,  pattern = "yyyy-MM-dd")
    @Column(name = "birth_date", nullable = false, updatable = false)
    private Date birth_date;

    @NotNull(message = "Please Fill Dept No!")
    @NotBlank(message = "Please Fill Dept No!")
    @Column(name = "dept_no", nullable = false, updatable = false)
    private String dept_no;

    public Employee(){}

    public Employee(String emp_no, String first_name, String last_name, String gender, Date birth_date, String dept_no) {
        this.emp_no = emp_no;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.birth_date = birth_date;
        this.dept_no = dept_no;
    }

    public String getEmp_no() {
        return emp_no;
    }

    public void setEmp_no(String emp_no) {
        this.emp_no = emp_no;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public String getDept_no() {
        return dept_no;
    }

    public void setDept_no(String dept_no) {
        this.dept_no = dept_no;
    }
}
