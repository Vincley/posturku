## Get Articles
Request:
- URL:
  - /articles 
- Parameters:
  - Keyword: `as string, search in title`
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
  "listArticles": [
    {
      "id": 1,
      "source": {
        "id": 1,
        "name": "Kompas.com"
      },
      "author": "Jon Fingas",
      "title": "Apple WWDC 2023: What to expect, from iOS 17 to new MacBooks",
      "description": "Apple’s Worldwide Developers Conference regularly sets the tone for the company’s future, and that may be truer than ever for 2023. Many expect the company to introduce its first mixed reality headset at the event, with a new platform to match. However, the w…",
      "url": "https://www.engadget.com/apple-wwdc-2023-what-to-expect-from-ios-17-to-new-macbooks-160033810.html",
      "urlToImage": "https://s.yimg.com/uu/api/res/1.2/w5eokYEru719pqXSfleocA--~B/Zmk9ZmlsbDtoPTYzMDtweW9mZj0wO3c9MTIwMDthcHBpZD15dGFjaHlvbg--/https://media-mbst-pub-ue1.s3.amazonaws.com/creatr-uploaded-images/2023-03/989f60f0-ce54-11ed-ae79-580cd063061e.cf.jpg",
      "publishedAt": "2023-05-31T16:00:33Z",
      "content": "Apples Worldwide Developers Conference regularly sets the tone for the companys future, and that may be truer than ever for 2023. Many expect the company to introduce its first mixed reality headset … [+7109 chars]"
    },
    {
      "id": 2,
      "source": {
        "id": 1,
        "name": "Kompas.com"
      },
      "author": "Mat Smith",
      "title": "The Morning After: Industry leaders say AI presents 'risk of extinction' on par with nuclear war",
      "description": "With the rise of AI language models and tools like ChatGPT and Bard, we've heard warnings from people involved, like Elon Musk, about the risks posed by AI. Now, a group of high-profile industry leaders has issued a one-sentence statement: “Mitigating the ris…",
      "url": "https://www.engadget.com/the-morning-after-industry-leaders-say-ai-presents-risk-of-extinction-on-par-with-nuclear-war-111545269.html",
      "urlToImage": "https://s.yimg.com/uu/api/res/1.2/QEXUJO6VnMtfc92eqjj4QA--~B/Zmk9ZmlsbDtoPTYzMDtweW9mZj0wO3c9MTIwMDthcHBpZD15dGFjaHlvbg--/https://media-mbst-pub-ue1.s3.amazonaws.com/creatr-uploaded-images/2023-02/aee5ff80-a867-11ed-bb3f-bb42181ba6dd.cf.jpg",
      "publishedAt": "2023-05-31T11:15:45Z",
      "content": "With the rise of AI language models and tools like ChatGPT and Bard, we've heard warnings from people involved, like Elon Musk, about the risks posed by AI. Now, a group of high-profile industry lead… [+4164 chars]"
    }
  ]
}
```
## Save Article Favorite
Request:
- URL:
  - /articles/favorite/create
- Method:
  - POST
- Request Body:
  - ArticleId: `article id database`
- Headers:
  - Authorization : `Bearer <token>`
  
Response:
```json
{
    "error": false,
    "message": "success"
}
```
## Get Favorite Articles
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
  "listArticles": [
    {
      "id": 1,
      "source": {
        "id": 1,
        "name": "Kompas.com"
      },
      "author": "Jon Fingas",
      "title": "Apple WWDC 2023: What to expect, from iOS 17 to new MacBooks",
      "description": "Apple’s Worldwide Developers Conference regularly sets the tone for the company’s future, and that may be truer than ever for 2023. Many expect the company to introduce its first mixed reality headset at the event, with a new platform to match. However, the w…",
      "url": "https://www.engadget.com/apple-wwdc-2023-what-to-expect-from-ios-17-to-new-macbooks-160033810.html",
      "urlToImage": "https://s.yimg.com/uu/api/res/1.2/w5eokYEru719pqXSfleocA--~B/Zmk9ZmlsbDtoPTYzMDtweW9mZj0wO3c9MTIwMDthcHBpZD15dGFjaHlvbg--/https://media-mbst-pub-ue1.s3.amazonaws.com/creatr-uploaded-images/2023-03/989f60f0-ce54-11ed-ae79-580cd063061e.cf.jpg",
      "publishedAt": "2023-05-31T16:00:33Z",
      "content": "Apples Worldwide Developers Conference regularly sets the tone for the companys future, and that may be truer than ever for 2023. Many expect the company to introduce its first mixed reality headset … [+7109 chars]"
    },
    {
      "id": 2,
      "source": {
        "id": 1,
        "name": "Kompas.com"
      },
      "author": "Mat Smith",
      "title": "The Morning After: Industry leaders say AI presents 'risk of extinction' on par with nuclear war",
      "description": "With the rise of AI language models and tools like ChatGPT and Bard, we've heard warnings from people involved, like Elon Musk, about the risks posed by AI. Now, a group of high-profile industry leaders has issued a one-sentence statement: “Mitigating the ris…",
      "url": "https://www.engadget.com/the-morning-after-industry-leaders-say-ai-presents-risk-of-extinction-on-par-with-nuclear-war-111545269.html",
      "urlToImage": "https://s.yimg.com/uu/api/res/1.2/QEXUJO6VnMtfc92eqjj4QA--~B/Zmk9ZmlsbDtoPTYzMDtweW9mZj0wO3c9MTIwMDthcHBpZD15dGFjaHlvbg--/https://media-mbst-pub-ue1.s3.amazonaws.com/creatr-uploaded-images/2023-02/aee5ff80-a867-11ed-bb3f-bb42181ba6dd.cf.jpg",
      "publishedAt": "2023-05-31T11:15:45Z",
      "content": "With the rise of AI language models and tools like ChatGPT and Bard, we've heard warnings from people involved, like Elon Musk, about the risks posed by AI. Now, a group of high-profile industry lead… [+4164 chars]"
    }
  ]
}
```
