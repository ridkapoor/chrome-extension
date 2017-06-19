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


var dummyJson = "{\"checkIn\":\"2017-06-28\",\"checkOut\":\"2017-06-29\",\"roomInfo\":{\"roomType\":\"Standard Room\",\"noOfRooms\":1,\"adults\":1,\"children\":[]},\"currency\":\"USD\",\"hotelInfo\":[{\"hotelId\":\"25033\",\"price\":300}],\"domain\":\"htt:\/\/tripadvisor.com\"}";


$.ajax({
    type: 'post',
    url: 'http://localhost:8080/service/getMinHotelData',
    data: dummyJson,//JSON.stringify(getMinHotelData),
    contentType: "application/json; charset=utf-8",
    success: function (data) {
        if (data != null) {

        }
    },
    error: function (data) {

    }
});