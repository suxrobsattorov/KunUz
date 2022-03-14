package ok.suxrob.controller;

<<<<<<< HEAD
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
=======
>>>>>>> edf6660ae0d96ffba560949c1fa4ba04cff25f23
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
<<<<<<< HEAD
@Api(tags = "Email History")
=======
>>>>>>> edf6660ae0d96ffba560949c1fa4ba04cff25f23
public class EmailHistoryController {

    @Autowired
    private EmailService emailService;

    @GetMapping
<<<<<<< HEAD
    @ApiOperation(value = "email history getAll method", notes = "email history")
=======
>>>>>>> edf6660ae0d96ffba560949c1fa4ba04cff25f23
    public ResponseEntity<?> getAll(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        PageImpl<EmailHistoryDTO> pageImpl = emailService.getAll(page, size);
        return ResponseEntity.ok(pageImpl);
    }

    @GetMapping("/today")
<<<<<<< HEAD
    @ApiOperation(value = "email history getToday method", notes = "email history")
=======
>>>>>>> edf6660ae0d96ffba560949c1fa4ba04cff25f23
    public ResponseEntity<?> getToday() {
        List<EmailHistoryDTO> dtoList = emailService.getToday();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/last")
<<<<<<< HEAD
    @ApiOperation(value = "email history getLast method", notes = "email history")
=======
>>>>>>> edf6660ae0d96ffba560949c1fa4ba04cff25f23
    public ResponseEntity<?> getLast() {
        EmailHistoryDTO dto = emailService.getLast();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/notUsed")
<<<<<<< HEAD
    @ApiOperation(value = "email history getNotUsedEmail method", notes = "email history")
=======
>>>>>>> edf6660ae0d96ffba560949c1fa4ba04cff25f23
    public ResponseEntity<?> getNotUsedEmail(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        PageImpl<EmailHistoryDTO> pageImpl = emailService.getNotUsedEmail(page, size);
        return ResponseEntity.ok(pageImpl);
    }
}
