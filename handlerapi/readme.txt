Sample api

http://localhost:8080//service/getMinHotelData


Request:
{
"checkIn": "2017-06-28",
"checkOut": "2017-06-29",
"roomInfo": {
"roomType": "Standard Room",
"noOfRooms": 1,
"adults": 1,
"children": [
]
},
"currency":"USD",
"hotelInfo": [
{
"hotelId": "25033",
"price": 300
}
],
"domain": "htt://tripadvisor.com"
}


Response :

{
    "hotels": {
        "25033": {
            "hotelId": "25033",
            "price": 219.62,
            "url": "https://www.expedia.com/New-York-Hotels-Row-NYC.h25033.Hotel-Information"
        }
    }
}