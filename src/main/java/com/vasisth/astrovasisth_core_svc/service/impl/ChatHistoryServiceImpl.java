package com.vasisth.astrovasisth_core_svc.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vasisth.astrovasisth_core_svc.constants.Role;
import com.vasisth.astrovasisth_core_svc.dto.ChatHistoryReqRes;
import com.vasisth.astrovasisth_core_svc.entity.ChatHistory;
import com.vasisth.astrovasisth_core_svc.entity.Colleague;
import com.vasisth.astrovasisth_core_svc.entity.Customer;
import com.vasisth.astrovasisth_core_svc.entity.Message;
import com.vasisth.astrovasisth_core_svc.exception.CustomException;
import com.vasisth.astrovasisth_core_svc.repo.ChatHistoryRepository;
import com.vasisth.astrovasisth_core_svc.repo.ColleagueRepository;
import com.vasisth.astrovasisth_core_svc.repo.CustomerRepository;
import com.vasisth.astrovasisth_core_svc.service.ChatHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class ChatHistoryServiceImpl implements ChatHistoryService {

    private final ColleagueRepository colleagueRepository;
    private final CustomerRepository customerRepository;
    private final ChatHistoryRepository chatHistoryRepository;
    ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public List<ChatHistoryReqRes> getChaHistoryByUserId(String userId) {
        List<ChatHistory> chatHistory = chatHistoryRepository.findByCustomerId(UUID.fromString(userId));
        if(chatHistory.isEmpty()) {
            throw new CustomException("No chat history found for customer id: " + userId);
        }

        return getDetailsFromChatHistoryList(chatHistory);
    }

    @Override
    public ChatHistoryReqRes getChatsById(String customerId, String colleagueId) throws JsonProcessingException {
        Optional<ChatHistory> chatHistoryOptional = chatHistoryRepository.findByCustomerIdAndColleagueId(UUID.fromString(customerId), UUID.fromString(colleagueId));
        if(chatHistoryOptional.isPresent()) {
            ChatHistory chatHistory = chatHistoryOptional.get();
            ChatHistoryReqRes response = new ChatHistoryReqRes();
            return getDetailsFromChatHistory(chatHistory);
        }else{
            ChatHistory chatHistory = new ChatHistory();
            chatHistory.setCustomerId(UUID.fromString(customerId));
            chatHistory.setColleagueId(UUID.fromString(colleagueId));
            List<Message> message = new ArrayList<>();
            String messageJsonString = objectMapper.writeValueAsString(message);
            chatHistory.setMessage(messageJsonString);
            chatHistoryRepository.save(chatHistory);
            return getDetailsFromChatHistory(chatHistory);
        }
    }

    @Override
    public ChatHistoryReqRes saveChatHistoryByCustomer(ChatHistoryReqRes chatHistoryReqRes, Role role) throws JsonProcessingException {
        chatHistoryRepository.findByCustomerIdAndColleagueId(UUID.fromString(chatHistoryReqRes.getCustomerId()), UUID.fromString(chatHistoryReqRes.getColleagueId())).ifPresent(chatHistory -> {;
            try {
                List<Message> messages = objectMapper.readValue(chatHistory.getMessage(),ArrayList.class);
                Message newMessage = new Message();
                newMessage.setRead(false);
                newMessage.setTimestamp(LocalDateTime.now().toString());
                newMessage.setSentBy(role.name());
                newMessage.setMessage(chatHistoryReqRes.getMessage());
                messages.add(newMessage);
                chatHistory.setMessage(objectMapper.writeValueAsString(messages));
                chatHistoryRepository.save(chatHistory);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        return null;
    }

    private ChatHistoryReqRes getDetailsFromChatHistory(ChatHistory chatHistory) {
        ChatHistoryReqRes response = new ChatHistoryReqRes();
        Optional<Customer> customerOptional = customerRepository.findById(chatHistory.getCustomerId());
        Optional<Colleague> colleagueOptional = colleagueRepository.findById(chatHistory.getColleagueId());
        customerOptional.ifPresent(customer -> response.setCustomerName(customer.getFirstName() + " " + customer.getLastName()));
        colleagueOptional.ifPresent(colleague -> response.setColleagueName(colleague.getFirstName() + " " + colleague.getLastName()));
        response.setId(chatHistory.getId());
        response.setColleagueId(String.valueOf(chatHistory.getColleagueId()));
        response.setCustomerId(String.valueOf(chatHistory.getCustomerId()));
        response.setMessage(chatHistory.getMessage());
        return response;
    }

    private List<ChatHistoryReqRes> getDetailsFromChatHistoryList(List<ChatHistory> chatHistories) {
        List<ChatHistoryReqRes> responseList = new ArrayList<>();
        List<UUID> customerIds = chatHistories.stream().map(ChatHistory:: getCustomerId).toList();
        List<UUID> colleagueIds = chatHistories.stream().map(ChatHistory:: getColleagueId).toList();
        List<Customer> customers = customerRepository.findByIdInAndIsDeletedFalse(customerIds);
        List<Colleague> colleagues = colleagueRepository.findByIdInAndIsActiveTrue(colleagueIds);
        chatHistories.forEach(history -> {
            ChatHistoryReqRes response = new ChatHistoryReqRes();
            response.setId(history.getId());
            customers.stream()
                    .filter(cust -> cust.getId().equals(history.getCustomerId()))
                    .findFirst()
                    .ifPresent(cust -> {
                        response.setCustomerName(cust.getFirstName() + " " + cust.getLastName());
                        response.setCustomerId(String.valueOf(cust.getId()));
                    });
            colleagues.stream()
                    .filter(col -> col.getId().equals(history.getColleagueId()))
                    .findFirst()
                    .ifPresent(col -> {
                        response.setColleagueName(col.getFirstName() + " " + col.getLastName());
                        response.setColleagueId(String.valueOf(col.getId()));
                        response.setColleagueAvatar("assets/icon.png");
                    });
            try {
                List<Message> messages = objectMapper.readValue(
                        history.getMessage(),
                        new com.fasterxml.jackson.core.type.TypeReference<List<Message>>() {}
                );
                Optional<Message> lastMessageOpt = messages.stream().reduce((first, second) -> second);
                lastMessageOpt.ifPresent(lastMessage -> {
                    response.setMessage(lastMessage.getMessage());
                    response.setLastTimeStamp(lastMessage.getTimestamp());
                });
                long unreadCount = messages.stream().filter(msg -> !msg.isRead() && msg.getSentBy().equals(Role.USER.name())).count();
                response.setUnreadMessages((int) unreadCount);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            responseList.add(response);
        });
        return responseList;
    }
}
