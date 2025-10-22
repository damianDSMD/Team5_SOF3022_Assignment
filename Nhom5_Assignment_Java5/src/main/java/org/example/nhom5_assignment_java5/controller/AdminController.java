package org.example.nhom5_assignment_java5.controller;

import org.example.nhom5_assignment_java5.entity.HoaDon;
import org.example.nhom5_assignment_java5.entity.KhachHang;
import org.example.nhom5_assignment_java5.entity.SanPham;
import org.example.nhom5_assignment_java5.repository.HoaDonRepository;
import org.example.nhom5_assignment_java5.repository.KhachHangRepository;
import org.example.nhom5_assignment_java5.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @GetMapping("/admin")
    public String dashboard(Model model) {

        long customerCount = khachHangRepository.count();
        long productCount = sanPhamRepository.count();
        long orderCount = hoaDonRepository.count();

        // ✅ Total revenue (sum of tongTien)
        BigDecimal totalRevenue = hoaDonRepository.findAll().stream()
                .map(HoaDon::getTongTien)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // ✅ Recent 5 orders (by NgayTaoHD or MaHD desc)
        List<HoaDon> recentOrders = hoaDonRepository.findAll().stream()
                .sorted(Comparator.comparing(
                                (HoaDon h) -> Optional.ofNullable(h.getNgayTaoHD()).orElse(new Date(0)),
                                Comparator.nullsLast(Comparator.naturalOrder()))
                        .reversed()
                        .thenComparing((HoaDon h) -> Optional.ofNullable(h.getMaHD()).orElse(0), Comparator.reverseOrder()))
                .limit(5)
                .collect(Collectors.toList());

        // ✅ Recent 5 customers
        List<KhachHang> recentCustomers = khachHangRepository.findAll().stream()
                .sorted(Comparator.comparing((KhachHang k) -> Optional.ofNullable(k.getMaKH()).orElse(0)).reversed())
                .limit(5)
                .collect(Collectors.toList());

        // ✅ Orders by status
        Map<String, Long> ordersByStatus = hoaDonRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        h -> h.getTrangThai() == null ? "Chưa cập nhật" : h.getTrangThai(),
                        Collectors.counting()
                ));

        // ✅ Top 5 best-selling products
        Map<String, Long> topProducts = new LinkedHashMap<>();
        try {
            Map<String, Long> counts = new HashMap<>();
            hoaDonRepository.findAll().forEach(h -> {
                if (h.getChiTiet() != null) {
                    h.getChiTiet().forEach(detail -> {
                        if (detail.getSanPham() != null) {
                            String name = detail.getSanPham().getTenSP();
                            Integer qty = detail.getSoLuong() == null ? 1 : detail.getSoLuong();
                            counts.put(name, counts.getOrDefault(name, 0L) + qty);
                        }
                    });
                }
            });
            counts.entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .limit(5)
                    .forEach(e -> topProducts.put(e.getKey(), e.getValue()));
        } catch (Exception ignored) {
            sanPhamRepository.findAll().stream()
                    .sorted(Comparator.comparing(p -> Optional.ofNullable(p.getMaSP()).orElse("0"), Comparator.reverseOrder()))
                    .limit(5)
                    .forEach(p -> topProducts.put(p.getTenSP(), 0L));
        }

        model.addAttribute("customerCount", customerCount);
        model.addAttribute("productCount", productCount);
        model.addAttribute("orderCount", orderCount);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("recentOrders", recentOrders);
        model.addAttribute("recentCustomers", recentCustomers);
        model.addAttribute("ordersByStatus", ordersByStatus);
        model.addAttribute("topProducts", topProducts);

        return "admin-dashboard";
    }
}
