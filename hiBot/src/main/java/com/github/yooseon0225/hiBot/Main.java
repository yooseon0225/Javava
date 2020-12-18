package com.github.yooseon0225.hiBot;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

class SettingEmbed {
	EmbedBuilder embed = new EmbedBuilder();

	SettingEmbed(MessageCreateEvent event) {
		this.embed.setColor(Color.pink)
		.setFooter(event.getMessageAuthor().getName(), event.getMessageAuthor().getAvatar())
		.setTimestamp(new Date().toInstant());
	}
}

class BringMeal {
	final static String URL = "https://open.neis.go.kr/hub/mealServiceDietInfo?" + "Type=json"
			+ "&KEY=8fa2439e557c491aab0609512626e704&pIndex=1&pSize=100" + "&ATPT_OFCDC_SC_CODE=F10"
			+ "&SD_SCHUL_CODE=7380292" + "&MLSV_YMD=20201217";
	String mealList;
	EmbedBuilder embed;

	BringMeal(MessageCreateEvent event){
 		parseText();
 		JSONParser p = new JSONParser();
 		JSONObject obj = null;
 		int mealCount = 0;
 		
 		try {
            mealCount = Integer.parseInt((
                    (JSONObject)(
                            (JSONArray)(
                                    (JSONObject)(
                                            (JSONArray)obj.get("mealServiceDietInfo")
                                            ).get(0)
                                    ).get("head")
                            ).get(0)
                    ).get("list_total_count").toString());
        } catch(java.lang.NullPointerException e) {
            System.out.println("���±޽�");
        }
 		
 		if(mealCount != 0) {
 			JSONArray info = (JSONArray)(
 					(JSONObject)(
 							(JSONArray)obj.get("mealSerciceDietInfo")
 							).get(1)
 					).get("row");
 			for(int i = 0; i < mealCount; i++) {
 				JSONObject mealType = (JSONObject)info.get(i); 
 				
 				String fieldTitle = mealType.get("MMEAL_SC_NM").toString();
 				String fieldDesc = mealType.get("DDISH_NM").toString();
 				fieldDesc = fieldDesc.replaceAll("[*0~9./]", "");
 				fieldDesc = fieldDesc.replaceAll("", "");
 				EmbedBuilder em = new SettingEmbed(event).embed;
 				embed.addInlineField(fieldTitle, fieldDesc);
 				embed = em;
 			}
 					
 		}

 		
 	}

	public void parseText() {
		Document doc = null;
		try {
			doc = Jsoup.connect(this.URL).userAgent("Mozilla").get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.mealList = doc.text();
	}
}

class Token {
	static String getToken(String path) {
		String prv = null;

		try {

			BufferedReader br = new BufferedReader(new FileReader(path));
			prv = br.readLine();

			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prv;

	}

}

public class Main {
	public static void main(String[] args) {
		String path = "./token.txt";
		String token = Token.getToken(path);

		DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

		api.addMessageCreateListener(event -> {
			if (event.getMessage().getAuthor().isBotUser())
				return;

			EmbedBuilder embed = new EmbedBuilder();
			embed.setColor(Color.yellow);
			embed.setFooter(event.getMessageAuthor().getName(), event.getMessageAuthor().getAvatar());
			embed.setTimestamp(new Date().toInstant());
			String prefix = "���־�";

			if (event.getMessageContent().startsWith(prefix)) {

				if (event.getMessageContent().startsWith("������ ����"))
					if (event.getMessageContent().contains("�̻ڴ�. �̻�~")) {
						embed.addField("������", "����");
					} else if (event.getMessage().getContent().contains("�޽� �εյ���")) {
						embed = new BringMeal(event).embed;
						
					} else if (event.getMessageContent().equalsIgnoreCase("�� �̻�??"))
						event.getChannel().sendMessage("��¥ �ʹ��ʹ� �̻� ������ !");
					else if (event.getMessageContent().equalsIgnoreCase("���־� �ٹ� �"))
						event.getChannel().sendMessage("�ٹ� �ǤǤǤǤ�");

				event.getChannel().sendMessage(embed);
			}
		});
	}
}
