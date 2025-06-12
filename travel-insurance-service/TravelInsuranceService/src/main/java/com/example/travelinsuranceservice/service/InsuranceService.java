package com.example.travelinsuranceservice.service;
 
import com.example.travelinsuranceservice.model.Insurance;
import com.example.travelinsuranceservice.repository.InsuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
 
@Service
public class InsuranceService {
 
    @Autowired
    private InsuranceRepository repo;
 
    public Insurance createInsurance(Insurance insurance) {
        return repo.save(insurance);
    }
 
    public List<Insurance> getUserInsurance(Integer userId) {
        return repo.findByUserId(userId);
    }
 
    public Insurance updateInsuranceStatus(Integer insuranceId, String status) {
        Insurance ins = repo.findById(insuranceId).orElse(null);
        if (ins != null) {
            ins.setStatus(status);
            return repo.save(ins);
        }
        return null;
    }
}
 