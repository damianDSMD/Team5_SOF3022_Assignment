package org.example.nhom5_assignment_java5.service.Impl;

import jakarta.transaction.Transactional;
import org.example.nhom5_assignment_java5.dto.CartItem;
import org.example.nhom5_assignment_java5.dto.CheckoutForm;
import org.example.nhom5_assignment_java5.entity.*;
import org.example.nhom5_assignment_java5.repository.*;
import org.example.nhom5_assignment_java5.service.CheckoutService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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

        KhachHang kh = khachHangRepository.findById(khachHang.getMaKH())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

        HoaDon hd = new HoaDon();
        hd.setKhachHang(kh);

        // Địa chỉ giao hàng
        DiaChi diaChi;
        if (form.getDiaChiId() != null) {
            diaChi = diaChiRepository.findById(form.getDiaChiId())
                    .orElseThrow(() -> new RuntimeException("Địa chỉ không tồn tại"));
        } else {
            DiaChi newDc = new DiaChi();
            newDc.setKhachHang(kh);
            newDc.setDiaChi(form.getDiaChiText());
            newDc.setMacDinh(false);
            diaChi = diaChiRepository.save(newDc);
        }
        hd.setDiaChi(diaChi);

        hd.setNgayTaoHD(new Date());
        hd.setNgayUpHD(new Date());
        hd.setTrangThai("Pending");
        hd.setGhiChu(form.getGhiChu());

        List<HoaDonChiTiet> dsCT = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (CartItem item : form.getItems()) {
            SanPham sp = sanPhamRepository.findById(String.valueOf(item.getSanPhamId()))
                    .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại: " + item.getSanPhamId()));

            if (sp.getSoLuongTonKho() < item.getSoLuong()) {
                throw new RuntimeException("Sản phẩm " + sp.getTenSP() + " không đủ tồn kho");
            }

            HoaDonChiTiet ct = new HoaDonChiTiet();
            ct.setHoaDon(hd);
            ct.setSanPham(sp);
            ct.setSoLuong(item.getSoLuong());
            ct.setDonGia(sp.getDonGia());
            ct.setThanhTien(sp.getDonGia().multiply(BigDecimal.valueOf(item.getSoLuong())));

            dsCT.add(ct);

            sp.setSoLuongTonKho(sp.getSoLuongTonKho() - item.getSoLuong());
            sanPhamRepository.save(sp);

            total = total.add(ct.getThanhTien());
        }

        hd.setTongTien(total);
        hd.setChiTiet(dsCT);

        return hoaDonRepository.save(hd);
    }
}
