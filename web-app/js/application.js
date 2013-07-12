if (typeof jQuery !== 'undefined') {
	(function($) {
		$('#spinner').ajaxStart(function() {
			$(this).fadeIn();
		}).ajaxStop(function() {
			$(this).fadeOut();
		});
	})(jQuery);

	(function($) {
		var quotationsUrl = "/CurrencyRates/quotation",
			disableForm = function() {
				$('select#base_currency, select#term_currency').attr('disabled', 'disabled');
			},
			enableForm = function() {
				$('select#base_currency, select#term_currency').removeAttr('disabled');
			},
			getFormData = function() {
				return {
					base: $('select#base_currency :selected').text(),
					term: $('select#term_currency :selected').text()
				}
			}
			resetResults = function() {
				$('div.results dd').text('-');
			},
			fillData = function(data) {
				$('div.results dd.base-currency-value').text(data.base);
				$('div.results dd.term-currency-value').text(data.term);
				$('div.results dd.average-rate-value').text(data.average);
				$.each(data.quotations, function(index, quotation) {
					console.log(quotation.source);
					$('div.results dd.quotation-source-' + quotation.source).text(quotation.rate);
				});
			},
			fetchQuotations = function() {
				$.getJSON(quotationsUrl, getFormData(), function(data) {
					enableForm();
					fillData(data);
				});
			};

		$('select#base_currency, select#term_currency').on('change', function() {
			disableForm();
			resetResults();
			fetchQuotations();
		});
	})(jQuery);
}
