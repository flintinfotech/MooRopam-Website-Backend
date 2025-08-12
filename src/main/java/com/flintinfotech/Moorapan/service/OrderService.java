package com.flintinfotech.Moorapan.service;


import com.flintinfotech.Moorapan.dto.PaymentRequestDto;

import javax.transaction.Transactional;

public interface OrderService {

    @Transactional
    void processPaymentAndCreateOrder(PaymentRequestDto paymentRequest) throws Exception;
}