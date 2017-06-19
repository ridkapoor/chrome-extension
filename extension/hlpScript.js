// chrome.browserAction.onClicked.addListener(function(tab) {
//     chrome.tabs.executeScript({
//         code: 'document.body.style.backgroundColor="red"'
//     });
// });


var listingsSelector = $(".hotel_content"),
    listings = listingsSelector.map(function() {
        return this;
    }),
    expediaHotelIds = [],
    hotelsData = {},
    pageSize = 4,
    hotelsInfoList = [];


$(function() {
    $.ajax({
        url: chrome.extension.getURL("templates/widget.html"),
        dataType: "html",
        success: function(html) {
            $($.parseHTML(html)).appendTo('.atf_meta_and_photos');
            $('#logo-exp').attr("src", chrome.extension.getURL('images/expedia.svg'));
        }
    });

    hotelsInfoList = getHotelData();
    var start = 0;
    var hotels = {};
    for (i = 0; i < Math.ceil(listings.length / pageSize); i++) {

        chrome.runtime.sendMessage({ "cmd": "fetchWidgetData", "data": getWidgetRequestDTO(start + i * pageSize) }, function(response) {
            console.log(response);
        });

    }

});

//$('.loadingWhiteBox').on('remove', showWidget);


function onWindowScroll() {
    var cur = null,
        hotelId = null,
        price = null;
    var listingsSelector = $(".ppr_priv_hr_atf_north_star_nostalgic");
    $.each(listingsSelector, function(index, value) {
        if (isElementInViewport(value)) {
            cur = value;
            if (cur) {
                hotelId = document.getElementsByClassName('blRow')[index].getAttribute('data-locid');
                price = hotelsData[hotelId].price;
                return false;
            }
        }
    });

    if (cur && expediaHotelIds[hotelId]) {
        //price = $(cur).find('.sidebyside.addprice.sidebysideaddprice div.price').text();

        $(".xthrough-exp").html(price);
        $(".hotel-name-exp a").text($(cur).find('h1.heading_title').text());
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

    var checkinDate = $('*[data-datetype="CHECKIN"]').find('.picker-inner span.picker-label')[0].innerText.trim();
    var checkoutDate = $('*[data-datetype="CHECKOUT"]').find('.picker-inner span.picker-label')[0].innerText.trim();
    var dateFormat = 'dd/MM/yyyy';
    var rooms = parseInt($('.rooms span.picker-label')[0].text().replace(/room/i, '').trim());
    var children = parseInt($('.children span.picker-label')[0].text().replace(/child/i, '').trim());
    var adults = parseInt($('.adults span.picker-label')[0].text().replace(/adult/i, '').trim());
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
        "hotelInfo": hotelsInfoList.slice(start, start + pageSize)
    };
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

    otaLength = document.getElementsByClassName('no_cpu offer').length;
    for (var j = 0; j < otaLength; j++) {
        ota = document.getElementsByClassName('no_cpu offer')[j];
        otaName = ota.getAttribute('data-provider');
        
        if (pattern.test(otaName)) {
            hotelId = ota.getAttribute('data-locationid');
            price = ota.getAttribute('data-pernight');
            //hotelName = listing.getElementsByClassName('photo_image')[0].getAttribute('alt');
            hotelsData[hotelId] = {
                'price': parseFloat(price)
            };
            hotelInfoList.push({ "hotelId": hotelId, "price": parseFloat(price) || -1 });
        }
    }

    return hotelInfoList;
}
