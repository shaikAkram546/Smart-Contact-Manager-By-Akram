package com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.helper;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    public String content;
    @Builder.Default
    public MessageType type = MessageType.blue;


}
