package com.github.yooseon0225.hiBot;

import java.awt.Color;
import java.io.IOException;
import java.util.Date;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.Icon;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

class SettingEmbed{
	private EmbedBuilder embed = new EmbedBuilder();
	SettingEmbed(String userName, Icon userIcon){
		this.embed.setColor(Color.pink)
		.setFooter(userName, userIcon)
		.setTimestamp(new Date().toInstant());
	}
}

class BringMeal{
	final static String URL = "https://open.neis.go.kr/hub/mealServiceDietInfo?" 
		 + "Type=json"
		 + "&KEY=8fa2439e557c491aab0609512626e704&pIndex=1&pSize=100"
		 + "&ATPT_OFCDC_SC_CODE=F10"
		 + "&MLSV_YMD=20201217";
 	BringMeal(){
 		String doctext = parseText(URL);
 		System.out.println(doctext);
 		}
 	public static String parseText(String url) {
 		Document doc = null;
 		try {
 			doc = Jsoup.connect(url)
 					.userAgent("Mozilla")
 					.get();
 		} catch (IOException e) {
 			e.printStackTrace();
 		}
 		return doc.text();
 	}
}

public class Main {
	public static void main(String[] args) {
		String token = "Nzg4OTQ5ODA2NjA1MjcxMDgw.X9q8ww.Fsk9MU9o6C_vWzmuWQ_YxjCJ72o";
		BringMeal test = new BringMeal();
		
		DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

		
		api.addMessageCreateListener(event -> {
			if ( event.getMessage().getAuthor().isBotUser() ) 
				return;
			
			EmbedBuilder embed = new EmbedBuilder();
			embed.setTitle("yuseon is gong-ju");
			embed.setDescription("gong-ju is yuseon!!");
			embed.setColor(Color.yellow);
			embed.setFooter(event.getMessageAuthor().getName(),event.getMessageAuthor().getAvatar());
			embed.addField("공주필드", "유선이는 공주다@!!!");
			embed.setTimestamp(new Date().toInstant());
			
			if(event.getMessageContent().equalsIgnoreCase("공주야"))
				event.getChannel().sendMessage(embed);
			else if(event.getMessageContent().equalsIgnoreCase("서유선 공주"))
				event.getChannel().sendMessage("유선이는 진짜 공주 !!!야");
			else if(event.getMessageContent().equalsIgnoreCase("나 이뻐??"))
				event.getChannel().sendMessage("진짜 너무너무 이뻐 유선아 !");
			else if(event.getMessageContent().equalsIgnoreCase("공주야 다미 어때"))
				event.getChannel().sendMessage("다미 ㅗㅗㅗㅗㅗ");
			
		});
	}
}
