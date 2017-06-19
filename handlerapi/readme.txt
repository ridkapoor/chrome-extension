Sample api

http://localhost:8080/service/getMinHotelData

Request:
{
"checkIn": "28 Jul",
"checkOut": "29 Jul",
"roomInfo": {
"noOfRooms": 1,
"adults": 1,
"children": 2
},
"currency":"USD",
"hotelInfo": [
{
"hotelId": "25033",
"price": 300
}
]}


Response :

{
    "hotels": {
        "25033": {
            "hotelId": "25033",
            "oldPrice": "300",
            "price": "179.37",
            "savings": "120.63",
            "url": "https://www.expedia.com/New-York-Hotels-Row-NYC.h25033.Hotel-Information",
            "roomType": "Standard Room"
        }
    }
}