package com.example.boardApi.web;

import com.example.boardApi.domain.Reply;
import com.example.boardApi.dto.ReplyDto;
import com.example.boardApi.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReplyApi {

    private final ReplyService replyService;


    @GetMapping("/reply/list")
    public List<ReplyDto> list() {
        return replyService.findAll();
    }
    @PostMapping("/reply/save")
    public HttpStatus save(@RequestBody ReplyDto replyDto) {
        replyService.save(replyDto);
        return HttpStatus.OK;
    }

}