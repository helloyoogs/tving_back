package com.tving_back.myapp.payment.controller;

import com.tving_back.myapp.payment.model.Payment;
import com.tving_back.myapp.payment.repository.PaymentRepository;
import com.tving_back.myapp.usage.model.UsageState;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    // 모든 게시물 조회
    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Date getDateAfterMonths(int months, Integer day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, months);
        if (day != null) {
            calendar.set(Calendar.DAY_OF_MONTH, day);
        }

        return calendar.getTime();
    }
    // 결제 완료 시 이용 현황 테이블 저장
    @PostMapping("/pay")
    public Payment createPayment(@RequestBody Payment payment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        payment.setUser_id(username);
        return paymentRepository.save(payment);
    }

    @GetMapping("/list")
    public Payment getPayment(@ModelAttribute UsageState usageState) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        List<Payment> allPayment = paymentRepository.findAll();

        if (allPayment == null) {
            return null;
        }
        if (usageState == null) {
            return null;
        }

        Optional<Payment> paymentOptional = allPayment.stream()
                .filter(state ->
                        userId.equals(state.getUser_id())
                                && state.getUsageState().getStart_date() != null
                                && state.getUsageState().getStart_date().compareTo(getDateAfterMonths(0, 1)) >= 0
                                && state.getUsageState().getStart_date().compareTo(getDateAfterMonths(1, 1)) < 0
                                && ("true".equals(state.getUsageState().getSubscription_check()) || "true".equals(state.getPay_check()))
                )
                .findFirst();

        return paymentOptional.orElse(null);
    }

    @GetMapping("/nextList")
    public Payment getPaymentNext(@ModelAttribute UsageState usageState) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        List<Payment> allPayment = paymentRepository.findAll();

        if (allPayment == null) {
            return null;
        }
        if (usageState == null) {
            return null;
        }

        Optional<Payment> paymentOptional = allPayment.stream()
                .filter(state ->
                        userId.equals(state.getUser_id())
                                && state.getUsageState().getStart_date() != null
                                && state.getUsageState().getStart_date().compareTo(getDateAfterMonths(1, 1)) >= 0
                                && ("true".equals(state.getUsageState().getSubscription_check()) || "true".equals(state.getPay_check()))
                )
                .findFirst();

        return paymentOptional.orElse(null);
    }


    // 게시물 수정
    @PutMapping("/{id}")
    public Payment updatePayment(@PathVariable Long id, @RequestBody Payment updatedPayment) throws NotFoundException {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment not found with id: " + id));
        if (updatedPayment.getPay_check() != null) {
            payment.setPay_check(updatedPayment.getPay_check());
        }
        return paymentRepository.save(payment);
    }

}
