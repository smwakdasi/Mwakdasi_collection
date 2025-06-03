rules_version = '2';
service cloud.firestore {
match /databases/{database}/documents {

    // ========== USERS ==========
    match /users/{userId} {
      allow read, write: if request.auth.uid == userId;
    }

    // ========== CARTS ==========
    match /carts/{userId} {
      allow read, write: if request.auth.uid == userId;
    }

    // ========== ORDERS ==========
    match /orders/{orderId} {
      allow read, write: if request.auth.uid == resource.data.userId || request.auth.uid == request.resource.data.userId;
    }

    // ========== TRANSACTIONS ==========
    match /transactions/{transactionId} {
      allow read, write: if request.auth.uid == resource.data.userId || request.auth.uid == request.resource.data.userId;
    }

    // ========== REVIEWS ==========
    match /reviews/{reviewId} {
      allow read: if true;
      allow write: if request.auth.uid == request.resource.data.userId;
    }

    // ========== WISHLISTS ==========
    match /wishlists/{docId} {
      allow read, write: if request.auth.uid == resource.data.userId || request.auth.uid == request.resource.data.userId;
    }

    // ========== ADDRESSES ==========
    match /addresses/{addressId} {
      allow read, write: if request.auth.uid == resource.data.userId || request.auth.uid == request.resource.data.userId;
    }

    // ========== INVENTORY ==========
    match /inventory/{productId} {
      allow read: if true;
      allow write: if request.auth.token.admin == true;
    }

    // ========== NOTIFICATIONS ==========
    match /notifications/{notificationId} {
      allow read, write: if request.auth.uid == resource.data.userId || request.auth.uid == request.resource.data.userId;
    }

    // ========== PRODUCTS ==========
    match /products/{productId} {
      allow read: if true;
      allow write: if request.auth.token.admin == true;
    }

    // ========== CATEGORIES ==========
    match /category/{categoryId} {
      allow read: if true;
      allow write: if request.auth.token.admin == true;
    }

    // ========== ADMINS ==========
    match /admins/{adminId} {
      allow read, write: if request.auth.token.admin == true;
    }
}
}
