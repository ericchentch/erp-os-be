package com.chilleric.franchise_sys.service.message;

import java.util.List;
import java.util.Optional;
import org.springframework.http.codec.ServerSentEvent;
import com.chilleric.franchise_sys.dto.common.ListWrapperResponse;
import com.chilleric.franchise_sys.dto.message.ChatRoom;
import com.chilleric.franchise_sys.dto.message.MessageRequest;
import com.chilleric.franchise_sys.dto.message.MessageResponse;
import com.chilleric.franchise_sys.dto.message.OnlineUserResponse;
import reactor.core.publisher.Flux;

public interface MessageService {

  Optional<ListWrapperResponse<MessageResponse>> getOldMessage(String userId, String sendId,
      int page);

  Optional<List<ChatRoom>> getChatroom(String loginId, int page);

  Optional<MessageResponse> sendMessage(MessageRequest messageRequest, String loginId,
      String receiveId);

  void addOnlineUser(String userId);

  void removeOnlineUser(String userId);

  Flux<ServerSentEvent<List<OnlineUserResponse>>> getOnlineUsers(String userId);

  Flux<ServerSentEvent<MessageResponse>> getLastUserMessage(String userId);
}
