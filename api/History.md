## Save History
Request:
- URL:
  - /history/create
- Method:
  - POST
- Request Body:
  - StartTime: `as long (epoch utc)`
  - EndTime: `as long (epoch utc)`
  - DurationBad: `as int (minutes),`
  - DurationGood: `as int (minutes)`

- Headers:
  - Authorization : `Bearer <token>`
  
Response:
```json
{
    "error": false,
    "message": "success"
}
```
## Get History
Request:
- URL:
  - /articles/favorite 
- Parameters:
  - page: 1  `as int, optional`
  - size: 10 `as int, optional`
- Method:
  - GET
- Headers:
  - Authorization : `Bearer <token>`
  
Response:
```json
{
  "success": true,
  "message": "Articles fetched successfully",
  "listHistories": [
    {
      "id": 1,
      "StartTime": 1685863779,
      "EndTime": 1685867379,
      "DurationBad": 20,
      "DurationGood": 40,
    },
        {
      "id": 2,
      "StartTime": 1685863779,
      "EndTime": 1685867379,
      "DurationBad": 20,
      "DurationGood": 40,
    },
  ]
}
```
