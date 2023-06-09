from flask import Flask, request, jsonify
from firebase_admin import credentials, firestore, initialize_app

cred = credentials.Certificate('posturku-46f8e-firebase-adminsdk-qb0ct-e5aef04ef0.json')
app = initialize_app(cred)
db = firestore.client()
todo_ref = db.collection('articles')

app = Flask(__name__)

# Endpoint for getting articles
@app.route('/articles', methods=['GET'])
def get_articles():
    keyword = request.args.get('keyword')
    page = int(request.args.get('page', 1))
    size = int(request.args.get('size', 10))

    # Querying articles from Firestore
    articles = todo_ref.get()
    articles = [article.to_dict() for article in articles]

    # Filtering articles by keyword
    filtered_articles = [article for article in articles if keyword.lower() in article['title'].lower()]

    # Pagination
    start_index = (page - 1) * size
    end_index = start_index + size
    paginated_articles = filtered_articles[start_index:end_index]

    response = {
        "success": True,
        "message": "Articles fetched successfully",
        "listArticles": paginated_articles
    }

    return jsonify(response), 200

# Endpoint for saving a favorite article
@app.route('/articles/favorite/create', methods=['POST'])
def save_favorite_article():
    data = request.get_json()
    article_id = data.get('ArticleId')

    # Saving the article to Firestore
    favorite_article = {"ArticleId": article_id}
    todo_ref.add(favorite_article)

    response = {
        "error": False,
        "message": "Article saved as favorite"
    }

    return jsonify(response), 200

# Endpoint for getting favorite articles
@app.route('/articles/favorite', methods=['GET'])
def get_favorite_articles():
    page = int(request.args.get('page', 1))
    size = int(request.args.get('size', 10))

    # Querying favorite articles from Firestore
    favorite_articles = todo_ref.get()
    favorite_articles = [article.to_dict() for article in favorite_articles]

    # Pagination
    start_index = (page - 1) * size
    end_index = start_index + size
    paginated_articles = favorite_articles[start_index:end_index]

    response = {
        "success": True,
        "message": "Favorite articles fetched successfully",
        "listArticles": paginated_articles
    }

    return jsonify(response), 200

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=5000, debug=False)
