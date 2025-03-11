package com.example.lab6.Controller;

import com.example.lab6.Api.ApiResponse;
import com.example.lab6.Model.Employee;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    ArrayList<Employee> emploees = new ArrayList<>();

    @GetMapping("/get")
    public ArrayList<Employee> getEmployees() {
        return emploees;
    }

    @PostMapping("/add")
    public ResponseEntity addEmployee(@RequestBody @Valid Employee employee, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        emploees.add(employee);
        return ResponseEntity.status(200).body(new ApiResponse("successfulle added Emploee"));
    }

    @PutMapping("/update/{index}")
    public ResponseEntity updateEmploees(@PathVariable int index, @RequestBody @Valid Employee employee, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        emploees.set(index, employee);
        return ResponseEntity.status(200).body(new ApiResponse("successfule update emploee"));
    }

    @DeleteMapping("/delete/{index}")
    public ResponseEntity deleteEmplotee(@PathVariable int index) {
        if (index > emploees.size() || index < 0) {

            return ResponseEntity.status(400).body(new ApiResponse("Invalid Index"));
        }
        emploees.remove(index);
        return ResponseEntity.status(200).body(new ApiResponse("successfuly deleted Emploee"));

    }
    ArrayList<Employee> employeeByPosition=new ArrayList<>();
    @GetMapping("/search")
    public ArrayList<Employee> searchByPosition(@RequestParam String position){
        for(Employee e:emploees){
            if(e.getPosition().equalsIgnoreCase(position)){
                employeeByPosition.add(e);
            }
        }
        return employeeByPosition;
    }

    ArrayList<Employee>EmploeeMatchAge=new ArrayList<>();
@GetMapping("/getByAge")
    public ArrayList<Employee> getByAgeRange(@RequestParam int min,@RequestParam int max){
        for(Employee e:emploees){
            if(e.getAge()>=min && e.getAge()<=max){
                EmploeeMatchAge.add(e);
            }
        }
        return EmploeeMatchAge;
    }
@PutMapping("/getAplly")
    public ResponseEntity ApplyAnnualLeave(@RequestParam String id){
    for(Employee e:emploees) {
        if (e.getId().equalsIgnoreCase(id) || e.isOnLeave()  || e.getAnnualLeave() > 1) {
            e.setAnnualLeave(e.getAnnualLeave() - 1);
            e.setOnLeave(true);
           return ResponseEntity.status(200).body(new ApiResponse("Successfully "));
        }
    }
          return ResponseEntity.status(400).body(new ApiResponse("Sorry, you cannot request leave due to the lack of remaining leaves."));
}

ArrayList<Employee> employeesNoAnnualLeave=new ArrayList<>();
@GetMapping("/getNoAnnual")
public ArrayList<Employee> getNoAnnualLeave(){
    for (Employee e:emploees){
        if(e.getAnnualLeave()==0){
            employeesNoAnnualLeave.add(e);
        }
    }
    return employeesNoAnnualLeave;
}

@PutMapping("/PromoteEmployee")
public ResponseEntity PromoteEmployee(@RequestParam String ids,@RequestParam String idc) {
    for (Employee rm : emploees) {
        if (rm.getId().equalsIgnoreCase(ids) && rm.getPosition().equalsIgnoreCase("supervisor")) {
            for (Employee e : emploees) {
                if (e.getId().equalsIgnoreCase(idc) || e.isOnLeave() || e.getAge() >= 30) {
                    e.setPosition("supervisor");
                    return ResponseEntity.status(200).body(new ApiResponse("successfully"));
                }
            }
        }
    }
    return ResponseEntity.status(400).body(new ApiResponse("You do not have this permission."));
 }
}
