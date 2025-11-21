package org.example.nhom5_assignment_java5.controller;

import org.example.nhom5_assignment_java5.entity.*;
import org.example.nhom5_assignment_java5.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @GetMapping("/admin")
    public String dashboard(Model model) {

        long customerCount = khachHangRepository.count();

        long productCount = sanPhamRepository.count();

        long orderCount = hoaDonRepository.count();

        BigDecimal totalRevenue = hoaDonRepository.findAll().stream()
                .map(HoaDon::getTongTien)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        List<HoaDon> recentOrders = hoaDonRepository.findAll().stream()
                .sorted(Comparator.comparing(
                                (HoaDon h) -> Optional.ofNullable(h.getNgayTaoHD()).orElse(new Date(0)),
                                Comparator.nullsLast(Comparator.naturalOrder()))
                        .reversed()
                        .thenComparing((HoaDon h) -> Optional.ofNullable(h.getMaHD()).orElse(0), Comparator.reverseOrder()))
                .limit(10)
                .collect(Collectors.toList());
        List<KhachHang> recentCustomers = khachHangRepository.findAll().stream()
                .sorted(Comparator.comparing((KhachHang k) -> Optional.ofNullable(k.getMaKH()).orElse(0)).reversed())
                .limit(10)
                .collect(Collectors.toList());

        // ✅ 7. Đơn hàng chờ duyệt
        List<HoaDon> pendingOrders = hoaDonRepository.findAll().stream()
                .filter(h -> h.getTrangThai() != null && h.getTrangThai().toLowerCase().contains("chờ"))
                .limit(15)
                .collect(Collectors.toList());

        // ✅ 8. Danh sách sản phẩm trong kho
        List<SanPham> productList = sanPhamRepository.findAll().stream()
                .sorted(Comparator.comparing(SanPham::getMaSP))
                .collect(Collectors.toList());

        // ✅ 9. Nhân viên
        List<NhanVien> staffList = nhanVienRepository.findAll().stream()
                .sorted(Comparator.comparing(NhanVien::getMaNV))
                .collect(Collectors.toList());

        // ✅ 10. Thống kê đơn hàng theo trạng thái
        Map<String, Long> ordersByStatus = hoaDonRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        h -> h.getTrangThai() == null ? "Chưa cập nhật" : h.getTrangThai(),
                        LinkedHashMap::new,
                        Collectors.counting()
                ));

        // ✅ 11. Top sản phẩm bán chạy
        Map<String, Long> topProducts = new LinkedHashMap<>();
        try {
            Map<String, Long> counts = new HashMap<>();
            hoaDonRepository.findAll().forEach(h -> {
                if (h.getChiTiet() != null) {
                    h.getChiTiet().forEach(ct -> {
                        if (ct.getSanPham() != null) {
                            String name = ct.getSanPham().getTenSP();
                            Integer qty = ct.getSoLuong() == null ? 1 : ct.getSoLuong();
                            counts.put(name, counts.getOrDefault(name, 0L) + qty);
                        }
                    });
                }
            });

            counts.entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .limit(5)
                    .forEachOrdered(e -> topProducts.put(e.getKey(), e.getValue()));
        } catch (Exception ex) {
            // fallback nếu thiếu dữ liệu
            sanPhamRepository.findAll().stream()
                    .limit(5)
                    .forEach(p -> topProducts.put(p.getTenSP(), 0L));
        }

        // ✅ 12. Đơn hàng sắp hết hàng hoặc lỗi trạng thái
        long lowStockCount = productList.stream().filter(p -> p.getSoLuongTonKho() != null && p.getSoLuongTonKho() <= 5).count();
        long pendingCount = pendingOrders.size();
        long staffOnline = Math.max(1, staffList.size() - 1); // giả định có 1-2 người nghỉ

        // =================== //
        // 🟢 Đưa dữ liệu ra view
        // =================== //
        model.addAttribute("customerCount", customerCount);
        model.addAttribute("productCount", productCount);
        model.addAttribute("orderCount", orderCount);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("recentOrders", recentOrders);
        model.addAttribute("recentCustomers", recentCustomers);
        model.addAttribute("ordersByStatus", ordersByStatus);
        model.addAttribute("topProducts", topProducts);
        model.addAttribute("productList", productList);
        model.addAttribute("staffList", staffList);
        model.addAttribute("pendingOrders", pendingOrders);
        model.addAttribute("lowStockCount", lowStockCount);
        model.addAttribute("pendingCount", pendingCount);
        model.addAttribute("staffOnline", staffOnline);

        // ✅ format date for frontend if needed
        model.addAttribute("dateFormat", new SimpleDateFormat("dd/MM/yyyy HH:mm"));

        return "admin-dashboard";
    }

}
