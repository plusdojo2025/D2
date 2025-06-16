<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ヒストリー機能</title>
</head>
<body>

		<h1>
			<a href="HomeServlet">ケンコークラフト</a>
		</h1>
		<h2>
			<a href="ヘルプを表示">ヘルプ</a>
		</h2>
		<td><label> インポートされたファイル <select name="history_date">
								<option value="未定">未定</option>
								<option value="未定">未定</option>
								<option value="未定">未定2025年6月</option>
						</select> 選択
					</label></td>
		
				
				
		<h4>
			<a href="まちをみる">街を見る</a>
		</h4>
		<h5>
			<a href="まちをみる">画像の表示</a>
		</h5>
		<form action="SearchServlet" method="get">
        <label for="keyword">日付を入力：</label>
        <input type="text" id="keyword" name="keyword" required>
        <input type="submit" value="検索">
    </form>
		
		
		<td><label> 過去のデータ <select name="history_date">
								<option value="7">2025年4月</option>
								<option value="7.5">2025年5月</option>
								<option value="8.0">2025年6月</option>
						</select> 選択
					</label></td>
		
		
</body>
</html>