package com.tving_back.myapp.usage.controller;

import com.tving_back.myapp.payment.repository.PaymentRepository;
import com.tving_back.myapp.usage.model.UsageState;
import com.tving_back.myapp.usage.repository.UsageStateRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/usageState")
public class UsageStateController {

    @Autowired
    private UsageStateRepository usageStateRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    // 모든 게시물 조회
    @GetMapping
    public List<UsageState> getAllUsageStates() {
        return usageStateRepository.findAll();
    }
    //기본 게시물 작성
//    @PostMapping
//    public Payment createUsageState(@RequestBody Payment usageState) {
//        return usageStateRepository.save(usageState);
//    }
    // 로그인한 아이디값과 end_date날짜가 지나지 않은 리스트만 출력(배열 형태)
//    @GetMapping("/list")
//    public List<UsageState> getUsageStatesByUserIdAndEndDate() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userId = authentication.getName();
//
//        List<UsageState> allUsageStates = usageStateRepository.findAll();
//        List<UsageState> userUsageStates = new ArrayList<>();
//
//        for (UsageState usageState : allUsageStates) {
//            if (usageState.getUser_id().equals(userId) && usageState.getEnd_date().compareTo(new Date()) > 0) {
//                userUsageStates.add(usageState);
//            }
//        }
//
//        return userUsageStates;
//    }


    //결제하면 이용 현황 테이블에 데이터 저장
    @PostMapping("/usage")
    public UsageState createUsageState(@RequestBody UsageState usageState) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        usageState.setUser_id(username);
        return usageStateRepository.save(usageState);
    }


    // 게시물 수정
    @PutMapping("/{id}")
    public UsageState updateUsageState(@PathVariable Long id, @RequestBody UsageState updatedUsageState) throws NotFoundException{
        UsageState usageState = usageStateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment not found with id: "+ id));
       if(updatedUsageState.getSubscription_check() != null){
           usageState.setSubscription_check(updatedUsageState.getSubscription_check());
       }
        return usageStateRepository.save(usageState);
    }

    // 게시물 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsageState(@PathVariable Long id) throws NotFoundException{
        UsageState usageState = usageStateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment not found with id: "+ id));
        usageStateRepository.delete(usageState);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/updateSubscriptionCheck")
    public void updateSubscriptionCheck() {
        List<UsageState> allUsages = usageStateRepository.findAll();
        for (UsageState usage : allUsages) {
            if (usage.getEnd_date().before(new Date())) {
                usage.setSubscription_check("false");
                usageStateRepository.save(usage);
            }
        }
    }

}
