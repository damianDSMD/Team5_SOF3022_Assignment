package org.example.nhom5_assignment_java5.service;

import org.example.nhom5_assignment_java5.dto.CheckoutForm;
import org.example.nhom5_assignment_java5.entity.HoaDon;
import org.example.nhom5_assignment_java5.entity.KhachHang;

public interface CheckoutService {
    HoaDon createOrder(KhachHang khachHang, CheckoutForm form);
}
