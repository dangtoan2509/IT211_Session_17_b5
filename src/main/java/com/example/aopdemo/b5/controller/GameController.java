package com.example.aopdemo.b5.controller;


import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/games")
public class GameController {

    /**
     * Chức năng 1: Người chơi thực hiện gửi bình luận đánh giá Game.
     * Sử dụng @Secured đơn giản: Yêu cầu vai trò bắt buộc là PLAYER.
     */
    @PostMapping("/{gameId}/comments")
    @Secured("ROLE_PLAYER")
    public String postComment(@PathVariable UUID gameId, @RequestBody String content) {
        return "Bình luận đã được gửi thành công cho game: " + gameId;
    }

    /**
     * Chức năng 2: Kiểm duyệt viên thực hiện xóa hoặc ẩn Game vi phạm.
     * Sử dụng @Secured đơn giản: Chỉ cho phép GAME_MODERATOR hoặc ADMIN thao tác.
     */
    @DeleteMapping("/{gameId}")
    @Secured({"ROLE_GAME_MODERATOR", "ROLE_ADMIN"})
    public String deleteGame(@PathVariable UUID gameId) {
        return "Trò chơi có ID " + gameId + " đã bị gỡ bỏ khỏi hệ thống Arcade.";
    }
}