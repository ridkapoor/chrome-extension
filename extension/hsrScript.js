// chrome.browserAction.onClicked.addListener(function(tab) {
//     chrome.tabs.executeScript({
//         code: 'document.body.style.backgroundColor="red"'
//     });
// });


var listingsSelector = $(".listing.easyClear"),
    listings = listingsSelector.map(function() {
        return this;
    }),
    expediaHotelIds = [],
    hotelsData = {},
    pageSize = 4,
    hotelsInfoList = [],
    widgetHotelData = {};


$(function() {
    $.ajax({
        url: chrome.extension.getURL("templates/widget.html"),
        dataType: "html",
        success: function(html) {
            $($.parseHTML(html)).appendTo('#BODYCON');
            $('#logo-exp').attr("src", chrome.extension.getURL('images/expedia.svg'));
        }
    });

    hotelsInfoList = getHotelData();
    var start = 0;
    for (i = 0; i < Math.ceil(listings.length / pageSize); i++) {

        chrome.runtime.sendMessage({ "cmd": "fetchWidgetData", "data": getWidgetRequestDTO(start + i * pageSize) }, function(response) {
            if (response && response.hotels) {
                $.each(response.hotels, function(key, value) {
                    widgetHotelData[key] = value;
                });

            }


            // private String oldPrice;
            // private String price;
            // private String savings;
            // private String url;
            // private String roomType;

        });
    }

});

//$('.loadingWhiteBox').on('remove', showWidget);


function onWindowScroll() {
    var cur = null,
        hotelId = null,
        price = null,
        hotelData = null;
    $.each(listingsSelector, function(index, value) {
        hotelId = $(value)[0].attributes['data-locationid'].value;
        if (widgetHotelData[hotelId] && isElementInViewport(value)) {
            cur = value;
            hotelData = widgetHotelData[hotelId];
            return false;
        }
    });

    if (cur) {
        //price = $(cur).find('.sidebyside.addprice.sidebysideaddprice div.price').text();

        $(".xthrough-exp").html(hotelData.price);
        $(".hotel-name-exp a").text($(cur).find('.hotel-content .listing_title a.property_title').text());
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

$(window).on('scroll', debounce(onWindowScroll, 50));

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

// var hotelIds = []
// gethotelids = hotelid();

// function gethotelids() {
//     itinLength = document.getElementsByClassName('listing easyClear').length;
//     for (var i = 0; i < itinLength; i++) {
//         otaLength = document.getElementsByClassName('listing easyClear')[i].getElementsByClassName('no_cpu').length;
//         console.log(otaLength);
//         for (var j = 0; j < otaLength; j++) {
//             if (document.getElementsByClassName('listing easyClear')[i].getElementsByClassName('no_cpu')[j].getElementsByClassName('provider').length > 0) {
//                 otaName = document.getElementsByClassName('listing easyClear')[i].getElementsByClassName('no_cpu')[j].getElementsByClassName('provider')[0].textContent;
//                 if (otaName.test(/Exp/g)) {
//                     city = document.getElementsByClassName('listing easyClear')[i].getAttribute('data-locationid');
//                     price = document.getElementsByClassName('listing easyClear')[i].getElementsByClassName('no_cpu')[j].getAttribute('data-pernight');
//                     hotelname = document.getElementsByClassName('listing easyClear')[i].getElementsByClassName('photo_image')[0].getAttribute('alt');
//                     hotelIds.push({ city: parseInt(city), price: parseInt(price), hotelname: hotelname });
//                     console.log(hotelIds[i]);
//                 }
//             }
//             if (document.getElementsByClassName('listing easyClear')[i].getElementsByClassName('no_cpu')[j].getElementsByClassName('vendor').length > 0) {
//                 otaName = document.getElementsByClassName('listing easyClear')[i].getElementsByClassName('no_cpu')[j].getElementsByClassName('vendor')[0].textContent;
//                 if (otaName.test(/Exp/g)) {
//                     console.log(otaName);
//                     city = document.getElementsByClassName('listing easyClear')[i].getAttribute('data-locationid');
//                     price = document.getElementsByClassName('listing easyClear')[i].getElementsByClassName('no_cpu')[j].getAttribute('data-pernight');
//                     hotelname = document.getElementsByClassName('listing easyClear')[i].getElementsByClassName('photo_image')[0].getAttribute('alt');
//                     hotelIds.push({ city: parseInt(city), price: parseInt(price), hotelname: hotelname });
//                     console.log(hotelIds[i]);
//                 }
//             }
//         }
//     }
//     return hotelIds;
// }


function getWidgetRequestDTO(start) {

    var checkinDate = $('*[data-datetype="CHECKIN"]').find('.picker-inner span.picker-label.target')[0].innerText.trim();
    var checkoutDate = $('*[data-datetype="CHECKOUT"]').find('.picker-inner span.picker-label.target')[0].innerText.trim();
    var rooms = parseInt($('.room-info').text().replace(/room/i, '').trim());
    var children = parseInt($('.child-info').text().replace(/child/i, '').trim());
    var adults = parseInt($('.adult-info').text().replace(/adult/i, '').trim());
    var currency = $('*[data-prwidget-name="homepage_footer_pickers"]').find('.unified-picker .picker-inner span')[0].innerText;
    currency = currency.substring(1, currency.length);

    return {
        "checkIn": checkinDate,
        "checkOut": checkoutDate,
        "roomInfo": {
            "noOfRooms": rooms,
            "adults": adults,
            "children": children
        },
        "currency": currency,
        "hotelInfo": hotelsInfoList.slice(start, start + pageSize)
    };
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
                //hotelName = listing.getElementsByClassName('photo_image')[0].getAttribute('alt');
                hotelsData[hotelId] = {
                    'price': parseFloat(price)
                };
                hotelInfoList.push({ "hotelId": hotelId, "price": parseFloat(price) || -1 });
            }
        }
    }
    return hotelInfoList;
}
