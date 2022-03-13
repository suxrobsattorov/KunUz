package ok.suxrob.controller;

import ok.suxrob.dto.EmailHistoryDTO;
import ok.suxrob.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/emailHistory")
public class EmailHistoryController {

    @Autowired
    private EmailService emailService;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        PageImpl<EmailHistoryDTO> pageImpl = emailService.getAll(page, size);
        return ResponseEntity.ok(pageImpl);
    }

    @GetMapping("/today")
    public ResponseEntity<?> getToday() {
        List<EmailHistoryDTO> dtoList = emailService.getToday();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/last")
    public ResponseEntity<?> getLast() {
        EmailHistoryDTO dto = emailService.getLast();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/notUsed")
    public ResponseEntity<?> getNotUsedEmail(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        PageImpl<EmailHistoryDTO> pageImpl = emailService.getNotUsedEmail(page, size);
        return ResponseEntity.ok(pageImpl);
    }
}
