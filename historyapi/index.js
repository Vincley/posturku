const express = require('express')
const bodyParser = require('body-parser')

const fs = require('firebase-admin');
const serviceAccount = require('./posturku-46f8e-1a3eb27bb708.json');
fs.initializeApp({
    credential: fs.credential.cert(serviceAccount)
   });
const db = fs.firestore();

const app = express()
const port = 5000


app.use(bodyParser.json())
app.use(
  bodyParser.urlencoded({
    extended: true,
  })
)

const historyDb = db.collection('history');
app.post('/history/create', async (req, res) => {
    try {
      console.log(req.body);
      const id = req.body.userId;
      const historyJson = {
        durationBad: req.body.durationBad,
        durationGood: req.body.durationGood,
        endTime: req.body.endTime,
        startTime: req.body.startTime,
      };
      const historyDb = db.collection('history'); 
      const response = await historyDb.doc(id).set(historyJson);
      res.send(response);
    } catch(error) {
      res.send(error);
    }
  });

app.get('/history/read/:id', async (req, res) => {
    try {
      const historyRef = db.collection("history").doc(req.params.userId);
      const response = await historyRef.get();
      res.send(response.data());
    } catch(error) {
      res.send(error);
    }
  });

app.delete('/history/delete/:id', async (req, res) => {
    try {
      const response = await db.collection("history").doc(req.params.userId).delete();
      res.send(response);
    } catch(error) {
      res.send(error);
    }
  })

app.listen(port, () => {
    console.log(`App running on port ${port}.`)
  })