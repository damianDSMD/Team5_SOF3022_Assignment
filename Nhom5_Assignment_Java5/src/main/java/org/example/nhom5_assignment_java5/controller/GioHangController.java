package org.example.nhom5_assignment_java5.controller;

import org.example.nhom5_assignment_java5.entity.GioHang;
import org.example.nhom5_assignment_java5.entity.KhachHang;
import org.example.nhom5_assignment_java5.service.GioHangService;
import org.example.nhom5_assignment_java5.service.KhachHangService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class GioHangController {
    private final GioHangService gioHangService;
    private final KhachHangService khachHangService;

    public GioHangController(GioHangService gioHangService, KhachHangService khachHangService) {
        this.gioHangService = gioHangService;
        this.khachHangService = khachHangService;
    }

    // Hiển thị trang giỏ hàng
    @GetMapping
    public String viewCart(Model model) {
        KhachHang kh = khachHangService.getCurrentKhachHang();
        if (kh == null) {
            return "redirect:/login";
        }

        List<GioHang> gioHangList = gioHangService.getGioHangByKhachHang(kh);
        model.addAttribute("gioHangList", gioHangList);
        model.addAttribute("tongTien", gioHangService.getTongTien(kh));

        return "cart";
    }

    // ✅ API thêm sản phẩm vào giỏ (AJAX)
    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addToCart(
            @RequestParam String maSP,
            @RequestParam(defaultValue = "1") Integer soLuong
    ) {
        KhachHang kh = khachHangService.getCurrentKhachHang();

        // ✅ Kiểm tra đăng nhập trước khi thêm giỏ hàng
        if (kh == null) {
            return ResponseEntity.status(401).body(Map.of(
                    "success", false,
                    "message", "Vui lòng đăng nhập để sử dụng giỏ hàng!"
            ));
        }

        // ✅ Chỉ gọi service khi KH không null
        gioHangService.addToCart(kh, maSP, soLuong);

        int total = gioHangService.getTotalItems(kh); // tổng số sản phẩm trong giỏ

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Đã thêm vào giỏ hàng!");
        response.put("totalItems", total);

        return ResponseEntity.ok(response);
    }
    // cập nhật số lượng giỏ hàng
    @PostMapping("/update")
    public String updateSoLuong(@RequestParam("maGH") Integer maGH,
                                @RequestParam("soLuong") Integer soLuong) {
        gioHangService.updateSoLuong(maGH, soLuong);
        return "redirect:/cart";
    }


    // ✅ Xóa 1 sản phẩm rồi load lại trang giỏ hàng
    @GetMapping("/remove/{id}")
    public String removeItem(@PathVariable("id") Integer id) {
        gioHangService.removeItem(id);
        return "redirect:/cart"; // đổi lại đường dẫn redirect cho khớp
    }



    // ✅ Xóa toàn bộ giỏ hàng rồi load lại trang giỏ hàng
    @GetMapping("/clear")
    public String clearCart() {
        KhachHang kh = khachHangService.getCurrentKhachHang();
        if (kh != null) {
            gioHangService.clearCart(kh);
        }
        return "redirect:/cart";
    }
}
