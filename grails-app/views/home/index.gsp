<html>
<head>
</head>
<body>
	<div class="container">
		<div class="span12">
			<h1><g:message code="home.index.title" /></h1>
			<hr>
			<h2><g:message code="home.index.form.title" /></h2>
			<g:form name="quotations" url="[controller:'quotation',action:'index']" method="GET" class="form-inline">
				<label>
					<g:message code="home.index.form.from.label" />
					<select id="base_currency" name="base">
						<g:render template="/shared/currencies" />
					</select>
				</label>
				<label>
					<g:message code="home.index.form.to.label" />
					<select id="term_currency" name="term">
						<g:render template="/shared/currencies" />
					</select>
				</label>
			</g:form>
			<div class="results">
				<h2><g:message code="home.index.results.title" /></h2>
				<div class="row">
					<div class="span4">
						<dl>
							<dt><g:message code="home.index.results.baseCurrency.label" /></dt>
							<dd class="base-currency-value currency"></dd>
							<dt><g:message code="home.index.results.termCurrency.label" /></dt>
							<dd class="term-currency-value currency"></dd>
						</dl>
					</div>
					<div class="span8">
						<dl class="dl-horizontal">
							<dt><g:message code="quotationSource.Google.title" /></dt>
							<dd class="quotation-source-Google quotation-source"></dd>
							<dt><g:message code="quotationSource.Yahoo.title" /></dt>
							<dd class="quotation-source-Yahoo quotation-source"></dd>
							<dt><g:message code="quotationSource.EUCentralBank.title" /></dt>
							<dd class="quotation-source-EUCentralBank quotation-source"></dd>
							<dt><g:message code="home.index.results.averageRate.label" /></dt>
							<dd class="average-rate-value"></dd>
						</dl>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>