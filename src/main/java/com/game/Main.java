
package com.game;

import com.game.util.OperUtil;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {

    private static Map<Integer,String> gameMap = new HashMap<Integer, String>();


    public static void main(String[] args) throws Exception {

        int userId = 7507411;
        String up = "E2UOUEU2UZUEUKUMUMIDEZUOUMUMUMUMDMUMZZ";
        String token = OperUtil.login(userId, up);
        String[] a = new String[] {"1_2","1_3","2_3"};
        Random randon = new Random();
        int successCount = 0;
        int failCount = 0;
        while (true){
            JsonObject game = OperUtil.getGameStatus(userId,token);
            JsonObject gameInfo = game.get("gameInfo").getAsJsonObject();
            int gameStatus = gameInfo.get("gameStatus").getAsInt();
            int gameNo = gameInfo.get("gameNo").getAsInt();
            long endTime = gameInfo.get("endTime").getAsLong();

            if(gameStatus == 0 || gameStatus == 3){
                Thread.sleep(endTime - System.currentTimeMillis());
            }else if (gameStatus == 1){
                String des = a[randon.nextInt(3)];
                String[] s = des.split("_");
                OperUtil.playGame(gameNo, Integer.valueOf(s[0]),100,userId,token);
                OperUtil.playGame(gameNo, Integer.valueOf(s[1]),100,userId,token);
                gameMap.put(gameNo,des);
                Thread.sleep(endTime - System.currentTimeMillis());
                /*if((endTime - System.currentTimeMillis()) > 2000){
                    Thread.sleep(endTime - System.currentTimeMillis() - 2000);
                }else {
                    JsonObject betInfos = gameInfo.get("betInfos").getAsJsonObject();
                    if(betInfos.has("betInfoArry")){
                        JsonArray betInfoArry = betInfos.get("betInfoArry").getAsJsonArray();
                        int length = betInfoArry.size();
                        int totalBet1 = 0, totalBet2 = 0, totalBet3 = 0;
                        for(JsonElement ele : betInfoArry){
                            JsonObject obj = (JsonObject)ele;
                            int country = obj.get("country").getAsInt();
                            String totalBet = obj.get("totalBet").getAsString();
                            if(country == 1){
                                totalBet1 = Integer.valueOf(totalBet);
                            }else if (country == 2){
                                totalBet2 = Integer.valueOf(totalBet);
                            }else if (country == 3){
                                totalBet3 = Integer.valueOf(totalBet);
                            }
                        }
                        if(totalBet1 > totalBet2 + totalBet3){
                            OperUtil.playGame(gameNo,2,100,userId,token);
                            OperUtil.playGame(gameNo,3,100,userId,token);
                            Thread.sleep(endTime - System.currentTimeMillis());
                        }else if (totalBet2 > totalBet1 + totalBet3){
                            OperUtil.playGame(gameNo,1,100,userId,token);
                            OperUtil.playGame(gameNo,3,100,userId,token);
                            Thread.sleep(endTime - System.currentTimeMillis());
                        }else if (totalBet3 > totalBet1 + totalBet2){
                            OperUtil.playGame(gameNo,1,100,userId,token);
                            OperUtil.playGame(gameNo,2,100,userId,token);
                            Thread.sleep(endTime - System.currentTimeMillis());
                        }

                    }
                }*/

            }else if (gameStatus == 2){
                String bet = gameMap.get(gameNo);
                int result = gameInfo.get("gameResult").getAsInt();
                if(bet != null){
                    if(bet.contains(String.valueOf(result))){
                        System.out.println("bet success : " + successCount++);
                    }else{
                        System.out.println("bet fail : " + failCount);
                    }

                }
                Thread.sleep(endTime - System.currentTimeMillis());
            }
        }
    }
}
