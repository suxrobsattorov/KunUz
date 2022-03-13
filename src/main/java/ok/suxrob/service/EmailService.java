package ok.suxrob.service;

import ok.suxrob.dto.EmailHistoryDTO;
import ok.suxrob.entity.EmailHistoryEntity;
import ok.suxrob.enums.EmailStatus;
import ok.suxrob.repository.EmailHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private EmailHistoryRepository emailHistoryRepository;
    private Integer emailHistoryId;

    public void sendEmail(String toAccount, String jwt) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Salom jigar qalaysan\n");
        stringBuilder.append("Agar bu sen bo'lsang shu linkga bos : ");
        stringBuilder.append("http://localhost:8080/auth/verification/" + jwt);

        String title = "Registration kunUz test";
        String text = stringBuilder.toString();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toAccount);
        message.setSubject(title);
        message.setText(text);
        javaMailSender.send(message);
        createEmailHistory(message);
    }

    public void createEmailHistory(SimpleMailMessage message) {
        EmailHistoryEntity entity = new EmailHistoryEntity();
        entity.setFromAccount(message.getFrom());
        entity.setToAccount(Arrays.toString(message.getTo()));
        entity.setStatus(EmailStatus.NOT_USED);
        emailHistoryRepository.save(entity);
        emailHistoryId = entity.getId();
    }

    public void updateEmailHistory() {
        EmailHistoryEntity entity = get(emailHistoryId);
        entity.setStatus(EmailStatus.USED);
        entity.setUsedAt(LocalDateTime.now());
        emailHistoryRepository.save(entity);
    }

    public PageImpl<EmailHistoryDTO> getAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<EmailHistoryEntity> pages = emailHistoryRepository.findAll(pageable);
        Long tatalElement = pages.getTotalElements();
        Integer p = pages.getTotalPages();
        List<EmailHistoryEntity> entityList = pages.getContent();
        List<EmailHistoryDTO> dtoList = entityList.stream().map(this::toDto).collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, tatalElement);
    }

    public List<EmailHistoryDTO> getToday() {
        LocalDateTime date1 = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        LocalDateTime date2 = LocalDateTime.now();
        List<EmailHistoryEntity> entityList = emailHistoryRepository.findAllByCreatedAtBetween(date1, date2);
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }

    public EmailHistoryDTO getLast() {
        return toDto(emailHistoryRepository.findFirstByOrderByCreatedAtDesc());
    }

    public PageImpl<EmailHistoryDTO> getNotUsedEmail(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<EmailHistoryEntity> pages = emailHistoryRepository.getAllByStatus(EmailStatus.NOT_USED, pageable);
        Long totalElement = pages.getTotalElements();
        List<EmailHistoryEntity> entityList = pages.getContent();
        List<EmailHistoryDTO> dtoList = entityList.stream().map(this::toDto).collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, totalElement);
    }

    public EmailHistoryDTO toDto(EmailHistoryEntity entity) {
        EmailHistoryDTO dto = new EmailHistoryDTO();
        dto.setId(entity.getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setFromAccount(entity.getFromAccount());
        dto.setToAccount(entity.getToAccount());
        dto.setStatus(entity.getStatus());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public EmailHistoryEntity get(Integer id) {
        return emailHistoryRepository.findById(id).orElseThrow(() -> new RuntimeException("EmailHistory not found"));
    }
}
