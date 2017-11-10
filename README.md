# NukkitPlugins
ライセンスはああなってるけど、基本的に再配布は許可制で。

©CopyLight [gamesukimanIRS](https://www.twitter.com/gamesukimanIRS) All Rights Reversed



色んなプラグインがありますが、ほとんどこれは配布向けではありません。動作保証などは致しませんし、するつもりもありません。

勝手にフォーラムでスレッドを立て、ここのURLを広める行為もおやめください。

尚、許可制とし、許可あれば行っていいものとします。

- EconomyAirportNukkitJapanVersion
- RankPlugin

### EconomyAirportNukmitJapanVersion
EconomyAirportのNukkitバージョン。
彩須県サーバー(Nukkit)向けに製作致しました。

[こちら](https://forums.nukkit.io/resources/economyapi.26/)のプラグインが前提です。

ワープ看板の情報は保存されず、タッチした時にワープ看板の情報をそこから取得するようにできているため、Configの負担はなくなっています。

##### commands
ゲーム内でのみ実行可能。

```/setwarp <WarpName>```  今立ってる場所がワープ地点:```<WarpNane>```になります。ワープ名の日本語は非対応。

```/deltwarp <WarpName>```　ワープ地点：```<WarpName>```を削除します。
##### Configs
- warp.yml
```/setwarp```で決めたワープ地点の情報を記録します。xyzlevel座標とワープ名です。

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

- [WARP]

ワープ看板

- WarpName

```/setwarp``` で決めたワープ地点です。大文字小文字は気にしなくて大丈夫です。

- 運賃

￥<運賃>を消費してワープ地点にワープします。0と書けば消費されません。

- 好きな説明
書いても書かなくても大丈夫ですが、書いた場合自動で先頭に§fが付くため、看板の文字数制限を気にする必要はありません。

### RankPlugin
コマンドで称号を決められるプラグイン。
採須県Server(Nukkit)向けに製作致しました。

Configに称号を保存します。
また、サーバーにログイン/ログアウトする時のメッセージを若干変更します。
また、初めてサーバーに入った人には「鯖民」という称号と、サーバー全体に新参さんがログインしたことが伝えられます。

##### commands
コンソールでも実行可能。

```/setrank <名前(フルネーム)> <称号>``` <名前>さんの称号を<称号>に変更します。
```/ranksarch <名前(フルネーム)>``` <名前>さんの称号を調べます。

##### Configs
- rank.yml
その人の称号データです。
