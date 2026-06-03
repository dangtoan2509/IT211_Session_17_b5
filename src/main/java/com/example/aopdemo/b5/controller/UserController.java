package com.example.aopdemo.b5.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    /**
     * Chức năng 3: Xem lịch sử giao dịch mua vật phẩm ảo.
     * Sử dụng @PreAuthorize phức tạp:
     * - Chấp nhận ADMIN vào xem bất kỳ ai để làm thống kê.
     * - Nếu là PLAYER, tên đăng nhập (principal.username) phải trùng khớp với username trong Path thì mới được xem.
     */
    @GetMapping("/{username}/purchase-history")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('PLAYER') and principal.username == #username)")
    public String getPurchaseHistory(@PathVariable String username) {
        return "Trả về danh sách vật phẩm đã mua của tài khoản: " + username;
    }

    /**
     * Chức năng 4: Thực hiện mua vật phẩm ảo trong Game.
     * Giả định hệ thống có một Bean tên 'arcadeItemService' với hàm check số dư: checkBalance(itemId, username)
     * Sử dụng @PreAuthorize phức tạp:
     * - Người thực hiện phải có quyền PLAYER.
     * - Đồng thời, số dư tài khoản của họ phải đủ điều kiện để mua vật phẩm đó.
     */
    @PostMapping("/purchase-item")
    @PreAuthorize("hasRole('PLAYER') and @arcadeItemService.checkBalance(#itemId, principal.username)")
    public String purchaseVirtualItem(@RequestParam UUID itemId) {
        return "Giao dịch thành công! Vật phẩm " + itemId + " đã được thêm vào kho đồ của bạn.";
    }
}
