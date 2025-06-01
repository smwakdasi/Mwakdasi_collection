rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {

    // Users can read and write only their profile
    match /users/{userId} {
      allow read, write: if request.auth.uid == userId;

      // Cart items under each user
      match /cart/{productId} {
        allow read, write: if request.auth.uid == userId;
      }

      // Orders under each user
      match /orders/{orderId} {
        allow read, write: if request.auth.uid == userId;
      }
    }

    // Public products (read-only for users)
    match /products/{productId} {
      allow read: if true;
      allow write: if false; // Only admins should have write access
    }
  }
}

