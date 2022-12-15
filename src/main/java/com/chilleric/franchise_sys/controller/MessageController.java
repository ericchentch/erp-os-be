package com.chilleric.franchise_sys.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.dto.common.CommonResponse;
import com.chilleric.franchise_sys.dto.common.ListWrapperResponse;
import com.chilleric.franchise_sys.dto.common.ValidationResult;
import com.chilleric.franchise_sys.dto.message.ChatRoom;
import com.chilleric.franchise_sys.dto.message.MessageRequest;
import com.chilleric.franchise_sys.dto.message.MessageResponse;
import com.chilleric.franchise_sys.dto.message.OnlineUserResponse;
import com.chilleric.franchise_sys.service.message.MessageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/message")
public class MessageController extends AbstractController<MessageService> {

  @SecurityRequirement(name = "Bearer Authentication")
  @PostMapping("/to-chat-room")
  public ResponseEntity<CommonResponse<String>> toChatRoom(HttpServletRequest request) {
    ValidationResult result = validateToken(request);
    service.addOnlineUser(result.getLoginId());
    return new ResponseEntity<CommonResponse<String>>(
        new CommonResponse<String>(true, null, LanguageMessageKey.CONNECTED_TO_CHAT,
            HttpStatus.OK.value(), new ArrayList<>(), new ArrayList<>()),
        null, HttpStatus.OK.value());
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @PostMapping("/out-chat-room")
  public ResponseEntity<CommonResponse<String>> outChatRoom(HttpServletRequest request) {
    ValidationResult result = validateToken(request);
    service.removeOnlineUser(result.getLoginId());
    return new ResponseEntity<CommonResponse<String>>(
        new CommonResponse<String>(true, null, LanguageMessageKey.DISCONNECT_CHAT,
            HttpStatus.OK.value(), new ArrayList<>(), new ArrayList<>()),
        null, HttpStatus.OK.value());
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @PostMapping("/send-message")
  public ResponseEntity<CommonResponse<MessageResponse>> sendMessages(
      @RequestBody MessageRequest messageRequest, @RequestParam("id") String receiveId,
      HttpServletRequest request) {
    ValidationResult result = validateToken(request);
    return response(service.sendMessage(messageRequest, result.getLoginId(), receiveId),
        LanguageMessageKey.SUCCESS, new ArrayList<>(), new ArrayList<>());

  }

  @GetMapping("/online-users")
  public Flux<ServerSentEvent<List<OnlineUserResponse>>> streamUsers(@RequestParam String token) {
    ValidationResult result = validateSSE(token);
    return service.getOnlineUsers(result.getLoginId());
  }

  @GetMapping("/get-last-messages")
  public Flux<ServerSentEvent<MessageResponse>> streamLastMessage(@RequestParam String token) {
    ValidationResult result = validateSSE(token);
    return service.getLastUserMessage(result.getLoginId());
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping("/get-old-messages")
  public ResponseEntity<CommonResponse<ListWrapperResponse<MessageResponse>>> getOldSendMessage(
      @RequestParam(defaultValue = "1") int page, @RequestParam("id") String sendId,
      HttpServletRequest request) {
    ValidationResult result = validateToken(request);
    return response(service.getOldMessage(result.getLoginId(), sendId, page),
        LanguageMessageKey.SUCCESS, new ArrayList<>(), new ArrayList<>());
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping(value = "/get-chat-room")
  public ResponseEntity<CommonResponse<List<ChatRoom>>> getChatRoom(
      @RequestParam(defaultValue = "1") int page, HttpServletRequest request) {
    ValidationResult result = validateToken(request);
    return response(service.getChatroom(result.getLoginId(), page), LanguageMessageKey.SUCCESS,
        new ArrayList<>(), new ArrayList<>());
  }

}
