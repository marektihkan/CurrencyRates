<html>
<head>
</head>
<body>
	<div class="container">
		<div class="span12">
			<h1>Currency Exchange Rates</h1>
			<hr>
			<h2>Choose currencies</h2>
			<form class="form-inline">
				<label>From:
					<select id="base_currency">
						<option>EUR</option>
						<option>USD</option>
						<option>GBP</option>
					</select>
				</label>
				<label>
					To:
					<select id="term_currency">
						<option>EUR</option>
						<option>USD</option>
						<option>GBP</option>
					</select>
				</label>
			</form>
			<div class="results">
				<h2>Results</h2>
				<dl class="dl-horizontal">
					<dt>Base currency</dt>
					<dd class="base-currency-value"></dd>
				</dl>
				<dl class="dl-horizontal">
					<dt>Term currency</dt>
					<dd class="term-currency-value"></dd>
				</dl>
			</div>
		</div>
	</div>
</body>
</html>