# NukkitPlugins
ライセンスはああなってるけど、基本的に再配布は許可制で。
©CopyLight [gamesukimanIRS](https://www.twitter.com/gamesukimanIRS) All Rights Reversed

## Plugins
色んなプラグインがありますが、ほとんどこれは配布向けではありません。動作保証などは致しませんし、するつもりもありません。
勝手にフォーラムでスレッドを立て、ここのURLを広める行為もおやめください。
尚、許可制とし、許可あれば行っていいものとします。

- EconomyAirportNukkitJapanVersion

#### EconomyAirportNukmitJapanVersion
EconomyAirportのNukkitバージョン。
[こちら](https://forums.nukkit.io/resources/economyapi.26/)のプラグインが前提です。


##### commands
ゲーム内でのみ実行可能。
```/setwarp <WarpName>```  今立ってる場所がワープ地点:```<WarpNane>```になります。

```/deltwarp <WarpName>```　ワープ地点：```<WarpName>```を削除します。

##### Signs
サーバーのOPはこれに従ってワープ看板を設置できます。
また、鯖民はこの看板タップする事で、3行目に記載された通りのEconomyMoneyを払って2行目に記載された行先へワープすることができます。

OPが書く看板：
```
[WARP]
<WarpName>
<運賃>
好きな説明
```

間違っている形式でなければ以下のようになります：
```
[WARP]
行先：<WarpName>
移動料金¥<運賃>
§f好きな説明
```
