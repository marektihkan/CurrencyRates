if (typeof jQuery !== 'undefined') {
	(function($) {
		var options = {
				lines: 12,
				length: 7,
				width: 5,
				radius: 15,
				color: '#000',
				speed: 1,
				trail: 100,
				shadow: true
			},
			spinner = new Spinner(options);

		$('.container').ajaxStart(function() {
			spinner.spin(this);
		}).ajaxStop(function() {
			spinner.stop();
		});
	})(jQuery);

	(function($) {
		var quotationsUrl = "",
			getQuotationsUrl = function() {
				if (quotationsUrl === "") {
					quotationsUrl = $('form#quotations').attr('action');
				}
				return quotationsUrl;
			},
			disableForm = function() {
				$('select#base_currency, select#term_currency').attr('disabled', 'disabled');
			},
			enableForm = function() {
				$('select#base_currency, select#term_currency').removeAttr('disabled');
			},
			getFormData = function() {
				return {
					base: $('select#base_currency :selected').val(),
					term: $('select#term_currency :selected').val()
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
					$('div.results dd.quotation-source-' + quotation.source).text(quotation.rate || '-');
				});
			},
			fetchQuotations = function() {
				$.getJSON(getQuotationsUrl(), getFormData(), function(data) {
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
