## Phân tích

### 1. Phân quyền dựa trên URL (URL-based Authorization trong SecurityConfig)
- Ngữ cảnh áp dụng: Áp dụng cho các cụm API có chung tiền tố đường dẫn mang tính chất phân vùng tài nguyên cố định (ví dụ: /api/admin/, /api/moderator/).
- Lý do lựa chọn: Đây là lớp tường thành đầu tiên (Tầng thô). Nó chặn đứng các request không hợp lệ ngay từ vòng gửi xe (tại tầng lọc SecurityFilterChain) trước khi request đó kịp chạm vào các Controller hay sinh ra các câu lệnh truy vấn Logic. Điều này giúp tối ưu hiệu năng hệ thống và bảo vệ diện rộng các API quản trị nhạy cảm.
### 2. Phân quyền cấp phương thức (Method-based Authorization: @PreAuthorize, @Secured)
- Ngữ cảnh áp dụng: Áp dụng trực tiếp tại các hàm xử lý trong Controller hoặc tầng Service, nơi logic phân quyền bị phụ thuộc vào dữ liệu động hoặc các điều kiện nghiệp vụ phức tạp.
- Lý do lựa chọn: Đây là lớp bảo mật chuyên sâu (Tầng mịn). URL-based không thể biết được ai là chủ sở hữu của món hàng, hay người chơi đó có đủ tiền mua vật phẩm hay không.
    - Ta dùng @Secured cho các tác vụ kiểm tra vai trò đơn giản mang tính chất cục bộ (ví dụ: @Secured("ROLE_PLAYER") cho việc gửi bình luận).
    - Ta dùng @PreAuthorize tích hợp SpEL cho các logic động (ví dụ: Người chơi chỉ được xem lịch sử mua vật phẩm của chính mình dựa trên userId truyền vào URL).
      
  => **Kết luận**: Sự kết hợp này mang lại tính mở rộng (Scalability) cao. Khi thêm Game mới, cấu hình URL tĩnh không bị lung lay; khi thay đổi logic mua sắm, ta chỉ cần chỉnh sửa biểu thức SpEL tại đúng phương thức đó mà không sợ ảnh hưởng đến toàn bộ hệ thống.
  