/**
 * Created by ridkapoor on 6/19/17.
 */

var getMinHotelData = {};
getMinHotelData.checkIn = "";
getMinHotelData.checkOut = "";
getMinHotelData.roomInfo = {};
getMinHotelData.roomInfo.roomType = "";
getMinHotelData.roomInfo.noOfRooms = "";
getMinHotelData.roomInfo.adults = 1;
getMinHotelData.roomInfo.children = []; // age of children

getMinHotelData.currency = "";
var hotelData = {};
hotelData.hotelId = "";
hotelData.price = "";
getMinHotelData.hotelInfo = [hotelData];
getMinHotelData.domain = "";

$(function() {
    chrome.runtime.onMessage.addListener(
        function(request, sender, sendResponse) {
            if (request.cmd == "fetchWidgetData") {

                $.ajax({
                    type: 'POST',
                    url: 'http://localhost:8080/service/getMinHotelData',
                    crossDomain: true,
                    data: JSON.stringify(request.data),
                    contentType: "application/json; charset=utf-8",
                    success: function(responseData, textStatus, jqXHR) {
                        sendResponse(responseData);
                    },

                    error: function (responseData, textStatus, errorThrown) {
                        console.log("POST failed");                    }
                });

            }

            return true;
        });

});
