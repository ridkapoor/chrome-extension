var listingsSelector = $(".ppr_priv_hr_atf_north_star_nostalgic"),
    listings = listingsSelector.map(function() {
        return this;
    }),
    pageSize = 5,
    hotelsInfoList = [],
    widgetHotelData = {},
    userRequestDTO = {};



$(function() {
    hotelsInfoList = getHotelData();
    userRequestDTO = getUserRequestDTO();
    $.when(loadWidget()).done(fetchExpediaHotelsData);

});

function loadWidget() {
    return $.ajax({
        url: chrome.extension.getURL("templates/widget.html"),
        dataType: "html",
        success: function(html) {
            $($.parseHTML(html)).appendTo('.atf_meta_and_photos');
            $('#logo-exp').attr("src", chrome.extension.getURL('images/expedia.svg'));
            $('#sticky-widget').hide();
        }
    });
}

function fetchExpediaHotelsData() {

    var start = 0;
    var loadData = true;
    var req = {};

    for (i = 0; i < Math.ceil(hotelsInfoList.length / pageSize); i++) {

        req = getWidgetRequestDTO(start + i * pageSize);

        chrome.runtime.sendMessage({ "cmd": "fetchWidgetData", "data": req }, function(response) {
            if (response && response.hotels) {
                $.each(response.hotels, function(key, value) {
                    widgetHotelData[key] = value;
                });

                if (loadData && !$.isEmptyObject(widgetHotelData)) {
                    loadData = populateWidgetData();
                }
            }

        });
    }

}

function populateWidgetData() {
    var cur = null,
        hotelId = null,
        price = null,
        hotelData = null;
    var currency = $('*[data-prwidget-name="homepage_footer_pickers"]').find('.unified-picker .picker-inner span')[0].innerText;
    var currencySymbol = currency.substring(0, 1);
    var listingsSelector = $(".ppr_priv_hr_atf_north_star_nostalgic");
    $.each(listingsSelector, function(index, value) {
        hotelId = document.getElementsByClassName('blRow')[index].getAttribute('data-locid');
        if (widgetHotelData[hotelId] && isElementInViewport(value)) {
            cur = value;
            //$(cur).find('div[data-provider="Expedia"] .price').addClass('ta-beacon');
            hotelData = widgetHotelData[hotelId];
            return false;
        }
    });

    if (cur) {

        $(".xthrough-exp span.price").html(hotelData.oldPrice);
        $('.save-text-exp span.saving').html(hotelData.savings);
        $(".final-price-exp span.price").html(hotelData.price);
        $(".hotel-name-exp").text($(cur).find('h1.heading_title').text());
        $('a.view-deal-link-exp').attr('href', hotelData.url);
        $('.price-block-exp .currency').html(currencySymbol);

        if (hotelData.oldPrice && hotelData.savings) {
            $('.xthrough-exp').show();
            $('.save-text-exp').show();
            $('.final-price-exp .beacon').hide();
        } else {
            $('.xthrough-exp').hide();
            $('.save-text-exp').hide();
            $('.final-price-exp .beacon').show();
        }

        $('#sticky-widget').show();
        // $('.ta-beacon').removeClass('ta-beacon');
    }

    return cur ? false : true;
}

function debounce(func, time) {
    var timer;
    return function() {
        if (timer) {
            clearTimeout(timer);
        }
        timer = window.setTimeout(function() {
            func();
            clearTimeout(timer);
            timer = null;
        }, time);
    }
}

$(window).on('scroll', debounce(populateWidgetData, 50));

function isElementInViewport(el) {

    //special bonus for those using jQuery
    if (typeof jQuery === "function" && el instanceof jQuery) {
        el = el[0];
    }


    var rect = el.getBoundingClientRect();

    return (
        rect.top >= 0 &&
        rect.left >= 0 &&
        rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) && /*or $(window).height() */
        rect.right <= (window.innerWidth || document.documentElement.clientWidth) /*or $(window).width() */
    );

}

function getUserRequestDTO() {

    var checkinDate = $('*[data-datetype="CHECKIN"]').find('.picker-inner span.picker-label')[0].innerText.trim();
    var checkoutDate = $('*[data-datetype="CHECKOUT"]').find('.picker-inner span.picker-label')[0].innerText.trim();
    var dateFormat = 'dd/MM/yyyy';
    var rooms = parseInt($('.rooms span.picker-label')[0].innerText.replace(/room/i, '').trim());
    var children = parseInt($('.children span.picker-label')[0].innerText.replace(/child/i, '').trim());
    var adults = parseInt($('.adults span.picker-label')[0].innerText.replace(/adult/i, '').trim());
    var currency = $('*[data-prwidget-name="homepage_footer_pickers"]').find('.unified-picker .picker-inner span')[0].innerText;
    currency = currency.substring(1, currency.length);

    return {
        "checkIn": checkinDate,
        "checkOut": checkoutDate,
        "dateFormat": dateFormat,
        "roomInfo": {
            "noOfRooms": rooms,
            "adults": adults,
            "children": children
        },
        "currency": currency,
        "dateFormat": dateFormat
    };
}

function getWidgetRequestDTO(start) {

    var hotelReqData = userRequestDTO;
    hotelReqData.hotelInfo = hotelsInfoList.slice(start, start + pageSize);

    return hotelReqData;
}




function getHotelData() {
    pattern = /Expedia/i,
        listing = null,
        otaLength = null,
        ota = null,
        hotelId = null,
        otaName = null,
        hotelName = null,
        price = null,
        hotelInfoList = [];

    otaLength = document.getElementsByClassName('prw_common_base_dropdown ui_options')[0].getElementsByClassName('no_cpu').length;
    for (var j = 0; j < otaLength; j++) {
        ota = document.getElementsByClassName('prw_common_base_dropdown ui_options')[0].getElementsByClassName('no_cpu')[j];
        otaName = ota.getAttribute('data-provider');

        if (pattern.test(otaName)) {
            hotelId = ota.getAttribute('data-locationid');
            price = ota.getAttribute('data-pernight');
            hotelInfoList.push({ "hotelId": hotelId, "price": parseFloat(price) || -1 });
        }
    }

    return hotelInfoList;
}
