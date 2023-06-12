from flask import Flask, request, jsonify
from firebase_admin import credentials, firestore, initialize_app


cred = credentials.Certificate('posturku-46f8e-a45e8f72f5c3.json')
app = initialize_app(cred)
db = firestore.client()
todo_ref = db.collection('feedback')


app = Flask(__name__)

@app.route('/', methods=['POST'])
def create():
    try:
        id = request.json['id']
        todo_ref.document(id).set(request.json)
        return jsonify({"success": True}), 200
    except Exception as e:
        return f"An Error Occured: {e}"

if __name__ == "__main__":
    app.run(debug=True)