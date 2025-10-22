package org.example.nhom5_assignment_java5.service.Impl;

import jakarta.transaction.Transactional;
import org.example.nhom5_assignment_java5.dto.CartItem;
import org.example.nhom5_assignment_java5.dto.CheckoutForm;
import org.example.nhom5_assignment_java5.entity.*;
import org.example.nhom5_assignment_java5.repository.DiaChiRepository;
import org.example.nhom5_assignment_java5.repository.HoaDonRepository;
import org.example.nhom5_assignment_java5.repository.KhachHangRepository;
import org.example.nhom5_assignment_java5.repository.SanPhamRepository;
import org.example.nhom5_assignment_java5.service.CheckoutService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final HoaDonRepository hoaDonRepository;
    private final SanPhamRepository sanPhamRepository;
    private final KhachHangRepository khachHangRepository;
    private final DiaChiRepository diaChiRepository;

    public CheckoutServiceImpl(HoaDonRepository hoaDonRepository,
                               SanPhamRepository sanPhamRepository,
                               KhachHangRepository khachHangRepository,
                               DiaChiRepository diaChiRepository) {
        this.hoaDonRepository = hoaDonRepository;
        this.sanPhamRepository = sanPhamRepository;
        this.khachHangRepository = khachHangRepository;
        this.diaChiRepository = diaChiRepository;
    }

    @Transactional
    @Override
    public HoaDon createOrder(KhachHang khachHang, CheckoutForm form) {
        if (form.getItems() == null || form.getItems().isEmpty()) {
            throw new RuntimeException("Giỏ hàng rỗng");
        }

        // ensure KhachHang persisted
        KhachHang kh = khachHang;
        if (kh != null && kh.getMaKH() != null) {
            kh = khachHangRepository.findById(kh.getMaKH()).orElseThrow(() -> new RuntimeException("Khách không tồn tại"));
        } else {
            // if not logged in, create a minimal customer record (optional)
            kh = khachHangRepository.save(kh);
        }

        HoaDon hd = new HoaDon();
        hd.setKhachHang(kh);

        // set DiaChi
        if (form.getDiaChiId() != null) {
            DiaChi d = diaChiRepository.findById(form.getDiaChiId())
                    .orElseThrow(() -> new RuntimeException("Địa chỉ không tồn tại"));
            hd.setDiaChi(d);
        } else {
            // create transient DiaChi record if user entered text
            DiaChi newDc = new DiaChi();
            newDc.setKhachHang(kh);
            newDc.setDiaChi(form.getDiaChiText());
            newDc.setMacDinh(false);
            DiaChi savedDc = diaChiRepository.save(newDc);
            hd.setDiaChi(savedDc);
        }

        hd.setNgayTao(LocalDateTime.now());
        hd.setTrangThai("Chờ xác nhận");
        hd.setGhiChu(form.getGhiChu());

        List<HoaDonChiTiet> dsCT = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (CartItem item : form.getItems()) {
            SanPham sp = sanPhamRepository.findById(String.valueOf(item.getSanPhamId()))
                    .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại: " + item.getSanPhamId()));
            if (sp.getSoLuongTonKho() < item.getSoLuong()) {
                throw new RuntimeException("Sản phẩm " + sp.getTenSP() + " không đủ số lượng. Tồn: " + sp.getSoLuongTonKho());
            }
            // tạo chi tiết
            HoaDonChiTiet ct = new HoaDonChiTiet();
            ct.setHoaDon(hd);
            ct.setSanPham(sp);
            ct.setSoLuong(item.getSoLuong());
            ct.setDonGia(sp.getDonGia());
            ct.setThanhTien(sp.getDonGia().multiply(BigDecimal.valueOf(item.getSoLuong())));
            dsCT.add(ct);

            // trừ tồn kho
            sp.setSoLuongTonKho(sp.getSoLuongTonKho() - item.getSoLuong());
            sanPhamRepository.save(sp);

            total = total.add(ct.getThanhTien());

        }

        hd.setTongTien(total.doubleValue());
        hd.setChiTiet(dsCT);

        // save order (cascade sẽ lưu chi tiết)
        HoaDon saved = hoaDonRepository.save(hd);
        return saved;
    }
}
