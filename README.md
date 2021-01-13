# riot-api-kotlin
<pre>
// ex)
// http://localhost:8080/summonerName?summonerName=hideonbush
------------------- 성공
{
  "name": "Hide on bush", //소환사명
  "sumWin": 9,            //승리합
  "sumLoss": 11,          //패배합
  "avgKill": 4.9,         //평균킬
  "avgDeath": 4.2,        //평균데스
  "avgAssist": 5.4,       //평균어시
  "avgActiveScore": 1.12, //평균인분
  "items": [
    {
      "gameId": 4916745833, //게임ID
      "participantId": 1,   //참가자번호
      "teamId": 100,        //팀ID(레드,블루)
      "championId": 131,    //챔피언ID
      "spell1Id": 12,       //소환사주문1
      "spell2Id": 4,        //소환사주문2
      "win": true,          //승리여부
      "kills": 10,          //킬
      "deaths": 4,          //데스
      "assists": 5,         //어시
      "queue": 420,         //게임종류
      "activeScore": 1.34   //인분
    },
    {
    ...
    }
  ],
  "status": "OK"
  
------------------- 실패
  {
    "status": {
      "message": "Data not found - summoner not found",
      "status_code": 404
    }
  }
