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
			$('div.results dd.base-currency-value').text($('select#base_currency :selected').text());
			$('div.results dd.term-currency-value').text($('select#term_currency :selected').text());
		});
	})(jQuery);
}
