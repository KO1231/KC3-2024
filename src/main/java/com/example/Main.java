package com.example;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;

public class Main {
	public static void main(String[] args) {
		String token = "TOKENをここに入力してください。";
		DiscordClient client = DiscordClient.create(token);
		GatewayDiscordClient gateway = client.login().block();
		
		gateway.on(MessageCreateEvent.class).subscribe(event -> {
			Message message = event.getMessage();
			if ("!ping".equals(message.getContent())) {
				MessageChannel channel = message.getChannel().block();
				channel.createMessage("Pong!").block();
			}
		});
		
		gateway.onDisconnect().block();
	}
}