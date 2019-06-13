const functions = require('firebase-functions');

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });

const admin = require('firebase-admin');
admin.initializeApp();

var db = admin.firestore();

function getDocument(db,userId) {
	// [START get_document]
	var cityRef = db.collection('Users').doc(userId);
	var getDoc = cityRef.get()
	  .then(doc => {
		if (!doc.exists) {
		   console.log('No such document!');
		} else {
			console.log('Document data:', doc.data());
		}

		return doc
	  })
	  .catch(err => {
		console.log('Error getting document', err);
	  });
	// [END get_document]
  
	return getDoc;
  }

exports.newNotification = functions.firestore
    .document('NewPost/{documentId}')
    .onUpdate((change, context) => {
      const newValue = change.after.data();
	  const userId = newValue.addedBy;
	  console.log("The User Id is",userId);

	  	var userRef = db.collection('Users').doc(userId);
		return userRef.get()
		  .then(doc => {
				console.log('Document data:', doc.data());
				const toThisUser = doc.get('userToken');
				const payload = {
					data:{
						username: "Someone Just Bidded in Your Post",
						email : "Click to see now"
					}
				};
				return admin.messaging().sendToDevice(toThisUser,payload)
				.then(function(response){
					console.log("The Notification Has been sent");
					return {};
				})
				.catch(function(error){
					console.log("Error sending the Notification",error);
				});
		  })
		  .catch(err => {
			console.log('Error getting document', err);
		  });
	});


		