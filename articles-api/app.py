from flask import Flask, request, jsonify
from firebase_admin import credentials, firestore, initialize_app, auth

cred = credentials.Certificate('posturku-46f8e-firebase-adminsdk-qb0ct-e5aef04ef0.json')
app = initialize_app(cred)
db = firestore.client()
todo_ref = db.collection('articles')
favorite_articles_ref = db.collection('favoriteArticles')
profile_ref = db.collection('profiles')

app = Flask(__name__)

@app.route('/articles', methods=['GET'])
def get_articles():
    keyword = request.args.get('keyword')
    page = int(request.args.get('page', 1))
    size = int(request.args.get('size', 10))

    articles = todo_ref.get()
    articles = [article.to_dict() for article in articles]

    filtered_articles = [article for article in articles if 'title' in article and keyword.lower() in article['title'].lower()]

    start_index = (page - 1) * size
    end_index = start_index + size
    paginated_articles = filtered_articles[start_index:end_index]

    response = {
        "success": True,
        "message": "Articles fetched successfully",
        "listArticles": paginated_articles
    }

    return jsonify(response), 200

@app.route('/articles/favorite/create', methods=['POST'])
def save_favorite_article():
    data = request.get_json()
    article_id = data.get('ArticleId')

    id_token = request.headers.get('Authorization')
    decoded_token = auth.verify_id_token(id_token)
    user_id = decoded_token['uid']

    favorite_article = {
        "ArticleId": article_id,
        "UserId": user_id
    }
    favorite_articles_ref.add(favorite_article)

    response = {
        "error": False,
        "message": "Article saved as favorite"
    }

    return jsonify(response), 200

@app.route('/articles/favorite', methods=['GET'])
def get_favorite_articles():
    user_id = request.args.get('userId')

    if not user_id:
        response = {
            "success": False,
            "message": "UserId is required"
        }
        return jsonify(response), 400

    page = int(request.args.get('page', 1))
    size = int(request.args.get('size', 10))

    favorite_articles = favorite_articles_ref.where("UserId", "==", user_id).get()
    favorite_articles = [article.to_dict() for article in favorite_articles]

    start_index = (page - 1) * size
    end_index = start_index + size
    paginated_articles = favorite_articles[start_index:end_index]

    response = {
        "success": True,
        "message": "Favorite articles fetched successfully",
        "listArticles": paginated_articles
    }

    return jsonify(response), 200

@app.route('/articles/favorite/delete', methods=['POST'])
def delete_favorite_article():
    data = request.get_json()
    article_id = data.get('ArticleId')

    id_token = request.headers.get('Authorization') 
    decoded_token = auth.verify_id_token(id_token)
    user_id = decoded_token['uid']

    query = favorite_articles_ref.where("ArticleId", "==", article_id).where("UserId", "==", user_id).limit(1)
    favorite_articles = query.get()

    if len(favorite_articles) == 0:
        response = {
            "success": False,
            "message": "Favorite article not found"
        }
        return jsonify(response), 404

    favorite_articles_ref.document(favorite_articles[0].id).delete()

    response = {
        "success": True,
        "message": "Favorite article deleted successfully"
    }

    return jsonify(response), 200

@app.route('/profiles/create', methods=['POST'])
def create_profile():
    data = request.get_json()
    about_me = data.get('aboutMe')
    name = data.get('name')
    phone = data.get('phone')
    email = data.get('email')
    address = data.get('address')
    skills = data.get('skills', '')
    hobbies = data.get('hobbies', '')

    id_token = request.headers.get('Authorization')
    decoded_token = auth.verify_id_token(id_token)
    user_id = decoded_token['uid']

    profile_data = {
        "userId": user_id,
        "aboutMe": about_me,
        "name": name,
        "phone": phone,
        "email": email,
        "address": address,
        "skills": skills,
        "hobbies": hobbies
    }

    profile_ref.document(user_id).set(profile_data)

    response = {
        "success": True,
        "message": "Profile created successfully"
    }

    return jsonify(response), 200

@app.route('/profiles/update', methods=['PUT'])
def update_profile():
    data = request.get_json()
    user_id = data.get('userId')
    about_me = data.get('aboutMe')
    name = data.get('name')
    phone = data.get('phone')
    email = data.get('email')
    address = data.get('address')
    skills = data.get('skills', '')
    hobbies = data.get('hobbies', '')

    profile_data = {
        "userId": user_id,
        "aboutMe": about_me,
        "name": name,
        "phone": phone,
        "email": email,
        "address": address,
        "skills": skills,
        "hobbies": hobbies
    }

    profile_ref.document(user_id).update(profile_data)

    response = {
        "success": True,
        "message": "Profile updated successfully"
    }

    return jsonify(response), 200

@app.route('/profiles', methods=['GET'])
def get_profile():
    user_id = request.args.get('userId')

    if not user_id:
        response = {
            "success": False,
            "message": "UserId is required"
        }
        return jsonify(response), 400

    profile = profile_ref.document(user_id).get()

    if not profile.exists:
        response = {
            "success": False,
            "message": "Profile not found"
        }
        return jsonify(response), 404

    profile_data = profile.to_dict()

    response = {
        "success": True,
        "message": "Profile fetched successfully",
        "profile": profile_data
    }

    return jsonify(response), 200

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=5000, debug=False)
