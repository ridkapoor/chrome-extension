var listingsSelector = $(".listing.easyClear"),
    listings = listingsSelector.map(function() {
        return this;
    }),
    pageSize = 5,
    hotelsInfoList = [],
    widgetHotelData = {},
    userRequestDTO = {},
    currencySymbol=null;


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
            $($.parseHTML(html)).appendTo('#BODYCON');
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
        console.log(req);

        chrome.runtime.sendMessage({ "cmd": "fetchWidgetData", "data": req }, function(response) {
            if (response && response.hotels) {
                $.each(response.hotels, function(key, value) {
                    widgetHotelData[key] = value;
                });

                if (loadData && widgetHotelData[req.hotelId]) {
                    populateWidgetData();
                    loadData = false;
                }
            }

        });
    }

}


function populateWidgetData() {
    var cur = null,
        hotelId = null,
        price = null,
        hotelData = null,
        pattern = /Expedia/i;


    var selector = $(".listing.easyClear");
    $.each(selector, function(index, value) {
        hotelId = $(value)[0].attributes['data-locationid'].value;
        if (widgetHotelData[hotelId] && isElementInViewport(value)) {
            cur = value;
            hotelData = widgetHotelData[hotelId];
            return false;
        }
    });

    if (cur) {
        $(".xthrough-exp span.price").html(hotelData.oldPrice);
        $('.save-text-exp span.saving').html(hotelData.savings);
        $(".final-price-exp span.price").html(hotelData.price);
        $(".hotel-name-exp").text($(cur).find('.listing_title a.property_title').text());
        $('a.view-deal-link-exp').attr('href', hotelData.url);
        if (hotelData.oldPrice && hotelData.savings) {
            $('.xthrough-exp').show();
            $('.save-text-exp').show();
            $('.final-price-exp .circle-red.blink').hide();
        } else {
            $('.xthrough-exp').hide();
            $('.save-text-exp').hide();
            $('.final-price-exp .circle-red.blink').show();
        }

        $('#sticky-widget').show();

        // attach red beacon 

        var priceBlockProvider = cur.getElementsByClassName("priceBlock")[0].getElementsByClassName("provider")[0].innerText;
        if(pattern.test(priceBlockProvider)){
            // ATTACH TO PRICE BLOCK 

        } else{

            var textLinksLength = cur.getElementsByClassName("text-links")[0].children.length;
            var children = cur.getElementsByClassName("text-links")[0].children;
            for (var i = 0; i < textLinksLength ; i++) {
               var attrValue =  children[i].getAttribute('data-offerauthor');  
               if (pattern.test(attrValue)) {
                // attach to text links 

               }

            }

        }
    }

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

$(window).on('scroll', debounce(populateWidgetData, 1));


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

    var checkinDate = $('*[data-datetype="CHECKIN"]').find('.picker-inner span.picker-label.target')[0].innerText.trim();
    var checkoutDate = $('*[data-datetype="CHECKOUT"]').find('.picker-inner span.picker-label.target')[0].innerText.trim();
    var rooms = parseInt($('.room-info').text().replace(/room/i, '').trim());
    var children = parseInt($('.child-info').text().replace(/child/i, '').trim());
    var adults = parseInt($('.adult-info').text().replace(/adult/i, '').trim());
    var dateFormat = "dd MMM";
    var currency = $('*[data-prwidget-name="homepage_footer_pickers"]').find('.unified-picker .picker-inner span')[0].innerText;
    currency = currency.substring(1, currency.length);
    currencySymbol=currency.substring(0, 1);

    return {
        "checkIn": checkinDate,
        "checkOut": checkoutDate,
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
    var itinLength = document.getElementsByClassName('listing easyClear').length,
        pattern = /Expedia/i,
        listing = null,
        otaLength = null,
        ota = null,
        hotelId = null,
        otaName = null,
        hotelName = null,
        price = null,
        hotelInfoList = [];

    for (var i = 0; i < itinLength; i++) {
        listing = document.getElementsByClassName('listing easyClear')[i];
        otaLength = listing.getElementsByClassName('no_cpu').length;

        for (var j = 0; j < otaLength; j++) {
            ota = listing.getElementsByClassName('no_cpu')[j];
            if (ota.getElementsByClassName('provider').length > 0) {
                otaName = ota.getElementsByClassName('provider')[0].textContent;

            } else if (ota.getElementsByClassName('vendor').length > 0) {
                otaName = ota.getElementsByClassName('vendor')[0].textContent;
            }

            if (pattern.test(otaName)) {
                hotelId = listing.getAttribute('data-locationid');
                price = ota.getAttribute('data-pernight');
                hotelInfoList.push({ "hotelId": hotelId, "price": parseFloat(price) || -1 });
            }

        }
    }
    return hotelInfoList;
}
