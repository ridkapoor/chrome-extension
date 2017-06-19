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
    hotelsData = {};

$(function() {

    chrome.runtime.sendMessage({
        "cmd": "fetchWidgetData",
        "data": {
            "checkIn": "2017-06-28",
            "checkOut": "2017-06-29",
            "roomInfo": {
                "roomType": "Standard Room",
                "noOfRooms": 1,
                "adults": 1,
                "children": []
            },
            "currency": "USD",
            "hotelInfo": [{
                "hotelId": "25033",
                "price": 300
            }],
            "domain": "htt://tripadvisor.com"
        }
    }, function(response) {
        console.log(response);
    });

    $.ajax({
        url: chrome.extension.getURL("templates/widget.html"),
        dataType: "html",
        success: function(html) {
            $($.parseHTML(html)).appendTo('#BODYCON');
            $('#logo-exp').attr("src", chrome.extension.getURL('images/expedia.svg'));
        }
    });


    getHotelData();

    getWidgetRequestDTO();

});
//$('.loadingWhiteBox').on('remove', showWidget);


function onWindowScroll() {
    var cur = null,
        hotelId = null,
        price = null;
    var listingsSelector = $(".hotel_content");
    $.each(listingsSelector, function(index, value) {
        if (isElementInViewport(value)) {
            cur = value;
            if (cur) {
                hotelId = document.getElementsByClassName('listing easyClear')[index].getAttribute('data-locationid');
                price = hotelsData[hotelId].price;

                return false;
            }
        }
    });

    if (cur && $.inArray(hotelId, expediaHotelIds) > -1) {
        //price = $(cur).find('.sidebyside.addprice.sidebysideaddprice div.price').text();

        $(".xthrough-exp").html(price);
        $(".hotel-name-exp a").text($(cur).find('.listing_title a.property_title').text());
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


function getWidgetRequestDTO() {

    var checkinDate = $('*[data-datetype="CHECKIN"]').find('.picker-inner .span.picker-label.target').innerText;
    var checkoutDate = $('*[data-datetype="CHECKOUT"]').find('.picker-inner .span.picker-label.target').innerText;
    var roomInfo = $('.room-info').text();
    var childInfo = $('.child-info').text();
    var adultInfo = $('.adult-info').text();
    var currency = $('*[data-prwidget-name="homepage_footer_pickers"]').find('.unified-picker .picker-inner span').innerText;



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
        price = null;

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
                hotelName = listing.getElementsByClassName('photo_image')[0].getAttribute('alt');
                hotelsData[hotelId] = { 'price': parseFloat(price), 'hotelName': hotelName };
                expediaHotelIds.push(hotelId);
            }
        }
    }
}
