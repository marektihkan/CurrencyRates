if (typeof jQuery !== 'undefined') {
	(function($) {
		$('#spinner').ajaxStart(function() {
			$(this).fadeIn();
		}).ajaxStop(function() {
			$(this).fadeOut();
		});
	})(jQuery);

	(function($) {
		$('select#base_currency, select#term_currency').on('change', function() {
			var formInputs = $('select#base_currency, select#term_currency');
			formInputs.attr('disabled', 'disabled');

			$.getJSON("/CurrencyRates/quotation", {
				base: $('select#base_currency :selected').text(),
				term: $('select#term_currency :selected').text()
			}, function(data) {
				formInputs.removeAttr('disabled');
				$('div.results dd.base-currency-value').text(data.base);
				$('div.results dd.term-currency-value').text(data.term);
				$('div.results dd.average-rate-value').text(data.average);
			});
		});
	})(jQuery);
}
