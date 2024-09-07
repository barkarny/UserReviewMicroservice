# RSocket Usage Guide to REVIEW MICRO SERVICE

## Introduction to OUR MICROSERVICE
Our microservice is designed to handle user reviews for movies.
It provides a RESTful API that allows other applications to interact with it, such as a movie recommendation engine or a website that displays user reviews. The microservice is responsible for storing, retrieving, and managing user reviews. It also handles tasks such as moderation and spam filtering.




## Introduction to RSocket

RSocket is a binary protocol for use on byte stream transports such as TCP, WebSockets, and Aeron. It enables the following symmetric interaction models via async message passing over a single connection:
- Request/Response (streaming and non-streaming)
- Fire-and-Forget
- Request/Stream
- Channel (bi-directional streams-not at this service but at our second MicroSERVICE of AWARDS)

RSocket supports session resumption, multiplexing, and application-layer flow control.

## Code Snippets

Here OUR  some example RSocket commands. You can copy these commands from the clipboard and use them as needed.

### UPDATE Review Request Respond &&CREATE Review Request STREAM &DELETE FNF

```bash
rsc --request --route=update-review-request-respond --data="{\"id\":\"1\",\"content\":\"This is an update\",\"author\":\"bar@gmail.com\",\"createdTimeStamp\":\"2024-07-11T12:00:00Z\",\"movieId\":\"123\",\"rating\":{\"score\":4.5,\"shape\":\"STARS\"},\"password\":\"123456\"}" --debug tcp://localhost:7003

rsc --stream --route=new-review-request-stream --data="{\"id\":\"231\",\"content\":\"This is a sample content.\",\"author\":\"bar@gmail.com\",\"createdTimeStamp\":\"2024-07-11T12:00:00Z\",\"movieId\":\"123\",\"rating\":{\"score\":4.5,\"shape\":\"STARS\"},\"password\":\"123456\"}" --debug tcp://localhost:7003

rsc --fnf --route=cleanup-fnf --debug tcp://localhost:7003
```
## DONT  FORGET  WE HAVE ALSO  RESTAPI  CONTROLLER  
POST,GET,DELETE,UPDATE methods
