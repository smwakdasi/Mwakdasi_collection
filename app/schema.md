# Firestore Data Schema Draft

## Collection: users
- **uid**: string (user ID)
- **name**: string
- **email**: string
- **createdAt**: timestamp

## Collection: products
- **productId**: string
- **name**: string
- **price**: number
- **imageUrl**: string
- **stock**: number

## Collection: carts
- **userId**: string (reference to users)
- **items**: array of {
  productId: string  
  quantity: number  
  price: number *(price per unit at time of adding to cart)*  
  total: number *(price * quantity)*
  }
- **deliveryOption**: string *(either "pickup" or "delivery")*
- **deliveryFee**: number *(0 if pickup, a set fee if delivery)*
- **cartTotal**: number *(sum of item totals + deliveryFee)*

## category
- **categoryId**: string
- **name**: string
- **description**: string *(optional)*


## Transaction
- **transactionId**: string
- **orderId**: string
- **userId**: string
- **amount**: number
- **paymentMethod**: string *(e.g., `"mpesa"`, `"card"`)*
- **status**: string *(e.g., `"paid"`, `"failed"`, `"refunded"`)*
- **timestamp**: timestamp

## Order ( stores finalized purchase details)
- **orderId**: string
- **userId**: string
- **items**: array of:
  - **productId**: string
  - **quantity**: number
  - **price**: number
- **totalAmount**: number
- **status**: string *(e.g., `"pending"`, `"shipped"`, `"delivered"`)*
- **orderDate**: timestamp

## Review 
- **reviewId**: string
- **productId**: string
- **userId**: string
- **rating**: number *(float, e.g., 4.5)*
- **comment**: string
- **timestamp**: timestamp

## wishlist
- **userId**: string
- **productId**: string

## address 
- **addressId**: string
- **userId**: string
- **street**: string
- **city**: string
- **zipCode**: string
- **isDefault**: boolean

## Inventory(manage item quantities, out-of-stock alerts)
- **productId**: string
- **stockLevel**: number
- **restockThreshold**: number *(optional: for low stock alerts)*
- **lastRestocked**: timestamp

## Notification(sending alerts: new arrivals)
- **notificationId**: string
- **userId**: string
- **message**: string
- **timestamp**: timestamp
- **isRead**: boolean

## Admin( backend management of products/orders)
- **adminId**: string
- **name**: string
- **email**: string