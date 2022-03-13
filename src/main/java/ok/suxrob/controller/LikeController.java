package ok.suxrob.controller;

import ok.suxrob.dto.LikeAndDislikeDTO;
import ok.suxrob.dto.LikeDTO;
import ok.suxrob.dto.ProfileJwtDTO;
import ok.suxrob.enums.LikeType;
import ok.suxrob.service.LikeService;
import ok.suxrob.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/like")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody LikeDTO dto, HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request);
        LikeDTO response = likeService.create(dto, jwtDTO.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/articleId/{id}")
    public ResponseEntity<?> articleLikeAndDislikeCount(@PathVariable("id") Integer articleId,
                                                        @RequestParam("type") LikeType type) {
        LikeAndDislikeDTO count = likeService.articleLikeAndDislikeCount(articleId, type);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/commentId/{id}")
    public ResponseEntity<?> commentLikeAndDislikeCount(@PathVariable("id") Integer commentId,
                                                        @RequestParam("type") LikeType type) {
        LikeAndDislikeDTO count = likeService.commentLikeAndDislikeCount(commentId, type);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/action/likedArticle/{id}")
    public ResponseEntity<?> likedArticle(@PathVariable("id") Integer profileId, HttpServletRequest request) {
        JwtUtil.getProfile(request);
        List<String> articleLikedList = likeService.likedArticle(profileId);
        return ResponseEntity.ok(articleLikedList);
    }

    @PutMapping("/action/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody LikeDTO dto, HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request);
        boolean result = likeService.update(jwtDTO.getId(), id, dto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/action/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id, HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request);
        boolean result = likeService.delete(jwtDTO.getId(), id);
        return ResponseEntity.ok(result);
    }
}
