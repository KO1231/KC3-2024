package com.example;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.guild.MemberJoinEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;

public class Sample {
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
			
			if(channel == null){
				System.out.println("メッセージの送信元チャンネルが取得できませんでした。");
			}else{
				if ("!ping".equals(messageContent)) {
					channel.createMessage("Pong!").block();
				}
			}
		});
		
		gateway.on(MessageCreateEvent.class).subscribe(event -> {
			/* メッセージ投稿イベント **/
			// メッセージを取得
			Message message = event.getMessage();
			MessageChannel channel = message.getChannel().block();
			String messageContent = message.getContent();
			
			if(channel == null){
				System.out.println("メッセージの送信元チャンネルが取得できませんでした。");
			}else{
				if(messageContent.startsWith("!guess")){
					String[] content = messageContent.split(" ");
					if(content.length == 2){
						try{
							int answer = Integer.parseInt(content[1]);
							int guess = (int)(Math.random() * 10);
							if(answer == guess){
								channel.createMessage("正解！").block();
							}else{
								channel.createMessage("不正解！ 正解は" + guess + "です。").block();
							}
						}catch(NumberFormatException e){
							channel.createMessage("数値を入力してください。").block();
						}
					}else{
						channel.createMessage("引数が不正です。").block();
					}
				}
			}
		});
		
		gateway.on(MemberJoinEvent.class).subscribe(event -> {
			/* メンバー参加イベント **/
			// メンバーを取得
			Member member = event.getMember();
			MessageChannel channel = member.getPrivateChannel().block();
			if (channel == null) {
				System.out.println("DMチャンネルが取得できませんでした。");
			} else {
				channel.createMessage("ようこそ！").block();
			}
		});
		
		gateway.onDisconnect().block();
	}
}