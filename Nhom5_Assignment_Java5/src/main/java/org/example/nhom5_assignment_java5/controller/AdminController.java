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

        // total revenue (sum of tongTien, ignore nulls)
        Double totalRevenue = hoaDonRepository.findAll().stream()
                .map(HoaDon::getTongTien)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .sum();

        // recent 5 orders (by ngayTao if present; otherwise by id desc)
        List<HoaDon> recentOrders = hoaDonRepository.findAll().stream()
                .sorted(Comparator.comparing((HoaDon h) -> Optional.ofNullable(h.getNgayTao()).orElse(null),
                                Comparator.nullsLast(Comparator.naturalOrder())).reversed()
                        .thenComparing((HoaDon h) -> Optional.ofNullable(h.getId()).orElse(Long.valueOf(0L)), Comparator.reverseOrder()))
                .limit(5)
                .collect(Collectors.toList());

        // recent 5 customers (by maKH desc)
        List<KhachHang> recentCustomers = khachHangRepository.findAll().stream()
                .sorted(Comparator.comparing((KhachHang k) -> Optional.ofNullable(k.getMaKH()).orElse(0)).reversed())
                .limit(5)
                .collect(Collectors.toList());

        // orders by status breakdown
        Map<String, Long> ordersByStatus = hoaDonRepository.findAll().stream()
                .collect(Collectors.groupingBy(h -> {
                    String s = h.getTrangThai();
                    return s == null ? "Chưa cập nhật" : s;
                }, Collectors.counting()));

        // top 5 best selling products by quantity in HoaDonChiTiet (fallback: by available product count if no details)
        // For safety we attempt to compute from HoaDonChiTiet if mapped; otherwise show top products by id desc.
        Map<String, Long> topProducts = new LinkedHashMap<>();
        try {
            // attempt: collect by SanPham name from HoaDonChiTiet via HoaDon -> chiTiet (if present)
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
            // fallback
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
