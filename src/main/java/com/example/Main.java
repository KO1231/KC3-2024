package com.example;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.guild.MemberJoinEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;

public class Main {
	public static void main(String[] args) {
		String token = "TOKENをここに入力してください。";
		DiscordClient client = DiscordClient.create(token);
		GatewayDiscordClient gateway = client.login().block();
		
		if (gateway == null) {
			System.out.println("ログインに失敗しました。");
			return;
		}
		
		/* ------- ログイン完了 ------- **/
		
		gateway.on(MessageCreateEvent.class).subscribe(event -> {
			/* メッセージ投稿イベント **/
			// メッセージを取得
			Message message = event.getMessage();
			MessageChannel channel = message.getChannel().block();
			String messageContent = message.getContent();
			
		});
		
		gateway.on(MemberJoinEvent.class).subscribe(event -> {
			/* メンバー参加イベント **/
			// メンバーを取得
			Member member = event.getMember();
			
		});
		
		gateway.onDisconnect().block();
	}
}